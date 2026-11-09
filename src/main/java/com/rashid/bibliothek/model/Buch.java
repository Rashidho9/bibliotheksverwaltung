package com.rashid.bibliothek.model;

import java.util.Objects;

/**
 * Fachklasse für ein Buch im Bestand der Bibliothek.
 * Die ISBN identifiziert ein Buch eindeutig und ist daher unveränderlich.
 * Der Ausleihstatus wird nur über ausleihen() und zurueckgeben() geändert.
 */
public class Buch {

    private final String isbn;
    private String titel;
    private String autor;
    private int erscheinungsjahr;
    private boolean ausgeliehen;

    /**
     * Erzeugt ein neues, verfügbares Buch und prüft alle Pflichtangaben.
     *
     * @param isbn             eindeutige ISBN, nicht leer
     * @param titel            Buchtitel, nicht leer
     * @param autor            Name des Autors, nicht leer
     * @param erscheinungsjahr Jahr zwischen 1450 und 2100
     * @throws IllegalArgumentException bei ungültigen Angaben
     */
    public Buch(String isbn, String titel, String autor, int erscheinungsjahr) {
        this.isbn = pruefeText(isbn, "ISBN");
        setTitel(titel);
        setAutor(autor);
        setErscheinungsjahr(erscheinungsjahr);
        this.ausgeliehen = false;
    }

    /**
     * Markiert das Buch als ausgeliehen.
     *
     * @throws IllegalStateException wenn das Buch bereits ausgeliehen ist
     */
    public void ausleihen() {
        if (ausgeliehen) {
            throw new IllegalStateException("Das Buch ist bereits ausgeliehen.");
        }
        ausgeliehen = true;
    }

    /**
     * Markiert das Buch wieder als verfügbar.
     *
     * @throws IllegalStateException wenn das Buch nicht ausgeliehen war
     */
    public void zurueckgeben() {
        if (!ausgeliehen) {
            throw new IllegalStateException("Das Buch ist nicht ausgeliehen.");
        }
        ausgeliehen = false;
    }

    /** Liefert die eindeutige ISBN. */
    public String getIsbn() {
        return isbn;
    }

    /** Liefert den Titel. */
    public String getTitel() {
        return titel;
    }

    /** Ändert den Titel nach Prüfung auf leere Eingaben. */
    public void setTitel(String titel) {
        this.titel = pruefeText(titel, "Titel");
    }

    /** Liefert den Autor. */
    public String getAutor() {
        return autor;
    }

    /** Ändert den Autor nach Prüfung auf leere Eingaben. */
    public void setAutor(String autor) {
        this.autor = pruefeText(autor, "Autor");
    }

    /** Liefert das Erscheinungsjahr. */
    public int getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    /**
     * Ändert das Erscheinungsjahr.
     *
     * @throws IllegalArgumentException bei Jahren außerhalb von 1450 bis 2100
     */
    public void setErscheinungsjahr(int erscheinungsjahr) {
        if (erscheinungsjahr < 1450 || erscheinungsjahr > 2100) {
            throw new IllegalArgumentException("Erscheinungsjahr muss zwischen 1450 und 2100 liegen.");
        }
        this.erscheinungsjahr = erscheinungsjahr;
    }

    /** Liefert true, wenn das Buch aktuell ausgeliehen ist. */
    public boolean isAusgeliehen() {
        return ausgeliehen;
    }

    /**
     * Prüft einen Pflichttext und entfernt Leerzeichen am Anfang und Ende.
     */
    private static String pruefeText(String wert, String feldname) {
        if (wert == null || wert.trim().isEmpty()) {
            throw new IllegalArgumentException(feldname + " darf nicht leer sein.");
        }
        return wert.trim();
    }

    /** Zwei Bücher sind gleich, wenn ihre ISBN übereinstimmt. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Buch)) return false;
        Buch anderes = (Buch) o;
        return isbn.equals(anderes.isbn);
    }

    /** Passend zu equals() basiert der Hashcode nur auf der ISBN. */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    /** Liefert eine lesbare Kurzbeschreibung für die Konsole. */
    @Override
    public String toString() {
        return titel + " von " + autor + " (" + erscheinungsjahr + "), ISBN " + isbn
                + (ausgeliehen ? " – ausgeliehen" : " – verfügbar");
    }
}