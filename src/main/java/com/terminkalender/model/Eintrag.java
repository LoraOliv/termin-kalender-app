package com.terminkalender.model;

import java.time.LocalDate;
import java.time.LocalTime;

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class Eintrag {

    private String titel;
    private String beschreibung;
    private LocalTime startzeit;
    private LocalTime endzeit;
    private LocalDate datum;
    private String ort;

    // Konstruktor
    public Eintrag(String titel, String beschreibung, LocalTime startzeit, LocalTime endzeit, LocalDate datum, String ort) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
        this.datum = datum;
        this.ort = ort;
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

    public LocalTime getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(LocalTime startzeit) {
        this.startzeit = startzeit;
    }

    public LocalTime getEndzeit() {
        return endzeit;
    }

    public void setEndzeit(LocalTime endzeit) {
        this.endzeit = endzeit;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
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
        return title +
                beschreibung +
                "Start: " + startzeit.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
                + " Ende: " + endzeit.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) +
                " Ort: " + ort;
    }
}
