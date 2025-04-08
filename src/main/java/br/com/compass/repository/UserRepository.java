package br.com.compass.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.compass.model.User;
import br.com.compass.util.DatabaseConnection;

public class UserRepository {

    public void save(User user) {
        String sql = "INSERT INTO users (name, cpf, birth_date, phone, password, role, is_blocked, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getCpf());
            stmt.setDate(3, Date.valueOf(user.getBirthDate()));
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
            stmt.setBoolean(7, user.isBlocked());
            stmt.setString(8, user.getAccountType());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findByCpf(String cpf) {
        String sql = "SELECT * FROM users WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("cpf"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getBoolean("is_blocked"),
                        rs.getString("account_type")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
