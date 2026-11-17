package com.yohann.ocihelper.security;
import com.yohann.ocihelper.utils.SecureKeyStorage;
import com.yohann.ocihelper.exception.OciException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;
import static org.junit.jupiter.api.Assertions.*;

class KeyStorageSecurityTest {
    @TempDir Path root;
    private final byte[] pem="-----BEGIN PRIVATE KEY-----\nTEST\n-----END PRIVATE KEY-----".getBytes();
    private MockMultipartFile file(String name,byte[] bytes){return new MockMultipartFile("file",name,"application/octet-stream",bytes);}
    @Test void storesUniqueFilesWithoutOverwritingExistingKey() throws Exception {
        Path a=Path.of(SecureKeyStorage.save(root.toString(),file("same.pem",pem)));
        Path b=Path.of(SecureKeyStorage.save(root.toString(),file("same.pem",pem)));
        assertNotEquals(a,b);assertEquals(root,a.getParent());assertArrayEquals(pem,Files.readAllBytes(a));
    }
    @Test void rejectsUnixTraversal() { assertThrows(OciException.class,()->SecureKeyStorage.save(root.toString(),file("../../application.yml",pem))); }
    @Test void rejectsWindowsTraversal() { assertThrows(OciException.class,()->SecureKeyStorage.save(root.toString(),file("..\\application.yml",pem))); }
    @Test void rejectsEmptyFile() { assertThrows(OciException.class,()->SecureKeyStorage.save(root.toString(),file("a.pem",new byte[0]))); }
    @Test void rejectsOversizeFile() { assertThrows(OciException.class,()->SecureKeyStorage.save(root.toString(),file("a.pem",new byte[65537]))); }
    @Test void rejectsNonPem() { assertThrows(OciException.class,()->SecureKeyStorage.save(root.toString(),file("a.pem","not a key".getBytes()))); }
    @Test void setsPrivateFilePermissions() throws Exception {
        Path p=Path.of(SecureKeyStorage.save(root.toString(),file("a.pem",pem)));
        assertEquals(PosixFilePermissions.fromString("rw-------"),Files.getPosixFilePermissions(p));
    }
    @Test void rejectsSymlinkDirectory() throws Exception {
        Path link=root.resolve("alias");Files.createSymbolicLink(link,root);
        assertThrows(OciException.class,()->SecureKeyStorage.save(link.toString(),file("a.pem",pem)));
    }
}
