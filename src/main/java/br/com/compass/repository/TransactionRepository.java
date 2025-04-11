package br.com.compass.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.compass.model.Transaction;
import br.com.compass.util.DatabaseConnection;

public class TransactionRepository {

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, account_id, tipo, valor, data, descricao, destino_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, transaction.getUserId());
            stmt.setInt(2, transaction.getAccountId());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setTimestamp(5, new Timestamp(transaction.getDate().getTime()));
            stmt.setString(6, transaction.getDescricao());

            if (transaction.getDestinationUserId() != null) {
                stmt.setLong(7, transaction.getDestinationUserId());
            } else {
                stmt.setNull(7, Types.BIGINT);
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
                    rs.getInt("account_id"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data"),
                    rs.getString("tipo"),
                    rs.getString("descricao"),
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
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY data DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    rs.getLong("user_id"),
                    rs.getInt("account_id"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data"),
                    rs.getString("tipo"),
                    rs.getString("descricao"),
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
