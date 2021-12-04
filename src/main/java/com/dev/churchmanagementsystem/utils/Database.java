package com.dev.churchmanagementsystem.utils;

import com.dev.churchmanagementsystem.MainApplication;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Location of database
     */
    private static final String location = Objects.requireNonNull(
            MainApplication.class.getResource("database/database.db"))
            .toExternalForm();

    /**
     * List of tables
     */
    private static final String recordsTable = "records";
    private static final String attendanceTable = "attendance";

    public static boolean isOK() {
        if (!checkDrivers()) return false; // driver errors

        if (!checkConnection()) return false; // can't connect to db

        return checkTables(recordsTable) && checkTables(attendanceTable); // tables don't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean checkTables(String tableName) {
        String checkTables =
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'";

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(checkTables);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("tbl_name").equals(tableName)) return true;
            }
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not find tables in database");
            return false;
        }
        return false;
    }

    public static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }
}
