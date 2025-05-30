package com.ecomarket.productservice.repository;

import com.ecomarket.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findBySku(String sku);
    
    List<Product> findByIsEcological(Boolean isEcological);
    
    List<Product> findByCategoryId(Long categoryId);
    
    boolean existsBySku(String sku);
}