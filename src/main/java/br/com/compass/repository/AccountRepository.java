package br.com.compass.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.compass.model.Account;
import br.com.compass.util.DatabaseConnection;

public class AccountRepository {

    public void salvar(Account account) {
        String sql = "INSERT INTO contas (usuario_id, numero_conta, saldo, tipo_conta, data_criacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account.getUsuarioId()); 
            stmt.setString(2, account.getNumeroConta());
            stmt.setBigDecimal(3, account.getSaldo());
            stmt.setString(4, account.getTipoConta());
            stmt.setDate(5, Date.valueOf(account.getDataCriacao()));

            stmt.executeUpdate();
            System.out.println(" Conta criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar conta: " + e.getMessage());
        }
}
    public Account findByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("user_id"),
                        rs.getString("account_number"),
                        rs.getBigDecimal("balance"),
                        rs.getString("account_type"),
                        rs.getDate("created_at").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar conta: " + e.getMessage());
        }

        return null;
    }

    public boolean hasAccount(int userId) {
        String sql = "SELECT id FROM accounts WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar conta: " + e.getMessage());
        }
        return false;
    }
}
