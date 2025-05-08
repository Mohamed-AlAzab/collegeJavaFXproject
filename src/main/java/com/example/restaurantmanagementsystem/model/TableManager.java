package com.example.restaurantmanagementsystem.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TableManager implements ReservationService {
    public int[] makeReservation(int id, String customerName, LocalDate time) {
        int[] arr = new int[2];
        Connection conn = DB.dbConnection();
        String sql1 = "insert into RESERVATION (ID, CUSTOMERNAME, DATERESERVATION) values(?,?,?)";

        String sql2 = "UPDATE TABLES SET ISRESERVED=1 WHERE ID= ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setInt(1, id);
            pst.setString(2, customerName);
            pst.setDate(3, Date.valueOf(time));
            arr[0] = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        try {
            PreparedStatement pst = conn.prepareStatement(sql2);
            pst.setInt(1, id);
            arr[1] = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return arr;
    }

    public int[] cancelReservation(int tableID) {
        int[] arr = new int[2];
        Connection conn = DB.dbConnection();
        String sql1 = "DELETE FROM RESERVATION WHERE ID=?";
        String sql2 = "UPDATE TABLES SET ISRESERVED=0 WHERE ID= ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setInt(1, tableID);
            arr[0] = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        try {
            PreparedStatement pst = conn.prepareStatement(sql2);
            pst.setInt(1, tableID);
            arr[1] = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return arr;
    }

    public List<Table> fetchAllTable() {
        List<Table> table = new ArrayList<>();
        String sql = "SELECT * FROM TABLES";

        try (Connection conn = DB.dbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String type = rs.getString("TYPE");
                int capacity = rs.getInt("CAPACITY");
                int isReserved = rs.getInt("ISRESERVED");
                Table tempTable = new Table(id, type, capacity, isReserved);
                table.add(tempTable);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return table;
    }
}