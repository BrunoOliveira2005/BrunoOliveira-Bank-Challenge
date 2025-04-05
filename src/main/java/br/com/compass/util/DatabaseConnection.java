package br.com.compass.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema-bancario";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
    }

   //Teste de conexão
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexão com o banco de dados bem-sucedida!");
        } catch (SQLException e) {
            System.err.println(" Erro ao conectar ao banco: " + e.getMessage());
        }
    }
}