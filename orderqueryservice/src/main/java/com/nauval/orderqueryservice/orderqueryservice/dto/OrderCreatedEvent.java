package com.nauval.orderqueryservice.orderqueryservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    // Salin SEMUA field dari OrderCreatedEvent di orderservice
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