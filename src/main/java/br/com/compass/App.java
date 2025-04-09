package br.com.compass;

import br.com.compass.model.User;
import br.com.compass.service.UserService;
import br.com.compass.util.DatabaseConnection;

import java.time.LocalDate;
import java.util.Scanner;

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
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    User loggedUser = userService.login(cpf, senha);
                    if (loggedUser != null) {
                        if (loggedUser.getRole().equals("GERENTE")) {
                            gerenteMenu(scanner, userService);
                        } else {
                            clienteMenu(scanner); // Necessário implementar ainda 
                        }
                    }
                    break;
                case 2:
                    System.out.println("========= Abertura de Conta =========");
                    System.out.print("Nome completo: ");
                    String name = scanner.nextLine();

                    System.out.print("CPF: ");
                    String newCpf = scanner.nextLine();

                    System.out.print("Data de nascimento (YYYY-MM-DD): ");
                    LocalDate birthDate = null;
                    while (birthDate == null) {
                        String birthDateInput = scanner.nextLine();
                        try {
                            birthDate = LocalDate.parse(birthDateInput);
                        } catch (Exception e) {
                            System.out.print("Formato inválido! Digite novamente (YYYY-MM-DD): ");
                        }
                    }

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

    public static void gerenteMenu(Scanner scanner, UserService userService) {
        boolean running = true;

        while (running) {
            System.out.println("========= Gerente Menu =========");
            System.out.println("|| 1. Listar usuários bloqueados ||");
            System.out.println("|| 2. Desbloquear usuário        ||");
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
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println(" Opção inválida.");
            }
        }
    }

    public static void clienteMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.println("Depósito");//necessário fazer
                    break;
                case 2:
                    System.out.println("Saque");//necessário fazer
                    break;
                case 3:
                    System.out.println("Visualizar saldo ");//necessário fazer
                    break;
                case 4:
                    System.out.println("Transferência");//necessário fazer
                    break;
                case 5:
                    System.out.println("Extrato bancário");//necessário fazer
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
