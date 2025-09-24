package com.nauval.rabbitmq.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderConsumerService {

    private final OrderRepository orderRepository;
    private final EmailService emailService; // <-- ADD THIS

    // UPDATE THE CONSTRUCTOR
    public OrderConsumerService(OrderRepository orderRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.emailService = emailService; // <-- ADD THIS
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    @Transactional
    public void receiveOrder(@Payload Order order) {
        try {
            System.out.println("Order received from RabbitMQ: " + order);

            // Update status order
            order.setStatus(Order.OrderStatus.PROCESSING);
            orderRepository.save(order);

            // Simulasi proses bisnis
            processOrder(order);

            // Update status setelah selesai diproses
            order.setStatus(Order.OrderStatus.COMPLETED);
            order.setProcessedAt(java.time.LocalDateTime.now());
            orderRepository.save(order);

            System.out.println("Order processed successfully: " + order.getId());

            // --- SEND CONFIRMATION EMAIL ---
            emailService.sendOrderConfirmationEmail(order); // <-- ADD THIS LINE

        } catch (Exception e) {
            System.err.println("Error processing order: " + order.getId() + ", Error: " + e.getMessage());

            // Update status jika gagal
            order.setStatus(Order.OrderStatus.FAILED);
            orderRepository.save(order);

            // --- SEND FAILURE NOTIFICATION EMAIL ---
            emailService.sendOrderProcessingFailedEmail(order, e); // <-- ADD THIS LINE

            // Bisa ditambahkan logic untuk retry atau dead letter queue
            throw new RuntimeException("Failed to process order", e);
        }
    }

    private void processOrder(Order order) {
        // Simulasi proses bisnis
        System.out.println("Processing order: " + order.getId());

        // This log is now redundant as we are sending a real email
        // System.out.println("Sending confirmation email to: " +
        // order.getCustomerEmail());

        // Simulasi delay processing
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Order processing completed: " + order.getId());
    }
}