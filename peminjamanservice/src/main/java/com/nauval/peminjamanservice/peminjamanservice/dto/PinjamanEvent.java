package com.nauval.peminjamanservice.peminjamanservice.dto;

import com.nauval.peminjamanservice.peminjamanservice.model.Pinjaman;

public class PinjamanEvent {

    private EventType eventType;
    private Long kd_transaksi;

    // Hanya field input mentah yang disimpan sebagai state
    private String nasabah;
    private double jumlahPinjam;
    private int lamaPinjam;

    // Field hasil kalkulasi (bunga, angsuran, total) telah dihapus dari state.

    public enum EventType {
        CREATED, UPDATED, DELETED
    }

    // Constructor kosong (wajib untuk deserialisasi JSON)
    public PinjamanEvent() {
    }

    // --- GETTER & SETTER UNTUK FIELD INPUT ---
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Long getKd_transaksi() {
        return kd_transaksi;
    }

    public void setKd_transaksi(Long kd_transaksi) {
        this.kd_transaksi = kd_transaksi;
    }

    public String getNasabah() {
        return nasabah;
    }

    public void setNasabah(String nasabah) {
        this.nasabah = nasabah;
    }

    public double getJumlahPinjam() {
        return jumlahPinjam;
    }

    public void setJumlahPinjam(double jumlahPinjam) {
        this.jumlahPinjam = jumlahPinjam;
    }

    public int getLamaPinjam() {
        return lamaPinjam;
    }

    public void setLamaPinjam(int lamaPinjam) {
        this.lamaPinjam = lamaPinjam;
    }

    // --- GETTER "PINTAR" YANG MELAKUKAN KALKULASI ---

    /**
     * Menghitung nilai bunga secara on-the-fly setiap kali dipanggil.
     * 
     * @return Nilai bunga (10% dari jumlah pinjaman).
     */
    public double getBunga() {
        if (this.getJumlahPinjam() <= 0) {
            return 0;
        }
        return this.getJumlahPinjam() * 0.10;
    }

    /**
     * Menghitung nilai angsuran per bulan secara on-the-fly.
     * 
     * @return Nilai angsuran per bulan yang dihitung.
     */
    public double getAngsuranPerbulan() {
        if (this.getJumlahPinjam() <= 0 || this.getLamaPinjam() <= 0) {
            return 0;
        }
        // Memanggil getter lain untuk melakukan kalkulasi
        return this.getBunga() + (this.getJumlahPinjam() / this.getLamaPinjam());
    }

    /**
     * Menghitung nilai total pinjaman secara on-the-fly.
     * 
     * @return Nilai total pinjaman yang dihitung.
     */
    public double getTotalPinjam() {
        if (this.getJumlahPinjam() <= 0) {
            return 0;
        }
        return this.getJumlahPinjam() + this.getBunga();
    }

    // Setter untuk field kalkulasi tidak ada, karena nilainya tidak bisa di-set
    // secara manual.

    // --- HELPER METHOD UNTUK KONVERSI KE ENTITY ---
    /**
     * Mengubah DTO ini menjadi Entity Pinjaman. Method ini akan memanggil
     * semua getter "pintar" untuk mengisi field-field di entity.
     * 
     * @return Objek Pinjaman yang siap disimpan ke database.
     */
    public Pinjaman toEntity() {
        Pinjaman pinjaman = new Pinjaman();
        pinjaman.setKd_transaksi(this.getKd_transaksi());
        pinjaman.setNasabah(this.getNasabah());
        pinjaman.setJumlahPinjam(this.getJumlahPinjam());
        pinjaman.setLamaPinjam(this.getLamaPinjam());

        // Panggil getter "pintar" untuk mengisi entity
        pinjaman.setBunga(this.getBunga());
        pinjaman.setAngsuranPerbulan(this.getAngsuranPerbulan());
        pinjaman.setTotalPinjam(this.getTotalPinjam());

        return pinjaman;
    }

    @Override
    public String toString() {
        return "PinjamanEvent{" +
                "eventType=" + eventType +
                ", kd_transaksi=" + kd_transaksi +
                ", nasabah='" + nasabah + '\'' +
                ", jumlahPinjam=" + jumlahPinjam +
                ", lamaPinjam=" + lamaPinjam +
                ", bunga=" + getBunga() +
                ", angsuranPerbulan=" + getAngsuranPerbulan() +
                ", totalPinjam=" + getTotalPinjam() +
                '}';
    }
}