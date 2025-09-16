package com.nauval.order.order.service;

import com.nauval.order.order.model.Order;
import com.nauval.order.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.nauval.order.order.vo.Pelanggan; // Import VO
import com.nauval.order.order.vo.Produk; // Import VO
import com.nauval.order.order.vo.ResponseTemplate; // Import VO
import org.springframework.web.client.RestTemplate; // Import RestTemplate
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // ... (di dalam kelas OrderService)

    // Method LENGKAP: Mengambil Order + Produk + Pelanggan
    public ResponseTemplate getOrderWithDetail(Long id) {
        // 1. Perbaikan: Ambil Order dari Optional atau lempar exception
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // --- Memanggil Product Service ---
        // 2. Perbaikan: Gunakan nama service yang benar ("PRODUCT-SERVICE")
        List<ServiceInstance> produkInstances = discoveryClient.getInstances("PRODUCT-SERVICE");
        // 3. Perbaikan: Cek apakah service ditemukan sebelum digunakan
        if (produkInstances.isEmpty()) {
            throw new RuntimeException("PRODUCT-SERVICE not available");
        }
        // Ambil URI dari instance pertama yang ditemukan
        String produkServiceUri = produkInstances.get(0).getUri().toString();
        Produk produk = restTemplate.getForObject(produkServiceUri + "/api/produk/" + order.getProdukId(),
                Produk.class);

        // --- Memanggil Pelanggan Service ---
        // 4. Perbaikan: Gunakan nama service yang benar ("PELANGGAN-SERVICE")
        List<ServiceInstance> pelangganInstances = discoveryClient.getInstances("PELANGGAN-SERVICE");
        // 5. Perbaikan: Cek lagi apakah service ditemukan
        if (pelangganInstances.isEmpty()) {
            throw new RuntimeException("PELANGGAN-SERVICE not available");
        }
        String pelangganServiceUri = pelangganInstances.get(0).getUri().toString();
        Pelanggan pelanggan = restTemplate
                .getForObject(pelangganServiceUri + "/api/pelanggan/" + order.getPelangganId(), Pelanggan.class);

        // 6. Perbaikan: Buat dan kembalikan SATU objek ResponseTemplate
        return new ResponseTemplate(order, produk, pelanggan);
    }

    // Method untuk mengambil Order + Produk SAJA
    public ResponseTemplate getOrderWithProduk(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        Produk produk = restTemplate.getForObject("http://localhost:8081/api/produk/" + order.getProdukId(),
                Produk.class);

        // Buat ResponseTemplate, tapi set pelanggan menjadi null
        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduk(produk);
        // vo.setPelanggan() tidak dipanggil, sehingga nilainya akan null

        return vo;
    }

    // Method untuk mengambil Order + Pelanggan SAJA
    public ResponseTemplate getOrderWithPelanggan(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        Pelanggan pelanggan = restTemplate.getForObject("http://localhost:8082/api/pelanggan/" + order.getPelangganId(),
                Pelanggan.class);

        // Buat ResponseTemplate, tapi set produk menjadi null
        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setPelanggan(pelanggan);
        // vo.setProduk() tidak dipanggil, sehingga nilainya akan null

        return vo;
    }

    public Order createOrder(Order order) {
        if (order.getTanggal() == null || order.getTanggal().isEmpty()) {
            order.setTanggal(LocalDate.now().toString());
        }
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        existingOrder.setProdukId(orderDetails.getProdukId());
        existingOrder.setPelangganId(orderDetails.getPelangganId());
        existingOrder.setJumlah(orderDetails.getJumlah());
        existingOrder.setStatus(orderDetails.getStatus());
        existingOrder.setTotal(orderDetails.getTotal());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}