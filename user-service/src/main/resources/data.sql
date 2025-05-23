-- Insertar productos de ejemplo para MySQL
INSERT INTO product_catalog (sku, name, description, price, purchase_cost, profit_margin, is_ecological, category_id, main_supplier_id, registration_date, status)
VALUES 
('ECO-001', 'Jabón orgánico de lavanda', 'Jabón 100% natural elaborado con aceites esenciales de lavanda.', 5.99, 2.50, 58.0, true, 1, 1, NOW(), 'ACTIVE'),
('ECO-002', 'Champú sólido de coco', 'Champú en barra sin plástico, ideal para cabello seco.', 8.50, 3.20, 62.0, true, 1, 1, NOW(), 'ACTIVE'),
('ECO-003', 'Cepillo de dientes de bambú', 'Cepillo de dientes biodegradable con mango de bambú.', 4.95, 1.80, 64.0, true, 2, 2, NOW(), 'ACTIVE'),
('ECO-004', 'Bolsa reutilizable de algodón', 'Bolsa de compras resistente hecha de algodón orgánico.', 3.50, 1.20, 66.0, true, 3, 3, NOW(), 'ACTIVE'),
('ECO-005', 'Pajitas de acero inoxidable', 'Set de 4 pajitas reutilizables con cepillo de limpieza.', 9.99, 4.00, 60.0, true, 2, 2, NOW(), 'ACTIVE');