package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.dao.AttendanceDAO;
import com.dev.churchmanagementsystem.dao.GivingDAO;
import com.dev.churchmanagementsystem.dao.MemberDAO;
import com.dev.churchmanagementsystem.dao.RecordDAO;
import com.dev.churchmanagementsystem.models.Attendance;
import com.dev.churchmanagementsystem.models.Giving;
import com.dev.churchmanagementsystem.models.Member;
import com.dev.churchmanagementsystem.models.Record;
import com.dev.churchmanagementsystem.utils.Database;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import static com.dev.churchmanagementsystem.utils.Helpers.dateUtil;
import static com.dev.churchmanagementsystem.utils.Helpers.navigateToPage;

public class MainView implements Initializable {

    private final FilteredList<Record> filteredRecords = new FilteredList<>(RecordDAO.getRecords(), p -> true);
    private final FilteredList<Member> filteredMembers = new FilteredList<>(MemberDAO.getMembers(), p -> true);
    private final FilteredList<Attendance> filteredAttendance = new FilteredList<>(AttendanceDAO.getAttendances(), p -> true);
    @FXML
    private Button buttonAccountAdd;

    @FXML
    private Button buttonAccountDelete;

    @FXML
    private Button buttonAccountEdit;

    @FXML
    private Button buttonMarkAttendance;

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
    private Button buttonAccounts;

    @FXML
    private Button buttonAttendance;


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
    private Pane paneAttendance;
    @FXML
    private TextField textFieldMemberSearch;
    @FXML
    private TextField textFieldAttendanceSearch;
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
    @FXML
    private TableColumn<Member, String> columnAddress;
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
    // Attendance table
    @FXML
    private TableView<Attendance> tableAttendance;
    @FXML
    private TableColumn<Attendance, String> columnAttendanceDate;

    @FXML
    private TableColumn<Attendance, String> columnAttendanceGender;

    @FXML
    private TableColumn<Attendance, Integer> columnAttendanceID;

    @FXML
    private TableColumn<Attendance, String> columnAttendanceName;

    @FXML
    private TableColumn<Attendance, String> columnAttendanceStatus;


    @FXML
    void mainViewButtonEvents(ActionEvent event) {
        if (event.getSource() == buttonDashboard) {
            getTotalMembers();
            getGiving();
            initializedListView();
            paneDashboard.toFront();
        }
        if (event.getSource() == buttonMembers) {
            paneMembers.toFront();
        }
        if (event.getSource() == buttonRecords) {
            initRecordsTable();
            paneRecords.toFront();
        }
        if (event.getSource() == buttonAttendance) {
            initAttendanceTable();
            paneAttendance.toFront();
        }
        if (event.getSource() == buttonAccounts) {
            initAccountsTable();
            paneAccounts.toFront();
        }
        if (event.getSource() == buttonLogout) {
            navigateToPage(buttonLogout, LOGIN_VIEW);
        }
        event.consume();
    }

    @FXML
    void onClickAction(ActionEvent event) {
        if (event.getSource() == buttonRegister) {
            insertMember();
        }
        if (event.getSource() == buttonEdit) {
            editMember();
        }
        if (event.getSource() == buttonDelete) {
            for (Member member : tableMembers.getSelectionModel().getSelectedItems()) {
                MemberDAO.delete(member.getId());
            }
        }
        if (event.getSource() == buttonMarkAttendance) {
            insertAttendance();
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
            for (Giving giving : tableAccounts.getSelectionModel().getSelectedItems()) {
                GivingDAO.delete(giving.getId());
            }
        }

        event.consume();
    }

    private void insertAttendance() {
        AttendanceEntry controller = new AttendanceEntry();
        Dialog<Attendance> dialog = controller.attendanceDialog(tableMembers.getSelectionModel().getSelectedItem());
        Optional<Attendance> optionalMember = dialog.showAndWait();
        optionalMember.ifPresent(member -> {
            controller.insertAttendance();
        });
    }

    private void editMember() {
        RegisterMember controller = new RegisterMember();
        Dialog<Member> dialog = controller.registerMemberDialog(tableMembers.getSelectionModel().getSelectedItem());
        Optional<Member> optionalMember = dialog.showAndWait();
        optionalMember.ifPresent(MemberDAO::update);
    }

    private void insertMember() {
        RegisterMember controller = new RegisterMember();
        Dialog<Member> dialog = controller.registerMemberDialog(null);
        Optional<Member> optionalMember = dialog.showAndWait();
        optionalMember.ifPresent(member -> {
            controller.addMember();
        });
    }

    private void editGiving() {
        AccountEntry controller = new AccountEntry();
        Dialog<Giving> dialog = controller.createDialog(tableAccounts.getSelectionModel().getSelectedItem());
        Optional<Giving> optionalGiving = dialog.showAndWait();
        optionalGiving.ifPresent(GivingDAO::update);
    }

    private void insertGiving() {
        AccountEntry controller = new AccountEntry();
        Dialog<Giving> dialog = controller.createDialog(null);
        Optional<Giving> optionalGiving = dialog.showAndWait();
        optionalGiving.ifPresent(giving -> controller.addGiving());
    }

