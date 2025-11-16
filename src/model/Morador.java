/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Morador implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorId = 1;

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String apartamento;
    private String telefone;
    private List<Taxa> taxas;
    private List<Visitante> visitantes;

    public Morador(String nome, String cpf, String email, String apartamento, String telefone) {
        this.id = contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.apartamento = apartamento;
        this.telefone = telefone;
        this.taxas = new ArrayList<>();
        this.visitantes = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getEmail() {
        return email;
    }
    public String getApartamento() {
        return apartamento;
    }
    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Metodos de dominio
    public void editarMorador(String nome, String cpf, String email, String apartamento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.apartamento = apartamento;
        this.telefone = telefone;
    }

    public void removerMorador() {
        System.out.println("Morador removido: " + nome);
    }

    public List<Taxa> listarTaxas() {
        return taxas;
    }

    public List<Visitante> listarVisitantes() {
        return visitantes;
    }

    public String getSituacaoFinanceira() {
        boolean pendente = taxas.stream().anyMatch(t -> !t.isStatusPagamento());
        return pendente ? "Possui pendências" : "Em dia";
    }

    public void adicionarTaxa(Taxa taxa) {
        taxas.add(taxa);
    }

    public void adicionarVisitante(Visitante v) {
        visitantes.add(v);
    }

    public double calcularValorTotal() {
        return taxas.stream()
                .filter(t -> !t.isStatusPagamento())   //  soma taxas pendentes
                .mapToDouble(Taxa::getValor)
                .sum();
    }

    @Override
    public String toString() {
        return String.format("%s (Apto %s) - %s - Total devido: R$ %.2f",
                nome, apartamento, getSituacaoFinanceira(), calcularValorTotal());
    }
}