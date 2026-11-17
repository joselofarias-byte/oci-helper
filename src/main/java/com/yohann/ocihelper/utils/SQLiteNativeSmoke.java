package com.yohann.ocihelper.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/** A real native-JDBC check, usable without starting Spring or connecting to OCI. */
public final class SQLiteNativeSmoke {
    private SQLiteNativeSmoke() {}
    public static void main(String[] args) throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table native_probe (value text not null)");
            statement.executeUpdate("insert into native_probe values ('SQLITE_NATIVE_OK')");
            try (ResultSet result = statement.executeQuery("select value, sqlite_version() from native_probe")) {
                if (!result.next() || !"SQLITE_NATIVE_OK".equals(result.getString(1))) {
                    throw new IllegalStateException("Falló la comprobación nativa de SQLite");
                }
                System.out.println(result.getString(1) + " version=" + result.getString(2)
                        + " arch=" + System.getProperty("os.arch"));
            }
        }
    }
}
