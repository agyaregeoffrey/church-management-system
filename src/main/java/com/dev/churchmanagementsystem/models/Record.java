package com.dev.churchmanagementsystem.models;

import javafx.beans.property.*;

import java.util.Objects;

public class Record {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);
    private final StringProperty date = new SimpleStringProperty(this, "date", "");
    private final IntegerProperty male = new SimpleIntegerProperty(this, "male", 0);
    private final IntegerProperty female = new SimpleIntegerProperty(this, "female", 0);
    private final IntegerProperty child = new SimpleIntegerProperty(this, "child", 0);
    private final IntegerProperty visitor = new SimpleIntegerProperty(this, "visitor", 0);
    private final IntegerProperty totalAttendance = new SimpleIntegerProperty(this, "total attendance", 0);
    private final DoubleProperty giving = new SimpleDoubleProperty(this, "giving", 0.0);
    private final DoubleProperty thanksgiving = new SimpleDoubleProperty(this, "thanksgiving", 0.0);
    private final DoubleProperty totalGiving = new SimpleDoubleProperty(this, "total giving", 0.0);

    public Record(int id, String date, int male, int female, int child, int visitor, int totalAttendance, double giving, double thanksgiving, double totalGiving) {
        this.id.set(id);
        this.date.set(date);
        this.male.set(male);
        this.female.set(female);
        this.child.set(child);
        this.visitor.set(visitor);
        this.totalAttendance.set(totalAttendance);
        this.giving.set(giving);
        this.thanksgiving.set(thanksgiving);
        this.totalGiving.set(totalGiving);
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

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getMale() {
        return male.get();
    }

    public IntegerProperty maleProperty() {
        return male;
    }

    public void setMale(int male) {
        this.male.set(male);
    }

    public int getFemale() {
        return female.get();
    }

    public IntegerProperty femaleProperty() {
        return female;
    }

    public void setFemale(int female) {
        this.female.set(female);
    }

    public int getChild() {
        return child.get();
    }

    public IntegerProperty childProperty() {
        return child;
    }

    public void setChild(int child) {
        this.child.set(child);
    }

    public int getVisitor() {
        return visitor.get();
    }

    public IntegerProperty visitorProperty() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor.set(visitor);
    }

    public int getTotalAttendance() {
        return totalAttendance.get();
    }

    public IntegerProperty totalAttendanceProperty() {
        return totalAttendance;
    }

    public void setTotalAttendance(int totalAttendance) {
        this.totalAttendance.set(totalAttendance);
    }

    public double getGiving() {
        return giving.get();
    }

    public DoubleProperty givingProperty() {
        return giving;
    }

    public void setGiving(double giving) {
        this.giving.set(giving);
    }

    public double getThanksgiving() {
        return thanksgiving.get();
    }

    public DoubleProperty thanksgivingProperty() {
        return thanksgiving;
    }

    public void setThanksgiving(double thanksgiving) {
        this.thanksgiving.set(thanksgiving);
    }

    public double getTotalGiving() {
        return totalGiving.get();
    }

    public DoubleProperty totalGivingProperty() {
        return totalGiving;
    }

    public void setTotalGiving(double totalGiving) {
        this.totalGiving.set(totalGiving);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(date, record.date) && Objects.equals(male, record.male) && Objects.equals(female, record.female) && Objects.equals(child, record.child) && Objects.equals(visitor, record.visitor) && Objects.equals(totalAttendance, record.totalAttendance) && Objects.equals(giving, record.giving) && Objects.equals(thanksgiving, record.thanksgiving) && Objects.equals(totalGiving, record.totalGiving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, male, female, child, visitor, totalAttendance, giving, thanksgiving, totalGiving);
    }

    @Override
    public String toString() {
        return "Date: " + date.get();
    }
}
