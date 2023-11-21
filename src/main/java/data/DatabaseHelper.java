package data;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:stockdata.db";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DatabaseHelper() {
        initialize();
    }

    private void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS stock_data (" +
                    "symbol TEXT PRIMARY KEY," +
                    "data TEXT," +
                    "last_updated DATETIME)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDataPresent(String symbol) {
        String sql = "SELECT 1 FROM stock_data WHERE symbol = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, symbol);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertOrUpdateData(String symbol, String data) {
        String sql = "INSERT INTO stock_data (symbol, data, last_updated) VALUES (?, ?, ?) " +
                "ON CONFLICT(symbol) DO UPDATE SET data = ?, last_updated = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, symbol);
            pstmt.setString(2, data);
            pstmt.setString(3, LocalDateTime.now().format(DTF));
            pstmt.setString(4, data);
            pstmt.setString(5, LocalDateTime.now().format(DTF));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getData(String symbol) {
        String sql = "SELECT data FROM stock_data WHERE symbol = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, symbol);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
