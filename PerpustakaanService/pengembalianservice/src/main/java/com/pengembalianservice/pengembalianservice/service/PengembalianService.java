package com.pengembalianservice.pengembalianservice.service;

import com.pengembalianservice.pengembalianservice.model.Pengembalian;
import com.pengembalianservice.pengembalianservice.repository.PengembalianRepository;
import com.pengembalianservice.pengembalianservice.vo.Peminjaman;
import com.pengembalianservice.pengembalianservice.vo.PeminjamanResponseTemplateVO;
import com.pengembalianservice.pengembalianservice.vo.PengembalianResponseTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PengembalianService {
    @Autowired
    private PengembalianRepository pengembalianRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Pengembalian save(Pengembalian pengembalian) {
        Peminjaman peminjaman = restTemplate.getForObject(
                "http://PEMINJAMAN-SERVICE/api/peminjaman/data/" + pengembalian.getPeminjamanId(),
                Peminjaman.class);

        if (peminjaman == null) {
            throw new RuntimeException("Data Peminjaman tidak ditemukan dengan ID: " + pengembalian.getPeminjamanId());
        }

        pengembalian.setTanggalDikembalikan(LocalDate.now());
        long daysBetween = ChronoUnit.DAYS.between(peminjaman.getTanggalKembali(),
                pengembalian.getTanggalDikembalikan());
        int keterlambatan = daysBetween > 0 ? (int) daysBetween : 0;
        pengembalian.setTerlambat(keterlambatan);
        double denda = keterlambatan * 1000.0;
        pengembalian.setDenda(denda);

        return pengembalianRepository.save(pengembalian);
    }

    // --- METHOD BARU YANG ANDA BUTUHKAN ---
    public PengembalianResponseTemplateVO findDetailById(Long id) {
        // 1. Cari data pengembalian dari database lokal
        Pengembalian pengembalian = pengembalianRepository.findById(id).orElse(null);
        if (pengembalian == null) {
            return null;
        }

        // 2. Panggil peminjaman-service untuk mendapatkan detail lengkap peminjaman
        // Pastikan peminjaman-service memiliki endpoint ini yang mengembalikan
        // PeminjamanResponseTemplateVO
        PeminjamanResponseTemplateVO peminjamanDetail = restTemplate.getForObject(
                "http://PEMINJAMAN-SERVICE/api/peminjaman/" + pengembalian.getPeminjamanId(),
                PeminjamanResponseTemplateVO.class);

        // 3. Gabungkan hasilnya ke dalam satu objek respons
        PengembalianResponseTemplateVO response = new PengembalianResponseTemplateVO();
        response.setPengembalian(pengembalian);
        response.setPeminjamanDetail(peminjamanDetail);

        return response;
    }
}