package com.ecomarket.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String sku;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    private BigDecimal purchaseCost;
    
    private BigDecimal profitMargin;
    
    @Column(name = "is_ecological")
    private Boolean isEcological;
    
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "main_supplier_id")
    private Long mainSupplierId;
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    
    @Column(nullable = false)
    private String status;
    
    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }
}