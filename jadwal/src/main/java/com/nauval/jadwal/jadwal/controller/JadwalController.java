package com.nauval.jadwal.jadwal.controller; // Sesuaikan package Anda

import com.nauval.jadwal.jadwal.model.Day;
import com.nauval.jadwal.jadwal.model.Teacher;
import com.nauval.jadwal.jadwal.service.JadwalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jadwal/dosen") // Path dasar diubah agar lebih konsisten
public class JadwalController {

    private final JadwalService jadwalService;

    public JadwalController(JadwalService jadwalService) {
        this.jadwalService = jadwalService;
    }

    /**
     * Endpoint untuk mendapatkan daftar semua KODE ALIAS dosen.
     * URL: GET /api/jadwal/dosen
     */
    @GetMapping
    public List<String> getAllTeacherAliases() {
        return jadwalService.getAllTeacherAliases();
    }

    /**
     * Endpoint untuk mencari jadwal lengkap berdasarkan KODE ALIAS dosen.
     * URL: GET /api/jadwal/dosen/{alias}
     * Contoh: /api/jadwal/dosen/AINIL
     */
    @GetMapping("/{alias}")
    public ResponseEntity<Teacher> getScheduleByTeacherAlias(@PathVariable String alias) {
        return jadwalService.getScheduleByTeacherAlias(alias)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ENDPOINT BARU: Mencari jadwal berdasarkan KODE ALIAS dan HARI.
     * URL: GET /api/jadwal/dosen/{alias}/{hari}
     * Contoh: /api/jadwal/dosen/AINIL/senin
     */
    @GetMapping("/{alias}/{hari}")
    public ResponseEntity<Day> getScheduleByTeacherAliasAndDay(
            @PathVariable String alias,
            @PathVariable String hari) {
        return jadwalService.getScheduleByTeacherAliasAndDay(alias, hari)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}