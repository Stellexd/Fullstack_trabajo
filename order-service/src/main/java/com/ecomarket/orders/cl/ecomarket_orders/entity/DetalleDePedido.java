package com.ecomarket.orders.cl.ecomarket_orders.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

//import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "detalle_de_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)

public class DetalleDePedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Long detalleId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pedido pedido;
    
    @NotNull
    @Column(name = "producto_id")
    private Long productoId;
    
    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @Min(1)
    @Column(name = "cantidad")
    @Builder.Default
    private Integer cantidad = 1;
    
    @Column(name = "sub_total", precision = 10, scale = 2)
    private BigDecimal subTotal;
    
    @PrePersist
    @PreUpdate
    protected void calculateSubTotal() {
        if (precioUnitario != null && cantidad != null) {
            this.subTotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }

}
