package com.ecomarket.orders.cl.ecomarket_orders.service;

import com.ecomarket.orders.cl.ecomarket_orders.dto.CarritoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.dto.ItemCarritoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.entity.Carrito;
import com.ecomarket.orders.cl.ecomarket_orders.entity.ItemCarrito;
import com.ecomarket.orders.cl.ecomarket_orders.entity.ItemCarritoId;
import com.ecomarket.orders.cl.ecomarket_orders.exception.CarritoNotFoundException;
import com.ecomarket.orders.cl.ecomarket_orders.repository.CarritoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class CarritoService {
    private final CarritoRepository carritoRepository;
    
    public CarritoDTO obtenerCarrito(Long clienteId) {
        log.info("Obteniendo carrito para cliente: {}", clienteId);
        Carrito carrito = carritoRepository.findByClienteIdWithItems(clienteId)
                .orElseGet(() -> crearNuevoCarrito(clienteId));
        return convertirEntidadADTO(carrito);
    }
    
    public CarritoDTO agregarItem(Long clienteId, ItemCarritoDTO itemDTO) {
        log.info("Agregando item al carrito del cliente: {}", clienteId);
        Carrito carrito = carritoRepository.findByClienteIdWithItems(clienteId)
                .orElseGet(() -> crearNuevoCarrito(clienteId));
        
        if (carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }
        
        // Buscar si el item ya existe
        ItemCarrito itemExistente = carrito.getItems().stream()
                .filter(item -> item.getId().getProductoId().equals(itemDTO.getProductoId()))
                .findFirst()
                .orElse(null);
        
        if (itemExistente != null) {
            // Actualizar cantidad
            itemExistente.setCantidad(itemExistente.getCantidad() + itemDTO.getCantidad());
            log.info("Item actualizado en carrito, nueva cantidad: {}", itemExistente.getCantidad());
        } else {
            // Agregar nuevo item
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setId(new ItemCarritoId(carrito.getCarritoId(), itemDTO.getProductoId()));
            nuevoItem.setCarrito(carrito);
            nuevoItem.setCantidad(itemDTO.getCantidad());
            carrito.getItems().add(nuevoItem);
            log.info("Nuevo item agregado al carrito");
        }
        
        Carrito carritoGuardado = carritoRepository.save(carrito);
        return convertirEntidadADTO(carritoGuardado);
    }
    
    public CarritoDTO actualizarItem(Long clienteId, Long productoId, Integer nuevaCantidad) {
        log.info("Actualizando item en carrito del cliente: {}", clienteId);
        Carrito carrito = carritoRepository.findByClienteIdWithItems(clienteId)
                .orElseThrow(() -> new CarritoNotFoundException("Carrito no encontrado para el cliente: " + clienteId));
        
        ItemCarrito item = carrito.getItems().stream()
                .filter(i -> i.getId().getProductoId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item no encontrado en el carrito"));
        
        if (nuevaCantidad <= 0) {
            carrito.getItems().remove(item);
            log.info("Item removido del carrito");
        } else {
            item.setCantidad(nuevaCantidad);
            log.info("Cantidad del item actualizada a: {}", nuevaCantidad);
        }
        
        Carrito carritoGuardado = carritoRepository.save(carrito);
        return convertirEntidadADTO(carritoGuardado);
    }
    
    public void eliminarItem(Long clienteId, Long productoId) {
        log.info("Eliminando item del carrito del cliente: {}", clienteId);
        Carrito carrito = carritoRepository.findByClienteIdWithItems(clienteId)
                .orElseThrow(() -> new CarritoNotFoundException("Carrito no encontrado para el cliente: " + clienteId));
        
        carrito.getItems().removeIf(item -> item.getId().getProductoId().equals(productoId));
        carritoRepository.save(carrito);
        log.info("Item eliminado del carrito");
    }
    
    public void limpiarCarrito(Long clienteId) {
        log.info("Limpiando carrito del cliente: {}", clienteId);
        carritoRepository.findByClienteId(clienteId).ifPresent(carrito -> {
            if (carrito.getItems() != null) {
                carrito.getItems().clear();
                carritoRepository.save(carrito);
                log.info("Carrito limpiado");
            }
        });
    }
    
    private Carrito crearNuevoCarrito(Long clienteId) {
        log.info("Creando nuevo carrito para cliente: {}", clienteId);
        Carrito carrito = Carrito.builder()
                .clienteId(clienteId)
                .items(new ArrayList<>())
                .build();
        return carritoRepository.save(carrito);
    }
    
    private CarritoDTO convertirEntidadADTO(Carrito carrito) {
        return CarritoDTO.builder()
                .carritoId(carrito.getCarritoId())
                .clienteId(carrito.getClienteId())
                .fechaCreacion(carrito.getFechaCreacion())
                .ultimaActualizacion(carrito.getUltimaActualizacion())
                .items(carrito.getItems() != null ? 
                        carrito.getItems().stream()
                                .map(this::convertirItemADTO)
                                .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }
    
    private ItemCarritoDTO convertirItemADTO(ItemCarrito item) {
        return ItemCarritoDTO.builder()
                .productoId(item.getId().getProductoId())
                .cantidad(item.getCantidad())
                .fechaAgregado(item.getFechaAgregado())
                .build();
    }

}
