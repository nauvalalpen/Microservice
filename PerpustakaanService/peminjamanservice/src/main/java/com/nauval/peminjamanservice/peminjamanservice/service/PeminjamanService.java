package com.nauval.peminjamanservice.peminjamanservice.service;

import org.springframework.web.client.RestTemplate;
import com.nauval.peminjamanservice.peminjamanservice.model.Peminjaman;
import com.nauval.peminjamanservice.peminjamanservice.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nauval.peminjamanservice.peminjamanservice.vo.ResponseTemplateVO;
import com.nauval.peminjamanservice.peminjamanservice.vo.Anggota;
import com.nauval.peminjamanservice.peminjamanservice.vo.Buku;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.nauval.peminjamanservice.peminjamanservice.dto.PeminjamanNotificationDTO;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value; // === ADD THIS IMPORT ===

@Service
public class PeminjamanService {

    private final PeminjamanRepository peminjamanRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;
    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    @Autowired
    public PeminjamanService(PeminjamanRepository peminjamanRepository, RestTemplate restTemplate,
            RabbitTemplate rabbitTemplate) {
        this.peminjamanRepository = peminjamanRepository;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Peminjaman save(Peminjaman peminjaman) {
        // Fetch Anggota and Buku (your existing code)
        Anggota anggota = restTemplate.getForObject("http://anggotaservice/api/anggota/" + peminjaman.getAnggotaId(),
                Anggota.class);
        Buku buku = restTemplate.getForObject("http://bukuservice/api/buku/" + peminjaman.getBukuId(), Buku.class);

        // Save the loan (your existing code)
        peminjaman.setTanggalPinjam(LocalDate.now());
        peminjaman.setTanggalKembali(LocalDate.now().plusDays(7));
        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);

        // --- NEW LOGIC: Send message to RabbitMQ ---
        // 1. Create the DTO
        PeminjamanNotificationDTO notificationDTO = new PeminjamanNotificationDTO(
                savedPeminjaman.getId(),
                anggota.getNama(),
                anggota.getEmail(),
                buku.getJudul(),
                savedPeminjaman.getTanggalPinjam(),
                savedPeminjaman.getTanggalKembali());

        // 2. Send the message
        rabbitTemplate.convertAndSend(exchange, routingKey, notificationDTO);
        System.out.println("Peminjaman notification sent to RabbitMQ: " + notificationDTO);

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