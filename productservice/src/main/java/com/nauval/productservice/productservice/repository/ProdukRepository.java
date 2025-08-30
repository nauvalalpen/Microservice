package com.nauval.productservice.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nauval.productservice.productservice.model.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Long> {

}