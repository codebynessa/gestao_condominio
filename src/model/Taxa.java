/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */
package model;

import java.io.Serializable;

public class Taxa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String mesReferencia;
    private double valor;
    private boolean statusPagamento;

    public Taxa(String mesReferencia, double valor) {
        this.id = (int) (Math.random() * 10000);
        this.mesReferencia = mesReferencia;
        this.valor = valor;
        this.statusPagamento = false;
    }

    public int getId() {
        return id;
    }
    public String getMesReferencia() {
        return mesReferencia;
    }
    public double getValor() {
        return valor;
    }
    public boolean isStatusPagamento() {
        return statusPagamento;
    }

    public void marcarComoPago() {
        this.statusPagamento = true;
    }

    public double calcularValorTotal() {
        return valor;
    }

    @Override
    public String toString() {
        String status = statusPagamento ? "Pago" : "Pendente";
        return mesReferencia + " - " + status + " - R$ " + valor;
    }
}
