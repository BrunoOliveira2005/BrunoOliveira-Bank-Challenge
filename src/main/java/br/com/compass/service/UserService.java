package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;
import br.com.compass.util.PasswordEncryptor;

import java.time.LocalDate;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    // Registra um novo usuário
    public void registerUser(String name, String cpf, LocalDate birthDate, String phone, String password, String accountType) {
        try {
            String hashedPassword = PasswordEncryptor.encrypt(password);
            String role = "USER";

            User user = new User(0, name, cpf, birthDate, phone, hashedPassword, role, false, accountType);
            userRepository.save(user);
            System.out.println("Conta criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    // Realiza login se o CPF e a senha coincidirem
    public User login(String cpf, String password) {
        User user = userRepository.findByCpf(cpf);

        if (user != null && PasswordEncryptor.matches(password, user.getPassword())) {
            if (user.isBlocked()) {
                System.out.println("Conta bloqueada. Contate o gerente.");
                return null;
            }
            return user;
        }

        return null;
    }
}
