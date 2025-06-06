package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseOrderDto {
    private Long id;
    private Long warehouseId;
    private Long productId;
    private int amount;
    private int orderStatus;
    private Date orderDate;

    public PurchaseOrderDto() {
    }
}
