package com.dev.churchmanagementsystem.utils;

import com.dev.churchmanagementsystem.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class Helpers {

    public static void navigateToPage(Button source, String resource) {
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        Scene scene;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(resource))));
            stage.titleProperty().setValue("COC New Town");
            stage.setScene(scene);
            stage.resizableProperty().set(false);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void showActionStage(String resource) {
        Stage stage = new Stage();
        Scene scene;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(resource))));
            stage.titleProperty().setValue("COC New Town");
            stage.setScene(scene);
            stage.resizableProperty().set(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String dateUtil(String ds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM, yyyy");
        Date date = null;
        try {
            date = sdf.parse(ds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    //ensure only numeric input in text fields
    public static UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
        if (!change.getText().matches("\\d*(\\.\\d*)?") && !change.getText().equals("")) {
            change.setText(""); //else make no change
            change.setRange(    //don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart()
            );
        }
        return change; //if change is a number or if a deletion is being made
    };
}
