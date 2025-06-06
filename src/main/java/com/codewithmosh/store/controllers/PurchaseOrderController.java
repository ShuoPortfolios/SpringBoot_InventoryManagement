package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.dtos.PurchaseOrderDto;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.Inventory;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.entities.PurchaseOrder;
import com.codewithmosh.store.entities.Warehouses;
import com.codewithmosh.store.mappers.PurchaseOrderMapper;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.*;
import com.codewithmosh.store.request.CreatePurchaseOrderRequest;
import com.codewithmosh.store.request.RegisterUserRequest;
import com.codewithmosh.store.request.UpdateUserRequest;
import com.codewithmosh.store.request.VerifyPurchaseOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final WarehousesRepository warehousesRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @GetMapping
    public Iterable<PurchaseOrderDto> getAllPurchaseOrders(
//            @RequestHeader(name = "x-auth-token") String authToken,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy){

        if(!Set.of("id", "name", "email").contains(sortBy))
            sortBy = "orderDate";

        return purchaseOrderRepository.findAll(Sort.by(sortBy).ascending())
                .stream()
                .map(purchaseOrderMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDto> createPurchaseOrders(
            @RequestBody CreatePurchaseOrderRequest purchaseOrderRequest,
            UriComponentsBuilder uriComponentsBuilder){

        var purchaseOrder = purchaseOrderMapper.toEntity(purchaseOrderRequest);

        Warehouses warehouse = warehousesRepository
                .findById(purchaseOrderRequest.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        Product product = productRepository
                .findById(purchaseOrderRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        purchaseOrder.setProduct(product);
        purchaseOrder.setWarehouses(warehouse);

        purchaseOrderRepository.save(purchaseOrder);

        var purchaseOrderDto = purchaseOrderMapper.toDto(purchaseOrder);
        var uri = uriComponentsBuilder.path("/purchaseOrder/{id}").buildAndExpand(purchaseOrderDto.getId()).toUri();

        return ResponseEntity.created(uri).body(purchaseOrderDto);
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<PurchaseOrderDto> verifyPurchaseOrder(
            @PathVariable(name = "id") Long id,
            @RequestBody VerifyPurchaseOrderRequest verifyPurchaseOrderRequest){

        var purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder == null){
            return ResponseEntity.notFound().build();
        }

        purchaseOrderMapper.update(verifyPurchaseOrderRequest, purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder);

        if (verifyPurchaseOrderRequest.getOrderStatus() == 1){
            Inventory inventory = inventoryRepository
                    .findById(purchaseOrder.getWarehouses().getId())
                    .orElse(null);

                    if (inventory == null){
                        var newInventory = new Inventory();
                        newInventory.setProduct(purchaseOrder.getProduct());
                        newInventory.setWarehouses(purchaseOrder.getWarehouses());
                        newInventory.setAmount(purchaseOrder.getAmount());

                        inventoryRepository.save(newInventory);
                    } else {
                        inventory.setAmount(inventory.getAmount() + purchaseOrder.getAmount());
                        inventoryRepository.save(inventory);
                    }
        }

        var purchaseOrderDto = purchaseOrderMapper.toDto(purchaseOrder);

        return ResponseEntity.ok(purchaseOrderMapper.toDto(purchaseOrder));
    }
}
