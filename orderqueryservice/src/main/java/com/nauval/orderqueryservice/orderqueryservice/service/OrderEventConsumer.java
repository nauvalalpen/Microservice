package com.nauval.orderqueryservice.orderqueryservice.service;

import com.nauval.orderqueryservice.orderqueryservice.dto.OrderCreatedEvent;
import com.nauval.orderqueryservice.orderqueryservice.model.OrderReadModel;
import com.nauval.orderqueryservice.orderqueryservice.repository.OrderReadModelRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final OrderReadModelRepository repository;

    public OrderEventConsumer(OrderReadModelRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Received OrderCreatedEvent for order ID: " + event.getOrderId());

        OrderReadModel readModel = new OrderReadModel();
        readModel.setOrderId(event.getOrderId());
        readModel.setNamaProduk(event.getNamaProduk());
        readModel.setHargaProduk(event.getHargaProduk());
        readModel.setJumlah(event.getJumlah());
        readModel.setTotal(event.getTotal());
        readModel.setNamaPelanggan(event.getNamaPelanggan());
        readModel.setEmailPelanggan(event.getEmailPelanggan());
        readModel.setTanggal(event.getTanggal());

        repository.save(readModel);
        System.out.println("OrderReadModel saved to MongoDB for order ID: " + event.getOrderId());
    }
}