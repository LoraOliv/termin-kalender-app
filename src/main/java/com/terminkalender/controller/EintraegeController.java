package com.terminkalender.controller;
import com.terminkalender.model.Eintrag;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.format.DateTimeParseException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EintraegeController {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField startTimeField;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField endTimeField;

    private Stage dialogStage;
    private Eintrag appointment;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEintrag(Eintrag eintrag) {
        this.eintrag = eintrag;

        titleField.setText(eintrag.getTitel());
        descriptionArea.setText(eintrag.getBeschreibung());
        startDatePicker.setValue(eintrag.getStartzeit().toLocalDate());
        startTimeField.setText(eintrag.getStartzeit().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        endDatePicker.setValue(eintrag.getEndzeit().toLocalDate());
        endTimeField.setText(eintrag.getEndzeit().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
    }

    public Eintrag getAppointment() {
        return appointment;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            // Wenn der Termin neu ist oder bearbeitet wird, aktualisieren Sie ihn
            if (appointment == null) {
                // Dies sollte nicht passieren, wenn setAppointment korrekt aufgerufen wird
                // Aber zur Sicherheit initialisieren wir es hier.
                appointment = new Eintrag("", "", null, null);
            }

            appointment.setTitel(titleField.getText());
            appointment.setBeschreibung(descriptionArea.getText());

            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(startTimeField.getText());
            appointment.setStartzeit(LocalDateTime.of(startDate, startTime));

            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = LocalTime.parse(endTimeField.getText());
            appointment.setEndzeit(LocalDateTime.of(endDate, endTime));

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().isEmpty()) {
            errorMessage += "Kein gültiger Titel!\n";
        }
        if (startDatePicker.getValue() == null) {
            errorMessage += "Kein gültiges Startdatum!\n";
        }
        if (endDatePicker.getValue() == null) {
            errorMessage += "Kein gültiges Enddatum!\n";
        }
        try {
            LocalTime.parse(startTimeField.getText());
        } catch (DateTimeParseException e) {
            errorMessage += "Ungültiges Startzeit-Format (HH:mm)!\n";
        }
        try {
            LocalTime.parse(endTimeField.getText());
        } catch (DateTimeParseException e) {
            errorMessage += "Ungültiges Endzeit-Format (HH:mm)!\n";
        }

        if (errorMessage.isEmpty()) {
            // Zusätzliche Logik: Prüfen, ob Endzeit nach Startzeit liegt
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeField.getText()));
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeField.getText()));

            if (end.isBefore(start)) {
                errorMessage += "Endzeit muss nach Startzeit liegen!\n";
            }
        }


        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungültige Eingaben");
            alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
