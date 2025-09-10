package com.nauval.pelangganservice.pelangganservice.repository;

import com.nauval.pelangganservice.pelangganservice.model.Pelanggan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
}
