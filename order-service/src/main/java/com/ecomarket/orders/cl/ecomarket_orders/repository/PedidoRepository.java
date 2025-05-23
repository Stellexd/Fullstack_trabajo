package com.ecomarket.orders.cl.ecomarket_orders.repository;

import com.ecomarket.orders.cl.ecomarket_orders.entity.EstadoPedido;
import com.ecomarket.orders.cl.ecomarket_orders.entity.Pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByClienteId(Long clienteId);
    
    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);
    
    List<Pedido> findByEstado(EstadoPedido estado);
    
    List<Pedido> findByFechaDePedidoBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    @Query("SELECT p FROM Pedido p WHERE p.clienteId = :clienteId AND p.estado = :estado")
    List<Pedido> findByClienteIdAndEstado(@Param("clienteId") Long clienteId, 
                                          @Param("estado") EstadoPedido estado);
    
    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.detalles WHERE p.pedidoId = :id")
    Optional<Pedido> findByIdWithDetalles(@Param("id") Long id);
    
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.clienteId = :clienteId")
    Long countByClienteId(@Param("clienteId") Long clienteId);
    
    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p WHERE p.clienteId = :clienteId AND p.estado != 'CANCELADO'")
    BigDecimal sumTotalByClienteId(@Param("clienteId") Long clienteId);
}
