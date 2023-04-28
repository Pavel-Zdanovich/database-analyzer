package com.example.databaseanalyzer.service;

import com.example.databaseanalyzer.dto.Corrections;
import com.example.databaseanalyzer.dto.Credentials;
import com.example.databaseanalyzer.model.*;
import com.example.databaseanalyzer.web.AnalyzerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class Analyzer {

    private static final String TABLE_CAT = "TABLE_CAT";
    private static final String TABLE_SCHEM = "TABLE_SCHEM";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String TYPE_NAME = "TYPE_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";

    public Statistics analyze(Credentials credentials) {
        Statistics statistics = new Statistics();
        Set<Map<String, Object>> catalogs;
        Set<Map<String, Object>> schemas;
        Set<Map<String, Object>> tables;
        Set<Map<String, Object>> columns;
        try (Connection connection = DriverManager.getConnection(
                credentials.getUrl(),
                credentials.getUsername(),
                credentials.getPassword()
        )) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            statistics.setName(databaseMetaData.getDatabaseProductName());
            statistics.setVersion(databaseMetaData.getDatabaseProductVersion());
            catalogs = this.any(databaseMetaData.getCatalogs());
            schemas = this.any(databaseMetaData.getSchemas());
            tables = this.any(databaseMetaData.getTables(null, null, null, null));
            columns = this.any(databaseMetaData.getColumns(null, null, null, null));
        } catch (SQLException e) {
            String s = "Database connection error!";
            log.error(s, e);
            throw new AnalyzerException(s, e);
        } catch (ClassNotFoundException e) {
            String s = "Unexpected data type!";
            log.error(s, e);
            throw new AnalyzerException(s, e);
        }

        //unify
        if (catalogs.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put(TABLE_CAT, "null");
            catalogs.add(map);
        } else {
            for (Map<String, Object> map : catalogs) {
                if (map.containsKey("CATALOG_NAME")) {
                    map.put(TABLE_CAT, map.get("CATALOG_NAME"));
                }
            }
        }
        if (schemas.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put(TABLE_CAT, "null");
            map.put(TABLE_SCHEM, "null");
            schemas.add(map);
        } else {
            for (Map<String, Object> map : schemas) {
                if (map.containsKey("TABLE_CATALOG")) {
                    map.put(TABLE_CAT, map.get("TABLE_CATALOG"));
                }
            }
        }

        this.calculate(statistics, catalogs, schemas, tables, columns);

        return statistics;
    }

    private Map<String, Class<?>> meta(ResultSetMetaData resultSetMetaData) throws SQLException, ClassNotFoundException {
        Map<String, Class<?>> map = new HashMap<>();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            String name = resultSetMetaData.getColumnName(i);
            String className = resultSetMetaData.getColumnClassName(i);
            Class<?> clazz = Class.forName(className);
            map.put(name, clazz);
        }
        return map;
    }

    private Set<Map<String, Object>> any(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        Set<Map<String, Object>> result = new HashSet<>();
        Map<String, Class<?>> meta = this.meta(resultSet.getMetaData());
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Class<?>> entry : meta.entrySet()) {
                Object object = resultSet.getObject(entry.getKey(), entry.getValue());
                map.put(entry.getKey(), object);
            }
            result.add(map);
        }
        return result;
    }

    private void calculate(
            Statistics statistics,
            Set<Map<String, Object>> databases,
            Set<Map<String, Object>> schemas,
            Set<Map<String, Object>> tables,
            Set<Map<String, Object>> columns
    ) {
        for (Map<String, Object> databaseMap : databases) {
            this.database(databaseMap.get(TABLE_CAT), statistics);
        }
        for (Map<String, Object> schemaMap : schemas) {
            Object databaseName = schemaMap.get(TABLE_CAT);
            Database database = statistics.get(databaseName);
            if (database == null) {
                database = this.database(databaseName, statistics);
            }
            this.schema(schemaMap.get(TABLE_SCHEM), database);
        }
        for (Map<String, Object> tableMap : tables) {
            Object databaseName = tableMap.get(TABLE_CAT);
            Database database = statistics.get(databaseName);
            if (database == null) {
                database = this.database(databaseName, statistics);
            }
            Object schemaName = tableMap.get(TABLE_CAT);
            Schema schema = database.get(databaseName);
            if (schema == null) {
                schema = this.schema(schemaName, database);
            }
            this.table(tableMap.get(TABLE_NAME), schema);
        }
        for (Map<String, Object> columnMap : columns) {
            Object databaseName = columnMap.get(TABLE_CAT);
            Database database = statistics.get(databaseName);
            if (database == null) {
                database = this.database(databaseName, statistics);
            }
            Object schemaName = columnMap.get(TABLE_CAT);
            Schema schema = database.get(databaseName);
            if (schema == null) {
                schema = this.schema(schemaName, database);
            }
            Object tableName = columnMap.get(TABLE_NAME);
            Table table = schema.get(tableName);
            if (table == null) {
                table = this.table(tableName, schema);
            }

            Object columnName = columnMap.get(COLUMN_NAME);
            String columnNameString = columnName == null ? "null" : (String) columnName;
            Column column = new Column();
            column.setName(columnNameString);
            column.setType(columnMap.get(TYPE_NAME).toString());
            column.setSize(columnMap.get(COLUMN_SIZE) == null ? null : (Integer) columnMap.get(COLUMN_SIZE));
            table.put(columnNameString, column);
        }
    }

    private Database database(Object object, Statistics statistics) {
        String name = object == null ? "null" : (String) object;
        Database database = new Database();
        database.setName(name);
        statistics.put(name, database);
        return database;
    }

    private Schema schema(Object object, Database database) {
        String name = object == null ? "null" : (String) object;
        Schema schema = new Schema();
        schema.setName(name);
        database.put(name, schema);
        return schema;
    }

    private Table table(Object object, Schema schema) {
        String name = object == null ? "null" : (String) object;
        Table table = new Table();
        table.setName(name);
        schema.put(name, table);
        return table;
    }

    public boolean capitalize(Corrections corrections) {
        try (Connection connection = DriverManager.getConnection(
                corrections.getUrl(),
                corrections.getUsername(),
                corrections.getPassword()
        )) {
            Statement statement = connection.createStatement();
            if (corrections.getDatabase() != null) {
                String sql = this.renameDatabase(corrections.getDatabase(), corrections.getDatabase().toUpperCase());
                statement.execute(sql);
            }
            if (corrections.getSchema() != null) {
                String sql = this.renameSchema(corrections.getSchema(), corrections.getSchema().toUpperCase());
                statement.execute(sql);
            }
            if (corrections.getTable() != null) {
                String sql = this.renameTable(corrections.getTable(), corrections.getTable().toUpperCase());
                statement.execute(sql);
            }
            if (corrections.getTable() != null && corrections.getColumn() != null) {
                String sql = this.renameColumn(corrections.getTable(), corrections.getColumn(), corrections.getColumn().toUpperCase());
                statement.execute(sql);
            }
            return true;
        } catch (SQLException e) {
            String s = "Database connection error!";
            log.error(s, e);
            throw new AnalyzerException(s, e);
        }
    }

    private String renameDatabase(String oldDatabaseName, String newDatabaseName) {
        return String.format("ALTER DATABASE '%s' MODIFY NAME = '%s';", oldDatabaseName, newDatabaseName);
    }

    private String renameSchema(String oldSchemaName, String newSchemaName) {
        return String.format("ALTER SCHEMA '%s' RENAME TO '%s';", oldSchemaName, newSchemaName);
    }

    private String renameTable(String olTableName, String newTableName) {
        return String.format("ALTER TABLE '%s' RENAME '%s';", olTableName, newTableName);
    }

    private String renameColumn(String tableName, String oldColumnName, String newColumnName) {
        return String.format("ALTER TABLE '%s' RENAME COLUMN '%s' TO '%s';", tableName, oldColumnName, newColumnName);
    }
}
