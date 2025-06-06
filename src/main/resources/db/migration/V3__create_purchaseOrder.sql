CREATE TABLE store_api.purchaseOrder
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    warehouseId          BIGINT   NOT NULL,
    productId          BIGINT   NOT NULL,
    amount	INT DEFAULT 0 NOT NULL ,
    orderStatus SMALLINT NOT NULL,
    orderDate DATETIME NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

ALTER TABLE store_api.purchaseOrder
    ADD CONSTRAINT fk_purchaseOrder_warehouse FOREIGN KEY (warehouseId) REFERENCES warehouses (id) ON DELETE NO ACTION;

ALTER TABLE store_api.purchaseOrder
    ADD CONSTRAINT fk_purchaseOrder_product FOREIGN KEY (productId) REFERENCES products (id) ON DELETE NO ACTION;