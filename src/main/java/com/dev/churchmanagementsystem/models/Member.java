package com.dev.churchmanagementsystem.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Member {
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty phone = new SimpleStringProperty(this, "phone", "");
    private final StringProperty dateBaptized = new SimpleStringProperty(this, "dateBaptized", "");
    private final StringProperty imageResource = new SimpleStringProperty(this, "image", "image path");

    public Member(String name, String phone, String dateBaptized, String imageResource) {
        this.name.set(name);
        this.phone.set(phone);
        this.dateBaptized.set(dateBaptized);
        this.imageResource.set(imageResource);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
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

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getDateBaptized() {
        return dateBaptized.get();
    }

    public StringProperty dateBaptizedProperty() {
        return dateBaptized;
    }

    public void setDateBaptized(String dateBaptized) {
        this.dateBaptized.set(dateBaptized);
    }

    public String getImageResource() {
        return imageResource.get();
    }

    public StringProperty imageResourceProperty() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource.set(imageResource);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(name, member.name) && Objects.equals(phone, member.phone) && Objects.equals(dateBaptized, member.dateBaptized) && Objects.equals(imageResource, member.imageResource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, dateBaptized, imageResource);
    }
}
