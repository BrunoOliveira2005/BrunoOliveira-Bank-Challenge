package br.com.compass.model;

import java.time.LocalDate;

public class User {
    private long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String phone;
    private String password;
    private String role;
    private boolean isBlocked;
    private String accountType;

    public User(long id, String name, String cpf, LocalDate birthDate, String phone,
                String password, String role, boolean isBlocked, String accountType) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.isBlocked = isBlocked;
        this.accountType = accountType;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }
}
