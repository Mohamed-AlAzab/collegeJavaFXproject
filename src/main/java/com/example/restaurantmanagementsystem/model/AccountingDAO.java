package com.example.restaurantmanagementsystem.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountingDAO {
    private Connection conn;

    public AccountingDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Accounting> getAllRecords() throws SQLException {
        List<Accounting> list = new ArrayList<>();
        String sql = "SELECT * FROM Accounting ORDER BY time DESC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(new Accounting(
                    rs.getInt("id"),
                    rs.getDate("time").toLocalDate(),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("amount")
            ));
        }
        return list;
    }

    public void insertRecord(Accounting record) throws SQLException {
        String sql = "INSERT INTO Accounting (time, type, description, amount) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, Date.valueOf(record.getDate()));
        stmt.setString(2, record.getType());
        stmt.setString(3, record.getDescription());
        stmt.setDouble(4, record.getAmount());
        stmt.executeUpdate();
    }

    public void deleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM Accounting WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}

