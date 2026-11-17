package com.yohann.ocihelper.security;
import com.yohann.ocihelper.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtSecurityTest {
    private static final String KEY = "test-signing-key-with-at-least-32-characters";
    private String token(Map<String,Object> p) { return JwtUtils.genToken(p,KEY); }
    @Test void acceptsValidToken() { assertTrue(JwtUtils.verifyToken(JwtUtils.genToken(Map.of("account","jolufa"),KEY,3),KEY)); }
    @Test void rejectsWrongKey() { assertFalse(JwtUtils.verifyToken(JwtUtils.genToken(Map.of(),KEY,3),KEY+"x")); }
    @Test void rejectsExpiredToken() { assertFalse(JwtUtils.verifyToken(token(Map.of("exp",Instant.now().minusSeconds(1).getEpochSecond())),KEY)); }
    @Test void rejectsExpiryNow() { assertTrue(JwtUtils.isTokenExpired(token(Map.of("exp",Instant.now().getEpochSecond())))); }
    @Test void rejectsMissingExpiry() { assertFalse(JwtUtils.verifyToken(token(Map.of("account","a")),KEY)); }
    @Test void rejectsStringExpiry() { assertFalse(JwtUtils.verifyToken(token(Map.of("exp","9999999999")),KEY)); }
    @Test void rejectsNoneAlgorithm() {
        String header=Base64.getUrlEncoder().withoutPadding().encodeToString("{\"alg\":\"none\"}".getBytes());
        assertFalse(JwtUtils.verifyToken(header+".e30.signature",KEY));
    }
    @Test void rejectsTamperedPayload() {
        String t=JwtUtils.genToken(Map.of("account","a"),KEY,3);
        String[] parts=t.split("\\.");parts[1]=Base64.getUrlEncoder().withoutPadding().encodeToString("{\"exp\":9999999999}".getBytes());
        assertFalse(JwtUtils.verifyToken(String.join(".",parts),KEY));
    }
    @Test void rejectsMalformedTokens() { for(String t:List.of("","a.b.c","a.b.","a.b.c.d","x".repeat(9000))) assertFalse(JwtUtils.verifyToken(t,KEY));assertFalse(JwtUtils.verifyToken(null,KEY)); }
    @Test void rejectsShortSigningKey() { assertThrows(IllegalArgumentException.class,()->JwtUtils.genToken(Map.of(),"weak",3)); }
    @Test void preservesEscapedClaimsWithoutMutatingCaller() {
        Map<String,Object> p=Map.of("account","José \"Luis\"\nFarías");String t=JwtUtils.genToken(p,KEY,3);
        assertEquals(p.get("account"),JwtUtils.getPayloadClaim(t,"account"));assertFalse(p.containsKey("exp"));
    }
}