    private void insertRecord() {
        RecordEntry controller = new RecordEntry();
        Dialog<Record> dialog = controller.createDialog(null);
        Optional<Record> optionalRecord = dialog.showAndWait();
        optionalRecord.ifPresent(record -> {
            controller.addRecord();
        });
    }

    private void editRecord() {
        RecordEntry controller = new RecordEntry();
        Dialog<Record> dialog = controller.createDialog(tableRecords.getSelectionModel().getSelectedItem());
        Optional<Record> optionalRecord = dialog.showAndWait();
        optionalRecord.ifPresent(RecordDAO::update);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Dashboard
        getTotalMembers();
        getGiving();
//        averageGiving();
        initializedListView();

        // Tables
        initMembersTable();
        initRecordsTable();
        initAttendanceTable();
        initAccountsTable();

        // Search
        memberSearch();
        recordSearch();
        attendanceSearch();
    }

    private void memberSearch() {
        textFieldMemberSearch.setOnKeyReleased(event -> {
            textFieldMemberSearch.textProperty().addListener((observe, newValue, oldValue) ->
                    filteredMembers.setPredicate(member -> {
                        if (newValue == null || newValue.isEmpty()) return true;

                        String lowercaseText = newValue.toLowerCase();
                        return member.getName().toLowerCase().contains(lowercaseText);
                    }));

            SortedList<Member> sortedData = new SortedList<>(filteredMembers);
            sortedData.comparatorProperty().bind(tableMembers.comparatorProperty());
            tableMembers.setItems(sortedData);
        });
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

    private void attendanceSearch() {
        textFieldAttendanceSearch.setOnKeyReleased(event -> {
            textFieldAttendanceSearch.textProperty().addListener((observe, newValue, oldValue) ->
                    filteredAttendance.setPredicate(attendance -> {
                        if (newValue == null || newValue.isEmpty()) return true;

                        String lowercaseText = newValue.toLowerCase();
                        return attendance.getName().toLowerCase().contains(lowercaseText);
                    }));

            SortedList<Attendance> sortedData = new SortedList<>(filteredAttendance);
            sortedData.comparatorProperty().bind(tableAttendance.comparatorProperty());
            tableAttendance.setItems(sortedData);
        });
    }
    private void initMembersTable() {
        tableMembers.setItems(MemberDAO.getMembers());
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTelephone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnDateBaptized.setCellValueFactory(new PropertyValueFactory<>("dateBaptized"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("houseAddress"));

        buttonEdit.disableProperty().bind(Bindings.isEmpty(tableMembers.getSelectionModel().getSelectedItems()));
        buttonDelete.disableProperty().bind(Bindings.isEmpty(tableMembers.getSelectionModel().getSelectedItems()));
        buttonMarkAttendance.disableProperty().bind(Bindings.isEmpty(tableMembers.getSelectionModel().getSelectedItems()));
    }

    private void initAttendanceTable() {
        tableAttendance.setItems(AttendanceDAO.getAttendances());
        columnAttendanceID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAttendanceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAttendanceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnAttendanceStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnAttendanceGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
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

    private void initAccountsTable() {
        tableAccounts.setItems(GivingDAO.getGivings());
        columnAccountID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAccountDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnAccountGiving.setCellValueFactory(new PropertyValueFactory<>("giving"));
        columnAccountThanksgiving.setCellValueFactory(new PropertyValueFactory<>("thanksgiving"));
        columnAccountTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        buttonAccountEdit.disableProperty().bind(Bindings.isEmpty(tableAccounts.getSelectionModel().getSelectedItems()));
        buttonAccountDelete.disableProperty().bind(Bindings.isEmpty(tableAccounts.getSelectionModel().getSelectedItems()));
    }

    private void getTotalMembers() {
        String query = "SELECT SUM(id) FROM member";
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString(1) == null) {
                    labelTotalMembers.setText("0");
                } else {
                    labelTotalMembers.setText(rs.getString(1));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getGiving() {
        String query = "select date, total_giving from records where id = (select max(id) from records)";
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("date") == null && rs.getDouble("total_giving") == 0) {
                    labelGivingDate.setText("Date");
                    labelGiving.setText("₵0.00");
                } else {
                    labelGivingDate.setText(dateUtil(rs.getString("date")));
                    labelGiving.setText("₵" + rs.getDouble("total_giving"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void averageGiving() {
        String query = "SELECT AVG(giving) from records";
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getDouble("giving") == 0) {
                    labelAverageAttendance.setText("0");
                } else {
                    labelAverageAttendance.setText(String.valueOf(rs.getDouble("giving")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializedListView() {
        ObservableList<Member> members = FXCollections.observableArrayList();
        String query = "SELECT * FROM member";
        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                members.add(new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("house_address"),
                        rs.getString("post_address"),
                        rs.getString("hometown"),
                        rs.getString("region"),
                        rs.getString("dob"),
                        rs.getString("date_baptized"),
                        rs.getString("marital_status"),
                        rs.getString("occupation"),
                        rs.getBytes("image"),
                        rs.getString("father_name"),
                        rs.getString("mother_name"),
                        rs.getString("emergency_contact_name"),
                        rs.getString("emergency_contact_phone"),
                        rs.getString("zone")
                ));
            }
            listViewAttendance.setItems(members);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
