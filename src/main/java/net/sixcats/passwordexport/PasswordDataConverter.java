package net.sixcats.passwordexport;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Converts password data into JSON.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
class PasswordDataConverter {
    private final Connection connection;

    public PasswordDataConverter(Connection connection) {
        this.connection = connection;
    }

    public JSONObject getPasswordData() throws SQLException {
        final JSONObject passwordData = new OrderedJSONObject();
        for (Map.Entry<String, String> category : getCategories().entrySet()) {
            passwordData.put(category.getKey(), getEntries(category.getValue()));
        }
        return passwordData;
    }

    /**
     * Returns a map containing category ids mapped by category name.
     *
     * @return the categories
     */
    private Map<String, String> getCategories() throws SQLException {
        final Map<String, String> categories = new LinkedHashMap<>();
        final String query = "SELECT name, id FROM categories ORDER BY name COLLATE NOCASE";
        try (final PreparedStatement statement = connection.prepareStatement(query);
             final ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                categories.put(resultSet.getString(1), resultSet.getString(2));
            }
        }
        return categories;
    }

    private JSONArray getEntries(String categoryId) throws SQLException {
        final JSONArray entries = new JSONArray();
        try (final PreparedStatement statement = createGetEntriesStatement(categoryId);
             final ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                entries.put(getEntry(resultSet.getString(1), resultSet.getString(2)));
            }
        }
        return entries;
    }

    private PreparedStatement createGetEntriesStatement(String categoryId) throws SQLException {
        final String query = "SELECT name, id FROM entries WHERE category_id=? ORDER BY name COLLATE NOCASE";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, categoryId);
        return statement;
    }

    private JSONObject getEntry(String name, String id) throws SQLException {
        final JSONObject entry = new JSONObject();
        entry.put(name, getFields(id));
        return entry;
    }

    private JSONArray getFields(String entityId) throws SQLException {
        final JSONArray fields = new JSONArray();
        try (final PreparedStatement statement = createGetFieldsQuery(entityId);
             final ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                final JSONObject field = new JSONObject();
                field.put(resultSet.getString(1), resultSet.getString(2));
                fields.put(field);
            }
        }
        return fields;
    }

    private PreparedStatement createGetFieldsQuery(String entityId) throws SQLException {
        final String query = "SELECT types.name, fields.value FROM fields, types WHERE fields.entry_id=? AND fields.type_id=types.id ORDER BY fields.idx";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entityId);
        return statement;
    }
}