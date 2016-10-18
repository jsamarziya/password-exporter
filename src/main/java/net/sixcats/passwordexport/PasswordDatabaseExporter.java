package net.sixcats.passwordexport;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PasswordDatabaseExporter {
    private final String dbFile;
    private final String dbPassword;

    public PasswordDatabaseExporter(String dbFile, String dbPassword) {
        this.dbFile = dbFile;
        this.dbPassword = dbPassword;
    }

    public JSONObject export() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        final JSONObject exportedData;
        try (final Connection connection = createConnection()) {
            exportedData = new PasswordDataConverter(connection).getPasswordData();
        }
        return exportedData;
    }

    private String createDatabaseUrl() {
        return "jdbc:sqlite:" + dbFile;
    }

    private Properties createConnectionProperties() {
        final Properties properties = new Properties();
        properties.put("key", dbPassword);
        return properties;
    }

    private Connection createConnection() throws SQLException {
        final String url = createDatabaseUrl();
        final Properties properties = createConnectionProperties();
        return DriverManager.getConnection(url, properties);
    }
}
