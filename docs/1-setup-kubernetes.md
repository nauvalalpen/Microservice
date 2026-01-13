# Langkah 1: Setup Infrastruktur & Aplikasi

Panduan ini menjelaskan cara menjalankan aplikasi inti (Microservices) beserta database pendukungnya di Kubernetes.

## Langkah-Langkah

1.  **Buat Namespace**

    ```bash
    kubectl create namespace perpustakaan-ns
    ```

2.  **Deploy Database & Broker**
    Masuk ke folder `kubernetes`:

    ```bash
    kubectl apply -f mongodb-secret.yaml
    kubectl apply -f mongodb-pvc.yaml
    kubectl apply -f mongodb-deployment.yaml
    kubectl apply -f rabbitmq-deployment.yaml
    ```

3.  **Deploy Microservices**
    Terapkan konfigurasi deployment untuk semua service:

    ```bash
    kubectl apply -f eureka-deployment.yaml
    kubectl apply -f api-gateway-deployment.yaml
    kubectl apply -f buku-service-deployment.yaml
    kubectl apply -f anggota-service-deployment.yaml
    kubectl apply -f notification-service-deployment.yaml
    kubectl apply -f peminjaman-query-service-deployment.yaml
    kubectl apply -f peminjaman-service-deployment.yaml
    kubectl apply -f pengembalian-service-deployment.yaml
    ```

4.  **Verifikasi**
    Pastikan semua pod berjalan:
    ```bash
    kubectl get pods -n perpustakaan-ns
    ```
