package view;

import model.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaCondominio sistema = SistemaCondominio.carregarDados();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE GESTÃO DE CONDOMÍNIO ===");
            System.out.println("1. Cadastrar morador");
            System.out.println("2. Editar morador");
            System.out.println("3. Remover morador");
            System.out.println("4. Pesquisar morador");
            System.out.println("5. Adicionar taxa a morador");
            System.out.println("6. Registrar visitante");
            System.out.println("7. Registrar entrada de visitante");
            System.out.println("8. Registrar saída de visitante");
            System.out.println("9. Gerar relatório financeiro");
            System.out.println("10. Listar moradores e visitantes");
            System.out.println("11. Marcar taxa como paga");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.println("\n--- CADASTRAR MORADOR ---");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("E-mail: ");
                    String email = sc.nextLine();
                    System.out.print("Apartamento: ");
                    String apartamento = sc.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();

                    Morador morador = new Morador(nome, cpf, email, apartamento, telefone);
                    sistema.cadastrarMorador(morador);
                    System.out.println("✅ Morador cadastrado com sucesso!");
                }

                case 2 -> {
                    System.out.println("\n--- EDITAR MORADOR ---");
                    System.out.print("Nome do morador a editar: ");
                    String nome = sc.nextLine();
                    Morador m = sistema.pesquisarMorador(nome);

                    if (m != null) {
                        System.out.print("Novo e-mail: ");
                        String email = sc.nextLine();
                        System.out.print("Novo telefone: ");
                        String telefone = sc.nextLine();
                        sistema.editarMorador(nome, email, telefone);
                        System.out.println("✅ Morador atualizado com sucesso!");
                    } else {
                        System.out.println("❌ Morador não encontrado!");
                    }
                }

                case 3 -> {
                    System.out.println("\n--- REMOVER MORADOR ---");
                    System.out.print("Nome do morador: ");
                    String nome = sc.nextLine();
                    sistema.removerMorador(nome);
                    System.out.println("✅ Morador removido (se existia).");
                }

                case 4 -> {
                    System.out.println("\n--- PESQUISAR MORADOR ---");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    Morador m = sistema.pesquisarMorador(nome);

                    if (m != null) {
                        System.out.println("\n🔍 Morador encontrado:");
                        System.out.println("Nome: " + m.getNome());
                        System.out.println("CPF: " + m.getCpf());
                        System.out.println("Email: " + m.getEmail());
                        System.out.println("Apartamento: " + m.getApartamento());
                        System.out.println("Telefone: " + m.getTelefone());
                        System.out.println("Situação Financeira: " + m.getSituacaoFinanceira());
                        System.out.println("Total Devido: R$ " + m.calcularValorTotal());
                    } else {
                        System.out.println("❌ Morador não encontrado!");
                    }
                }

                case 5 -> {
                    System.out.println("\n--- ADICIONAR TAXA ---");
                    System.out.print("Nome do morador: ");
                    String nome = sc.nextLine();
                    System.out.print("Mês de referência: ");
                    String mes = sc.nextLine();
                    System.out.print("Valor da taxa: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    sistema.cadastrarTaxa(nome, new Taxa(mes, valor));
                    System.out.println("✅ Taxa adicionada com sucesso!");
                }

                case 6 -> {
                    System.out.println("\n--- REGISTRAR VISITANTE ---");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    Visitante v = new Visitante(nome, cpf);
                    sistema.cadastrarVisitante(v);
                    System.out.println("✅ Visitante registrado!");
                }

                case 7 -> {
                    System.out.println("\n--- REGISTRAR ENTRADA ---");
                    System.out.print("Nome do visitante: ");
                    String nome = sc.nextLine();
                    Visitante v = sistema.getVisitantes().stream()
                            .filter(x -> x.getNome().equalsIgnoreCase(nome))
                            .findFirst().orElse(null);
                    if (v != null) {
                        v.registrarEntrada();
                        sistema.salvarDados();
                        System.out.println("✅ Entrada registrada!");
                    } else {
                        System.out.println("❌ Visitante não encontrado!");
                    }
                }

                case 8 -> {
                    System.out.println("\n--- REGISTRAR SAÍDA ---");
                    System.out.print("Nome do visitante: ");
                    String nome = sc.nextLine();
                    Visitante v = sistema.getVisitantes().stream()
                            .filter(x -> x.getNome().equalsIgnoreCase(nome))
                            .findFirst().orElse(null);
                    if (v != null) {
                        v.registrarSaida();
                        sistema.salvarDados();
                        System.out.println("✅ Saída registrada!");
                    } else {
                        System.out.println("❌ Visitante não encontrado!");
                    }
                }

                case 9 -> {
                    System.out.println("\n--- RELATÓRIO FINANCEIRO ---");
                    sistema.gerarRelatorioFinanceiro();
                }

                case 10 -> {
                    System.out.println("\n--- MORADORES ---");
                    sistema.getMoradores().forEach(System.out::println);
                    System.out.println("\n--- VISITANTES ---");
                    sistema.getVisitantes().forEach(System.out::println);
                }

                case 11 -> {
                    System.out.println("\n--- MARCAR TAXA COMO PAGA ---");
                    System.out.print("Nome do morador: ");
                    String nome = sc.nextLine();
                    Morador m = sistema.pesquisarMorador(nome);

                    if (m != null) {
                        var taxas = m.listarTaxas();
                        if (taxas.isEmpty()) {
                            System.out.println("⚠️ Nenhuma taxa cadastrada para este morador.");
                        } else {
                            System.out.println("\nTaxas pendentes:");
                            for (int i = 0; i < taxas.size(); i++) {
                                Taxa t = taxas.get(i);
                                System.out.println((i + 1) + ". " + t.getMesReferencia() + " - " + (t.isStatusPagamento() ? "Pago" : "Pendente") + " - R$ " + t.getValor());
                            }

                            System.out.print("Escolha o número da taxa a marcar como paga: ");
                            int indice = sc.nextInt() - 1;
                            sc.nextLine();

                            if (indice >= 0 && indice < taxas.size()) {
                                Taxa taxa = taxas.get(indice);
                                taxa.marcarComoPago();
                                sistema.salvarDados();
                                System.out.println("✅ Taxa marcada como paga!");
                            } else {
                                System.out.println("❌ Opção inválida!");
                            }
                        }
                    } else {
                        System.out.println("❌ Morador não encontrado!");
                    }
                }

                case 0 -> System.out.println("👋 Encerrando o sistema...");
                default -> System.out.println("❌ Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}
