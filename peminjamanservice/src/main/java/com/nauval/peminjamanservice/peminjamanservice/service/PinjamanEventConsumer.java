package com.nauval.peminjamanservice.peminjamanservice.service;

import com.nauval.peminjamanservice.peminjamanservice.dto.PinjamanEvent;
import com.nauval.peminjamanservice.peminjamanservice.model.PinjamanReadModel;
import com.nauval.peminjamanservice.peminjamanservice.repository.PinjamanReadModelRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PinjamanEventConsumer {

    private final PinjamanReadModelRepository readModelRepository;

    public PinjamanEventConsumer(PinjamanReadModelRepository readModelRepository) {
        this.readModelRepository = readModelRepository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handlePinjamanEvent(PinjamanEvent event) {
        System.out.println("Received event of type: " + event.getEventType());

        // Gunakan switch case untuk menentukan tindakan
        switch (event.getEventType()) {
            case CREATED:
            case UPDATED:
                // Logika untuk Create dan Update sama: simpan/timpa data di MongoDB
                PinjamanReadModel readModel = new PinjamanReadModel();
                readModel.setKd_transaksi(event.getKd_transaksi());
                readModel.setNasabah(event.getNasabah());
                readModel.setJumlahPinjam(event.getJumlahPinjam());
                readModel.setLamaPinjam(event.getLamaPinjam());
                readModel.setBunga(event.getBunga());
                readModel.setAngsuranPerbulan(event.getAngsuranPerbulan());
                readModel.setTotalPinjam(event.getTotalPinjam());

                readModelRepository.save(readModel);
                System.out.println("Saved/Updated Read Model in MongoDB for id: " + event.getKd_transaksi());
                break;

            case DELETED:
                // Logika untuk Delete: hapus data dari MongoDB
                readModelRepository.deleteById(event.getKd_transaksi());
                System.out.println("Deleted Read Model from MongoDB for id: " + event.getKd_transaksi());
                break;

            default:
                System.err.println("Unknown event type received: " + event.getEventType());
                break;
        }
    }
}