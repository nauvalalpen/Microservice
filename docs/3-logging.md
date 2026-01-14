# Langkah 3: Implementasi Centralized Logging (ELK Stack)

Menggunakan **Elasticsearch, Logstash, Kibana (ELK)** ditambah **Filebeat** sebagai log shipper.

## Arsitektur Logging

- **Filebeat (DaemonSet):** Mengambil log dari setiap node Kubernetes.
- **Logstash:** Memproses dan mem-parsing log (JSON filter).
- **Elasticsearch:** Menyimpan data log.
- **Kibana:** Interface untuk mencari dan visualisasi log.

## Langkah Instalasi

1.  **Buat Namespace Logging**

    ```bash
    kubectl create namespace logging-ns
    ```

2.  **Deploy Komponen ELK**
    Masuk ke folder `kubernetes/logging`:
    ```bash
    kubectl apply -f elasticsearch.yaml
    kubectl apply -f kibana.yaml
    kubectl apply -f logstash.yaml
    kubectl apply -f filebeat.yaml
    ```

## Konfigurasi Kibana

1.  Buka http://localhost:30601
2.  Masuk ke **Stack Management > Index Patterns**.
3.  Buat index pattern: `microservices-log-*`.
4.  Buka menu **Discover** untuk melihat log aplikasi.
