package com.ecomarket.orders.cl.ecomarket_orders.repository;

import com.ecomarket.orders.cl.ecomarket_orders.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    Optional<Carrito> findByClienteId(Long clienteId);
    
    @Query("SELECT c FROM Carrito c LEFT JOIN FETCH c.items WHERE c.clienteId = :clienteId")
    Optional<Carrito> findByClienteIdWithItems(@Param("clienteId") Long clienteId);
    
    boolean existsByClienteId(Long clienteId);
}