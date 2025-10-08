package com.nauval.orderqueryservice.orderqueryservice.controller;

import com.nauval.orderqueryservice.orderqueryservice.model.OrderReadModel;
import com.nauval.orderqueryservice.orderqueryservice.repository.OrderReadModelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderQueryController {

    private final OrderReadModelRepository repository;

    public OrderQueryController(OrderReadModelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<OrderReadModel> getAllOrders() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadModel> getOrderById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}