package com.nauval.orderqueryservice.orderqueryservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("order_views")
@Data
public class OrderReadModel {
    @Id
    private Long orderId; // Gunakan ID dari SQL sebagai ID di MongoDB
    private String namaProduk;
    private double hargaProduk;
    private int jumlah;
    private double total;
    private String namaPelanggan;
    private String emailPelanggan;
    private String tanggal;
}