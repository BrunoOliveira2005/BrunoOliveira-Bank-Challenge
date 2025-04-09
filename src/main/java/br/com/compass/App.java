package br.com.compass;

import br.com.compass.model.User;
import br.com.compass.service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService(scanner);

    public static void main(String[] args) {
        mainMenu();
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (option) {
                case 1 -> login();
                case 2 -> createAccount();
                case 0 -> running = false;
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Digite seu CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        User user = userService.login(cpf, senha);

        if (user != null) {
            if ("GERENTE".equalsIgnoreCase(user.getRole())) {
                menuGerente(user);
            } else {
                menuUsuario(user);
            }
        }
    }

    private static void createAccount() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate nascimento = LocalDate.parse(scanner.nextLine());

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Tipo de conta (Corrente, Salário, etc): ");
        String tipoConta = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        userService.registerUser(nome, cpf, nascimento, telefone, senha, tipoConta);
    }

    private static void menuUsuario(User user) {
        int option;
        do {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> System.out.println("Deposit.");
                case 2 -> System.out.println("Withdraw.");
                case 3 -> System.out.println("Check Balance.");
                case 4 -> System.out.println("Transfer.");
                case 5 -> System.out.println("Bank Statement.");
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option! Please try again.");
            }

        } while (option != 0);
    }

    private static void menuGerente(User user) {
        int option;
        do {
            System.out.println("========= Gerente Menu =========");
            System.out.println("1. Listar usuários bloqueados");
            System.out.println("2. Desbloquear usuário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> userService.listarUsuariosBloqueados();
                case 2 -> {
                    System.out.print("Digite o CPF do usuário para desbloquear: ");
                    String cpf = scanner.nextLine();
                    userService.desbloquearUsuario(cpf);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (option != 0);
    }
}
