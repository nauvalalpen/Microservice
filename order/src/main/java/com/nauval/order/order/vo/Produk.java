package com.nauval.order.order.vo;

// Jika Anda tidak pakai Lombok, pastikan mengubah constructor dan getter/setter juga.
// Saya akan berikan contoh dengan Lombok agar lebih jelas.
import lombok.Data;

@Data
public class Produk {

    private int id; // 'Id' dengan 'I' besar mungkin juga bisa jadi masalah, sebaiknya 'id'
    private String nama;
    private String satuan; // Tambahkan field ini jika dibutuhkan
    private Double harga; // <-- UBAH DARI 'satuanHarga' MENJADI 'harga'
}