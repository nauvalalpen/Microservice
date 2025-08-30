package com.nauval.pelangganservice.pelangganservice.controller;

import com.nauval.pelangganservice.pelangganservice.model.Pelanggan;
import com.nauval.pelangganservice.pelangganservice.service.PelangganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pelanggan") // Base URL untuk semua endpoint di controller ini
public class PelangganController {

    @Autowired
    private PelangganService pelangganService;

    // Endpoint untuk membuat pelanggan baru
    @PostMapping
    public Pelanggan createPelanggan(@RequestBody Pelanggan pelanggan) {
        return pelangganService.createOrUpdatePelanggan(pelanggan);
    }

    // Endpoint untuk mendapatkan semua data pelanggan
    @GetMapping
    public List<Pelanggan> getAllPelanggan() {
        return pelangganService.getAllPelanggan();
    }

    // Endpoint untuk mendapatkan pelanggan berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Pelanggan> getPelangganById(@PathVariable Long id) {
        Optional<Pelanggan> pelanggan = pelangganService.getPelangganById(id);
        return pelanggan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint untuk memperbarui data pelanggan
    @PutMapping("/{id}")
    public ResponseEntity<Pelanggan> updatePelanggan(@PathVariable Long id, @RequestBody Pelanggan pelangganDetails) {
        Optional<Pelanggan> pelangganOptional = pelangganService.getPelangganById(id);

        if (pelangganOptional.isPresent()) {
            Pelanggan pelanggan = pelangganOptional.get();
            pelanggan.setKode(pelangganDetails.getKode());
            pelanggan.setNama(pelangganDetails.getNama());
            pelanggan.setAlamat(pelangganDetails.getAlamat());
            return ResponseEntity.ok(pelangganService.createOrUpdatePelanggan(pelanggan));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint untuk menghapus pelanggan berdasarkan ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelanggan(@PathVariable Long id) {
        if (pelangganService.getPelangganById(id).isPresent()) {
            pelangganService.deletePelangganById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
