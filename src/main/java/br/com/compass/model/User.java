package br.com.compass.model;

public class User {
    private int id;
    private String name;
    private String cpf;
    private String phone;
    private String password;
    private boolean isManager;
    private boolean isBlocked;

    public User() {}

    public User(String name, String cpf, String phone, String password, boolean isManager) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.password = password;
        this.isManager = isManager;
        this.isBlocked = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isManager() { return isManager; }
    public void setManager(boolean isManager) { this.isManager = isManager; }

    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean isBlocked) { this.isBlocked = isBlocked; }
}
