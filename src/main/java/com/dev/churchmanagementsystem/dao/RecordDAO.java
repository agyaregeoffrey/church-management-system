package com.dev.churchmanagementsystem.dao;

import com.dev.churchmanagementsystem.models.Record;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordDAO {
    public static final String tableName = "records";
    public static final String idColumn = "id";
    public static final String dateColumn = "date";
    public static final String malesColumn = "males";
    public static final String femalesColumn = "females";
    public static final String childrenColumn = "children";
    public static final String visitorsColumn = "visitors";
    public static final String totalAttendance = "total_attendance";
    public static final String givingColumn = "giving";
    public static final String thanksgivingColumn = "thanksgiving";
    public static final String totalGivingColumn = "total_giving";

    public static final ObservableList<Record> records;

    static {
        records = FXCollections.observableArrayList();
        updateRecordsFromDB();
    }

    public static ObservableList<Record> getRecords() {
        return FXCollections.unmodifiableObservableList(records);
    }

    private static void updateRecordsFromDB() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            records.clear();
            while (rs.next()) {
                records.add(new Record(
                        rs.getInt(idColumn),
                        rs.getString(dateColumn),
                        rs.getInt(malesColumn),
                        rs.getInt(femalesColumn),
                        rs.getInt(childrenColumn),
                        rs.getInt(visitorsColumn),
                        rs.getInt(totalAttendance),
                        rs.getDouble(givingColumn),
                        rs.getDouble(thanksgivingColumn),
                        rs.getDouble(totalGivingColumn)));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Records from database ");
            records.clear();
        }
    }

    public static void insertRecord(String date, int male, int female, int child, int visitor, int totalAttendance, double giving, double thanksgiving, double totalGiving) {
        // Insert into database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{
                        "date",
                        "males",
                        "females",
                        "children",
                        "visitors",
                        "giving",
                        "thanksgiving",
                        "total_attendance",
                        "total_giving"
                },
                new Object[]{
                        date,
                        male,
                        female,
                        child,
                        visitor,
                        giving,
                        thanksgiving,
                        totalAttendance,
                        totalGiving
                },
                new int[]{
                        Types.VARCHAR,
                        Types.INTEGER,
                        Types.INTEGER,
                        Types.INTEGER,
                        Types.INTEGER,
                        Types.REAL,
                        Types.REAL,
                        Types.INTEGER,
                        Types.REAL
                }
        );


        // Update cache
        records.add(new Record(id, date, male, female, child, visitor, totalAttendance, giving, thanksgiving, totalGiving));
    }

    public static void update(Record newRecord) {
        // Update database
        int rows = CRUDHelper.update(
                tableName,
                new String[]{dateColumn, malesColumn, femalesColumn, childrenColumn, visitorsColumn, givingColumn, thanksgivingColumn, totalAttendance, totalGivingColumn},
                new Object[]{newRecord.getDate(), newRecord.getMale(), newRecord.getFemale(), newRecord.getChild(), newRecord.getVisitor(), newRecord.getGiving(), newRecord.getThanksgiving(), newRecord.getTotalAttendance(), newRecord.getTotalGiving()},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.REAL, Types.REAL, Types.INTEGER, Types.REAL},
                idColumn,
                Types.INTEGER,
                newRecord.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Record to be updated with id " + newRecord.getId() + " doesn't exist in database");

        //update cache
        Optional<Record> optionalRecord = getRecord(newRecord.getId());
        optionalRecord.ifPresentOrElse((oldRecord) -> {
            records.remove(oldRecord);
            records.add(newRecord);
        }, () -> {
            throw new IllegalStateException("Record to be updated with id " + newRecord.getId() + " doesn't exist in database");
        });

    }

    public static void delete(int id) {
        //update database
        CRUDHelper.delete(tableName, id);

        //update cache
        Optional<Record> record = getRecord(id);
        record.ifPresent(records::remove);
    }

    public static Optional<Record> getRecord(int id) {
        for (Record record : records) {
            if (record.getId() == id) return Optional.of(record);
        }
        return Optional.empty();
    }
}
