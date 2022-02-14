package com.dev.churchmanagementsystem.dao;

import com.dev.churchmanagementsystem.models.Member;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberDAO {
    // Table columns
    private static final String tableName = "member";
    private static final String idColumn = "id";
    private static final String nameColumn = "name";
    private static final String phoneColumn = "phone";
    private static final String emailColumn = "email";
    private static final String genderColumn = "gender";
    private static final String houseAddressColumn = "house_address";
    private static final String postAddressColumn = "post_address";
    private static final String hometownColumn = "hometown";
    private static final String regionColumn = "region";
    private static final String dobColumn = "dob";
    private static final String dateBaptizedColumn = "date_baptized";
    private static final String maritalStatusColumn = "marital_status";
    private static final String occupationColumn = "occupation";
    private static final String imageColumn = "image";
    private static final String fatherNameColumn = "father_name";
    private static final String motherNameColumn = "mother_name";
    private static final String emergencyContactNameColumn = "emergency_contact_name";
    private static final String emergencyContactPhoneColumn = "emergency_contact_phone";
    private static final String zoneColumn = "zone";

    private static final ObservableList<Member> members;

    static {
        members = FXCollections.observableArrayList();
        updateMembersFromDB();
    }

    public static ObservableList<Member> getMembers() {
        return FXCollections.unmodifiableObservableList(members);
    }

    private static void updateMembersFromDB() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            members.clear();
            while (rs.next()) {
                members.add(new Member(
                        rs.getInt(idColumn),
                        rs.getString(nameColumn),
                        rs.getString(phoneColumn),
                        rs.getString(emailColumn),
                        rs.getString(genderColumn),
                        rs.getString(houseAddressColumn),
                        rs.getString(postAddressColumn),
                        rs.getString(hometownColumn),
                        rs.getString(regionColumn),
                        rs.getString(dobColumn),
                        rs.getString(dateBaptizedColumn),
                        rs.getString(maritalStatusColumn),
                        rs.getString(occupationColumn),
                        rs.getBytes(imageColumn),
                        rs.getString(fatherNameColumn),
                        rs.getString(motherNameColumn),
                        rs.getString(emergencyContactNameColumn),
                        rs.getString(emergencyContactPhoneColumn),
                        rs.getString(zoneColumn)
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Members from database ");
            members.clear();
        }
    }

    public static void insertMember(
            String name,
            String phone,
            String email,
            String gender,
            String houseAddress,
            String postAddress,
            String hometown,
            String region,
            String dob,
            String dateBaptized,
            String maritalStatus,
            String occupation,
            byte[] image,
            String fatherName,
            String motherName,
            String emergencyContactName,
            String emergencyContactPhone,
            String zone
    ) {
        // Insert into database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{
                        nameColumn,
                        phoneColumn,
                        emailColumn,
                        genderColumn,
                        houseAddressColumn,
                        postAddressColumn,
                        hometownColumn,
                        regionColumn,
                        dobColumn,
                        dateBaptizedColumn,
                        maritalStatusColumn,
                        occupationColumn,
                        imageColumn,
                        fatherNameColumn,
                        motherNameColumn,
                        emergencyContactNameColumn,
                        emergencyContactPhoneColumn,
                        zoneColumn
                },
                new Object[]{
                        name,
                        phone,
                        email,
                        gender,
                        houseAddress,
                        postAddress,
                        hometown,
                        region,
                        dob,
                        dateBaptized,
                        maritalStatus,
                        occupation,
                        image,
                        fatherName,
                        motherName,
                        emergencyContactName,
                        emergencyContactPhone,
                        zone
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.BLOB,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR
                }
        );

        // Update cache
        members.add(
                new Member(
                        id,
                        name,
                        phone,
                        email,
                        gender,
                        houseAddress,
                        postAddress,
                        hometown,
                        region,
                        dob,
                        dateBaptized,
                        maritalStatus,
                        occupation,
                        image,
                        fatherName,
                        motherName,
                        emergencyContactName,
                        emergencyContactPhone,
                        zone
                ));
    }

    public static void update(Member newMember) {
        // Update database
        int rows = CRUDHelper.update(
                tableName,
                new String[]{
                        nameColumn,
                        phoneColumn,
                        emailColumn,
                        houseAddressColumn,
                        postAddressColumn,
                        hometownColumn,
                        regionColumn,
                        dobColumn,
                        dateBaptizedColumn,
                        maritalStatusColumn,
                        occupationColumn,
                        imageColumn,
                        fatherNameColumn,
                        motherNameColumn,
                        emergencyContactNameColumn,
                        emergencyContactPhoneColumn,
                        zoneColumn
                },
                new Object[]{newMember.getName(),
                        newMember.getPhone(),
                        newMember.getEmail(),
                        newMember.getGender(),
                        newMember.getHouseAddress(),
                        newMember.getPostAddress(),
                        newMember.getHometown(),
                        newMember.getRegion(),
                        newMember.getDob(),
                        newMember.getDateBaptized(),
                        newMember.getMaritalStatus(),
                        newMember.getOccupation(),
                        newMember.getImage(),
                        newMember.getFatherName(),
                        newMember.getMotherName(),
                        newMember.getEmergencyContactName(),
                        newMember.getEmergencyContactPhone(),
                        newMember.getZone()
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.BLOB,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR
                },
                idColumn,
                Types.INTEGER,
                newMember.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Member to be updated with id " + newMember.getId() + " doesn't exist in database");

        // Update cache
        Optional<Member> optionalMember = getMember(newMember.getId());
        optionalMember.ifPresentOrElse((oldMember) -> {
            members.remove(oldMember);
            members.add(newMember);
        }, () -> {
            throw new IllegalStateException("Member to be updated with id " + newMember.getId() + " doesn't exist in database");
        });
    }

    public static void delete(int id) {
        // Delete from database
        CRUDHelper.delete(tableName, id);

        // Update cache
        Optional<Member> member = getMember(id);
        member.ifPresent(members::remove);
    }

    private static Optional<Member> getMember(int id) {
        for (Member member : members) {
            if (member.getId() == id) return Optional.of(member);
        }
        return Optional.empty();
    }
}
