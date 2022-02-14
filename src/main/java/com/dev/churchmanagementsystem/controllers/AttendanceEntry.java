package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.MainApplication;
import com.dev.churchmanagementsystem.dao.AttendanceDAO;
import com.dev.churchmanagementsystem.models.Attendance;
import com.dev.churchmanagementsystem.models.Member;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.dev.churchmanagementsystem.utils.Constants.*;

public class AttendanceEntry extends Dialog<Attendance> implements Initializable {

    private final ObservableList<String> attendances = FXCollections.observableArrayList(
            "Present",
            "Absent"
    );
    @FXML
    private ComboBox<String> comboBoxAttendance;
    @FXML
    private DatePicker datePickerAttendance;
    @FXML
    private TextField textFieldAttendanceName;
    @FXML
    private Rectangle rectangleAttendance;
    @FXML
    private TextField textFieldAttendanceGender;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxAttendance.setValue(attendances.get(0));
        comboBoxAttendance.setItems(attendances);
        datePickerAttendance.setValue(LocalDate.now());
    }

    public void insertAttendance() {
        String name = textFieldAttendanceName.getText();
        String attendance = comboBoxAttendance.getValue();
        String date = datePickerAttendance.getValue().toString();
        String gender = textFieldAttendanceGender.getText();
        if (gender.equals("Male")) {
            males += 1;
        } else {
            females += 1;
        }

        AttendanceDAO.insertAttendance(name, date, attendance, gender);
    }

    public Dialog<Attendance> attendanceDialog(Member member) {
        Dialog<Attendance> dialog = new Dialog<>();
        dialog.setTitle("Attendance");

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource(ATTENDANCE_VIEW)));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(root);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                (Bindings.createBooleanBinding(() -> textFieldAttendanceName.getText().trim().isEmpty(), textFieldAttendanceName.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> {
                            datePickerAttendance.getValue();
                            return false;
                        }))
                        .or(Bindings.createBooleanBinding(() -> {
                            comboBoxAttendance.getValue();
                            return false;
                        }))

        );

        // Dialog returns a member if available
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int id = -1;
                if (member != null) id = member.getId();
                return new Attendance(
                        id,
                        textFieldAttendanceName.getText(),
                        datePickerAttendance.getValue().toString(),
                        comboBoxAttendance.getValue(),
                        textFieldAttendanceGender.getText()
                );
            }
            return null;
        });

        if (member != null) {
            try {
                byte[] bytes = member.getImage();
                InputStream inputStream = new ByteArrayInputStream(bytes);
                OutputStream outputStream = new FileOutputStream("temp.jpg");
                byte[] imageContent = new byte[1024];
                while (inputStream.read(imageContent) > 0) {
                    outputStream.write(imageContent);
                }
                Image image = new Image("file:temp.jpg",
                        100, 100, false, true);
                ImagePattern pattern = new ImagePattern(image);
//                rectangleAttendance.setFill(pattern);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textFieldAttendanceName.setText(member.getName());
            textFieldAttendanceGender.setText(member.getGender());
        }

        return dialog;
    }
}
