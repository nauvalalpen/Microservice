package com.pengembalianservice.pengembalianservice.controller;

import com.pengembalianservice.pengembalianservice.model.Pengembalian;
import com.pengembalianservice.pengembalianservice.service.PengembalianService;
import com.pengembalianservice.pengembalianservice.vo.PengembalianResponseTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pengembalian")
public class PengembalianController {

    @Autowired
    private PengembalianService pengembalianService;

    @PostMapping
    public ResponseEntity<Pengembalian> save(@RequestBody Pengembalian pengembalian) {
        try {
            Pengembalian pengembalianBaru = pengembalianService.save(pengembalian);
            return ResponseEntity.ok(pengembalianBaru);
        } catch (Exception e) {
            // Jika peminjamanId tidak valid, service akan melempar exception
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint untuk melihat detail pengembalian beserta detail peminjamannya.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PengembalianResponseTemplateVO> findDetailById(@PathVariable("id") Long id) {
        PengembalianResponseTemplateVO response = pengembalianService.findDetailById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}