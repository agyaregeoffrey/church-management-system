package com.dev.churchmanagementsystem;

import com.dev.churchmanagementsystem.utils.Alerts;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.dev.churchmanagementsystem.utils.Constants.LOGIN_VIEW;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (Database.isOK()) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(LOGIN_VIEW)));
            stage.setTitle("COC New Town");
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            Alerts.error(
                    "Database error",
                    "Could not load database",
                    "Error loading SQLite database. See log. Quitting..."
            ).showAndWait();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}