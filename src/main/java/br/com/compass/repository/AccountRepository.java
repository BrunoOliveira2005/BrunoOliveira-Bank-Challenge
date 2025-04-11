package br.com.compass.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return 0;
    }
    public void atualizarSaldo(long userId, double valor) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            stmt.setLong(2, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar saldo: " + e.getMessage());
        }
    }
    public void salvar(Account conta) {
        String sql = "INSERT INTO accounts (user_id, numero_conta, saldo, tipo_conta, data_criacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setInt(1, conta.getUsuarioId());
            stmt.setString(2, conta.getNumeroConta());
            stmt.setBigDecimal(3, conta.getSaldo());
            stmt.setString(4, conta.getTipoConta());
            stmt.setTimestamp(5, Timestamp.valueOf(conta.getDataCriacao().atStartOfDay()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao salvar conta: " + e.getMessage());
        }
    }

}
