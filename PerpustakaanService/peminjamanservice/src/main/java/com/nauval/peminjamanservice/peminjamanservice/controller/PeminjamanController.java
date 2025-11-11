package com.nauval.peminjamanservice.peminjamanservice.controller;

import com.nauval.peminjamanservice.peminjamanservice.model.Peminjaman;
import com.nauval.peminjamanservice.peminjamanservice.service.PeminjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nauval.peminjamanservice.peminjamanservice.vo.ResponseTemplateVO;

@RestController
// UBAH PATH DASAR DI SINI
@RequestMapping("/api/peminjaman/command")
public class PeminjamanController {

    // Anotasi @Autowired pada field tidak direkomendasikan, lebih baik via
    // constructor
    private final PeminjamanService peminjamanService;

    @Autowired
    public PeminjamanController(PeminjamanService peminjamanService) {
        this.peminjamanService = peminjamanService;
    }

    @PostMapping
    public Peminjaman save(@RequestBody Peminjaman peminjaman) {
        return peminjamanService.save(peminjaman);
    }

    // CATATAN: Semua endpoint GET yang ada di sini sebaiknya dihapus
    // karena service ini sekarang adalah Command Service.
}