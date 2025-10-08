package com.nauval.notificationservice.notificationservice.service;

import com.nauval.notificationservice.notificationservice.dto.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderConfirmationEmail(OrderCreatedEvent event) {
        if (event.getEmailPelanggan() == null || event.getEmailPelanggan().isEmpty()) {
            System.err.println(
                    "Email not sent: Pelanggan '" + event.getNamaPelanggan() + "' does not have an email address.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(event.getEmailPelanggan());
            message.setSubject("Konfirmasi Pesanan #" + event.getOrderId());
            message.setText(buildEmailBody(event));

            mailSender.send(message);
            System.out.println("Email konfirmasi pesanan berhasil dikirim ke " + event.getEmailPelanggan());
        } catch (Exception e) {
            System.err.println("Gagal mengirim email untuk pesanan ID " + event.getOrderId() + ": " + e.getMessage());
        }
    }

    private String buildEmailBody(OrderCreatedEvent event) {
        return String.format(
                "Halo %s,\n\n" +
                        "Terima kasih atas pesanan Anda! Pesanan Anda telah kami terima dan sedang diproses.\n\n" +
                        "--- Rincian Pesanan ---\n" +
                        "Nomor Pesanan: %d\n" +
                        "Tanggal: %s\n\n" +
                        "Produk: %s\n" +
                        "Jumlah: %d\n" +
                        "Harga Satuan: Rp %.2f\n" +
                        "--------------------------\n" +
                        "Total: Rp %.2f\n\n" +
                        "Kami akan memberitahu Anda lagi setelah pesanan Anda dikirim.\n\n" +
                        "Salam,\n" +
                        "Toko Nauval",
                event.getNamaPelanggan(),
                event.getOrderId(),
                event.getTanggal(),
                event.getNamaProduk(),
                event.getJumlah(),
                event.getHargaProduk(),
                event.getTotal());
    }
}