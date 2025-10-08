package com.nauval.order.order.controller;

import com.nauval.order.order.model.Order;
import com.nauval.order.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Ini adalah endpoint COMMAND utama kita
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order newOrder = orderService.createOrder(order);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Jika produk atau pelanggan tidak ditemukan, kembalikan Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint GET ini nantinya akan dipindahkan ke orderqueryservice.
    // Untuk sekarang kita biarkan untuk testing awal.
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ... endpoint lain untuk UPDATE dan DELETE ...
}