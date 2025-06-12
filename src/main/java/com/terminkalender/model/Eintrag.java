package com.terminkalender.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Eintrag {

    private String titel;
    private String beschreibung;
    private LocalDateTime startzeit;
    private LocalDateTime endzeit;
    private LocalDateTime datum;
    private String ort;

    // Konstruktor
    public Eintrag(String titel, String beschreibung, LocalDateTime startzeit, LocalDateTime endzeit, LocalDateTime datum, String ort) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
        this.datum = datum;
        this.ort = ort;
    }

    public Eintrag(String titel, String beschreibung, LocalDateTime startzeit, LocalDateTime endzeit) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.startzeit = null;
        this.endzeit = null;

    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDateTime getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(LocalDateTime startzeit) {
        this.startzeit = startzeit;
    }

    public LocalDateTime getEndzeit() {
        return endzeit;
    }

    public void setEndzeit(LocalDateTime endzeit) {
        this.endzeit = endzeit;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }


    @Override
    public String toString() {
        return titel +
                beschreibung +
                "Start: " + startzeit.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
                + " Ende: " + endzeit.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) +
                " Ort: " + ort;
    }
}
