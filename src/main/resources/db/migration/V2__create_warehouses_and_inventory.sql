CREATE TABLE warehouses
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)   NOT NULL,
    `description` LONGTEXT       NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE inventory
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    warehouseId   BIGINT   NOT NULL,
    productId     BIGINT   NOT NULL,
    amount        INT DEFAULT 0 NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

ALTER TABLE inventory
    ADD CONSTRAINT fk_warehouse FOREIGN KEY (warehouseId) REFERENCES warehouses (id) ON DELETE NO ACTION;

ALTER TABLE inventory
    ADD CONSTRAINT fk_product FOREIGN KEY (productId) REFERENCES products (id) ON DELETE NO ACTION;
