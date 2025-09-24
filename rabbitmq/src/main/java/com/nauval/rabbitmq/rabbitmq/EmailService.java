package com.nauval.rabbitmq.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.admin-email}")
    private String adminEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a confirmation email to the customer after their order is processed.
     * 
     * @param order The completed order.
     */
    public void sendOrderConfirmationEmail(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(order.getCustomerEmail());
            message.setSubject("Your Order #" + order.getId() + " has been processed!");
            message.setText(buildEmailBody(order));
            mailSender.send(message);
            System.out.println("Confirmation email sent successfully to " + order.getCustomerEmail());
        } catch (Exception e) {
            System.err.println("Error sending confirmation email for order " + order.getId() + ": " + e.getMessage());
            // Depending on requirements, you might want to handle this failure differently
        }
    }

    /**
     * Sends a failure notification to an admin.
     * 
     * @param order     The order that failed to process.
     * @param exception The exception that occurred.
     */
    public void sendOrderProcessingFailedEmail(Order order, Exception exception) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(adminEmail);
            message.setSubject("URGENT: Order Processing Failed for Order #" + order.getId());
            message.setText(
                    "Processing failed for the following order:\n\n" +
                            order.toString() + "\n\n" +
                            "Error details:\n" +
                            exception.getMessage());
            mailSender.send(message);
            System.out.println("Failure notification email sent successfully to admin.");
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to send the failure notification email for order " + order.getId()
                    + ": " + e.getMessage());
        }
    }

    private String buildEmailBody(Order order) {
        return String.format(
                "Dear Customer,\n\n" +
                        "Thank you for your order! Your order has been successfully processed.\n\n" +
                        "Order Details:\n" +
                        "-----------------\n" +
                        "Order ID: %s\n" +
                        "Product: %s\n" +
                        "Quantity: %d\n" +
                        "Total Price: %.2f\n" +
                        "-----------------\n\n" +
                        "We will notify you again once your order has been shipped.\n\n" +
                        "Best regards,\n" +
                        "Your Awesome Store",
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice() * order.getQuantity());
    }
}