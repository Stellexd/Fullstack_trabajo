package com.ecomarket.orders.cl.ecomarket_orders.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long pedidoId;
    
    @NotNull
    @Column(name = "fecha_de_pedido")
    private LocalDate fechaDePedido;
    
    @NotNull
    @Column(name = "cliente_id")
    private Long clienteId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.PENDIENTE;
    
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "descuento", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal descuento = BigDecimal.ZERO;
    
    @NotNull
    @Column(name = "metodo_pago_id")
    private Long metodoPagoId;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;
    
    @Size(max = 200)
    @Column(name = "direccion_envio")
    private String direccionEnvio;
    
    @Size(max = 100)
    @Column(name = "ciudad_envio")
    private String ciudadEnvio;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DetalleDePedido> detalles;
    
    @PrePersist
    protected void onCreate() {
        if (fechaDePedido == null) {
            fechaDePedido = LocalDate.now();
        }
    }

}
