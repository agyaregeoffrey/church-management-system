package com.dev.churchmanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dev.churchmanagementsystem.utils.Constants.MAIN_VIEW;
import static com.dev.churchmanagementsystem.utils.Helpers.navigateToPage;


public class LoginController implements Initializable {

    @FXML
    private Button buttonLogin;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private Label labelError;

    @FXML
    void onLoginButtonClicked(ActionEvent event) {
        loginMouseClick(event);
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        loginKeyPressed(event);
    }

    private void loginMouseClick(ActionEvent event) {
        if (isFormValidated())
            navigateToPage(buttonLogin, MAIN_VIEW);
        else {
            labelError.textFillProperty().setValue(Color.TOMATO);
            labelError.textProperty().setValue("Login Failed, Wrong Username or Password");
        }
    }

    private void loginKeyPressed(KeyEvent event) {
        if (isFormValidated()) {
            navigateToPage(buttonLogin, MAIN_VIEW);
        } else {
            labelError.textFillProperty().setValue(Color.TOMATO);
            labelError.textProperty().setValue("Login Failed, Wrong Username or Password");
        }
    }

    private boolean isFormValidated() {
        return textFieldUsername.getText().equals("username")
                && textFieldPassword.getText().equals("password");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogin.disableProperty().bind(
                textFieldUsername.textProperty().isEmpty()
                        .or(textFieldPassword.textProperty().isEmpty())
        );
    }

}