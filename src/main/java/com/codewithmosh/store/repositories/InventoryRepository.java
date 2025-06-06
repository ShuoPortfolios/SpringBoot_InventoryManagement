package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
