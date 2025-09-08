package br.com.os;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Criando alguns técnicos fixos
        List<Tecnico> tecnicos = new ArrayList<>();
        tecnicos.add(new Tecnico("Pedro", "pedro@empresa.com", "Redes"));
        tecnicos.add(new Tecnico("Maria", "maria@empresa.com", "Hardware"));

        // Lista de OS
        List<OrdemDeServico> listaOS = new ArrayList<>();

        int opcao = 0;
        int idOS = 1;

        while (opcao != 3) {
            System.out.println("=== Sistema de OS ===");
            System.out.println("1 - Abrir OS");
            System.out.println("2 - Listar OS abertas");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = sc.nextLine();
                    System.out.print("Digite o email do cliente: ");
                    String emailCliente = sc.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfCliente = sc.nextLine();
                    Cliente c = new Cliente(nomeCliente, emailCliente, cpfCliente);

                    System.out.println("Escolha o técnico:");
                    for (int i = 0; i < tecnicos.size(); i++) {
                        System.out.println((i + 1) + " - " + tecnicos.get(i).getNome());
                    }
                    int tEscolha = sc.nextInt();
                    sc.nextLine(); // limpar buffer
                    Tecnico t = tecnicos.get(tEscolha - 1);

                    System.out.print("Digite o assunto da OS: ");
                    String assunto = sc.nextLine();

                    System.out.print("Digite o valor da OS: ");
                    double valor = sc.nextDouble();
                    sc.nextLine(); // limpar buffer

                    OrdemDeServico os = new OrdemDeServico(idOS++, assunto, valor, c, t);
                    listaOS.add(os);
                    System.out.println("OS aberta com sucesso!\n");
                    break;

                case 2:
                    System.out.println("=== OS abertas ===");
                    for (OrdemDeServico o : listaOS) {
                        if (o.getStatus().equals("ABERTA")) {
                            o.exibirDetalhes();
                        }
                    }
                    break;

                case 3:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}
