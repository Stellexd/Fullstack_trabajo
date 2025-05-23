package com.ecomarket.orders.cl.ecomarket_orders.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;//arreglo del not null
import lombok.*;
import java.time.LocalDateTime;

//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ItemCarritoDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    private LocalDateTime fechaAgregado;

}
