package com.example.demo.repositories;

import com.example.demo.models.SKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKURepository extends JpaRepository<SKU,Long> {
}
