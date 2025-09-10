package com.pengembalianservice.pengembalianservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Pengembalian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long peminjamanId;
    private LocalDate tanggalDikembalikan;
    private int terlambat; // dalam hari
    private double denda;
}