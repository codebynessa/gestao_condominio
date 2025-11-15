
/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */
package test.model;

import model.Visitante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitanteTest {

    @Test
    void testRegistrarEntrada() {
        Visitante v = new Visitante("João", "00011122233");

        v.registrarEntrada();

        assertEquals("No condomínio", v.getStatus());
        assertNotNull(v.getHoraEntrada());
    }

    @Test
    void testRegistrarSaida() {
        Visitante v = new Visitante("Maria", "44455566677");

        v.registrarEntrada();
        v.registrarSaida();

        assertEquals("Saiu", v.getStatus());
        assertNotNull(v.getHoraSaida());
    }
}


