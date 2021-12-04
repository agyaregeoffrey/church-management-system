package com.dev.churchmanagementsystem.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Attendance {
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty date = new SimpleStringProperty(this, "date", "");
    private final BooleanProperty attended = new SimpleBooleanProperty(this, "attended", false);

    public Attendance(String name, String date, Boolean attended) {
        this.name.set(name);
        this.date.set(date);
        this.attended.set(attended);
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

    public boolean attended() {
        return attended.get();
    }

    public BooleanProperty attendedProperty() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended.set(attended);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(attended, that.attended);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, attended);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
