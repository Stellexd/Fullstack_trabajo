package com.ecomarket.orders.cl.ecomarket_orders.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class ItemCarritoId implements Serializable {

    @Column(name = "carrito_id")
    private Long carritoId;
    
    @Column(name = "producto_id")
    private Long productoId;

}
