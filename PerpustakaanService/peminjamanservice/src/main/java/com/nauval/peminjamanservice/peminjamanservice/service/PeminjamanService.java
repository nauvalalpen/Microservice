package com.nauval.peminjamanservice.peminjamanservice.service;

import org.springframework.web.client.RestTemplate;
import com.nauval.peminjamanservice.peminjamanservice.model.Peminjaman;
import com.nauval.peminjamanservice.peminjamanservice.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nauval.peminjamanservice.peminjamanservice.vo.ResponseTemplateVO;
import com.nauval.peminjamanservice.peminjamanservice.vo.Anggota;
import com.nauval.peminjamanservice.peminjamanservice.vo.Buku;
import java.time.LocalDate;

@Service
public class PeminjamanService {

    // Using constructor injection is a best practice
    private final PeminjamanRepository peminjamanRepository;
    private final RestTemplate restTemplate;
    private final EmailService emailService; // <-- INJECT THE NEW SERVICE

    @Autowired
    public PeminjamanService(PeminjamanRepository peminjamanRepository, RestTemplate restTemplate,
            EmailService emailService) {
        this.peminjamanRepository = peminjamanRepository;
        this.restTemplate = restTemplate;
        this.emailService = emailService; // <-- ADD TO CONSTRUCTOR
    }

    public Peminjaman save(Peminjaman peminjaman) {
        // Step 1: Validate that Anggota and Buku exist (your existing code)
        Anggota anggota = restTemplate.getForObject("http://anggotaservice/api/anggota/" + peminjaman.getAnggotaId(),
                Anggota.class);
        Buku buku = restTemplate.getForObject("http://bukuservice/api/buku/" + peminjaman.getBukuId(), Buku.class);

        // Step 2: Set dates and save the loan (your existing code)
        peminjaman.setTanggalPinjam(LocalDate.now());
        peminjaman.setTanggalKembali(LocalDate.now().plusDays(7));
        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);

        // Step 3: Send the notification email
        // We already fetched 'anggota' and 'buku' objects, so we can reuse them
        emailService.sendPeminjamanNotification(savedPeminjaman, anggota, buku);

        return savedPeminjaman;
    }

    public ResponseTemplateVO findById(Long id) {
        Peminjaman peminjaman = peminjamanRepository.findById(id).orElse(null);
        if (peminjaman == null)
            return null;
        Anggota anggota = restTemplate.getForObject("http://anggotaservice/api/anggota/" + peminjaman.getAnggotaId(),
                Anggota.class);
        Buku buku = restTemplate.getForObject("http://bukuservice/api/buku/" + peminjaman.getBukuId(), Buku.class);
        return new ResponseTemplateVO(peminjaman, anggota, buku);
    }

    public Peminjaman findPeminjamanDataById(Long id) {
        return peminjamanRepository.findById(id).orElse(null);
    }
}