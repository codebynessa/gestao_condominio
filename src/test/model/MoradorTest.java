
/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */

package test.model;

import model.Morador;
import model.Taxa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoradorTest {

    @Test
    void testAdicionarTaxa() {
        Morador m = new Morador("Ana", "11122233344", "ana@email", "101", "99999");
        Taxa t = new Taxa("Março", 100.0);

        m.adicionarTaxa(t);

        assertEquals(1, m.listarTaxas().size());
        assertEquals(100.0, m.calcularValorTotal());
    }

    @Test
    void testSituacaoFinanceira() {
        Morador m = new Morador("Carlos", "123", "a@a", "102", "999");

        m.adicionarTaxa(new Taxa("Abril", 150.0));
        assertEquals("Possui pendências", m.getSituacaoFinanceira());
    }

    @Test
    void testSituacaoAposPagamento() {
        Morador m = new Morador("Rafa", "999", "r@r", "202", "777");

        Taxa t = new Taxa("Junho", 80.0);
        m.adicionarTaxa(t);

        t.marcarComoPago();

        assertEquals("Em dia", m.getSituacaoFinanceira());
        assertEquals(0.0, m.calcularValorTotal());
    }
}

