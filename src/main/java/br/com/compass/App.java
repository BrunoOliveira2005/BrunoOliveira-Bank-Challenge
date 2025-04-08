package br.com.compass;

import br.com.compass.model.User;
import br.com.compass.service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        int option;

        do {
            System.out.println("====== MENU PRINCIPAL ======");
            System.out.println("1. Login");
            System.out.println("2. Criar conta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1 -> login();
                case 2 -> criarConta();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private static void criarConta() {
        System.out.print("Nome: ");
        String name = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Data de nascimento (yyyy-MM-dd): ");
        String birthDateStr = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateStr);

        System.out.print("Telefone: ");
        String phone = scanner.nextLine();

        System.out.print("Tipo de conta (CORRENTE/POUPANCA): ");
        String accountType = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        userService.registerUser(name, cpf, birthDate, phone, password, accountType);
    }

    private static void login() {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        User user = userService.login(cpf, password);
        if (user != null) {
            System.out.println("Login bem-sucedido! Bem-vindo(a), " + user.getName());
            if ("GERENTE".equalsIgnoreCase(user.getRole())) {
                menuGerente(user);
            } else {
                menuUsuario(user);
            }
        } else {
            System.out.println("CPF ou senha incorretos ou conta bloqueada.");
        }
    }

    private static void menuUsuario(User user) {
        System.out.println("===== MENU USUÁRIO =====");
        // Aqui virão as funcionalidades do usuário
    }

    private static void menuGerente(User user) {
        System.out.println("===== MENU GERENTE =====");
        // Aqui virão as funcionalidades do gerente
    }
}