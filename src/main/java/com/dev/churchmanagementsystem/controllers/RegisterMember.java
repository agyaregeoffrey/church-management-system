package com.dev.churchmanagementsystem.controllers;

import com.dev.churchmanagementsystem.MainApplication;
import com.dev.churchmanagementsystem.dao.MemberDAO;
import com.dev.churchmanagementsystem.models.Member;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.dev.churchmanagementsystem.utils.Constants.REGISTRATION_VIEW;
import static com.dev.churchmanagementsystem.utils.Helpers.numberValidationFormatter;

public class RegisterMember extends Window implements Initializable {

    private final ObservableList<String> maritalStatuses = FXCollections.observableArrayList(
            "Married",
            "Divorced",
            "Single",
            "Widowed"
    );
    private final ObservableList<String> zones = FXCollections.observableArrayList(
            "Hospital",
            "New Town",
            "ABC",
            "Asubone",
            "Whitehouse",
            "Abepotia",
            "Dubai",
            "Out Station"
    );
    private final ObservableList<String> genders = FXCollections.observableArrayList(
            "Male",
            "Female"
    );
    @FXML
    private Button buttonSelectImage;
    @FXML
    private ComboBox<String> comboMaritalStatus;
    @FXML
    private ComboBox<String> comboZone;
    @FXML
    private ComboBox<String> comboGender;
    @FXML
    private DatePicker datePickerDateBaptized;
    @FXML
    private DatePicker datePickerDob;
    @FXML
    private Rectangle rectangle;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldEmergencyName;
    @FXML
    private TextField textFieldEmergencyPhone;
    @FXML
    private TextField textFieldFatherName;
    @FXML
    private TextField textFieldHometown;
    @FXML
    private TextField textFieldMotherName;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldOccupation;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldPostAddress;
    @FXML
    private TextField textFieldRegion;
    @FXML
    private TextField textFieldResidentAddress;
    private byte[] image;

    @FXML
    void selectImage(ActionEvent event) {
        image = selectImage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboMaritalStatus.setValue(maritalStatuses.get(0));
        comboMaritalStatus.setItems(maritalStatuses);

        comboZone.setValue(zones.get(0));
        comboZone.setItems(zones);

        comboGender.setValue(genders.get(0));
        comboGender.setItems(genders);
    }

    public void addMember() {
        String name = textFieldName.getText().trim();
        String phone = textFieldPhone.getText().trim();
        String email = textFieldEmail.getText().trim().isEmpty() ? "No email provided" : textFieldEmail.getText().trim();
        String hometown = textFieldHometown.getText().trim();
        String region = textFieldRegion.getText().trim();
        String postAddress = textFieldPostAddress.getText().trim();
        String residentAddress = textFieldResidentAddress.getText().trim();
        String dob = datePickerDob.getValue().toString();
        String dateBaptized = datePickerDateBaptized.getValue().toString();
        String maritalStatus = comboMaritalStatus.getValue();
        String occupation = textFieldOccupation.getText().trim();
        String fatherName = textFieldFatherName.getText().trim();
        String motherName = textFieldMotherName.getText().trim();
        String emergencyContactName = textFieldEmergencyName.getText().trim();
        String emergencyContactPhone = textFieldEmergencyPhone.getText().trim();
        String zone = comboZone.getValue();
        String gender = comboGender.getValue();

        MemberDAO.insertMember(
                name,
                phone,
                email,
                gender,
                residentAddress,
                postAddress,
                hometown,
                region,
                dob,
                dateBaptized,
                maritalStatus,
                occupation,
                image,
                fatherName,
                motherName,
                emergencyContactName,
                emergencyContactPhone,
                zone
        );

    }

