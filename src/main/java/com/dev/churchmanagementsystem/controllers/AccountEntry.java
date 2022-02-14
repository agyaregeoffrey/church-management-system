package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.MainApplication;
import com.dev.churchmanagementsystem.dao.GivingDAO;
import com.dev.churchmanagementsystem.models.Giving;
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

import static com.dev.churchmanagementsystem.utils.Constants.ACCOUNTS_VIEW;
import static com.dev.churchmanagementsystem.utils.Constants.RECORD_VIEW;
import static com.dev.churchmanagementsystem.utils.Helpers.numberValidationFormatter;

public class AccountEntry extends Dialog<Giving> implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textFieldGiving;

    @FXML
    private TextField textFieldThanksgiving;

    @FXML
    private TextField textFieldTotalGiving;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
    }

    public void addGiving() {
        String date = datePicker.getValue().toString();
        double giving = Double.parseDouble(textFieldGiving.getText());
        double thanksgiving = Double.parseDouble(textFieldThanksgiving.getText());
        double total = Double.parseDouble(textFieldTotalGiving.getText());

        GivingDAO.insertGiving(date, giving, thanksgiving, total);
    }

    public Dialog<Giving> createDialog(Giving giving) {
        Dialog<Giving> dialog = new Dialog<>();
        if (giving == null) {
            dialog.setTitle("New Giving");
        } else  {
            dialog.setTitle("Edit Giving");
        }

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource(ACCOUNTS_VIEW)));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> textFieldGiving.getText().trim().isEmpty(), textFieldGiving.textProperty())
                        .or(Bindings.createBooleanBinding(() -> textFieldThanksgiving.getText().trim().isEmpty(), textFieldThanksgiving.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldTotalGiving.getText().trim().isEmpty(), textFieldTotalGiving.textProperty()))
        );

        textFieldGiving.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldThanksgiving.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldTotalGiving.setTextFormatter(new TextFormatter<>(numberValidationFormatter));

        textFieldTotalGiving.textProperty().bind(Bindings.createStringBinding(() -> {
            if (!textFieldGiving.getText().isEmpty() && !textFieldThanksgiving.getText().isEmpty()) {
                double result = Double.parseDouble(textFieldGiving.getText().trim()) + Double.parseDouble(textFieldThanksgiving.getText().trim());
                return String.valueOf(result);
            }
            return "";
        }, textFieldGiving.textProperty(), textFieldThanksgiving.textProperty()));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int id = -1;
                if (giving != null) id = giving.getId();
                return new Giving(
                        id,
                        datePicker.getValue().toString(),
                        Double.parseDouble(textFieldGiving.getText()),
                        Double.parseDouble(textFieldThanksgiving.getText()),
                        Double.parseDouble(textFieldTotalGiving.getText())
                );
            }
            return null;
        });

        if (giving != null) {
            datePicker.setValue(LocalDate.parse(giving.getDate()));
            textFieldGiving.setText(String.valueOf(giving.getGiving()));
            textFieldThanksgiving.setText(String.valueOf(giving.getThanksgiving()));
            textFieldTotalGiving.setText(String.valueOf(giving.getTotal()));
        }
        return dialog;
    }
}
