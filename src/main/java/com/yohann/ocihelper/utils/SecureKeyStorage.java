package com.yohann.ocihelper.utils;

import com.yohann.ocihelper.exception.OciException;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;

/** Stores API keys under server-generated names, without trusting upload paths. */
public final class SecureKeyStorage {
    private static final int MAX_SIZE = 65536;
    private SecureKeyStorage() {}

    public static String save(String directory, MultipartFile upload) {
        Path target = null;
        try {
            String name = upload == null ? null : upload.getOriginalFilename();
            if (name == null || name.isBlank() || name.contains("/") || name.contains("\\")
                    || name.contains("\0") || upload.isEmpty() || upload.getSize() > MAX_SIZE) {
                throw new OciException(-1, "Archivo de clave privada no válido (máximo 64 KiB)");
            }
            byte[] bytes;
            try (InputStream in = upload.getInputStream()) {
                bytes = in.readNBytes(MAX_SIZE + 1);
            }
            String pem = new String(bytes, StandardCharsets.US_ASCII);
            if (bytes.length > MAX_SIZE || !(pem.contains("-----BEGIN PRIVATE KEY-----")
                    || pem.contains("-----BEGIN RSA PRIVATE KEY-----")
                    || pem.contains("-----BEGIN EC PRIVATE KEY-----"))) {
                throw new OciException(-1, "El archivo no contiene una clave privada PEM válida");
            }
            Path root = Path.of(directory).toAbsolutePath().normalize();
            Files.createDirectories(root);
            if (Files.isSymbolicLink(root)) throw new OciException(-1, "El directorio de claves no puede ser un enlace");
            try {
                Files.setPosixFilePermissions(root, PosixFilePermissions.fromString("rwx------"));
                target = Files.createTempFile(root, "oci-", ".pem",
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------")));
            } catch (UnsupportedOperationException e) {
                target = Files.createTempFile(root, "oci-", ".pem");
            }
            Files.write(target, bytes);
            return target.toString();
        } catch (Exception e) {
            if (target != null) try { Files.deleteIfExists(target); } catch (Exception ignored) {}
            if (e instanceof OciException oe) throw oe;
            throw new OciException(-1, "No se pudo guardar la clave privada");
        }
    }
}
