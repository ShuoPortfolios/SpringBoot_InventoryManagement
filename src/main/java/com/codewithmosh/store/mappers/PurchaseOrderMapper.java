package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.PurchaseOrderDto;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.PurchaseOrder;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.request.CreatePurchaseOrderRequest;
import com.codewithmosh.store.request.RegisterUserRequest;
import com.codewithmosh.store.request.UpdateUserRequest;
import com.codewithmosh.store.request.VerifyPurchaseOrderRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface PurchaseOrderMapper {
    PurchaseOrderDto toDto(PurchaseOrder purchaseOrder);
    @Mapping(target = "warehouses", ignore = true)
    @Mapping(target = "product", ignore = true)
    PurchaseOrder toEntity(CreatePurchaseOrderRequest purchaseOrderRequest);

    void update(VerifyPurchaseOrderRequest verifyPurchaseOrderRequest, @MappingTarget PurchaseOrder purchaseOrder);
}
