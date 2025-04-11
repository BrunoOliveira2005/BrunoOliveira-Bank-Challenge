package br.com.compass;

import java.time.LocalDate;
import java.util.Scanner;

import br.com.compass.model.User;
import br.com.compass.service.ReversalService;
import br.com.compass.service.TransactionService;
import br.com.compass.service.UserService;
import br.com.compass.util.DatabaseConnection;

public class App {

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService(scanner);

        mainMenu(scanner, userService);

        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner, UserService userService) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    cpf = cpf.replaceAll("[^0-9]", "");
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    User loggedUser = userService.login(cpf, senha);
                    if (loggedUser != null) {
                        if (loggedUser.getRole().equals("GERENTE")) {
                            gerenteMenu(scanner, userService, loggedUser);
                        } else {
                            clienteMenu(scanner, loggedUser);
                        }
                    }
                    break;
                case 2:
                    System.out.println("========= Abertura de Conta =========");
                    System.out.print("Nome completo: ");
                    String name = scanner.nextLine();

                    System.out.print("Digite o CPF: ");
                    String newCpf = scanner.nextLine().replaceAll("[^\\d]", ""); // remove tudo que não for dígito

                    if (newCpf.length() != 11) {
                        System.out.println("CPF inválido. Deve conter 11 dígitos.");
                        return;
                    }

                    System.out.print("Data de nascimento (YYYY-MM-DD): ");
                    String birthDateInput = scanner.nextLine();
                    LocalDate birthDate = LocalDate.parse(birthDateInput);

                    System.out.print("Telefone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Tipo de conta (Ex: Corrente, Salário): ");
                    String accountType = scanner.nextLine();

                    System.out.print("Senha: ");
                    String password = scanner.nextLine();

                    userService.registerUser(name, newCpf, birthDate, phone, password, accountType);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println(" Opção inválida! Tente novamente.");
            }
        }
    }

    public static void gerenteMenu(Scanner scanner, UserService userService, User gerente) {
        boolean running = true;
        ReversalService reversalService = new ReversalService(scanner);

        while (running) {
            System.out.println("========= Gerente Menu =========");
            System.out.println("|| 1. Listar usuários bloqueados ||");
            System.out.println("|| 2. Desbloquear usuário        ||");
            System.out.println("|| 3. Ver Estornos Pendentes     ||");
            System.out.println("|| 4. Aprovar Estorno            ||");
            System.out.println("|| 0. Voltar                     ||");
            System.out.println("=================================");
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    userService.listarUsuariosBloqueados();
                    break;
                case 2:
                    System.out.print("Informe o CPF do usuário a ser desbloqueado: ");
                    String cpf = scanner.nextLine();
                    userService.desbloquearUsuario(cpf);
                    break;
                case 3:
                    reversalService.listarEstornosPendentes();
                    break;
                case 4:
                    System.out.print("Informe o ID do estorno a ser aprovado: ");
                    int reversalId = Integer.parseInt(scanner.nextLine());
                    reversalService.aprovarEstorno(reversalId, gerente.getId());
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println(" Opção inválida.");
            }
        }
    }

    public static void clienteMenu(Scanner scanner, User cliente) {
        boolean running = true;
        ReversalService reversalService = new ReversalService(scanner);
        TransactionService transactionService = new TransactionService(scanner);

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit                ||");
            System.out.println("|| 2. Withdraw               ||");
            System.out.println("|| 3. Check Balance          ||");
            System.out.println("|| 4. Transfer               ||");
            System.out.println("|| 5. Bank Statement         ||");
            System.out.println("|| 6. Solicitar Estorno      ||");
            System.out.println("|| 0. Exit                   ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    transactionService.deposit(cliente);
                    break;
                case 2:
                    transactionService.withdraw(cliente);
                    break;
                case 3:
                    System.out.println("Visualizar saldo (em construção).");
                    break;
                case 4:
                    System.out.println("Transferência (em construção).");
                    break;
                case 5:
                    System.out.println("Extrato bancário (em construção).");
                    break;
                case 6:
                    System.out.print("Informe o ID da transação a ser estornada: ");
                    int transacaoId = Integer.parseInt(scanner.nextLine());
                    reversalService.solicitarEstorno(transacaoId);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println(" Opção inválida! Tente novamente.");
            }
        }
    }
}