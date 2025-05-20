package com.ecomarket.productservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    
    @NotBlank(message = "El SKU es obligatorio")
    private String sku;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    
    private String description;
    
    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio debe ser un valor positivo o cero")
    private BigDecimal price;
    
    private BigDecimal purchaseCost;
    
    private BigDecimal profitMargin;
    
    private Boolean isEcological;
    
    private Long categoryId;
    
    private Long mainSupplierId;
    
    private String status;
}