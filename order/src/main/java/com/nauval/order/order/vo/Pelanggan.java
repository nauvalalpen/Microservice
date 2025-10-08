package com.nauval.order.order.vo;

public class Pelanggan {
    private Long id;
    private String kode;
    private String nama;
    private String alamat;
    private String email; // <-- TAMBAHKAN FIELD INI

    public Pelanggan() {
        // Constructor kosong penting untuk Jackson
    }

    // Perbarui constructor agar mencakup email
    public Pelanggan(Long id, String kode, String nama, String alamat, String email) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email; // <-- TAMBAHKAN INI
    }

    // Getter dan Setter yang ada (id, kode, nama, alamat)...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // === TAMBAHKAN GETTER DAN SETTER UNTUK EMAIL ===
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Pelanggan{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}