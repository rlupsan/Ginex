package ui;


import bll.AppointmentBLL;
import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Appointment;
import model.AppointmentType;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentView extends VBox {
    private TextField firstNameInput, lastNameInput, hourInput, phoneInput;
    private ComboBox<String> typeInput;
    private DatePicker dateInput, dateSelectedInput;
    private TableView<Appointment> tableViewAppointment;
    private Integer idInput;

    AppointmentView() {
        HBox hBox = new HBox(10);
        HBox hBox1 = new HBox(10);
        setSpacing(8);

        tableViewAppointment = new TableView<>();
        Appointment appointment = new Appointment(-1, null, null, null, null, null, null);
        TableGenerator<Appointment> t = new TableGenerator<>();
        tableViewAppointment = t.generateTable(appointment);
        tableViewAppointment.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableViewAppointment.setMaxWidth(900);
        refreshTableAppointment();

        firstNameInput = new TextField();
        firstNameInput.setPromptText("Prenume");

        lastNameInput = new TextField();
        lastNameInput.setPromptText("Nume");

        typeInput = new ComboBox<>();
        typeInput.getItems().addAll("CONSULTATIE", "ECOGRAFIE", "MONTARE_STERILET", "EXTRACTIE_STERILET", "RECOLTARE_FPBN");
        typeInput.setPromptText("Tipul Programarii");

        dateInput = new DatePicker();

        hourInput = new TextField();
        hourInput.setPromptText("Ora");

        phoneInput = new TextField();
        phoneInput.setPromptText("Telefon");

        Button clearAppointmentButton = new Button("Clear");
        Label chooseLabelAppointment = new Label("Choose an operation: ");
        Button addAppointmentButton = new Button("ADD");
        addAppointmentButton.setPrefWidth(100);
        Button updateAppointmentButton = new Button("UPDATE");
        updateAppointmentButton.setPrefWidth(100);
        Button deleteAppointmentButton = new Button("DELETE");
        deleteAppointmentButton.setPrefWidth(100);
        Button copyAppointmentButton = new Button("COPY");
        copyAppointmentButton.setPrefWidth(100);

        clearAppointmentButton.setOnAction(e -> clearAllFields());

        addAppointmentButton.setOnAction(e -> {
            try {
                AppointmentBLL appointmentBLL = new AppointmentBLL();
                Appointment appointment1 = new Appointment(
                        firstNameInput.getText(),
                        lastNameInput.getText(),
                        AppointmentType.valueOf(typeInput.getValue()),
                        Date.valueOf(dateInput.getValue()),
                        Time.valueOf(hourInput.getText() + ":00"),
                        phoneInput.getText());
                AppointmentBLL.insertAppointment(appointment1);
                refreshTableAppointment();
                clearAllFields();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Couldn't add appointment");
                alert.setContentText("The appointment could not be added.\nThe input might be missing or incompatible.\n" +
                        "Please try again.");
                alert.showAndWait();
            }
        });

        copyAppointmentButton.setOnAction(e -> {
            try {
                copyRow();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Couldn't copy the row");
                alert.setContentText("The row could not be copied.\n" +
                        "Please try again.");
                alert.showAndWait();
            }
        });
        updateAppointmentButton.setOnAction(e -> {
            try {
                AppointmentBLL appointmentBLL = new AppointmentBLL();
                Appointment appointment1 = new Appointment(
                        idInput,
                        firstNameInput.getText(),
                        lastNameInput.getText(),
                        AppointmentType.valueOf(typeInput.getValue()),
                        Date.valueOf(dateInput.getValue()),
                        Time.valueOf(hourInput.getText() + ":00"),
                        phoneInput.getText());
                AppointmentBLL.updateAppointment(appointment1);
                clearAllFields();
                refreshTableAppointment();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Couldn't edit appointment");
                alert.setContentText("The appointment could not be edited.\nThe input might be missing or incompatible.\n" +
                        "Please try again.");
                alert.showAndWait();
            }
        });

        deleteAppointmentButton.setOnAction(e -> {
            try {
                Appointment selectedItem = tableViewAppointment.getSelectionModel().getSelectedItem();
                int idSelected = selectedItem.getId();
                AppointmentDAO.delete(idSelected);
                refreshTableAppointment();
                clearAllFields();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Could not delete the appointment");
                alert.setContentText("The appointment could not be deleted.\n");
                alert.showAndWait();
            }
        });

        HBox hBox2 = new HBox(8);
        Label selectDateLabel = new Label("Selecteaza data: ");
        dateSelectedInput = new DatePicker();
        Button selectDateButton = new Button("SHOW TABLE");

        selectDateButton.setOnAction(e -> {
            try {
                showTableOnSelectedDates();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Couldn't show the table.");
                alert.setContentText("The table could not be shown on the selected dates\n" +
                        "Please try again.");
                alert.showAndWait();
            }
        });

        Button refreshTableButton = new Button("REFRESH TABLE");
        refreshTableButton.setOnAction(event -> refreshTableAppointment());

        hBox1.getChildren().addAll(chooseLabelAppointment, addAppointmentButton, updateAppointmentButton, deleteAppointmentButton, copyAppointmentButton);
        hBox.getChildren().addAll(firstNameInput, lastNameInput, typeInput, dateInput, hourInput, phoneInput, clearAppointmentButton);
        hBox2.getChildren().addAll(selectDateLabel, dateSelectedInput, selectDateButton, refreshTableButton);
        getChildren().addAll(tableViewAppointment, hBox, hBox1, hBox2);
    }

    private void showTableOnSelectedDates() {
        tableViewAppointment.getItems().removeAll();
        ArrayList<Appointment> appointments = AppointmentDAO.getSelectedAppointmentList(dateSelectedInput.getValue().toString());
        ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);
        tableViewAppointment.setItems(observableList);
        tableViewAppointment.refresh();
    }

    private void refreshTableAppointment() {
        tableViewAppointment.getItems().removeAll();
        ArrayList<Appointment> appointments = AppointmentDAO.getAppointmentList();
        ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);
        tableViewAppointment.setItems(observableList);
        tableViewAppointment.refresh();
    }

    private void clearAllFields() {
        firstNameInput.clear();
        lastNameInput.clear();
        typeInput.getSelectionModel().clearSelection();
        dateInput.getEditor().clear();
        hourInput.clear();
        phoneInput.clear();
        dateSelectedInput.getEditor().clear();
    }

    private void copyRow() {
        Appointment selectedItem = tableViewAppointment.getSelectionModel().getSelectedItem();
        idInput = selectedItem.getId();
        firstNameInput.setText(selectedItem.getFirstName());
        lastNameInput.setText(selectedItem.getLastName());
        typeInput.getSelectionModel().select(String.valueOf(selectedItem.getTypeAppointment()));
        dateInput.setValue(LocalDate.parse(selectedItem.getDateAppointment().toString()));
        String s = String.valueOf(selectedItem.getHourAppointment());
        String onlyHourMin = s.substring(0, Math.min(s.length(), 5));
        hourInput.setText(onlyHourMin);
        // hourInput.setText(String.valueOf(selectedItem.getHourAppointment()));
        phoneInput.setText(selectedItem.getPhone());
    }

}
