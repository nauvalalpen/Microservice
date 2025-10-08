package com.nauval.notificationservice.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long produkId;
    private Long pelangganId;
    private int jumlah;
    private String tanggal;
    private double total;
    private String namaProduk;
    private double hargaProduk;
    private String namaPelanggan;
    private String emailPelanggan;
}