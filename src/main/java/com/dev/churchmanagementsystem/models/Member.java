package com.dev.churchmanagementsystem.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Member {
    /**
     * BASIC INFORMATION
     * name
     * phone
     * email
     * houseAddress
     * postAddress
     * region
     * dob
     * baptized
     * maritalStatus
     * occupation
     * zone
     * image
     *
     * OTHER INFORMATION
     * fatherName
     * motherName
     * emergencyContactName
     * emergencyContactPhone
     */

    // BASIC INFO
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty phone = new SimpleStringProperty(this, "phone", "");
    private final StringProperty email = new SimpleStringProperty(this, "email", "");
    private final StringProperty gender = new SimpleStringProperty(this, "gender", "");
    private final StringProperty houseAddress = new SimpleStringProperty(this, "house address", "");
    private final StringProperty postAddress = new SimpleStringProperty(this, "post address", "");
    private final StringProperty hometown = new SimpleStringProperty(this, "hometown", "");
    private final StringProperty region = new SimpleStringProperty(this, "region", "");
    private final StringProperty dob = new SimpleStringProperty(this, "date of birth", "");
    private final StringProperty dateBaptized = new SimpleStringProperty(this, "dateBaptized", "");
    private final StringProperty maritalStatus = new SimpleStringProperty(this, "marital status", "");
    private final StringProperty occupation = new SimpleStringProperty(this, "occupation", "");
    private final StringProperty zone = new SimpleStringProperty(this, "zone", "");
    private final byte[] image;

    // OTHER INFO
    private final StringProperty fatherName = new SimpleStringProperty(this, "father's name", "");
    private final StringProperty motherName = new SimpleStringProperty(this, "mother's name", "");
    private final StringProperty emergencyContactName = new SimpleStringProperty(this, "emergency contact name", "");
    private final StringProperty emergencyContactPhone = new SimpleStringProperty(this, "emergency contact phone", "");

    public Member(
            int id,
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
        this.id.set(id);
        this.name.set(name);
        this.phone.set(phone);
        this.email.set(email);
        this.gender.set(gender);
        this.houseAddress.set(houseAddress);
        this.postAddress.set(postAddress);
        this.hometown.set(hometown);
        this.region.set(region);
        this.dob.set(dob);
        this.dateBaptized.set(dateBaptized);
        this.maritalStatus.set(maritalStatus);
        this.occupation.set(occupation);
        this.image = image;
        this.fatherName.set(fatherName);
        this.motherName.set(motherName);
        this.emergencyContactName.set(emergencyContactName);
        this.emergencyContactPhone.set(emergencyContactPhone);
        this.zone.set(zone);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getHouseAddress() {
        return houseAddress.get();
    }

    public StringProperty houseAddressProperty() {
        return houseAddress;
    }

    public String getPostAddress() {
        return postAddress.get();
    }

    public StringProperty postAddressProperty() {
        return postAddress;
    }

    public String getHometown() {
        return hometown.get();
    }

    public StringProperty hometownProperty() {
        return hometown;
    }

    public String getRegion() {
        return region.get();
    }

    public StringProperty regionProperty() {
        return region;
    }

    public String getDob() {
        return dob.get();
    }

    public StringProperty dobProperty() {
        return dob;
    }

    public String getDateBaptized() {
        return dateBaptized.get();
    }

    public StringProperty dateBaptizedProperty() {
        return dateBaptized;
    }

    public String getMaritalStatus() {
        return maritalStatus.get();
    }

    public StringProperty maritalStatusProperty() {
        return maritalStatus;
    }

    public String getOccupation() {
        return occupation.get();
    }

    public StringProperty occupationProperty() {
        return occupation;
    }

    public String getZone() {
        return zone.get();
    }

    public StringProperty zoneProperty() {
        return zone;
    }

    public byte[] getImage() {
        return image;
    }

    public String getFatherName() {
        return fatherName.get();
    }

    public StringProperty fatherNameProperty() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName.get();
    }

    public StringProperty motherNameProperty() {
        return motherName;
    }

    public String getEmergencyContactName() {
        return emergencyContactName.get();
    }

    public StringProperty emergencyContactNameProperty() {
        return emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone.get();
    }

    public StringProperty emergencyContactPhoneProperty() {
        return emergencyContactPhone;
    }

    @Override
    public String toString() {
        return name.get() + "      \t\t" + phone.get();
    }
}
