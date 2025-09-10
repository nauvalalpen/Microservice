package com.nauval.order.order.vo;

import com.nauval.order.order.model.Order;

public class ResponseTemplate {
    Order order;
    Produk produk;
    Pelanggan pelanggan;

    public ResponseTemplate() {

    }

    public ResponseTemplate(Order order, Produk produk, Pelanggan pelanggan) {
        this.order = order;
        this.produk = produk;
        this.pelanggan = pelanggan;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Order getOrder() {
        return order;
    }

    public Produk getProduk() {
        return produk;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

}