package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Visitante implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataVisita;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private Morador morador;
    private String status;

    public Visitante(String nome, String cpf) {
        this.id = (int) (Math.random() * 10000);
        this.nome = nome;
        this.cpf = cpf;
        this.status = "Não registrado";
    }

    // === Getters (conforme seu diagrama) ===
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    // === Métodos ===
    public void registrarEntrada() {
        this.dataVisita = LocalDate.now();
        this.horaEntrada = LocalTime.now().withNano(0); // sem nanossegundos
        this.status = "No condomínio";
        System.out.println("Entrada registrada para " + nome);
    }

    public void registrarSaida() {
        this.horaSaida = LocalTime.now().withNano(0); // sem nanossegundos
        this.status = "Saiu";
        System.out.println("Saída registrada para " + nome);
    }

    @Override
    public String toString() {
        return "\n--- Visitante ---" +
                "\nNome: " + nome +
                "\nCPF: " + cpf +
                "\nStatus: " + status +
                "\nEntrada: " + (horaEntrada != null ? horaEntrada : "—") +
                "\nSaída: " + (horaSaida != null ? horaSaida : "—") +
                "\n-------------------";
    }
}
