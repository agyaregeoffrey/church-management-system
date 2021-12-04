package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.MainApplication;
import com.dev.churchmanagementsystem.dao.RecordDAO;
import com.dev.churchmanagementsystem.models.Record;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import static com.dev.churchmanagementsystem.utils.Constants.RECORD_VIEW;
import static com.dev.churchmanagementsystem.utils.Helpers.numberValidationFormatter;

public class RecordEntryController extends Dialog<Record> implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textFieldChildren;

    @FXML
    private TextField textFieldFemales;

    @FXML
    private TextField textFieldGiving;

    @FXML
    private TextField textFieldMales;

    @FXML
    private TextField textFieldThanksgiving;

    @FXML
    private TextField textFieldTotalAttendance;

    @FXML
    private TextField textFieldTotalGiving;

    @FXML
    private TextField textFieldVisitors;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
    }

    public void addRecord() {
        String date = datePicker.getValue().toString();
        int males = Integer.parseInt(textFieldMales.getText());
        int females = Integer.parseInt(textFieldFemales.getText());
        int children = Integer.parseInt(textFieldChildren.getText());
        int visitors = Integer.parseInt(textFieldVisitors.getText());
        int totalAttendance = Integer.parseInt(textFieldTotalAttendance.getText());
        double giving = Double.parseDouble(textFieldGiving.getText());
        double thanksgiving = Double.parseDouble(textFieldThanksgiving.getText());
        double totalGiving = Double.parseDouble(textFieldGiving.getText());

        RecordDAO.insertRecord(date, males, females, children, visitors, totalAttendance, giving, thanksgiving, totalGiving);
    }

    public Dialog<Record> createDialog(Record record) {
        Dialog<Record> dialog = new Dialog<>();
        if (record == null) {
            dialog.setTitle("New Record");
        } else {
            dialog.setTitle("Edit Record");
        }

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource(RECORD_VIEW)));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> textFieldMales.getText().trim().isEmpty(), textFieldMales.textProperty())
                        .or(Bindings.createBooleanBinding(() -> textFieldFemales.getText().trim().isEmpty(), textFieldFemales.textProperty())
                                .or(Bindings.createBooleanBinding(() -> textFieldChildren.getText().trim().isEmpty(), textFieldChildren.textProperty())
                                        .or(Bindings.createBooleanBinding(() -> textFieldVisitors.getText().trim().isEmpty(), textFieldVisitors.textProperty())
                                                .or(Bindings.createBooleanBinding(() -> textFieldTotalAttendance.getText().trim().isEmpty(), textFieldTotalAttendance.textProperty())
                                                        .or(Bindings.createBooleanBinding(() -> textFieldGiving.getText().trim().isEmpty(), textFieldGiving.textProperty())
                                                                .or(Bindings.createBooleanBinding(() -> textFieldThanksgiving.getText().trim().isEmpty(), textFieldThanksgiving.textProperty())
                                                                        .or(Bindings.createBooleanBinding(() -> textFieldTotalGiving.getText().trim().isEmpty(), textFieldTotalGiving.textProperty())
                                                                        ))))))));

        textFieldMales.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldFemales.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldChildren.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldVisitors.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldTotalAttendance.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldGiving.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldThanksgiving.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldTotalGiving.setTextFormatter(new TextFormatter<>(numberValidationFormatter));

        // Automatically sum values from textFields
        textFieldTotalAttendance.textProperty().bind(Bindings.createStringBinding(() -> {
           if (!textFieldMales.getText().isEmpty() && !textFieldFemales.getText().isEmpty()
           && !textFieldChildren.getText().isEmpty() && !textFieldVisitors.getText().isEmpty()) {
               int result = Integer.parseInt(textFieldMales.getText().trim()) + Integer.parseInt(textFieldFemales.getText().trim())
                        + Integer.parseInt(textFieldChildren.getText().trim()) + Integer.parseInt(textFieldVisitors.getText().trim());
               return String.valueOf(result);
           }
           return "";
        }, textFieldMales.textProperty(), textFieldFemales.textProperty(), textFieldChildren.textProperty(), textFieldVisitors.textProperty()));

        textFieldTotalGiving.textProperty().bind(Bindings.createStringBinding(() -> {
            if (!textFieldGiving.getText().isEmpty() && !textFieldThanksgiving.getText().isEmpty()) {
                double result = Double.parseDouble(textFieldGiving.getText().trim()) + Double.parseDouble(textFieldThanksgiving.getText().trim());
                return String.valueOf(result);
            }
            return "";
        }, textFieldGiving.textProperty(), textFieldThanksgiving.textProperty()));

        // Dialog returns a record if available
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int id = -1;
                if (record != null) id = record.getId();
                return new Record(id,
                        datePicker.getValue().toString(),
                        Integer.parseInt(textFieldMales.getText()),
                        Integer.parseInt(textFieldFemales.getText()),
                        Integer.parseInt(textFieldChildren.getText()),
                        Integer.parseInt(textFieldVisitors.getText()),
                        Integer.parseInt(textFieldTotalAttendance.getText()),
                        Double.parseDouble(textFieldGiving.getText()),
                        Double.parseDouble(textFieldThanksgiving.getText()),
                        Double.parseDouble(textFieldTotalGiving.getText()));
            }
            return null;
        });

        if (record != null) {
            datePicker.setValue(LocalDate.parse(record.getDate()));
            textFieldMales.setText(String.valueOf(record.getMale()));
            textFieldFemales.setText(String.valueOf(record.getFemale()));
            textFieldChildren.setText(String.valueOf(record.getChild()));
            textFieldVisitors.setText(String.valueOf(record.getVisitor()));
            textFieldGiving.setText(String.valueOf(record.getGiving()));
            textFieldThanksgiving.setText(String.valueOf(record.getThanksgiving()));

        }
        return dialog;
    }

}
