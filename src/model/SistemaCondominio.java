/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */

package model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaCondominio implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String ARQUIVO = "sistema.dat";
    private static final String HISTORICO = "historico.log";
    private static final String BACKUP_JSON = "backup.json";

    private List<Morador> moradores;
    private List<Visitante> visitantes;

    public SistemaCondominio() {
        this.moradores = new ArrayList<>();
        this.visitantes = new ArrayList<>();
    }


    // MORADORES
    public void cadastrarMorador(Morador morador) {
        moradores.add(morador);
        registrarHistorico("Cadastrado morador: " + morador.getNome());
        salvarDados();
        System.out.println(" Morador cadastrado: " + morador.getNome());
    }

    public void editarMorador(String nome, String novoEmail, String novoTelefone) {
        Morador m = pesquisarMorador(nome);
        if (m != null) {
            m.setEmail(novoEmail);
            m.setTelefone(novoTelefone);
            registrarHistorico("Editado morador: " + nome);
            salvarDados();
            System.out.println(" Morador atualizado: " + nome);
        } else System.out.println(" Morador não encontrado!");
    }

    public void removerMorador(String nome) {
        Morador m = pesquisarMorador(nome);
        if (m != null) {
            moradores.remove(m);
            registrarHistorico("Removido morador: " + nome);
            salvarDados();
            System.out.println(" Morador removido: " + nome);
        } else System.out.println(" Morador não encontrado!");
    }

    public Morador pesquisarMorador(String nome) {
        if (nome == null) return null;
        return moradores.stream()
                .filter(m -> m.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    // Busca inteligente
    public List<Morador> buscar(String termo) {
        if (termo == null) return Collections.emptyList();

        String t = termo.toLowerCase();

        return moradores.stream()
                .filter(m ->
                        m.getNome().toLowerCase().contains(t) ||
                                m.getCpf().toLowerCase().contains(t) ||
                                m.getApartamento().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }

    // TAXAS
    public void cadastrarTaxa(String nomeMorador, Taxa taxa) {
        Morador m = pesquisarMorador(nomeMorador);
        if (m != null) {
            m.adicionarTaxa(taxa);
            registrarHistorico("Taxa adicionada para " + nomeMorador);
            salvarDados();
            System.out.println(" Taxa adicionada ao morador: " + nomeMorador);
        } else System.out.println(" Morador não encontrado!");
    }

    public void marcarTaxaComoPaga(String nomeMorador, int indiceTaxa) {
        Morador m = pesquisarMorador(nomeMorador);
        if (m == null) {
            System.out.println(" Morador não encontrado!");
            return;
        }

        List<Taxa> taxas = m.listarTaxas();

        if (indiceTaxa < 0 || indiceTaxa >= taxas.size()) {
            System.out.println(" Índice inválido!");
            return;
        }

        Taxa t = taxas.get(indiceTaxa);
        t.marcarComoPago();

        registrarHistorico("Taxa marcada como paga para " + nomeMorador);
        salvarDados();
        System.out.println(" Taxa marcada como paga!");
    }

    // Metodo usado no MAIN
    public void menuMarcarTaxaComoPaga(Scanner sc, String nomeMorador) {
        Morador m = pesquisarMorador(nomeMorador);

        if (m == null) {
            System.out.println(" Morador não encontrado!");
            return;
        }

        List<Taxa> taxas = m.listarTaxas();
        if (taxas.isEmpty()) {
            System.out.println("⚠ Nenhuma taxa cadastrada.");
            return;
        }

        System.out.println("\nTaxas de " + m.getNome() + ":");
        for (int i = 0; i < taxas.size(); i++) {
            Taxa t = taxas.get(i);
            System.out.println((i + 1) + ") " + t.getMesReferencia() +
                    " - " + (t.isStatusPagamento() ? "Pago" : "Pendente") +
                    " - R$ " + t.getValor());
        }

        System.out.print("Escolha a taxa para marcar como paga: ");
        int opc = -1;
        try { opc = Integer.parseInt(sc.nextLine()) - 1; } catch (Exception ignored) {}

        marcarTaxaComoPaga(nomeMorador, opc);
    }

    public List<Morador> listarMoradoresEmAtraso() {
        return moradores.stream()
                .filter(m -> !"Em dia".equalsIgnoreCase(m.getSituacaoFinanceira()))
                .collect(Collectors.toList());
    }

    public void gerarRelatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        moradores.forEach(m ->
                System.out.printf("%s - Total: R$ %.2f - %s%n",
                        m.getNome(), m.calcularValorTotal(), m.getSituacaoFinanceira()));
    }


    // VISITANTES

    public void cadastrarVisitante(Visitante visitante) {
        visitantes.add(visitante);
        registrarHistorico("Visitante registrado: " + visitante.getNome());
        salvarDados();
        System.out.println(" Visitante cadastrado: " + visitante.getNome());

    }

    public Visitante encontrarVisitante(String nome) {
        return visitantes.stream()
                .filter(v -> v.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public List<Visitante> listarVisitantesPresentes() {
        return visitantes.stream()
                .filter(v -> v.getStatus().equalsIgnoreCase("No condomínio"))
                .collect(Collectors.toList());
    }


    // DASHBOARD

    public void exibirDashboard() {
        System.out.println("\n=== DASHBOARD DO CONDOMÍNIO ===");
        System.out.println("- Moradores registrados: " + moradores.size());
        System.out.println("- Visitantes presentes: " + listarVisitantesPresentes().size());
        long pendentes = moradores.stream()
                .flatMap(m -> m.listarTaxas().stream())
                .filter(t -> !t.isStatusPagamento())
                .count();
        System.out.println("- Taxas pendentes: " + pendentes);
    }


    // BOLETO

    public void gerarBoleto(String nomeMorador) {
        Morador m = pesquisarMorador(nomeMorador);
        if (m == null) {
            System.out.println(" Morador não encontrado!");
            return;
        }
        System.out.println("\n=== BOLETO ===");
        System.out.println("Morador: " + m.getNome());
        System.out.printf("Valor: R$ %.2f%n", m.calcularValorTotal());
        System.out.println("Vencimento: " + LocalDateTime.now().plusDays(10).toLocalDate());
    }


    // EXPORTAÇÃO (TXT / CSV)

    public void exportarRelatorioTXT() {
        try (PrintWriter w = new PrintWriter("relatorio.txt")) {
            w.println("RELATÓRIO - " + LocalDateTime.now());
            for (Morador m : moradores) {
                w.println("Morador: " + m.getNome());
                w.println("Apartamento: " + m.getApartamento());
                w.printf("Total devido: R$ %.2f%n", m.calcularValorTotal());
                w.println("---------------------------");
            }
            System.out.println(" Arquivo gerado: relatorio.txt");
        } catch (Exception e) {
            System.out.println(" Erro ao exportar TXT");
        }
    }

    public void exportarRelatorioCSV() {
        try (PrintWriter w = new PrintWriter("relatorio.csv")) {
            w.println("nome,apartamento,total_devido");
            for (Morador m : moradores) {
                w.printf("%s,%s,%.2f%n",
                        m.getNome(), m.getApartamento(), m.calcularValorTotal());
            }
            System.out.println(" Arquivo gerado: relatorio.csv");
        } catch (Exception e) {
            System.out.println(" Erro ao exportar CSV");
        }
    }


    // BACKUP JSON

    public void gerarBackupJSON() {
        try (PrintWriter w = new PrintWriter("backup.json")) {
            w.println("{ \"moradores\": [");
            for (int i = 0; i < moradores.size(); i++) {
                Morador m = moradores.get(i);
                w.println("  {");
                w.println("    \"nome\": \"" + m.getNome() + "\",");
                w.println("    \"cpf\": \"" + m.getCpf() + "\",");
                w.println("    \"apartamento\": \"" + m.getApartamento() + "\"");
                w.print("  }" + (i < moradores.size() - 1 ? "," : ""));
                w.println();
            }
            w.println("]}");
            System.out.println(" Backup JSON gerado!");
        } catch (Exception e) {
            System.out.println(" Erro ao gerar JSON");
        }
    }

    // HISTÓRICO

    public void registrarHistorico(String texto) {
        try (PrintWriter w = new PrintWriter(new FileWriter(HISTORICO, true))) {
            w.println(LocalDateTime.now() + " - " + texto);
        } catch (Exception ignored) {}
    }

    public void imprimirHistorico() {
        try (BufferedReader br = new BufferedReader(new FileReader(HISTORICO))) {
            System.out.println("\n=== HISTÓRICO ===");
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (Exception e) {
            System.out.println("Nenhum histórico encontrado.");
        }
    }


    // SALVAR

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(this);
        } catch (Exception e) {
            System.out.println(" Erro ao salvar dados!");
        }
    }

    public static SistemaCondominio carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (SistemaCondominio) ois.readObject();
        } catch (Exception e) {
            System.out.println(" Nenhum dado encontrado. Criando novo sistema...");
            return new SistemaCondominio();
        }
    }


    // GETTERS

    public List<Morador> getMoradores() {
        return moradores;
    }
    public List<Visitante> getVisitantes() {
        return visitantes;
    }
}
