package test.model;

import model.Morador;
import model.SistemaCondominio;
import model.Taxa;
import model.Visitante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaCondominioTest {

    @Test
    void testCadastrarMorador() {
        SistemaCondominio s = new SistemaCondominio();

        Morador m = new Morador("Vanessa", "123", "v@v", "103", "999");
        s.cadastrarMorador(m);

        assertEquals(1, s.getMoradores().size());
    }

    @Test
    void testPesquisarMorador() {
        SistemaCondominio s = new SistemaCondominio();

        Morador m = new Morador("Tamires", "444", "t@t", "203", "111");
        s.cadastrarMorador(m);

        Morador achado = s.pesquisarMorador("Tamires");

        assertNotNull(achado);
        assertEquals("Tamires", achado.getNome());
    }

    @Test
    void testCadastrarVisitante() {
        SistemaCondominio s = new SistemaCondominio();

        Visitante v = new Visitante("Lucas", "000");
        s.cadastrarVisitante(v);

        assertEquals(1, s.getVisitantes().size());
    }

    @Test
    void testCadastrarTaxa() {
        SistemaCondominio s = new SistemaCondominio();

        Morador m = new Morador("Bruno", "987", "b@b", "405", "333");
        s.cadastrarMorador(m);

        s.cadastrarTaxa("Bruno", new Taxa("Julho", 150.0));

        assertEquals(1, m.listarTaxas().size());
    }
}
