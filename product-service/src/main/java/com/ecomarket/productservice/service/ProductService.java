package com.ecomarket.productservice.service;

import com.ecomarket.productservice.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    
    ProductDto createProduct(ProductDto productDto);
    
    ProductDto updateProduct(Long id, ProductDto productDto);
    
    ProductDto getProductById(Long id);
    
    ProductDto getProductBySku(String sku);
    
    List<ProductDto> getAllProducts();
    
    List<ProductDto> getEcologicalProducts(Boolean isEcological);
    
    List<ProductDto> getProductsByCategory(Long categoryId);
    
    void deleteProduct(Long id);
    
    boolean existsBySku(String sku);
}