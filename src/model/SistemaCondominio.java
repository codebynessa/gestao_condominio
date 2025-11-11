package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaCondominio implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ARQUIVO = "sistema.dat";

    private List<Morador> moradores;
    private List<Visitante> visitantes;

    public SistemaCondominio() {
        this.moradores = new ArrayList<>();
        this.visitantes = new ArrayList<>();
    }

    // ---------- MORADORES ----------
    public void cadastrarMorador(Morador morador) {
        moradores.add(morador);
        salvarDados();
    }

    public void editarMorador(String nome, String novoEmail, String novoTelefone) {
        Morador m = pesquisarMorador(nome);
        if (m != null) {
            m.setEmail(novoEmail);
            m.setTelefone(novoTelefone);
            salvarDados();
        } else {
            System.out.println("❌ Morador não encontrado!");
        }
    }

    public void removerMorador(String nome) {
        Morador m = pesquisarMorador(nome);
        if (m != null) {
            moradores.remove(m);
            salvarDados();
        } else {
            System.out.println("❌ Morador não encontrado!");
        }
    }

    public Morador pesquisarMorador(String nome) {
        return moradores.stream()
                .filter(m -> m.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    // ---------- TAXAS ----------
    public void cadastrarTaxa(String nomeMorador, Taxa taxa) {
        Morador m = pesquisarMorador(nomeMorador);
        if (m != null) {
            m.adicionarTaxa(taxa);
            salvarDados();
        } else {
            System.out.println("❌ Morador não encontrado!");
        }
    }

    public void gerarRelatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        for (Morador m : moradores) {
            System.out.printf("%s - Total Devido: R$ %.2f - Situação: %s%n",
                    m.getNome(), m.calcularValorTotal(), m.getSituacaoFinanceira());
        }
    }

    // ---------- VISITANTES ----------
    public void cadastrarVisitante(Visitante visitante) {
        visitantes.add(visitante);
        salvarDados();
    }

    // ---------- PERSISTÊNCIA ----------
    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }

    public static SistemaCondominio carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (SistemaCondominio) ois.readObject();
        } catch (Exception e) {
            System.out.println("⚠️ Nenhum dado encontrado, iniciando novo sistema...");
            return new SistemaCondominio();
        }
    }

    // ---------- GETTERS ----------
    public List<Morador> getMoradores() { return moradores; }
    public List<Visitante> getVisitantes() { return visitantes; }
}
