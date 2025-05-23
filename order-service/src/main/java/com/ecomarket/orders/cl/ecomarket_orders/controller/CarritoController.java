package com.ecomarket.orders.cl.ecomarket_orders.controller;

import com.ecomarket.orders.cl.ecomarket_orders.dto.CarritoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.dto.ItemCarritoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j

public class CarritoController {

    private final CarritoService carritoService;
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@PathVariable Long clienteId) {
        log.info("Recibida solicitud para obtener carrito del cliente: {}", clienteId);
        CarritoDTO carrito = carritoService.obtenerCarrito(clienteId);
        return ResponseEntity.ok(carrito);
    }
    
    @PostMapping("/cliente/{clienteId}/items")
    public ResponseEntity<CarritoDTO> agregarItem(
            @PathVariable Long clienteId, 
            @Valid @RequestBody ItemCarritoDTO itemDTO) {
        log.info("Recibida solicitud para agregar item al carrito del cliente: {}", clienteId);
        CarritoDTO carrito = carritoService.agregarItem(clienteId, itemDTO);
        return ResponseEntity.ok(carrito);
    }
    
    @PutMapping("/cliente/{clienteId}/items/{productoId}")
    public ResponseEntity<CarritoDTO> actualizarItem(
            @PathVariable Long clienteId,
            @PathVariable Long productoId,
            @RequestParam Integer cantidad) {
        log.info("Recibida solicitud para actualizar item en carrito del cliente: {}", clienteId);
        CarritoDTO carrito = carritoService.actualizarItem(clienteId, productoId, cantidad);
        return ResponseEntity.ok(carrito);
    }
    
    @DeleteMapping("/cliente/{clienteId}/items/{productoId}")
    public ResponseEntity<Void> eliminarItem(
            @PathVariable Long clienteId,
            @PathVariable Long productoId) {
        log.info("Recibida solicitud para eliminar item del carrito del cliente: {}", clienteId);
        carritoService.eliminarItem(clienteId, productoId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/cliente/{clienteId}")
    public ResponseEntity<Void> limpiarCarrito(@PathVariable Long clienteId) {
        log.info("Recibida solicitud para limpiar carrito del cliente: {}", clienteId);
        carritoService.limpiarCarrito(clienteId);
        return ResponseEntity.ok().build();
    }

}
