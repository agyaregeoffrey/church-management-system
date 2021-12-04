package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.models.DataManager;
import com.dev.churchmanagementsystem.models.Record;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ItemRecordViewController implements Initializable {

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

    private DataManager data;

    @FXML
    void onItemButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
