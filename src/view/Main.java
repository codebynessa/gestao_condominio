package view;

import model.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaCondominio sistema = SistemaCondominio.carregarDados();
        Scanner sc = new Scanner(System.in);

        String perfil = login(sc);

        sistema.exibirDashboard();

        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL === (Perfil: " + perfil + ")");
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
            System.out.println("12. Listar moradores em atraso");
            System.out.println("13. Listar visitantes presentes no condomínio");
            System.out.println("14. Exportar relatório (TXT/CSV)");
            System.out.println("15. Fazer backup automático (JSON)");
            System.out.println("16. Histórico de alterações");
            System.out.println("17. Login com perfis");
            System.out.println("18. Gerar boleto simples");
            System.out.println("19. Busca inteligente");
            System.out.println("20. Dashboard inicial");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = readIntSafe(sc); // SEM nextLine() aqui!

            // Restrição de porteiro
            if ("porteiro".equals(perfil)) {
                if (!(opcao == 6 || opcao == 7 || opcao == 8 || opcao == 10 || opcao == 13 || opcao == 20 || opcao == 0 || opcao == 17)) {
                    System.out.println("❌ Acesso negado para seu perfil (porteiro).");
                    continue;
                }
            }

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
                    String ap = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();

                    sistema.cadastrarMorador(new Morador(nome, cpf, email, ap, tel));
                }

                case 2 -> {
                    System.out.println("\n--- EDITAR MORADOR ---");
                    System.out.print("Nome do morador: ");
                    String nome = sc.nextLine();
                    Morador m = sistema.pesquisarMorador(nome);

                    if (m != null) {
                        System.out.print("Novo e-mail: ");
                        String email = sc.nextLine();
                        System.out.print("Novo telefone: ");
                        String tel = sc.nextLine();
                        sistema.editarMorador(nome, email, tel);
                    } else System.out.println("❌ Não encontrado.");
                }

                case 3 -> {
                    System.out.println("\n--- REMOVER MORADOR ---");
                    System.out.print("Nome: ");
                    sistema.removerMorador(sc.nextLine());
                }

                case 4 -> {
                    System.out.println("\n--- PESQUISAR MORADOR ---");
                    System.out.print("Nome: ");
                    Morador m = sistema.pesquisarMorador(sc.nextLine());
                    if (m != null) exibirDetalhesMorador(m);
                    else System.out.println("❌ Não encontrado.");
                }

                case 5 -> {
                    System.out.println("\n--- ADICIONAR TAXA ---");
                    System.out.print("Morador: ");
                    String nome = sc.nextLine();
                    System.out.print("Mês: ");
                    String mes = sc.nextLine();
                    System.out.print("Valor: ");
                    double valor = readDoubleSafe(sc);
                    sistema.cadastrarTaxa(nome, new Taxa(mes, valor));
                }

                case 6 -> {
                    System.out.println("\n--- REGISTRAR VISITANTE ---");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    sistema.cadastrarVisitante(new Visitante(nome, cpf));
                }

                case 7 -> {
                    System.out.print("Visitante: ");
                    Visitante v = sistema.encontrarVisitante(sc.nextLine());
                    if (v != null) {
                        v.registrarEntrada();
                        sistema.salvarDados();
                    } else System.out.println("❌ Não encontrado.");
                }

                case 8 -> {
                    System.out.print("Visitante: ");
                    Visitante v = sistema.encontrarVisitante(sc.nextLine());
                    if (v != null) {
                        v.registrarSaida();
                        sistema.salvarDados();
                    } else System.out.println("❌ Não encontrado.");
                }

                case 9 -> sistema.gerarRelatorioFinanceiro();

                case 10 -> {
                    System.out.println("\n--- MORADORES ---");
                    sistema.getMoradores().forEach(System.out::println);
                    System.out.println("\n--- VISITANTES ---");
                    sistema.getVisitantes().forEach(System.out::println);
                }

                case 11 -> {
                    System.out.print("Morador: ");
                    sistema.menuMarcarTaxaComoPaga(sc, sc.nextLine());
                }

                case 12 -> sistema.listarMoradoresEmAtraso().forEach(System.out::println);

                case 13 -> sistema.listarVisitantesPresentes().forEach(System.out::println);

                case 14 -> {
                    System.out.println("1) TXT  2) CSV");
                    int tipo = readIntSafe(sc);
                    if (tipo == 1) sistema.exportarRelatorioTXT();
                    else if (tipo == 2) sistema.exportarRelatorioCSV();
                }

                case 15 -> sistema.gerarBackupJSON();

                case 16 -> sistema.imprimirHistorico();

                case 17 -> perfil = login(sc);

                case 18 -> {
                    System.out.print("Morador: ");
                    sistema.gerarBoleto(sc.nextLine());
                }

                case 19 -> {
                    System.out.print("Termo: ");
                    sistema.buscar(sc.nextLine()).forEach(System.out::println);
                }

                case 20 -> sistema.exibirDashboard();

                case 0 -> {
                    sistema.salvarDados();
                    sistema.gerarBackupJSON();
                    System.out.println("Saindo...");
                }

                default -> System.out.println("❌ Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static String login(Scanner sc) {
        while (true) {
            System.out.println("\n=== LOGIN ===");
            System.out.println("1 - Síndico");
            System.out.println("2 - Porteiro");
            System.out.print("Escolha: ");
            int p = readIntSafe(sc);
            if (p == 1) return "sindico";
            if (p == 2) return "porteiro";
            System.out.println(" Opção inválida.");
        }
    }

    private static void exibirDetalhesMorador(Morador m) {
        System.out.println("\nMorador encontrado:");
        System.out.println("Nome: " + m.getNome());
        System.out.println("CPF: " + m.getCpf());
        System.out.println("Email: " + m.getEmail());
        System.out.println("Apartamento: " + m.getApartamento());
        System.out.println("Telefone: " + m.getTelefone());
        System.out.println("Situação Financeira: " + m.getSituacaoFinanceira());
        System.out.printf("Total Devido: R$ %.2f%n", m.calcularValorTotal());
    }

    private static int readIntSafe(Scanner sc) {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    private static double readDoubleSafe(Scanner sc) {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (Exception e) { return 0; }
    }
}
