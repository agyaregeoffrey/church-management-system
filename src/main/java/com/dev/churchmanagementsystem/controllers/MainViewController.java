package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.dao.GivingDAO;
import com.dev.churchmanagementsystem.dao.RecordDAO;
import com.dev.churchmanagementsystem.models.DataManager;
import com.dev.churchmanagementsystem.models.Giving;
import com.dev.churchmanagementsystem.models.Member;
import com.dev.churchmanagementsystem.models.Record;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dev.churchmanagementsystem.utils.Constants.LOGIN_VIEW;
import static com.dev.churchmanagementsystem.utils.Constants.REGISTRATION_VIEW;
import static com.dev.churchmanagementsystem.utils.Helpers.*;

public class MainViewController implements Initializable {

    private final FilteredList<Record> filteredRecords = new FilteredList<>(RecordDAO.getRecords(), p -> true);
    @FXML
    private Button buttonAccountAdd;

    @FXML
    private Button buttonAccountDelete;

    @FXML
    private Button buttonAccountEdit;

    @FXML
    private Button buttonAttendance;

    @FXML
    private Button buttonDashboard;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonMembers;

    @FXML
    private Button buttonRecordAdd;

    @FXML
    private Button buttonRecordDelete;

    @FXML
    private Button buttonRecordEdit;

    @FXML
    private Button buttonRecords;

    @FXML
    private Button buttonRegister;
    @FXML
    private Label labelAverageAttendance;
    @FXML
    private Label labelGiving;
    @FXML
    private Label labelGivingDate;
    @FXML
    private Label labelTotalMembers;
    @FXML
    private ListView<Member> listViewAttendance;
    @FXML
    private Pane paneDashboard;
    @FXML
    private Pane paneMembers;
    @FXML
    private Pane paneAccounts;
    @FXML
    private Pane paneRecords;
    @FXML
    private TextField textFieldRecordSearch;
    @FXML
    private TableView<Member> tableMembers;
    @FXML
    private TableColumn<Member, String> columnDateBaptized;
    @FXML
    private TableColumn<Member, String> columnID;
    @FXML
    private TableColumn<Member, String> columnName;
    @FXML
    private TableColumn<Member, String> columnTelephone;
    // Records table
    @FXML
    private TableView<Record> tableRecords;
    @FXML
    private TableColumn<Record, Integer> columnRecordChildren;
    @FXML
    private TableColumn<Record, String> columnRecordDate;
    @FXML
    private TableColumn<Record, Integer> columnRecordFemales;
    @FXML
    private TableColumn<Record, Double> columnRecordGiving;
    @FXML
    private TableColumn<Record, Integer> columnRecordID;
    @FXML
    private TableColumn<Record, Integer> columnRecordMales;
    @FXML
    private TableColumn<Record, Double> columnThanksGiving;
    @FXML
    private TableColumn<Record, Integer> columnRecordTotalAttendance;
    @FXML
    private TableColumn<Record, Double> columnRecordTotalGiving;
    @FXML
    private TableColumn<Record, Integer> columnRecordVisitors;
    // Accounts table
    @FXML
    private TableView<Giving> tableAccounts;
    @FXML
    private TableColumn<Giving, String> columnAccountDate;

    @FXML
    private TableColumn<Giving, Double> columnAccountGiving;

    @FXML
    private TableColumn<Giving, Integer> columnAccountID;

    @FXML
    private TableColumn<Giving, Double> columnAccountThanksgiving;

    @FXML
    private TableColumn<Giving, Double> columnAccountTotal;

    private DataManager data;

    @FXML
    void mainViewButtonEvents(ActionEvent event) {
        if (event.getSource() == buttonDashboard) {
            paneDashboard.toFront();
        }
        if (event.getSource() == buttonMembers) {
            paneMembers.toFront();
        }
        if (event.getSource() == buttonRecords) {
            paneRecords.toFront();
        }
        if (event.getSource() == buttonLogout) {
            navigateToPage(buttonLogout, LOGIN_VIEW);
        }
        event.consume();
    }

