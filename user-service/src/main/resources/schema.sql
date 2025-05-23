CREATE TABLE IF NOT EXISTS product_catalog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    purchase_cost DECIMAL(10, 2),
    profit_margin DECIMAL(5, 2),
    is_ecological BOOLEAN DEFAULT FALSE,
    category_id BIGINT,
    main_supplier_id BIGINT,
    registration_date TIMESTAMP,
    status VARCHAR(50)
);