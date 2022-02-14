package com.dev.churchmanagementsystem.models;

import javafx.beans.property.*;

import java.util.Objects;

public class Attendance {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty date = new SimpleStringProperty(this, "date", "");
    private final StringProperty status = new SimpleStringProperty(this, "status", "");
    private final StringProperty gender = new SimpleStringProperty(this, "gender", "");

    public Attendance(Integer id, String name, String date, String status, String gender) {
        this.id.set(id);
        this.name.set(name);
        this.date.set(date);
        this.status.set(status);
        this.gender.set(gender);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, status);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
