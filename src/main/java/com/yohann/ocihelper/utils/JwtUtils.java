package com.yohann.ocihelper.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** HS256 tokens with strict JSON parsing, required expiry and constant-time MAC comparison. */
public final class JwtUtils {
    private static final ObjectMapper JSON = new ObjectMapper().enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();
    private JwtUtils() {}

    public static String genToken(Map<String, Object> payload, String secretKey) {
        try {
            Map<String, Object> values = new HashMap<>(payload);
            values.replaceAll((k,v) -> v instanceof Date d ? d.getTime()/1000 : v);
            String header = ENCODER.encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
            String body = ENCODER.encodeToString(JSON.writeValueAsBytes(values));
            String input = header + "." + body;
            return input + "." + ENCODER.encodeToString(sign(input, secretKey));
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo generar el token", e);
        }
    }

    public static String genToken(Map<String, Object> payload, String secretKey, int hours) {
        Map<String, Object> values = new HashMap<>(payload);
        values.put("exp", Instant.now().plusSeconds(hours * 3600L).getEpochSecond());
        return genToken(values, secretKey);
    }

    public static boolean verifyToken(String token, String secretKey) {
        try {
            String[] parts = parts(token);
            JsonNode header = JSON.readTree(DECODER.decode(parts[0]));
            if (!"HS256".equals(header.path("alg").asText())) return false;
            if (!MessageDigest.isEqual(sign(parts[0]+"."+parts[1],secretKey),DECODER.decode(parts[2]))) return false;
            return !isTokenExpired(token);
        } catch (Exception e) { return false; }
    }

    public static boolean isTokenExpired(String token) {
        try {
            JsonNode exp = JSON.readTree(DECODER.decode(parts(token)[1])).path("exp");
            return !exp.isIntegralNumber() || !exp.canConvertToLong() || exp.longValue() <= Instant.now().getEpochSecond();
        } catch (Exception e) { return true; }
    }

    /** Only consume claims after verifyToken has succeeded. */
    public static String getPayloadClaim(String token, String key) {
        try {
            JsonNode claim = JSON.readTree(DECODER.decode(parts(token)[1])).get(key);
            return claim == null || claim.isNull() ? null : claim.asText();
        } catch (Exception e) { return null; }
    }

    private static String[] parts(String token) {
        if (token == null || token.length() > 8192) throw new IllegalArgumentException("Token no válido");
        String[] parts = token.split("\\.", -1);
        if (parts.length != 3 || parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) throw new IllegalArgumentException("Token no válido");
        return parts;
    }

    private static byte[] sign(String input, String key) throws Exception {
        if (key == null || key.length() < 32) throw new IllegalArgumentException("Clave JWT insuficiente");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
    }
}
