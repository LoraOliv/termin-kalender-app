package com.terminkalender.controller;

import com.terminkalender.model.Eintrag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

//public class KalenderController {
     //   @FXML
     // private Label welcomeText;
     //@FXML
    //protected void onHelloButtonClick() {welcomeText.setText("Willkomen!");}}

public class KalenderController {

    @FXML
    private Label dateLabel;
    @FXML
    private ListView<Eintrag> appointmentListView;

    private ObservableList<Eintrag> appointments = FXCollections.observableArrayList();
    private LocalDate currentDisplayedDate;

    @FXML
    public void initialize() {
        currentDisplayedDate = LocalDate.now();
        updateDateLabel();
        loadAppointmentsForDate(currentDisplayedDate);

        // Beispiel-Termine (in einer echten App würden diese aus einer Datenbank/Datei geladen)
        appointments.addAll(
                new Eintrag("Meeting", "Besprechung mit Team A",
                LocalDateTime.of(currentDisplayedDate, LocalTime.of(10, 0)),
                LocalDateTime.of(currentDisplayedDate, LocalTime.of(11, 0)),
                        LocalDateTime.of(currentDisplayedDate, LocalTime.of(11, 0)),
                        "Büro"), // Beispiel-Ort
                new Eintrag("Projektarbeit", "Feature X implementieren",
                        LocalDateTime.of(currentDisplayedDate, LocalTime.of(14, 0)),
                        LocalDateTime.of(currentDisplayedDate, LocalTime.of(16, 0)),
                        LocalDateTime.of(currentDisplayedDate, LocalTime.of(11, 0)),
                        "Home Office") // Beispiel-Ort
        );
        filterAppointmentsByDate(currentDisplayedDate);
    }

    private void updateDateLabel() {
        dateLabel.setText(currentDisplayedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    private void loadAppointmentsForDate(LocalDate date) {
        // In einer echten Anwendung würden Sie hier Termine für das gegebene Datum aus einer Datenquelle laden.
        // Für dieses Beispiel verwenden wir die bereits geladenen Termine und filtern sie.
        filterAppointmentsByDate(date);
    }

    private void filterAppointmentsByDate(LocalDate date) {
        List<Eintrag> filteredList = appointments.stream()
                .filter(a -> a.getStartzeit().toLocalDate().equals(date))
                .collect(Collectors.toList());
        appointmentListView.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    private void gehtZuVorherigerTag() { //Geht zu der vorheriger Tag / Go to the previous day.
        currentDisplayedDate = currentDisplayedDate.minusDays(1);
        updateDateLabel();
        loadAppointmentsForDate(currentDisplayedDate);
    }

    @FXML
    private void gehtZuVorherigeWoche() { //Geht zu die vorherige Woche / Go to the previous week.
        currentDisplayedDate = currentDisplayedDate.minusDays(7);
        updateDateLabel();
        loadAppointmentsForDate(currentDisplayedDate);
    }

    @FXML
    private void gehtZuNaechsterTag() { //Geht zu der nächster Tag / Go to the next day.
        currentDisplayedDate = currentDisplayedDate.plusDays(1);
        updateDateLabel();
        loadAppointmentsForDate(currentDisplayedDate);
    }

    @FXML
    private void gehtZuNaechsterWoche() { //Geht zu die nächste Woche / Go to the next week.
        currentDisplayedDate = currentDisplayedDate.minusDays(7);
        updateDateLabel();
        loadAppointmentsForDate(currentDisplayedDate);
    }

    @FXML
    private void TerminHinzufuegen() { // ein neuer Termin hinzufügen / Add an new appointment.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eintraege-form.fxml"));
            Parent root = loader.load();

            EintraegeController controller = loader.getController();
            // Dem Formular-Controller den Stage übergeben, damit er ihn schließen kann
            Stage formStage = new Stage(); // Eine neue Stage für das Formular
            formStage.setTitle("Termin hinzufügen");
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setScene(new Scene(root));
            controller.setDialogStage(formStage);
            // Optional: Setzen Sie ein Standarddatum für das neue Termin
            controller.setEintrag(new Eintrag("", "",
                    LocalDateTime.of(currentDisplayedDate, LocalTime.now()),
                    LocalDateTime.of(currentDisplayedDate, LocalTime.now().plusHours(1))));


            formStage.showAndWait(); // showAndWait auf der Formular-Stage

            // Nach dem Schließen des Formulars prüfen, ob ein Termin hinzugefügt wurde
            if (controller.isSaveClicked()) { // 'controller' ist der Instanz-Controller, den wir geladen haben
                Eintrag newEintrag = controller.getEintrag(); // Richtig: getEintrag auf der Instanz des Controllers aufrufen
                if (newEintrag != null) { // Überprüfen, ob wirklich ein Eintrag zurückgegeben wurde
                    appointments.add(newEintrag); // Richtig: Fügen Sie den Eintrag der ObservableList hinzu
                    filterAppointmentsByDate(currentDisplayedDate); // Liste aktualisieren
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditAppointment() {
        Eintrag selectedAppointment = appointmentListView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("eintraege-form.fxml"));
                Parent root = loader.load();

                EintraegeController controller = loader.getController(); // Richtig: Controller des Formulars laden
                // Dem Formular-Controller den Stage übergeben
                Stage formStage = new Stage(); // Eine neue Stage für das Formular
                formStage.setTitle("Termin bearbeiten");
                formStage.initModality(Modality.APPLICATION_MODAL);
                formStage.setScene(new Scene(root));
                controller.setDialogStage(formStage); // Wichtig: Setzen Sie die Dialog Stage im Formular-Controller

                controller.setEintrag(selectedAppointment); // Richtig: Termin zum Bearbeiten übergeben

                formStage.showAndWait(); // showAndWait auf der Formular-Stage

                // Nach dem Schließen des Formulars prüfen, ob der Termin gespeichert wurde
                if (controller.isSaveClicked()) { // Richtig: Instanzmethode des Formular-Controllers aufrufen
                    // Der Termin im Model wurde bereits direkt im Formularcontroller aktualisiert
                    // Wir müssen nur die ListView aktualisieren, um die Änderungen anzuzeigen
                    filterAppointmentsByDate(currentDisplayedDate);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Kein Termin ausgewählt");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie einen Termin zum Bearbeiten aus.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteAppointment() {
        Eintrag selectedAppointment = appointmentListView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Termin löschen");
            alert.setHeaderText(null);
            alert.setContentText("Möchten Sie diesen Termin wirklich löschen?\n" + selectedAppointment.getTitel());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    appointments.remove(selectedAppointment);
                    filterAppointmentsByDate(currentDisplayedDate);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Kein Termin ausgewählt");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie einen Termin zum Löschen aus.");
            alert.showAndWait();
        }
    }
}
