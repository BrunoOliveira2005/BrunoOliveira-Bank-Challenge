package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;
import br.com.compass.util.PasswordEncryptor;

import java.time.LocalDate;
import java.util.Scanner;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final Scanner scanner;

    public UserService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void registerUser(String name, String cpf, LocalDate birthDate, String phone, String password, String accountType) {
        try {
            String hashedPassword = PasswordEncryptor.encrypt(password);
            String role;

            // Verifica se já existe algum gerente no banco
            if (!userRepository.existsGerente()) {
                System.out.println("✅ Nenhum gerente cadastrado. Esta conta será GERENTE.");
                role = "GERENTE";
            } else {
                System.out.print("Você deseja criar uma conta como CLIENTE ou GERENTE? ");
                role = scanner.nextLine().toUpperCase();

                if (!role.equals("GERENTE") && !role.equals("CLIENTE")) {
                    System.out.println("❌ Tipo inválido. Criando como CLIENTE por padrão.");
                    role = "CLIENTE";
                }
            }

            User user = new User(0, name, cpf, birthDate, phone, hashedPassword, role, false, accountType);
            userRepository.save(user);
            System.out.println("✅ Conta criada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    public User login(String cpf, String senha) {
        User user = userRepository.findByCPF(cpf);

        if (user == null) {
            System.out.println("❌ Usuário não encontrado.");
            return null;
        }

        if (user.isBlocked()) {
            System.out.println("🚫 Conta bloqueada. Solicite desbloqueio a um gerente.");
            return null;
        }

        int tentativas = 0;
        while (tentativas < 3) {
            if (PasswordEncryptor.matches(senha, user.getPassword())) {
                System.out.println("✅ Login realizado com sucesso!");
                return user;
            } else {
                tentativas++;
                if (tentativas < 3) {
                    System.out.print("❌ Senha incorreta. Tente novamente: ");
                    senha = scanner.nextLine();
                }
            }
        }

        System.out.println("❌ 3 tentativas erradas. Conta será bloqueada.");
        userRepository.bloquearUsuario(user.getCpf());
        return null;
    }

    public void listarUsuariosBloqueados() {
        System.out.println("========= Usuários Bloqueados =========");
        userRepository.findBlockedUsers().forEach(u -> {
            System.out.println("Nome: " + u.getName() + " | CPF: " + u.getCpf());
        });
    }

    public void desbloquearUsuario(String cpf) {
        User user = userRepository.findByCPF(cpf);
        if (user == null) {
            System.out.println("❌ Usuário não encontrado.");
        } else if (!user.isBlocked()) {
            System.out.println("⚠️ Usuário não está bloqueado.");
        } else {
            userRepository.desbloquearUsuario(cpf);
            System.out.println("✅ Usuário desbloqueado com sucesso!");
        }
    }
}
