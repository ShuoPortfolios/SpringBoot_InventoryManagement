package com.codewithmosh.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class CreatePurchaseOrderRequest {
    private Long warehouseId;
    private Long productId;
    private int amount;
    private int orderStatus;
    private Date orderDate;
}
