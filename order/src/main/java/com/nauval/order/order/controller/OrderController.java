package com.nauval.order.order.controller;

import com.nauval.order.order.model.Order;
import com.nauval.order.order.service.OrderService;
import com.nauval.order.order.vo.ResponseTemplate; // <-- Import class ResponseTemplate
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // == ENDPOINT CRUD DASAR ==

    // Membuat Order baru
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Mendapatkan semua data Order
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Mendapatkan satu Order berdasarkan ID (hanya data order saja)
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mengupdate Order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDetails);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Menghapus Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // == ENDPOINT BARU UNTUK MENGGABUNGKAN DATA ==

    /**
     * Endpoint untuk mendapatkan detail order LENGKAP
     * (Order + Produk + Pelanggan)
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseTemplate> getOrderWithAllDetail(@PathVariable Long id) {
        try {
            ResponseTemplate response = orderService.getOrderWithDetail(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint untuk mendapatkan detail order HANYA DENGAN PRODUK
     * (Order + Produk)
     */
    @GetMapping("/produk/{id}")
    public ResponseEntity<ResponseTemplate> getOrderWithProdukInfo(@PathVariable Long id) {
        try {
            ResponseTemplate response = orderService.getOrderWithProduk(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint untuk mendapatkan detail order HANYA DENGAN PELANGGAN
     * (Order + Pelanggan)
     */
    @GetMapping("/pelanggan/{id}")
    public ResponseEntity<ResponseTemplate> getOrderWithPelangganInfo(@PathVariable Long id) {
        try {
            ResponseTemplate response = orderService.getOrderWithPelanggan(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}