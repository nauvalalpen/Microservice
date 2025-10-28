package com.nauval.peminjamanservice.peminjamanservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("pinjaman_views")
@Data
public class PinjamanReadModel {
    @Id
    private Long kd_transaksi;

    private String nasabah;
    private double jumlahPinjam;
    private int lamaPinjam;
    private double bunga;
    private double angsuranPerbulan;
    private double totalPinjam;
}