-- Insertar roles básicos
INSERT INTO roles (name, description) VALUES 
('ADMIN', 'Administrador del sistema'),
('GERENTE', 'Gerente de tienda'),
('VENDEDOR', 'Empleado de ventas'),
('LOGISTICA', 'Personal de logística');

-- Insertar usuario administrador (password: admin123)
INSERT INTO users (username, first_name, last_name, email, password, active, created_at) VALUES 
('admin', 'Administrador', 'Sistema', 'admin@ecomarket.cl', '$2a$10$8JXqctD1h0rSJX/h6Cyz2.r7pP6RheTTJ.yXxZ1Z9W8HRQd.JWnXi', true, NOW());

-- Asignar rol de administrador al usuario admin
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);