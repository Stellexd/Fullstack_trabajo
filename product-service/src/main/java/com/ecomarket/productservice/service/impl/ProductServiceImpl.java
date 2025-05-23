package com.ecomarket.productservice.service.impl;

import com.ecomarket.productservice.exception.DuplicateResourceException;
import com.ecomarket.productservice.exception.ResourceNotFoundException;
import com.ecomarket.productservice.model.Product;
import com.ecomarket.productservice.model.dto.ProductDto;
import com.ecomarket.productservice.repository.ProductRepository;
import com.ecomarket.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        // Verificar si ya existe un producto con el mismo SKU
        if (productRepository.existsBySku(productDto.getSku())) {
            throw new DuplicateResourceException("Ya existe un producto con el SKU: " + productDto.getSku());
        }
        
        // Convertir DTO a entidad
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        
        // Guardar entidad
        Product savedProduct = productRepository.save(product);
        
        // Convertir entidad guardada a DTO
        ProductDto savedProductDto = new ProductDto();
        BeanUtils.copyProperties(savedProduct, savedProductDto);
        
        return savedProductDto;
    }
    
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        // Buscar producto por ID
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        
        // Verificar si se está cambiando el SKU y si el nuevo SKU ya existe
        if (!product.getSku().equals(productDto.getSku()) && productRepository.existsBySku(productDto.getSku())) {
            throw new DuplicateResourceException("Ya existe un producto con el SKU: " + productDto.getSku());
        }
        
        // Actualizar entidad con datos del DTO
        BeanUtils.copyProperties(productDto, product, "id");
        
        // Guardar entidad actualizada
        Product updatedProduct = productRepository.save(product);
        
        // Convertir entidad actualizada a DTO
        ProductDto updatedProductDto = new ProductDto();
        BeanUtils.copyProperties(updatedProduct, updatedProductDto);
        
        return updatedProductDto;
    }
    
    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        
        return productDto;
    }
    
    @Override
    public ProductDto getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con SKU: " + sku));
        
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        
        return productDto;
    }
    
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDto> getEcologicalProducts(Boolean isEcological) {
        List<Product> products = productRepository.findByIsEcological(isEcological);
        
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        
        return products.stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        
        productRepository.deleteById(id);
    }
    
    @Override
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }
}