package com.ecomarket.orders.cl.ecomarket_orders.controller;

import com.ecomarket.orders.cl.ecomarket_orders.dto.PedidoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.entity.EstadoPedido;
import com.ecomarket.orders.cl.ecomarket_orders.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j


public class PedidoController {

    private final PedidoService pedidoService;
    
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        log.info("Recibida solicitud para crear pedido");
        PedidoDTO pedidoCreado = pedidoService.crearPedido(pedidoDTO);
        return new ResponseEntity<>(pedidoCreado, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        log.info("Recibida solicitud para obtener pedido con ID: {}", id);
        PedidoDTO pedido = pedidoService.obtenerPedido(id);
        return ResponseEntity.ok(pedido);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        log.info("Recibida solicitud para obtener pedidos del cliente: {}", clienteId);
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/cliente/{clienteId}/page")
    public ResponseEntity<Page<PedidoDTO>> obtenerPedidosPorClientePage(
            @PathVariable Long clienteId, 
            Pageable pageable) {
        log.info("Recibida solicitud para obtener pedidos paginados del cliente: {}", clienteId);
        Page<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorCliente(clienteId, pageable);
        return ResponseEntity.ok(pedidos);
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable Long id, 
            @RequestParam EstadoPedido estado) {
        log.info("Recibida solicitud para actualizar estado del pedido {} a {}", id, estado);
        PedidoDTO pedidoActualizado = pedidoService.actualizarEstadoPedido(id, estado);
        return ResponseEntity.ok(pedidoActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id, @RequestParam String motivo) {
        log.info("Recibida solicitud para cancelar pedido con ID: {}", id);
        pedidoService.cancelarPedido(id, motivo);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorEstado(@PathVariable EstadoPedido estado) {
        log.info("Recibida solicitud para obtener pedidos con estado: {}", estado);
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorEstado(estado);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/fecha")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorFecha(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        log.info("Recibida solicitud para obtener pedidos entre {} y {}", fechaInicio, fechaFin);
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(pedidos);
    }

}
