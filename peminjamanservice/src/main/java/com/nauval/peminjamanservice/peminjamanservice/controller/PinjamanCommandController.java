package com.nauval.peminjamanservice.peminjamanservice.controller;

import com.nauval.peminjamanservice.peminjamanservice.model.Pinjaman;
import com.nauval.peminjamanservice.peminjamanservice.service.PinjamanCommandService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pinjaman/command")
public class PinjamanCommandController {

    private final PinjamanCommandService commandService;

    public PinjamanCommandController(PinjamanCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<Pinjaman> createPinjaman(@RequestBody Pinjaman pinjaman) {
        Pinjaman newPinjaman = commandService.createPinjaman(pinjaman);
        return new ResponseEntity<>(newPinjaman, HttpStatus.CREATED);
    }

    // === ENDPOINT BARU UNTUK UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Pinjaman> updatePinjaman(@PathVariable Long id, @RequestBody Pinjaman pinjamanDetails) {
        try {
            Pinjaman updatedPinjaman = commandService.updatePinjaman(id, pinjamanDetails);
            return ResponseEntity.ok(updatedPinjaman);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePinjaman(@PathVariable Long id) {
        try {
            commandService.deletePinjaman(id);
            return ResponseEntity.noContent().build(); // Standard response for successful delete
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}