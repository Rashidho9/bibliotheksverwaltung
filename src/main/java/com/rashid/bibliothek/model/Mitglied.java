package com.rashid.bibliothek.model;

import java.util.Objects;

/**
 * Fachklasse für ein Mitglied der Bibliothek.
 * Die Mitgliedsnummer identifiziert ein Mitglied eindeutig und ist unveränderlich.
 * Ein Mitglied darf höchstens MAX_AUSLEIHEN Bücher gleichzeitig ausleihen.
 */
public class Mitglied {

    /** Höchstzahl gleichzeitig ausgeliehener Bücher pro Mitglied. */
    public static final int MAX_AUSLEIHEN = 3;

    private final String mitgliedsnummer;
    private String vorname;
    private String nachname;
    private String email;
    private int anzahlAusleihen;

    /**
     * Erzeugt ein neues Mitglied ohne laufende Ausleihen.
     *
     * @param mitgliedsnummer eindeutige Nummer, nicht leer
     * @param vorname         Vorname, nicht leer
     * @param nachname        Nachname, nicht leer
     * @param email           E-Mail-Adresse mit einem @-Zeichen
     * @throws IllegalArgumentException bei ungültigen Angaben
     */
    public Mitglied(String mitgliedsnummer, String vorname, String nachname, String email) {
        this.mitgliedsnummer = pruefeText(mitgliedsnummer, "Mitgliedsnummer");
        setVorname(vorname);
        setNachname(nachname);
        setEmail(email);
        this.anzahlAusleihen = 0;
    }

    /** Liefert true, wenn das Mitglied noch ein weiteres Buch ausleihen darf. */
    public boolean kannAusleihen() {
        return anzahlAusleihen < MAX_AUSLEIHEN;
    }

    /**
     * Erhöht die Zahl laufender Ausleihen um eins.
     *
     * @throws IllegalStateException wenn das Ausleihlimit erreicht ist
     */
    public void ausleiheHinzufuegen() {
        if (!kannAusleihen()) {
            throw new IllegalStateException(
                    "Ausleihlimit von " + MAX_AUSLEIHEN + " Büchern erreicht.");
        }
        anzahlAusleihen++;
    }

    /**
     * Verringert die Zahl laufender Ausleihen um eins.
     *
     * @throws IllegalStateException wenn keine Ausleihe vorhanden ist
     */
    public void ausleiheEntfernen() {
        if (anzahlAusleihen == 0) {
            throw new IllegalStateException("Das Mitglied hat keine laufende Ausleihe.");
        }
        anzahlAusleihen--;
    }

    /** Liefert die eindeutige Mitgliedsnummer. */
    public String getMitgliedsnummer() {
        return mitgliedsnummer;
    }

    /** Liefert den Vornamen. */
    public String getVorname() {
        return vorname;
    }

    /** Ändert den Vornamen nach Prüfung auf leere Eingaben. */
    public void setVorname(String vorname) {
        this.vorname = pruefeText(vorname, "Vorname");
    }

    /** Liefert den Nachnamen. */
    public String getNachname() {
        return nachname;
    }

    /** Ändert den Nachnamen nach Prüfung auf leere Eingaben. */
    public void setNachname(String nachname) {
        this.nachname = pruefeText(nachname, "Nachname");
    }

    /** Liefert Vor- und Nachnamen zusammen. */
    public String getVollerName() {
        return vorname + " " + nachname;
    }

    /** Liefert die E-Mail-Adresse. */
    public String getEmail() {
        return email;
    }

    /**
     * Ändert die E-Mail-Adresse nach einer einfachen Formatprüfung.
     *
     * @throws IllegalArgumentException wenn die Adresse leer ist oder kein @ enthält
     */
    public void setEmail(String email) {
        String wert = pruefeText(email, "E-Mail");
        if (!wert.contains("@")) {
            throw new IllegalArgumentException("E-Mail muss ein @ enthalten.");
        }
        this.email = wert;
    }

    /** Liefert die Zahl der aktuell ausgeliehenen Bücher. */
    public int getAnzahlAusleihen() {
        return anzahlAusleihen;
    }

    /** Prüft einen Pflichttext und entfernt Leerzeichen am Anfang und Ende. */
    private static String pruefeText(String wert, String feldname) {
        if (wert == null || wert.trim().isEmpty()) {
            throw new IllegalArgumentException(feldname + " darf nicht leer sein.");
        }
        return wert.trim();
    }

    /** Zwei Mitglieder sind gleich, wenn ihre Mitgliedsnummer übereinstimmt. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mitglied)) return false;
        Mitglied anderes = (Mitglied) o;
        return mitgliedsnummer.equals(anderes.mitgliedsnummer);
    }

    /** Passend zu equals() basiert der Hashcode nur auf der Mitgliedsnummer. */
    @Override
    public int hashCode() {
        return Objects.hash(mitgliedsnummer);
    }

    /** Liefert eine lesbare Kurzbeschreibung für die Konsole. */
    @Override
    public String toString() {
        return getVollerName() + " (" + mitgliedsnummer + "), "
                + anzahlAusleihen + "/" + MAX_AUSLEIHEN + " Ausleihen";
    }
}