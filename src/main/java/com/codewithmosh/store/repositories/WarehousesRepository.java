package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Warehouses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehousesRepository extends JpaRepository<Warehouses, Long> {
}