    public Dialog<Member> registerMemberDialog(Member member) {
        Dialog<Member> dialog = new Dialog<>();
        if (member == null) {
            dialog.setTitle("New Member");
        } else {
            dialog.setTitle("Edit Member");
        }

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource(REGISTRATION_VIEW)));
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
                (Bindings.createBooleanBinding(() -> textFieldName.getText().trim().isEmpty(), textFieldName.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldPhone.getText().trim().isEmpty(), textFieldPhone.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> {
                            datePickerDob.getValue();
                            return false;
                        }))
                        .or(Bindings.createBooleanBinding(() -> {
                            datePickerDateBaptized.getValue();
                            return false;
                        }))
                        .or(Bindings.createBooleanBinding(() -> {
                            comboZone.getValue();
                            return false;
                        }))
                        .or(Bindings.createBooleanBinding(() -> {
                            comboGender.getValue();
                            return false;
                        }))
                        .or(Bindings.createBooleanBinding(() -> {
                            comboMaritalStatus.getValue();
                            return false;
                        }))
                        .or(Bindings.createBooleanBinding(() -> !Objects.equals(buttonSelectImage.getText(), "Image Selected"), buttonSelectImage.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldHometown.getText().trim().isEmpty(), textFieldHometown.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldRegion.getText().trim().isEmpty(), textFieldRegion.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldOccupation.getText().trim().isEmpty(), textFieldOccupation.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldResidentAddress.getText().trim().isEmpty(), textFieldResidentAddress.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldPostAddress.getText().trim().isEmpty(), textFieldPostAddress.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldFatherName.getText().trim().isEmpty(), textFieldFatherName.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldMotherName.getText().trim().isEmpty(), textFieldMotherName.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldEmergencyName.getText().trim().isEmpty(), textFieldEmergencyName.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> textFieldEmergencyPhone.getText().trim().isEmpty(), textFieldEmergencyPhone.textProperty()))
        );

        textFieldPhone.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        textFieldEmergencyPhone.setTextFormatter(new TextFormatter<>(numberValidationFormatter));

        // Dialog returns a member if available
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int id = -1;
                if (member != null) id = member.getId();
                return new Member(
                        id,
                        textFieldName.getText(),
                        textFieldPhone.getText(),
                        textFieldEmail.getText(),
                        comboGender.getValue(),
                        textFieldResidentAddress.getText(),
                        textFieldPostAddress.getText(),
                        textFieldHometown.getText(),
                        textFieldRegion.getText(),
                        datePickerDob.getValue().toString(),
                        datePickerDateBaptized.getValue().toString(),
                        comboMaritalStatus.getValue(),
                        textFieldOccupation.getText(),
                        image,
                        textFieldFatherName.getText(),
                        textFieldMotherName.getText(),
                        textFieldEmergencyName.getText(),
                        textFieldEmergencyPhone.getText(),
                        comboZone.getValue()
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
                rectangle.setFill(pattern);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textFieldName.setText(member.getName());
            comboZone.setValue(member.getZone());
            textFieldPhone.setText(member.getPhone());
            textFieldEmail.setText(member.getEmail());
            textFieldHometown.setText(member.getHometown());
            textFieldRegion.setText(member.getRegion());
            textFieldPostAddress.setText(member.getPostAddress());
            textFieldResidentAddress.setText(member.getHouseAddress());
            datePickerDob.setValue(LocalDate.parse(member.getDob()));
            datePickerDateBaptized.setValue(LocalDate.parse(member.getDateBaptized()));
            comboMaritalStatus.setValue(member.getMaritalStatus());
            textFieldFatherName.setText(member.getFatherName());
            textFieldOccupation.setText(member.getOccupation());
            textFieldMotherName.setText(member.getMotherName());
            textFieldEmergencyName.setText(member.getEmergencyContactName());
            textFieldEmergencyPhone.setText(member.getEmergencyContactPhone());
        }

        return dialog;
    }

    private byte[] selectImage() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        Image image;
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg"
        ));
        File file = fileChooser.showOpenDialog(this);
        if (file != null) {
            image = new Image(file.toURI().toString(),
                    100, 100, false, true);
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            buttonSelectImage.setText("Image Selected");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                byteArrayOutputStream = new ByteArrayOutputStream();

                for (int len; (len = fileInputStream.read(buffer)) != -1; ) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null;

        // left -> 1702160
        // top -> 6700054
    }
}
