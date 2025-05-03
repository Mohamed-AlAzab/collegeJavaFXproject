package com.example.restaurantmanagementsystem.model;

import java.time.LocalDate;

public interface ReservationService {
    int[] makeReservation(int var1, String var2, LocalDate var3);

    int[] cancelReservation(int var1);
}