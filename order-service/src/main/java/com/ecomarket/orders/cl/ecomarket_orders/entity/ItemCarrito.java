package com.ecomarket.orders.cl.ecomarket_orders.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)

public class ItemCarrito {
    
    @EmbeddedId
    private ItemCarritoId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carritoId")
    @JoinColumn(name = "carrito_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Carrito carrito;
    
    @Column(name = "cantidad")
    @Builder.Default
    private Integer cantidad = 1;
    
    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;
    
    @PrePersist
    protected void onCreate() {
        if (fechaAgregado == null) {
            fechaAgregado = LocalDateTime.now();
        }
    }
    
    public ItemCarrito(Carrito carrito, Long productoId, Integer cantidad) {
        this.id = new ItemCarritoId(carrito.getCarritoId(), productoId);
        this.carrito = carrito;
        this.cantidad = cantidad;
    }


}
