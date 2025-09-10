package com.nauval.order.order.vo;

public class Produk {

    private int Id;
    private String nama;
    private Double satuanHarga;

    public Produk() {

    }

    public Produk(int id, String nama, Double satuanHarga) {
        Id = id;
        this.nama = nama;
        this.satuanHarga = satuanHarga;
    }

    public int getId() {
        return Id;
    }

    public String getNama() {
        return nama;
    }

    public Double getSatuanHarga() {
        return satuanHarga;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setSatuanHarga(Double satuanHarga) {
        this.satuanHarga = satuanHarga;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

}
