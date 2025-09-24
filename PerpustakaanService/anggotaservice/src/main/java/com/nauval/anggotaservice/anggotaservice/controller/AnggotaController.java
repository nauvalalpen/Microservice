package com.nauval.anggotaservice.anggotaservice.controller;

import com.nauval.anggotaservice.anggotaservice.model.Anggota;
import com.nauval.anggotaservice.anggotaservice.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {
    @Autowired
    private AnggotaService anggotaService;

    @PostMapping
    @ResponseBody
    public Anggota save(@RequestBody Anggota anggota) {
        return anggotaService.save(anggota);
    }

    @GetMapping("/{id}")
    public Anggota findById(@PathVariable Long id) {
        return anggotaService.findById(id);
    }

    @GetMapping("/")
    public List<Anggota> findAll() {
        return anggotaService.findAll();
    }

    // === ADD THIS ENTIRE METHOD ===
    @PutMapping("/{id}")
    public Anggota update(@PathVariable Long id, @RequestBody Anggota anggota) {
        // Set the ID from the URL path to ensure we update the correct record
        anggota.setId(id);
        return anggotaService.save(anggota);
    }
}