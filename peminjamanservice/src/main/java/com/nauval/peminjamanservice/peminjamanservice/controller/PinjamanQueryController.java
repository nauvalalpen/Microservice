package com.nauval.peminjamanservice.peminjamanservice.controller;

import com.nauval.peminjamanservice.peminjamanservice.model.PinjamanReadModel;
import com.nauval.peminjamanservice.peminjamanservice.service.PinjamanQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pinjaman/query")
public class PinjamanQueryController {

    private final PinjamanQueryService queryService;

    public PinjamanQueryController(PinjamanQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public List<PinjamanReadModel> getAllPinjaman() {
        return queryService.getAllPinjaman();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PinjamanReadModel> getPinjamanById(@PathVariable Long id) {
        return queryService.getPinjamanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}