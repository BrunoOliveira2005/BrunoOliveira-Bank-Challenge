package br.com.compass.repository;

import br.com.compass.model.Transaction;
import br.com.compass.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, amount, date, type, destination_user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, transaction.getUserId());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setTimestamp(3, new Timestamp(transaction.getDate().getTime()));
            stmt.setString(4, transaction.getType());

            if (transaction.getDestinationUserId() != null) {
                stmt.setLong(5, transaction.getDestinationUserId());
            } else {
                stmt.setNull(5, Types.BIGINT);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao salvar transação: " + e.getMessage());
        }
    }

    public Transaction findById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Transaction(
                    rs.getInt("id"),
                    rs.getLong("user_id"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("date"),
                    rs.getString("type"),
                    rs.getObject("destination_user_id") != null ? rs.getLong("destination_user_id") : null
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar transação por ID: " + e.getMessage());
        }

        return null;
    }

    public List<Transaction> findAllByUserId(long userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    rs.getLong("user_id"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("date"),
                    rs.getString("type"),
                    rs.getObject("destination_user_id") != null ? rs.getLong("destination_user_id") : null
                );
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar transações: " + e.getMessage());
        }

        return transactions;
    }
}
