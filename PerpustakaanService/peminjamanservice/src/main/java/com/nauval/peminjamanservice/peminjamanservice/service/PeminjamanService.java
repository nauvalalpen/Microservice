package com.nauval.peminjamanservice.peminjamanservice.service;

import org.springframework.web.client.RestTemplate; // Import RestTemplate
import com.nauval.peminjamanservice.peminjamanservice.model.Peminjaman;
import com.nauval.peminjamanservice.peminjamanservice.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nauval.peminjamanservice.peminjamanservice.vo.ResponseTemplateVO; // Import VO
import com.nauval.peminjamanservice.peminjamanservice.vo.Anggota;
import com.nauval.peminjamanservice.peminjamanservice.vo.Buku;
import java.time.LocalDate;

@Service
public class PeminjamanService {
    @Autowired
    private PeminjamanRepository peminjamanRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Peminjaman save(Peminjaman peminjaman) {
        // Validasi ke service lain
        restTemplate.getForObject("http://ANGGOTA-SERVICE/api/anggota/" + peminjaman.getAnggotaId(), Anggota.class);
        restTemplate.getForObject("http://BUKU-SERVICE/api/buku/" + peminjaman.getBukuId(), Buku.class);
        peminjaman.setTanggalPinjam(LocalDate.now());
        peminjaman.setTanggalKembali(LocalDate.now().plusDays(7));
        return peminjamanRepository.save(peminjaman);
    }

    public ResponseTemplateVO findById(Long id) {
        Peminjaman peminjaman = peminjamanRepository.findById(id).orElse(null);
        if (peminjaman == null)
            return null;
        Anggota anggota = restTemplate.getForObject("http://ANGGOTA-SERVICE/api/anggota/" + peminjaman.getAnggotaId(),
                Anggota.class);
        Buku buku = restTemplate.getForObject("http://BUKU-SERVICE/api/buku/" + peminjaman.getBukuId(), Buku.class);
        return new ResponseTemplateVO(peminjaman, anggota, buku);
    }
}