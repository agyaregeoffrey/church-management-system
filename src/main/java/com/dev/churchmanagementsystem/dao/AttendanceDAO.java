package com.dev.churchmanagementsystem.dao;

import com.dev.churchmanagementsystem.models.Attendance;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceDAO {
    public static final String tableName = "attendance";
    public static final String idColumn = "id";
    public static final String nameColumn = "name";
    public static final String dateColumn = "date";
    public static final String attendedColumn = "attended";
    public static final String genderColumn = "gender";

    private static final ObservableList<Attendance> attendances;

    static {
        attendances = FXCollections.observableArrayList();
        updateAttendancesFromDB();
    }

    public static ObservableList<Attendance> getAttendances() {
        return FXCollections.unmodifiableObservableList(attendances);
    }

    private static void updateAttendancesFromDB() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            attendances.clear();
            while (rs.next()) {
                attendances.add(new Attendance(
                        rs.getInt(idColumn),
                        rs.getString(nameColumn),
                        rs.getString(dateColumn),
                        rs.getString(attendedColumn),
                        rs.getString(genderColumn)
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Attendances from database ");
            attendances.clear();
        }
    }

    public static void insertAttendance(String name, String date, String attended, String gender) {
        // Insert into database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{nameColumn, dateColumn, attendedColumn, genderColumn},
                new Object[]{name, date, attended, gender},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}
        );

        // Update cache
        attendances.add(new Attendance(id, name, date, attended, gender));
    }

    public static void update(Attendance newAttendance) {
        // Update database
        int rows = CRUDHelper.update(
                tableName,
                new String[]{nameColumn, dateColumn, attendedColumn, genderColumn},
                new Object[]{
                        newAttendance.getName(),
                        newAttendance.getDate(),
                        newAttendance.getStatus(),
                        newAttendance.getGender()
                },
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR},
                idColumn,
                Types.INTEGER,
                newAttendance.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Attendance to be updated with id " + newAttendance.getId() + " doesn't exist in database");

        // Update cache
        Optional<Attendance> optionalAttendance = getAttendance(newAttendance.getId());
        optionalAttendance.ifPresentOrElse((oldAttendance) -> {
            attendances.remove(oldAttendance);
            attendances.add(newAttendance);
        }, () -> {
            throw new IllegalStateException("Attedance to be updated with id " + newAttendance.getId() + " doesn't exist in database");
        });
    }

    public static void delete(int id) {
        // Delete from database
        CRUDHelper.delete(tableName, id);

        // Update cache
        Optional<Attendance> attendance = getAttendance(id);
        attendance.ifPresent(attendances::remove);
    }

    private static Optional<Attendance> getAttendance(int id) {
        for (Attendance attendance : attendances) {
            if (attendance.getId() == id) return Optional.of(attendance);
        }
        return Optional.empty();
    }
}
