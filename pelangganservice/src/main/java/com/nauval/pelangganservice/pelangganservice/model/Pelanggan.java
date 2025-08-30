package com.nauval.pelangganservice.pelangganservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Anotasi Lombok untuk membuat getter, setter, dll. secara otomatis
@NoArgsConstructor // Lombok: constructor tanpa argumen
@AllArgsConstructor // Lombok: constructor dengan semua argumen
@Entity // Menandakan kelas ini sebagai entitas JPA (tabel di database)
public class Pelanggan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kode;
    private String nama;
    private String alamat;
}
