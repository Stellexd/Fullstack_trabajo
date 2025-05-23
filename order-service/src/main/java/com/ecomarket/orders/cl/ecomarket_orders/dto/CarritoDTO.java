package com.ecomarket.orders.cl.ecomarket_orders.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class CarritoDTO {
    
    private Long carritoId;
    private Long clienteId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaActualizacion;
    private List<ItemCarritoDTO> items;

}
