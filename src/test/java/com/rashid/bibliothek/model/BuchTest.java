package com.rashid.bibliothek.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Prüft Erzeugung, Validierung und Ausleihstatus der Klasse Buch.
 */
class BuchTest {

    /** Erzeugt ein gültiges Testbuch. */
    private Buch erstelleBuch() {
        return new Buch("978-3-16-148410-0", "Clean Code", "Robert C. Martin", 2008);
    }

    @Test
    void neuesBuchIstVerfuegbar() {
        Buch buch = erstelleBuch();
        assertFalse(buch.isAusgeliehen());
        assertEquals("Clean Code", buch.getTitel());
    }

    @Test
    void leererTitelWirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Buch("123", "  ", "Autor", 2020));
    }

    @Test
    void ungueltigesJahrWirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Buch("123", "Titel", "Autor", 1200));
    }

    @Test
    void ausleihenUndZurueckgebenAendernStatus() {
        Buch buch = erstelleBuch();
        buch.ausleihen();
        assertTrue(buch.isAusgeliehen());
        buch.zurueckgeben();
        assertFalse(buch.isAusgeliehen());
    }

    @Test
    void doppeltesAusleihenWirdAbgelehnt() {
        Buch buch = erstelleBuch();
        buch.ausleihen();
        assertThrows(IllegalStateException.class, buch::ausleihen);
    }

    @Test
    void zurueckgebenOhneAusleiheWirdAbgelehnt() {
        assertThrows(IllegalStateException.class, erstelleBuch()::zurueckgeben);
    }

    @Test
    void buecherMitGleicherIsbnSindGleich() {
        Buch a = new Buch("111", "Titel A", "Autor A", 2000);
        Buch b = new Buch("111", "Titel B", "Autor B", 2010);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}