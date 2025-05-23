package com.ecomarket.orders.cl.ecomarket_orders.service;



import com.ecomarket.orders.cl.ecomarket_orders.dto.DetallePedidoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.dto.PedidoDTO;
import com.ecomarket.orders.cl.ecomarket_orders.entity.DetalleDePedido;
import com.ecomarket.orders.cl.ecomarket_orders.entity.EstadoPedido;
import com.ecomarket.orders.cl.ecomarket_orders.entity.Pedido;
import com.ecomarket.orders.cl.ecomarket_orders.exception.PedidoNotFoundException;
import com.ecomarket.orders.cl.ecomarket_orders.exception.EstadoPedidoInvalidoException;
import com.ecomarket.orders.cl.ecomarket_orders.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoService carritoService;
    
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        log.info("Creando pedido para cliente: {}", pedidoDTO.getClienteId());
        
        // Convertir DTO a entidad
        Pedido pedido = convertirDTOAEntidad(pedidoDTO);
        
        // Calcular totales
        calcularTotales(pedido);
        
        // Guardar pedido
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        log.info("Pedido creado con ID: {}", pedidoGuardado.getPedidoId());
        
        // Limpiar carrito del cliente si existe
        carritoService.limpiarCarrito(pedidoDTO.getClienteId());
        
        return convertirEntidadADTO(pedidoGuardado);
    }
    
    @Transactional(readOnly = true)
    public PedidoDTO obtenerPedido(Long id) {
        log.info("Obteniendo pedido con ID: {}", id);
        Pedido pedido = pedidoRepository.findByIdWithDetalles(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));
        return convertirEntidadADTO(pedido);
    }
    
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorCliente(Long clienteId) {
        log.info("Obteniendo pedidos para cliente: {}", clienteId);
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        return pedidos.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<PedidoDTO> obtenerPedidosPorCliente(Long clienteId, Pageable pageable) {
        log.info("Obteniendo pedidos paginados para cliente: {}", clienteId);
        Page<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId, pageable);
        return pedidos.map(this::convertirEntidadADTO);
    }
    
    public PedidoDTO actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado) {
        log.info("Actualizando estado del pedido {} a {}", id, nuevoEstado);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));
        
        // Validar transición de estado
        validarTransicionEstado(pedido.getEstado(), nuevoEstado);
        
        pedido.setEstado(nuevoEstado);
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        
        return convertirEntidadADTO(pedidoActualizado);
    }
    
    public void cancelarPedido(Long id, String motivo) {
        log.info("Cancelando pedido con ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));
        
        if (pedido.getEstado() == EstadoPedido.ENVIADO || pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new EstadoPedidoInvalidoException("No se puede cancelar un pedido que ya fue enviado o entregado");
        }
        
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedido.setNotas(pedido.getNotas() != null ? 
                pedido.getNotas() + "\nCancelado: " + motivo : 
                "Cancelado: " + motivo);
        
        pedidoRepository.save(pedido);
    }
    
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorEstado(EstadoPedido estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        return pedidos.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Pedido> pedidos = pedidoRepository.findByFechaDePedidoBetween(fechaInicio, fechaFin);
        return pedidos.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }
    
    private void calcularTotales(Pedido pedido) {
        BigDecimal subtotal = pedido.getDetalles().stream()
                .map(detalle -> detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        pedido.setSubtotal(subtotal);
        
        BigDecimal descuento = pedido.getDescuento() != null ? pedido.getDescuento() : BigDecimal.ZERO;
        BigDecimal total = subtotal.subtract(subtotal.multiply(descuento.divide(BigDecimal.valueOf(100))));
        
        pedido.setTotal(total);
    }
    
    private void validarTransicionEstado(EstadoPedido estadoActual, EstadoPedido nuevoEstado) {
        switch (estadoActual) {
            case PENDIENTE:
                if (nuevoEstado != EstadoPedido.CONFIRMADO && nuevoEstado != EstadoPedido.CANCELADO) {
                    throw new EstadoPedidoInvalidoException("Transición de estado no válida desde PENDIENTE a " + nuevoEstado);
                }
                break;
            case CONFIRMADO:
                if (nuevoEstado != EstadoPedido.PREPARANDO && nuevoEstado != EstadoPedido.CANCELADO) {
                    throw new EstadoPedidoInvalidoException("Transición de estado no válida desde CONFIRMADO a " + nuevoEstado);
                }
                break;
            case PREPARANDO:
                if (nuevoEstado != EstadoPedido.ENVIADO && nuevoEstado != EstadoPedido.CANCELADO) {
                    throw new EstadoPedidoInvalidoException("Transición de estado no válida desde PREPARANDO a " + nuevoEstado);
                }
                break;
            case ENVIADO:
                if (nuevoEstado != EstadoPedido.ENTREGADO && nuevoEstado != EstadoPedido.DEVUELTO) {
                    throw new EstadoPedidoInvalidoException("Transición de estado no válida desde ENVIADO a " + nuevoEstado);
                }
                break;
            case ENTREGADO:
                if (nuevoEstado != EstadoPedido.DEVUELTO) {
                    throw new EstadoPedidoInvalidoException("Transición de estado no válida desde ENTREGADO a " + nuevoEstado);
                }
                break;
            case CANCELADO:
            case DEVUELTO:
                throw new EstadoPedidoInvalidoException("No se puede cambiar el estado de un pedido " + estadoActual);
        }
    }
    
    private Pedido convertirDTOAEntidad(PedidoDTO dto) {
        Pedido pedido = Pedido.builder()
                .fechaDePedido(dto.getFechaDePedido())
                .clienteId(dto.getClienteId())
                .estado(dto.getEstado() != null ? dto.getEstado() : EstadoPedido.PENDIENTE)
                .descuento(dto.getDescuento() != null ? dto.getDescuento() : BigDecimal.ZERO)
                .metodoPagoId(dto.getMetodoPagoId())
                .usuarioId(dto.getUsuarioId())
                .subtotal(dto.getSubtotal())
                .total(dto.getTotal())
                .direccionEnvio(dto.getDireccionEnvio())
                .ciudadEnvio(dto.getCiudadEnvio())
                .notas(dto.getNotas())
                .build();
        
        if (dto.getDetalles() != null) {
            List<DetalleDePedido> detalles = dto.getDetalles().stream()
                    .map(detalleDTO -> DetalleDePedido.builder()
                            .pedido(pedido)
                            .productoId(detalleDTO.getProductoId())
                            .precioUnitario(detalleDTO.getPrecioUnitario())
                            .cantidad(detalleDTO.getCantidad())
                            .build())
                    .collect(Collectors.toList());
            pedido.setDetalles(detalles);
        }
        
        return pedido;
    }
    
    private PedidoDTO convertirEntidadADTO(Pedido pedido) {
        return PedidoDTO.builder()
                .pedidoId(pedido.getPedidoId())
                .fechaDePedido(pedido.getFechaDePedido())
                .clienteId(pedido.getClienteId())
                .estado(pedido.getEstado())
                .descuento(pedido.getDescuento())
                .metodoPagoId(pedido.getMetodoPagoId())
                .usuarioId(pedido.getUsuarioId())
                .subtotal(pedido.getSubtotal())
                .total(pedido.getTotal())
                .direccionEnvio(pedido.getDireccionEnvio())
                .ciudadEnvio(pedido.getCiudadEnvio())
                .notas(pedido.getNotas())
                .detalles(pedido.getDetalles() != null ? 
                        pedido.getDetalles().stream()
                                .map(this::convertirDetalleADTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }
    
    private DetallePedidoDTO convertirDetalleADTO(DetalleDePedido detalle) {
        return DetallePedidoDTO.builder()
                .detalleId(detalle.getDetalleId())
                .productoId(detalle.getProductoId())
                .precioUnitario(detalle.getPrecioUnitario())
                .cantidad(detalle.getCantidad())
                .subTotal(detalle.getSubTotal())
                .build();
    }

}
