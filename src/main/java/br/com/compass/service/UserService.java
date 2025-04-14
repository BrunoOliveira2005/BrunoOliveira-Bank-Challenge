package br.com.compass.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.compass.model.Account;
import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;
import br.com.compass.util.DatabaseConnection;
import br.com.compass.util.PasswordEncryptor;

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

            if (!userRepository.existsGerente()) {
                System.out.println(" Nenhum gerente cadastrado. Esta conta será GERENTE.");
                role = "GERENTE";
            } else {
                System.out.print("Você deseja criar uma conta como CLIENTE ou GERENTE? ");
                role = scanner.nextLine().toUpperCase();

                if (!role.equals("GERENTE") && !role.equals("CLIENTE")) {
                    System.out.println(" Tipo inválido. Criando como CLIENTE por padrão.");
                    role = "CLIENTE";
                }
            }

            // Cria e salva o usuário
            User user = new User(0, name, cpf, birthDate, phone, hashedPassword, role, false, accountType);
            userRepository.save(user);

            // Recupera o ID do usuário recém-criado
            User savedUser = userRepository.findByCPF(cpf);
            if (savedUser == null) {
                System.out.println("Erro ao buscar o usuário após salvamento.");
                return;
            }

            // Gera conta bancária
            String numeroConta = gerarNumeroConta();
            Account conta = new Account(
                savedUser.getId(),
                numeroConta,
                BigDecimal.ZERO,
                accountType,
                LocalDate.now()
            );
            AccountService accountService = new AccountService();
            accountService.criarConta(conta);

            System.out.println(" Conta criada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    private String gerarNumeroConta() {
        return String.valueOf(System.currentTimeMillis()).substring(5); 
    }

    public User login(String cpf, String senha) {
        User user = userRepository.findByCPF(cpf);

        if (user == null) {
            System.out.println(" Usuário não encontrado.");
            return null;
        }

        if (user.isBlocked()) {
            System.out.println(" Conta bloqueada. Solicite desbloqueio a um gerente.");
            return null;
        }

        int tentativas = 0;
        while (tentativas < 3) {
            if (PasswordEncryptor.matches(senha, user.getPassword())) {
                System.out.println(" Login realizado com sucesso!");
                return user;
            } else {
                tentativas++;
                if (tentativas < 3) {
                    System.out.print(" Senha incorreta. Tente novamente: ");
                    senha = scanner.nextLine();
                }
            }
        }

        System.out.println(" 3 tentativas erradas. Conta será bloqueada.");
        userRepository.bloquearUsuario(user.getCpf());
        return null;
    }

    public void listarUsuariosBloqueados() {
        List<User> bloqueados = userRepository.findBlockedUsers();

        if (bloqueados.isEmpty()) {
            System.out.println(" Nenhum usuário está bloqueado no momento.");
            return;
        }

        System.out.println("========= Usuários Bloqueados =========");
        for (User u : bloqueados) {
            System.out.println("Nome: " + u.getName() + " | CPF: " + u.getCpf());
        }
    }

    public void desbloquearUsuario(String cpf) {
        User user = userRepository.findByCPF(cpf);

        if (user == null) {
            System.out.println(" Usuário não encontrado.");
        } else if (!user.isBlocked()) {
            System.out.println(" Este usuário não está bloqueado.");
        } else {
            userRepository.desbloquearUsuario(cpf);
            System.out.println(" Usuário desbloqueado com sucesso!");
        }
    }
    public boolean usuarioEhGerente(int userId) {
        String sql = "SELECT role FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                return "GERENTE".equals(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    		
    public boolean cadastrarGerente(String name, String cpf, LocalDate birthDate, String phone, String password, long gerenteId) {
        String sql = "INSERT INTO users (name, cpf, birth_date, phone, password, role, account_type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, cpf);
            stmt.setDate(3, Date.valueOf(birthDate));
            stmt.setString(4, phone);
            stmt.setString(5, password);
            stmt.setString(6, "GERENTE"); 
            stmt.setString(7, "SALARIO"); 

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
