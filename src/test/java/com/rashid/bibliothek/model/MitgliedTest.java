package com.rashid.bibliothek.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Prüft Erzeugung, Validierung und Ausleihlimit der Klasse Mitglied.
 */
class MitgliedTest {

    /** Erzeugt ein gültiges Testmitglied. */
    private Mitglied erstelleMitglied() {
        return new Mitglied("M001", "Anna", "Schmidt", "anna@example.de");
    }

    @Test
    void neuesMitgliedHatKeineAusleihen() {
        Mitglied mitglied = erstelleMitglied();
        assertEquals(0, mitglied.getAnzahlAusleihen());
        assertTrue(mitglied.kannAusleihen());
        assertEquals("Anna Schmidt", mitglied.getVollerName());
    }

    @Test
    void leererNachnameWirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mitglied("M002", "Max", " ", "max@example.de"));
    }

    @Test
    void emailOhneAtWirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mitglied("M003", "Max", "Muster", "max.example.de"));
    }

    @Test
    void ausleihlimitWirdEingehalten() {
        Mitglied mitglied = erstelleMitglied();
        for (int i = 0; i < Mitglied.MAX_AUSLEIHEN; i++) {
            mitglied.ausleiheHinzufuegen();
        }
        assertFalse(mitglied.kannAusleihen());
        assertThrows(IllegalStateException.class, mitglied::ausleiheHinzufuegen);
    }

    @Test
    void ausleiheEntfernenVerringertAnzahl() {
        Mitglied mitglied = erstelleMitglied();
        mitglied.ausleiheHinzufuegen();
        mitglied.ausleiheEntfernen();
        assertEquals(0, mitglied.getAnzahlAusleihen());
    }

    @Test
    void entfernenOhneAusleiheWirdAbgelehnt() {
        assertThrows(IllegalStateException.class, erstelleMitglied()::ausleiheEntfernen);
    }

    @Test
    void mitgliederMitGleicherNummerSindGleich() {
        Mitglied a = new Mitglied("M010", "Anna", "A", "a@example.de");
        Mitglied b = new Mitglied("M010", "Ben", "B", "b@example.de");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}