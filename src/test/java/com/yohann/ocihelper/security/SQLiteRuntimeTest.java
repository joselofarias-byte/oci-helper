package com.yohann.ocihelper.security;

import com.yohann.ocihelper.utils.SQLiteNativeSmoke;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class SQLiteRuntimeTest {
    @TempDir Path directory;

    @Test void loadsRealNativeDriverAndExecutesSql() {
        assertDoesNotThrow(() -> SQLiteNativeSmoke.main(new String[0]));
    }
    @Test void persistsAndReopensDatabase() throws Exception {
        String url = "jdbc:sqlite:" + directory.resolve("probe.db");
        try (Connection c = DriverManager.getConnection(url); Statement s = c.createStatement()) {
            s.executeUpdate("create table probe(value text)");
            s.executeUpdate("insert into probe values('persistido')");
        }
        try (Connection c = DriverManager.getConnection(url); Statement s = c.createStatement();
             ResultSet r = s.executeQuery("select value from probe")) {
            assertTrue(r.next());assertEquals("persistido",r.getString(1));
        }
    }
    @Test void driverContainsBothLinuxArchitectures() throws Exception {
        for (String arch : new String[]{"aarch64", "x86_64"}) {
            try (var stream = org.sqlite.JDBC.class.getResourceAsStream("/org/sqlite/native/Linux/" + arch + "/libsqlitejdbc.so")) {
                assertNotNull(stream,arch);assertTrue(stream.readAllBytes().length > 100000,arch);
            }
        }
    }
}
