package com.dev.churchmanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemRecordView implements Initializable {

    @FXML
    private Button buttonItemDelete;

    @FXML
    private Button buttonItemEdit;

    @FXML
    private Label labelItemChildren;

    @FXML
    private Label labelItemDate;

    @FXML
    private Label labelItemFemales;

    @FXML
    private Label labelItemMales;

    @FXML
    private Label labelItemTotal;

    @FXML
    private Label labelItemVisitors;


    @FXML
    void onItemButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
