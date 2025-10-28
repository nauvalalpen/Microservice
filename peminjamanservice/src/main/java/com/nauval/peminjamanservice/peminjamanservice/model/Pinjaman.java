package com.nauval.peminjamanservice.peminjamanservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pinjaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kd_transaksi;

    private String nasabah;
    private double jumlahPinjam;
    private int lamaPinjam;
    private double bunga;
    private double angsuranPerbulan;
    private double totalPinjam;
}