package com.hamed.microserviceinventory.repository;

import com.hamed.microserviceinventory.dto.InventoryResponse;
import com.hamed.microserviceinventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
