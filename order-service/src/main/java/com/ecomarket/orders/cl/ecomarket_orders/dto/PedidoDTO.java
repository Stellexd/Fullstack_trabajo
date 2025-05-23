package com.ecomarket.orders.cl.ecomarket_orders.dto;

import com.ecomarket.orders.cl.ecomarket_orders.entity.EstadoPedido;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid; //agregado
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PedidoDTO {
    
    private Long pedidoId;
    
    @NotNull(message = "La fecha del pedido es obligatoria")
    private LocalDate fechaDePedido;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;
    
    private EstadoPedido estado;
    
    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    private BigDecimal descuento;
    
    @NotNull(message = "El método de pago es obligatorio")
    private Long metodoPagoId;
    
    private Long usuarioId;
    
    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor a 0")
    private BigDecimal subtotal;
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private BigDecimal total;
    
    @Size(max = 200, message = "La dirección de envío no puede exceder 200 caracteres")
    private String direccionEnvio;
    
    @Size(max = 100, message = "La ciudad de envío no puede exceder 100 caracteres")
    private String ciudadEnvio;
    
    private String notas;
    
    @Valid
    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    private List<DetallePedidoDTO> detalles;
}