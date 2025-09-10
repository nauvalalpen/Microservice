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
}