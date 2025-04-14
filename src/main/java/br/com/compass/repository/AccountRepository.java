package br.com.compass.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.compass.model.Account;
import br.com.compass.util.DatabaseConnection;

public class AccountRepository {

    public double getBalance(long userId) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar saldo: " + e.getMessage());
        }
        return 0;
    }

    public void updateBalance(long userId, double amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setLong(2, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar saldo: " + e.getMessage());
        }
    }

    public int getAccountIdByCpf(String cpf) {
        String sql = "SELECT id FROM users WHERE cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID da conta por CPF: " + e.getMessage());
        }
        return 0;
    }

    public void atualizarSaldo(long userId, double valor) {
        updateBalance(userId, valor);
    }

    public void save(Account conta) {
        String sql = "INSERT INTO accounts (user_id, numero_conta, balance, tipo_conta, data_criacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, conta.getUserId());
            stmt.setString(2, conta.getNumeroConta());
            stmt.setBigDecimal(3, conta.getSaldo());
            stmt.setString(4, conta.getTipoConta());
            stmt.setDate(5, Date.valueOf(conta.getDataCriacao()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao salvar conta: " + e.getMessage());
        }
    }
    public void updateBalanceByAccountId(int accountId, double amount) {
        String sql = "UPDATE accounts SET saldo = saldo + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar saldo pela conta: " + e.getMessage());
        }
        
    }public int getAccountIdByUserId(long userId) {
        String sql = "SELECT id FROM accounts WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID da conta por ID de usuário: " + e.getMessage());
        }
        return 0;
    }


}
