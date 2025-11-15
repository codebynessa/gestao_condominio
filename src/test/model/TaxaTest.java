package test.model;

/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */

import model.Taxa;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaxaTest {

    @Test
    void testMarcarComoPago() {
        Taxa taxa = new Taxa("Janeiro", 150.0);
        assertFalse(taxa.isStatusPagamento());

        taxa.marcarComoPago();

        assertTrue(taxa.isStatusPagamento());
    }

    @Test
    void testGetMesValor() {
        Taxa taxa = new Taxa("Fevereiro", 200.0);

        assertEquals("Fevereiro", taxa.getMesReferencia());
        assertEquals(200.0, taxa.getValor());
    }
}
