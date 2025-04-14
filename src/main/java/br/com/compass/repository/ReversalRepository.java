package br.com.compass.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.compass.model.Reversal;
import br.com.compass.util.DatabaseConnection;

public class ReversalRepository {

    public void save(Reversal reversal) {
        String sql = "INSERT INTO reversals (transaction_id, approved, approved_at, approved_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reversal.getTransactionId());
            stmt.setBoolean(2, reversal.isApproved());

            if (reversal.getApprovedAt() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(reversal.getApprovedAt()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            if (reversal.getApprovedBy() != null) {
                stmt.setLong(4, reversal.getApprovedBy());
            } else {
                stmt.setNull(4, Types.BIGINT);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar estorno: " + e.getMessage());
        }
    }

    public Reversal findById(int id) {
        String sql = "SELECT * FROM reversals WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Reversal(
                    rs.getInt("id"),
                    rs.getInt("transaction_id"),
                    rs.getString("status"),
                    rs.getTimestamp("request_date").toLocalDateTime(),
                    rs.getBoolean("approved"),
                    rs.getTimestamp("approved_at") != null ? rs.getTimestamp("approved_at").toLocalDateTime() : null,
                    rs.getObject("approved_by", Long.class)
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar estorno: " + e.getMessage());
        }

        return null;
    }

    public void update(Reversal reversal) {
        String sql = "UPDATE reversals SET approved = ?, approved_at = ?, approved_by = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, reversal.isApproved());
            stmt.setTimestamp(2, Timestamp.valueOf(reversal.getApprovedAt()));
            stmt.setLong(3, reversal.getApprovedBy());
            stmt.setInt(4, reversal.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar estorno: " + e.getMessage());
        }
    }
 // Retorna os estornos 
    public List<Reversal> getReversalsByStatus(String status) {
        List<Reversal> reversals = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM reversals WHERE status = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reversal reversal = new Reversal(
                    rs.getInt("id"),
                    rs.getInt("transaction_id"),
                    rs.getString("status"),
                    rs.getTimestamp("request_date").toLocalDateTime()
                );
                reversals.add(reversal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reversals;
    }

    // Nova solicitação de estorno
    public void solicitarEstorno(int userId, int transactionId, String motivo, LocalDateTime dataSolicitacao) {
    	String sql = "INSERT INTO reversals (transaction_id, user_id, motivo, status, data_solicitacao, request_date) VALUES (?, ?, ?, ?, ?, ?)";

    	try (Connection conn = DatabaseConnection.getConnection();
    	     PreparedStatement stmt = conn.prepareStatement(sql)) {

    	    stmt.setInt(1, transactionId);
    	    stmt.setInt(2, userId);  
    	    stmt.setString(3, motivo); 
    	    stmt.setString(4, "PENDENTE");
    	    stmt.setTimestamp(5, Timestamp.valueOf(dataSolicitacao));
    	    stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

    	    stmt.executeUpdate();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    
    	}	
}
    
    public boolean aprovarEstorno(int reversalId, long gerenteId) {
        String sql = "UPDATE reversals SET approved = true, approved_at = NOW(), approved_by = ?, status = 'APROVADO' WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, gerenteId);
            stmt.setInt(2, reversalId);

            // Se a atualização for bem-sucedida, retorna true
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao aprovar estorno: " + e.getMessage());
            return false;
        }
    }
}
