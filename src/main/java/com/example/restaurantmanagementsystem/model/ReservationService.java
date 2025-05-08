package com.example.restaurantmanagementsystem.model;

import java.time.LocalDate;

public interface ReservationService {
    int[] makeReservation(int id, String customerName, LocalDate date);

    int[] cancelReservation(int id);
}