    @FXML
    void onClickAction(ActionEvent event) {
        if (event.getSource() == buttonRegister) {
            showActionStage(REGISTRATION_VIEW);
        }
        if (event.getSource() == buttonRecordAdd) {
            insertRecord();
        }
        if (event.getSource() == buttonRecordEdit) {
            editRecord();
        }
        if (event.getSource() == buttonRecordDelete) {
            for (Record record : tableRecords.getSelectionModel().getSelectedItems()) {
                RecordDAO.delete(record.getId());
            }
        }
        if (event.getSource() == buttonAccountAdd) {
            insertGiving();
        }

        if (event.getSource() == buttonAccountEdit) {
            editGiving();
        }
        if (event.getSource() == buttonAccountDelete) {
            for (Giving giving: tableAccounts.getSelectionModel().getSelectedItems()) {
                GivingDAO.delete(giving.getId());
            }
        }

        event.consume();
    }

    private void editGiving() {
        AccountEntryController controller = new AccountEntryController();
        Dialog<Giving> dialog = controller.createDialog(tableAccounts.getSelectionModel().getSelectedItem());
        Optional<Giving> optionalGiving = dialog.showAndWait();
        optionalGiving.ifPresent(GivingDAO::update);
    }

    private void insertGiving() {
        AccountEntryController controller = new AccountEntryController();
        Dialog<Giving> dialog = controller.createDialog(null);
        Optional<Giving> optionalGiving = dialog.showAndWait();
        optionalGiving.ifPresent(giving -> {
            controller.addGiving();
        });
    }

    private void insertRecord() {
        RecordEntryController controller = new RecordEntryController();
        Dialog<Record> dialog = controller.createDialog(null);
        Optional<Record> optionalRecord = dialog.showAndWait();
        optionalRecord.ifPresent(record -> {
            controller.addRecord();
        });
    }

    private void editRecord() {
        RecordEntryController controller = new RecordEntryController();
        Dialog<Record> dialog = controller.createDialog(tableRecords.getSelectionModel().getSelectedItem());
        Optional<Record> optionalRecord = dialog.showAndWait();
        optionalRecord.ifPresent(RecordDAO::update);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Dashboard
        getGiving();
        initRecordsTable();

        // Search Member


        // Search Record
        recordSearch();

    }

    private void recordSearch() {
        textFieldRecordSearch.setOnKeyReleased(event -> {
            textFieldRecordSearch.textProperty().addListener((observe, newValue, oldValue) ->
                    filteredRecords.setPredicate(record -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowercaseText = newValue.toLowerCase();
                return record.getDate().toLowerCase().contains(lowercaseText);
            }));

            SortedList<Record> sortedData = new SortedList<>(filteredRecords);
            sortedData.comparatorProperty().bind(tableRecords.comparatorProperty());
            tableRecords.setItems(sortedData);
        });
    }


    private void initRecordsTable() {
        tableRecords.setItems(RecordDAO.getRecords());
        columnRecordID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnRecordDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnRecordMales.setCellValueFactory(new PropertyValueFactory<>("male"));
        columnRecordFemales.setCellValueFactory(new PropertyValueFactory<>("female"));
        columnRecordChildren.setCellValueFactory(new PropertyValueFactory<>("child"));
        columnRecordVisitors.setCellValueFactory(new PropertyValueFactory<>("visitor"));
        columnRecordTotalAttendance.setCellValueFactory(new PropertyValueFactory<>("totalAttendance"));
        columnRecordGiving.setCellValueFactory(new PropertyValueFactory<>("giving"));
        columnThanksGiving.setCellValueFactory(new PropertyValueFactory<>("thanksgiving"));
        columnRecordTotalGiving.setCellValueFactory(new PropertyValueFactory<>("totalGiving"));

        buttonRecordEdit.disableProperty().bind(Bindings.isEmpty(tableRecords.getSelectionModel().getSelectedItems()));
        buttonRecordDelete.disableProperty().bind(Bindings.isEmpty(tableRecords.getSelectionModel().getSelectedItems()));
    }

    private void getGiving() {
        String query = "select date, total_giving from records where id = (select max(id) from records)";
        try (Connection connection = Database.connect()){
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                labelGivingDate.setText(dateUtil(rs.getString("date")));
                labelGiving.setText("₵" + rs.getDouble("total_giving"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
