-- Insertar usuarios de ejemplo (contraseña "password" codificada con SHA-256)
INSERT INTO users (first_name, last_name, username, email, password, role_id, store_id, registration_date, status, last_access)
VALUES ('Admin', 'Sistema', 'admin', 'admin@ecomarket.com', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 1, NULL, CURRENT_TIMESTAMP(), 'ACTIVE', NULL);

INSERT INTO users (first_name, last_name, username, email, password, role_id, store_id, registration_date, status, last_access)
VALUES ('Gerente', 'Santiago', 'gerente1', 'gerente.santiago@ecomarket.com', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 2, 1, CURRENT_TIMESTAMP(), 'ACTIVE', NULL);

INSERT INTO users (first_name, last_name, username, email, password, role_id, store_id, registration_date, status, last_access)
VALUES ('Gerente', 'Valdivia', 'gerente2', 'gerente.valdivia@ecomarket.com', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 2, 2, CURRENT_TIMESTAMP(), 'ACTIVE', NULL);

INSERT INTO users (first_name, last_name, username, email, password, role_id, store_id, registration_date, status, last_access)
VALUES ('Gerente', 'Antofagasta', 'gerente3', 'gerente.antofagasta@ecomarket.com', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 2, 3, CURRENT_TIMESTAMP(), 'ACTIVE', NULL);

INSERT INTO users (first_name, last_name, username, email, password, role_id, store_id, registration_date, status, last_access)
VALUES ('Vendedor', 'Santiago', 'vendedor1', 'vendedor1.santiago@ecomarket.com', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 3, 1, CURRENT_TIMESTAMP(), 'ACTIVE', NULL);

INSERT INTO users (first_name, last_name, username, email, password, role_id, store_id, registration_date, status, last_access)
VALUES ('Logistica', 'Central', 'logistica1', 'logistica@ecomarket.com', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 4, NULL, CURRENT_TIMESTAMP(), 'ACTIVE', NULL);