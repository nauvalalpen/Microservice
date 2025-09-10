package com.nauval.bukuservice.bukuservice.controller;

import com.nauval.bukuservice.bukuservice.model.Buku;
import com.nauval.bukuservice.bukuservice.service.BukuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buku") // Menggunakan /api/buku sebagai base path
public class BukuController {

    // 2. INJECT BUKUSERVICE, BUKAN BUKU
    @Autowired
    private BukuService bukuService;

    // 4. PERBAIKI ANOTASI DAN PANGGILAN METHOD
    @PostMapping
    public Buku save(@RequestBody Buku buku) {
        // Panggil method dari variabel 'bukuService'
        return bukuService.save(buku);
    }

    @GetMapping("/{id}")
    public Buku findById(@PathVariable("id") Long id) {
        // Panggil method dari variabel 'bukuService'
        return bukuService.findById(id);
    }

    @GetMapping
    public List<Buku> findAll() {
        // Panggil method dari variabel 'bukuService'
        return bukuService.findAll();
    }
}