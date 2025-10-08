package com.nauval.order.order.service;

import com.nauval.order.order.event.OrderCreatedEvent;
import com.nauval.order.order.model.Order;
import com.nauval.order.order.repository.OrderRepository;
import com.nauval.order.order.vo.Pelanggan;
import com.nauval.order.order.vo.Produk;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;
    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Method ini adalah COMMAND. Tugasnya:
     * 1. Validasi data dengan service lain.
     * 2. Menghitung total harga.
     * 3. Menyimpan Order ke database SQL.
     * 4. Menerbitkan event ke RabbitMQ.
     */
    public Order createOrder(Order order) {
        // 1. Panggil Product Service untuk validasi dan mendapatkan detail produk
        // Menggunakan nama service dari Eureka, bukan localhost
        Produk produk = restTemplate.getForObject("http://PRODUK-SERVICE/api/produk/" + order.getProdukId(),
                Produk.class);
        if (produk == null) {
            throw new RuntimeException("Produk tidak ditemukan dengan ID: " + order.getProdukId());
        }

        // 2. Panggil Pelanggan Service untuk validasi dan mendapatkan detail pelanggan
        Pelanggan pelanggan = restTemplate
                .getForObject("http://PELANGGAN-SERVICE/api/pelanggan/" + order.getPelangganId(), Pelanggan.class);
        if (pelanggan == null) {
            throw new RuntimeException("Pelanggan tidak ditemukan dengan ID: " + order.getPelangganId());
        }

        // 3. Lakukan logika bisnis (hitung total, set tanggal, dll.)
        order.setTanggal(LocalDate.now().toString());
        order.setStatus("PENDING");
        // Asumsi 'harga' ada di dalam objek Produk
        order.setTotal(produk.getHarga() * order.getJumlah());

        // 4. Simpan Order ke database SQL (Write Database)
        Order savedOrder = orderRepository.save(order);

        // 5. Buat event DTO dengan data yang sudah digabungkan
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getProdukId(),
                savedOrder.getPelangganId(),
                savedOrder.getJumlah(),
                savedOrder.getTanggal(),
                savedOrder.getTotal(),
                produk.getNama(), // Ambil dari objek produk
                produk.getHarga(), // Ambil dari objek produk
                pelanggan.getNama(), // Ambil dari objek pelanggan
                pelanggan.getEmail() // Ambil dari objek pelanggan
        );

        // 6. Kirim event ke RabbitMQ
        System.out.println("Sending OrderCreatedEvent to RabbitMQ...");
        rabbitTemplate.convertAndSend(exchange, routingKey, event);

        return savedOrder;
    }

    // --- METHOD-METHOD QUERY INI AKAN KITA PINDAHKAN NANTI KE QUERY SERVICE ---
    // Untuk sementara, kita bisa biarkan atau hapus

    public List<Order> getAllOrders() {
        System.out.println("WARNING: This method should be moved to a Query Service.");
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        System.out.println("WARNING: This method should be moved to a Query Service.");
        return orderRepository.findById(id);
    }

    // ... method update dan delete bisa tetap di sini karena mereka adalah COMMANDS
    public Order updateOrder(Long id, Order orderDetails) {
        // ... (logika update Anda)
        // Sebaiknya, update juga menerbitkan event 'OrderUpdatedEvent'
        return orderRepository.save(orderDetails);
    }

    public void deleteOrderById(Long id) {
        // ... (logika delete Anda)
        // Sebaiknya, delete juga menerbitkan event 'OrderDeletedEvent'
        orderRepository.deleteById(id);
    }
}