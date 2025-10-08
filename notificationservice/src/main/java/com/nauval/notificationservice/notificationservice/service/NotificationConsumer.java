package com.nauval.notificationservice.notificationservice.service;

import com.nauval.notificationservice.notificationservice.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Received OrderCreatedEvent for notification, order ID: " + event.getOrderId());
        emailService.sendOrderConfirmationEmail(event);
    }
}