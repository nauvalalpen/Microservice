package com.nauval.pelangganservice.pelangganservice.controller;

import com.nauval.pelangganservice.pelangganservice.model.Pelanggan;
import com.nauval.pelangganservice.pelangganservice.service.PelangganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pelanggan")
public class PelangganController {

    @Autowired
    private PelangganService pelangganService;

    @PostMapping
    public Pelanggan createPelanggan(@RequestBody Pelanggan pelanggan) {
        return pelangganService.createOrUpdatePelanggan(pelanggan);
    }

    @GetMapping
    public List<Pelanggan> getAllPelanggan() {
        return pelangganService.getAllPelanggan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelanggan> getPelangganById(@PathVariable Long id) {
        Optional<Pelanggan> pelanggan = pelangganService.getPelangganById(id);
        return pelanggan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelanggan(@PathVariable Long id) {
        if (pelangganService.getPelangganById(id).isPresent()) {
            pelangganService.deletePelangganById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ... (di dalam kelas PelangganController)

    // TAMBAHKAN METHOD INI SEBAGAI TES
    @GetMapping("/test")
    public String testEndpoint() {
        return "PELANGGAN CONTROLLER IS WORKING WITH LATEST CODE!";
    }
}
