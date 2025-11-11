package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class SistemaCondominioTest {
    private SistemaCondominio sistema;
    private Morador morador;

    @BeforeEach
    void setup() {
        sistema = new SistemaCondominio();
        morador = new Morador("Vanessa", "12345678900", "vanessa@email.com", "101", "99999-9999");
        sistema.cadastrarMorador(morador);
    }

    @Test
    void deveCadastrarMorador() {
        assertEquals(1, sistema.getMoradores().size());
        assertEquals("Vanessa", sistema.getMoradores().get(0).getNome());
    }

    @Test
    void deveEditarMorador() {
        sistema.editarMorador("Vanessa", "novo@email.com", "88888-8888");
        Morador m = sistema.pesquisarMorador("Vanessa");
        assertEquals("novo@email.com", m.getEmail());
        assertEquals("88888-8888", m.getTelefone());
    }

    @Test
    void deveRemoverMorador() {
        sistema.removerMorador("Vanessa");
        assertEquals(0, sistema.getMoradores().size());
    }

    @Test
    void devePesquisarMoradorExistente() {
        Morador m = sistema.pesquisarMorador("Vanessa");
        assertNotNull(m);
        assertEquals("Vanessa", m.getNome());
    }

    @Test
    void deveAdicionarTaxaAoMorador() {
        Taxa t = new Taxa("Novembro/2025", 150.00);
        sistema.cadastrarTaxa("Vanessa", t);
        Morador m = sistema.pesquisarMorador("Vanessa");
        assertEquals(1, m.listarTaxas().size());
        assertEquals(150.00, m.calcularValorTotal());
    }

    @Test
    void deveMarcarTaxaComoPaga() {
        Taxa t = new Taxa("Dezembro/2025", 200.00);
        morador.adicionarTaxa(t);
        t.marcarComoPago();
        assertTrue(t.isStatusPagamento());
    }

    @Test
    void deveCalcularTotalCorreto() {
        morador.adicionarTaxa(new Taxa("Água", 50.00));
        morador.adicionarTaxa(new Taxa("Luz", 70.00));
        assertEquals(120.00, morador.calcularValorTotal());
    }

    @Test
    void deveCadastrarVisitante() {
        Visitante v = new Visitante("João", "98765432100");
        sistema.cadastrarVisitante(v);
        assertEquals(1, sistema.getVisitantes().size());
        assertEquals("João", sistema.getVisitantes().get(0).getNome());
    }

    @Test
    void deveRegistrarEntradaESaidaVisitante() {
        Visitante v = new Visitante("Lucas", "00011122233");
        sistema.cadastrarVisitante(v);
        v.registrarEntrada();
        assertEquals("No condomínio", v.getStatus());
        v.registrarSaida();
        assertEquals("Saiu", v.getStatus());
    }

    @Test
    void deveGerarRelatorioFinanceiroSemErro() {
        morador.adicionarTaxa(new Taxa("Janeiro/2025", 100.00));
        assertDoesNotThrow(() -> sistema.gerarRelatorioFinanceiro());
    }

    @Test
    void deveSalvarECarregarDados() {
        // Salvar o sistema em arquivo temporário
        sistema.salvarDados();
        File arquivo = new File("sistema.dat");
        assertTrue(arquivo.exists());

        // Carregar dados e verificar se mantém informações
        SistemaCondominio novoSistema = SistemaCondominio.carregarDados();
        assertFalse(novoSistema.getMoradores().isEmpty());
        assertEquals("Vanessa", novoSistema.getMoradores().get(0).getNome());
    }
}
