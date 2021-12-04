module com.dev.churchmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.logging;
    requires org.xerial.sqlitejdbc;

    opens com.dev.churchmanagementsystem to javafx.fxml;
    exports com.dev.churchmanagementsystem;
    exports com.dev.churchmanagementsystem.models;
    opens com.dev.churchmanagementsystem.models to javafx.fxml;
    exports com.dev.churchmanagementsystem.controllers;
    opens com.dev.churchmanagementsystem.controllers to javafx.fxml;
    exports com.dev.churchmanagementsystem.utils;
    opens com.dev.churchmanagementsystem.utils to javafx.fxml;
}