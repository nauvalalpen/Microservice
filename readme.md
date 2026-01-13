# Perpustakaan Microservices System 📚

Proyek ini adalah implementasi sistem Perpustakaan berbasis Microservices menggunakan **Spring Boot**, yang dijalankan di atas **Kubernetes**. Sistem ini mencakup implementasi pola CQRS, Centralized Logging, Monitoring, dan CI/CD Pipeline.

## Arsitektur & Teknologi

- **Core Services:** Buku, Anggota, Peminjaman (Command & Query), Pengembalian, Notifikasi.
- **Infrastructure:** API Gateway, Eureka Server (Service Discovery).
- **Database & Messaging:** MongoDB, H2 Database, RabbitMQ.
- **Orchestration:** Kubernetes (K8s).
- **Monitoring:** Prometheus & Grafana.
- **Logging:** ELK Stack (Elasticsearch, Logstash, Kibana, Filebeat).
- **CI/CD:** Jenkins.

## 🚀 Panduan Instalasi & Dokumentasi

Silakan ikuti panduan langkah demi langkah di bawah ini untuk menjalankan sistem:

1.  **[Deploy Aplikasi Utama & Database](docs/1-setup-kubernetes.md)**
    - Setup MongoDB, RabbitMQ, dan Service Aplikasi (Buku, Anggota, dll).
2.  **[Implementasi Monitoring](docs/2-monitoring.md)**
    - Visualisasi metrik aplikasi menggunakan Prometheus & Grafana.
3.  **[Implementasi Centralized Logging](docs/3-logging.md)**
    - Setup ELK Stack untuk agregasi log dari seluruh container.
4.  **[Implementasi CI/CD dengan Jenkins](docs/4-jenkins-cicd.md)**
    - Otomatisasi build dan deploy menggunakan Jenkins Pipeline.

## 🛠️ Prasyarat

- Docker Desktop (Kubernetes enabled)
- Git
- Java JDK 17
- Maven
