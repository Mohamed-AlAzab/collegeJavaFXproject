package com.example.restaurantmanagementsystem.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TableManager implements ReservationService {
    public int[] makeReservation(int tableID, String customerName, LocalDate time) {
        int[] arr = new int[2];
        int id = tableID;
        String name = customerName;
        LocalDate date = time;
        Connection conn = DB.dbConnection();
        String sql1 = "insert into RESERVATION (TABLEID, CUSTOMER_NAME, TIME) values(?,?,?)";
        String sql2 = "UPDATE TABLES SET ISRESERVED=1 WHERE TABLEID= ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setDate(3, Date.valueOf(date));
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
        int id = tableID;
        Connection conn = DB.dbConnection();
        String sql1 = "DELETE FROM RESERVATION WHERE TABLEID=?";
        String sql2 = "UPDATE TABLES SET ISRESERVED=0 WHERE TABLEID= ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setInt(1, id);
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
}