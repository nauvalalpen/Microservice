# Langkah 2: Implementasi Monitoring (Prometheus & Grafana)

Menggunakan **Prometheus** untuk scraping metrik dan **Grafana** untuk visualisasi dashboard.

## Konfigurasi

File konfigurasi terletak di folder `kubernetes/monitoring`.

## Langkah Instalasi

1.  **Buat Namespace Monitoring**

    ```bash
    kubectl create namespace monitoring-ns
    ```

2.  **Deploy Prometheus**
    Prometheus dikonfigurasi untuk mendeteksi service Spring Boot secara otomatis melalui annotations.

    ```bash
    kubectl apply -f prometheus-configmap.yaml
    kubectl apply -f prometheus-deployment.yaml
    kubectl apply -f prometheus-rbac.yaml
    ```

3.  **Deploy Exporter (MongoDB & RabbitMQ)**

    ```bash
    kubectl apply -f monitoring-mongodb-secret.yaml
    kubectl apply -f mongodb-exporter.yaml
    kubectl apply -f rabbitmq-exporter.yaml
    ```

4.  **Deploy Grafana**
    ```bash
    kubectl apply -f grafana-deployment.yaml
    ```

## Akses Dashboard

- **Grafana:** http://localhost:30003 (Login default: admin/admin)
- **Prometheus:** http://localhost:30090
