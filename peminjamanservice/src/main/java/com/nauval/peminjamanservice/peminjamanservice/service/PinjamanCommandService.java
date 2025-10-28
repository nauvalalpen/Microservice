package com.nauval.peminjamanservice.peminjamanservice.service;

import com.nauval.peminjamanservice.peminjamanservice.dto.PinjamanEvent;
import com.nauval.peminjamanservice.peminjamanservice.model.Pinjaman;
import com.nauval.peminjamanservice.peminjamanservice.repository.PinjamanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PinjamanCommandService {

    private final PinjamanRepository pinjamanRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;
    @Value("${app.rabbitmq.routingkey.created}")
    private String routingKeyCreated;
    @Value("${app.rabbitmq.routingkey.updated}")
    private String routingKeyUpdated;
    @Value("${app.rabbitmq.routingkey.deleted}")
    private String routingKeyDeleted;

    @Autowired
    public PinjamanCommandService(PinjamanRepository pinjamanRepository, RabbitTemplate rabbitTemplate) {
        this.pinjamanRepository = pinjamanRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Pinjaman createPinjaman(Pinjaman pinjamanInput) {
        // 1. Buat DTO dan isi data mentah dari input
        PinjamanEvent event = new PinjamanEvent();
        event.setNasabah(pinjamanInput.getNasabah());
        event.setJumlahPinjam(pinjamanInput.getJumlahPinjam());
        event.setLamaPinjam(pinjamanInput.getLamaPinjam());

        // 2. Minta DTO untuk mengubah dirinya menjadi Entity (kalkulasi terjadi di
        // sini)
        Pinjaman pinjamanToSave = event.toEntity();

        // 3. Simpan ke DB SQL
        Pinjaman savedPinjaman = pinjamanRepository.save(pinjamanToSave);

        // 4. Lengkapi event dengan ID dan tipe
        event.setEventType(PinjamanEvent.EventType.CREATED);
        event.setKd_transaksi(savedPinjaman.getKd_transaksi());

        // 5. Kirim event ke RabbitMQ
        rabbitTemplate.convertAndSend(exchange, routingKeyCreated, event);
        System.out.println("Sent created event: " + event);

        return savedPinjaman;
    }

    public Pinjaman updatePinjaman(Long id, Pinjaman pinjamanDetails) {
        if (!pinjamanRepository.existsById(id)) {
            throw new EntityNotFoundException("Pinjaman tidak ditemukan dengan id: " + id);
        }

        // 1. Buat DTO dan isi data mentah dari input
        PinjamanEvent event = new PinjamanEvent();
        event.setNasabah(pinjamanDetails.getNasabah());
        event.setJumlahPinjam(pinjamanDetails.getJumlahPinjam());
        event.setLamaPinjam(pinjamanDetails.getLamaPinjam());

        // 2. Lengkapi DTO dengan ID dan tipe
        event.setEventType(PinjamanEvent.EventType.UPDATED);
        event.setKd_transaksi(id);

        // 3. Minta DTO untuk mengubah dirinya menjadi Entity (kalkulasi terjadi di
        // sini)
        Pinjaman pinjamanToUpdate = event.toEntity();

        // 4. Simpan perubahan ke DB SQL
        Pinjaman updatedPinjaman = pinjamanRepository.save(pinjamanToUpdate);

        // 5. Kirim event ke RabbitMQ
        rabbitTemplate.convertAndSend(exchange, routingKeyUpdated, event);
        System.out.println("Sent updated event: " + event);

        return updatedPinjaman;
    }

    public void deletePinjaman(Long id) {
        if (!pinjamanRepository.existsById(id)) {
            throw new EntityNotFoundException("Pinjaman tidak ditemukan dengan id: " + id);
        }
        pinjamanRepository.deleteById(id);

        // Buat DTO untuk delete
        PinjamanEvent event = new PinjamanEvent();
        event.setEventType(PinjamanEvent.EventType.DELETED);
        event.setKd_transaksi(id);

        rabbitTemplate.convertAndSend(exchange, routingKeyDeleted, event);
        System.out.println("Sent deleted event for id: " + id);
    }
}