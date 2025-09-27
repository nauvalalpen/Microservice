package com.nauval.notificationservice.notificationservice.service;

import com.nauval.notificationservice.notificationservice.dto.PeminjamanNotificationDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.nauval.notificationservice.notificationservice.dto.PeminjamanNotificationDTO;

@Service
public class NotificationConsumer {

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handlePeminjamanNotification(PeminjamanNotificationDTO notificationDTO) {
        System.out.println("Received notification from RabbitMQ: " + notificationDTO);
        emailService.sendPeminjamanNotification(notificationDTO);
    }
}