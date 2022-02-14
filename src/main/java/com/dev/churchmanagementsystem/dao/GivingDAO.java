package com.dev.churchmanagementsystem.dao;

import com.dev.churchmanagementsystem.models.Giving;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GivingDAO {
    public static final String tableName = "records";
    public static final String idColumn = "id";
    public static final String dateColumn = "date";
    public static final String givingColumn = "giving";
    public static final String thanksgivingColumn = "thanksgiving";
    public static final String totalGivingColumn = "total_giving";

    private static final ObservableList<Giving> givings;

    static {
        givings = FXCollections.observableArrayList();
        getGivingsFromDB();
    }

    public static ObservableList<Giving> getGivings() {
        return FXCollections.unmodifiableObservableList(givings);
    }

    private static void getGivingsFromDB() {
        String query = "SELECT id, date, giving, thanksgiving, total_giving FROM " + tableName;
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            givings.clear();
            while (rs.next()) {
                givings.add(new Giving(
                        rs.getInt(idColumn),
                        rs.getString(dateColumn),
                        rs.getDouble(givingColumn),
                        rs.getDouble(thanksgivingColumn),
                        rs.getDouble(totalGivingColumn)
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Givings from database ");
            givings.clear();
        }
    }

    private static void updateGivingsFromDB() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            givings.clear();
            while (rs.next()) {
                givings.add(new Giving(
                        rs.getInt(idColumn),
                        rs.getString(dateColumn),
                        rs.getDouble(givingColumn),
                        rs.getDouble(thanksgivingColumn),
                        rs.getDouble(totalGivingColumn)
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Givings from database ");
            givings.clear();
        }
    }

    public static void insertGiving(String date, double giving, double thanksgiving, double total) {
        // Insert into database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{dateColumn, givingColumn, thanksgivingColumn, totalGivingColumn},
                new Object[]{date, giving, thanksgiving, total},
                new int[]{Types.VARCHAR, Types.REAL, Types.REAL, Types.REAL}
        );

        // Update cache
        givings.add(new Giving(id, date, giving, thanksgiving, total));
    }

    public static void update(Giving newGiving) {
        // Update database
        int rows = CRUDHelper.update(
                tableName,
                new String[]{dateColumn, givingColumn, thanksgivingColumn, totalGivingColumn},
                new Object[]{newGiving.getDate(), newGiving.getGiving(), newGiving.getThanksgiving(), newGiving.getTotal()},
                new int[]{Types.VARCHAR, Types.REAL, Types.REAL, Types.REAL},
                idColumn,
                Types.INTEGER,
                newGiving.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Giving to be updated with id " + newGiving.getId() + " doesn't exist in database");

        // Update cache
        Optional<Giving> optionalGiving = getGiving(newGiving.getId());
        optionalGiving.ifPresentOrElse((oldGiving) -> {
            givings.remove(oldGiving);
            givings.add(newGiving);
        }, () -> {
            throw new IllegalStateException("Giving to be updated with id " + newGiving.getId() + " doesn't exist in database");
        });
    }

    public static void delete(int id) {
        // Delete from database
        CRUDHelper.delete(tableName, id);

        // Update cache
        Optional<Giving> giving = getGiving(id);
        giving.ifPresent(givings::remove);
    }

    public static Optional<Giving> getGiving(int id) {
        for (Giving giving : givings) {
            if (giving.getId() == id) return Optional.of(giving);
        }
        return Optional.empty();
    }
}
