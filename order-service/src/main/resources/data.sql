-- ===== DATOS DE PRUEBA PARA MICROSERVICIO DE ORDENES =====

-- Limpiar datos existentes (opcional)
-- DELETE FROM detalle_de_pedido;
-- DELETE FROM pedido;
-- DELETE FROM item_carrito;
-- DELETE FROM carrito;

-- Insertamos carritos para diferentes clientes
INSERT INTO carrito (cliente_id, fecha_creacion, ultima_actualizacion) VALUES 
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertamos items en los carritos
INSERT INTO item_carrito (carrito_id, producto_id, cantidad, fecha_agregado) VALUES 
(1, 1, 2, CURRENT_TIMESTAMP),
(1, 3, 1, CURRENT_TIMESTAMP),
(1, 5, 3, CURRENT_TIMESTAMP),
(2, 2, 1, CURRENT_TIMESTAMP),
(2, 4, 2, CURRENT_TIMESTAMP),
(3, 1, 1, CURRENT_TIMESTAMP),
(3, 2, 1, CURRENT_TIMESTAMP),
(3, 3, 1, CURRENT_TIMESTAMP),
(4, 4, 4, CURRENT_TIMESTAMP),
(4, 5, 2, CURRENT_TIMESTAMP),
(5, 1, 1, CURRENT_TIMESTAMP);

-- Insertamos pedidos con diferentes estados y clientes
INSERT INTO pedido (fecha_de_pedido, cliente_id, estado, descuento, metodo_pago_id, usuario_id, subtotal, total, direccion_envio, ciudad_envio, notas) VALUES 
('2024-01-15', 1, 'ENTREGADO', 0.00, 1, NULL, 28.47, 28.47, 'Av. Providencia 1234, Depto 501', 'Santiago', 'Entregar en porteria. Cliente prefiere horario de mañana.'),
('2024-01-16', 2, 'ENVIADO', 5.00, 2, 1, 17.00, 16.15, 'Los Leones 845, Casa 12', 'Santiago', 'Dejar con vecino si no esta. Timbre rojo.'),
('2024-01-17', 3, 'PREPARANDO', 0.00, 1, 2, 23.44, 23.44, 'Manuel Montt 2890, Oficina 304', 'Santiago', 'Entrega solo en horario de oficina de 9 a 18 hrs.'),
('2024-01-18', 1, 'CONFIRMADO', 10.00, 3, 1, 35.98, 32.38, 'Av. Las Condes 7800, Torre B, Piso 15', 'Santiago', 'Cliente corporativo. Solicitar factura.'),
('2024-01-19', 4, 'PENDIENTE', 0.00, 1, NULL, 19.48, 19.48, 'Santa Isabel 0155, Villa Las Rosas', 'Santiago', 'Primera compra del cliente. Contactar antes de entregar.'),
('2024-01-20', 2, 'CANCELADO', 0.00, 2, 2, 12.99, 12.99, 'Pedro de Valdivia 1650, Depto 205', 'Santiago', 'Cliente cancelo por cambio de opinion.'),
('2024-01-21', 5, 'ENTREGADO', 0.00, 1, 1, 41.93, 41.93, 'Av. Vitacura 5250, Las Condes Mall', 'Santiago', 'Entrega en mall, local de electronica del segundo piso.'),
('2024-01-22', 3, 'ENVIADO', 15.00, 3, NULL, 55.90, 47.52, 'Camino El Alba 9200, Condominio Los Robles', 'Santiago', 'Cliente VIP. Descuento especial aplicado.'),
('2024-01-23', 1, 'PREPARANDO', 0.00, 2, 2, 8.50, 8.50, 'Av. Providencia 1234, Depto 501', 'Santiago', 'Segunda compra del cliente. Mismo domicilio anterior.'),
('2024-01-24', 4, 'PENDIENTE', 0.00, 1, 1, 14.95, 14.95, 'Santa Isabel 0155, Villa Las Rosas', 'Santiago', 'Cliente solicita confirmacion antes del envio.');

-- Insertamos detalles para cada pedido
INSERT INTO detalle_de_pedido (pedido_id, producto_id, precio_unitario, cantidad) VALUES 
-- Pedido 1: Cliente 1, Entregado
(1, 1, 5.99, 2), (1, 3, 4.95, 1), (1, 5, 9.99, 1), (1, 2, 8.50, 1),
-- Pedido 2: Cliente 2, Enviado
(2, 2, 8.50, 1), (2, 4, 3.50, 2), (2, 3, 4.95, 1),
-- Pedido 3: Cliente 3, Preparando
(3, 1, 5.99, 1), (3, 2, 8.50, 1), (3, 4, 3.50, 1), (3, 5, 9.99, 1),
-- Pedido 4: Cliente 1, Confirmado
(4, 1, 5.99, 3), (4, 3, 4.95, 2), (4, 5, 9.99, 1),
-- Pedido 5: Cliente 4, Pendiente
(5, 2, 8.50, 1), (5, 4, 3.50, 2), (5, 3, 4.95, 1),
-- Pedido 6: Cliente 2, Cancelado
(6, 1, 5.99, 1), (6, 4, 3.50, 2),
-- Pedido 7: Cliente 5, Entregado
(7, 1, 5.99, 3), (7, 2, 8.50, 2), (7, 3, 4.95, 1), (7, 5, 9.99, 1),
-- Pedido 8: Cliente 3, Enviado (VIP)
(8, 1, 5.99, 4), (8, 2, 8.50, 2), (8, 3, 4.95, 1), (8, 4, 3.50, 3), (8, 5, 9.99, 2),
-- Pedido 9: Cliente 1, Preparando
(9, 2, 8.50, 1),
-- Pedido 10: Cliente 4, Pendiente
(10, 3, 4.95, 2), (10, 1, 5.99, 1);