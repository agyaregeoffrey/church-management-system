package com.dev.churchmanagementsystem.models;

import javafx.beans.property.*;

import java.util.Objects;

public class Giving {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);
    private final StringProperty date = new SimpleStringProperty(this, "date", "");
    private final DoubleProperty giving = new SimpleDoubleProperty(this, "giving", 0.0d);
    private final DoubleProperty thanksgiving = new SimpleDoubleProperty(this, "thanksgiving", 0.0d);
    private final DoubleProperty total = new SimpleDoubleProperty(this, "total", 0.0d);

    public Giving(Integer id, String date, Double giving, Double thanksgiving, Double total) {
        this.id.set(id);
        this.date.set(date);
        this.giving.set(giving);
        this.thanksgiving.set(thanksgiving);
        this.total.set(total);
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

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Giving giving = (Giving) o;
        return Objects.equals(date, giving.date) && Objects.equals(this.giving, giving.giving) && Objects.equals(thanksgiving, giving.thanksgiving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, giving, thanksgiving);
    }

    @Override
    public String toString() {
        return "Giving{" +
                "date=" + date +
                ", giving=" + giving +
                ", thanksgiving=" + thanksgiving +
                ", total=" + total +
                '}';
    }
}
