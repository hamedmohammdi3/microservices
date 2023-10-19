package com.hamed.microserviceinventory.controller;

import com.hamed.microserviceinventory.dto.InventoryResponse;
import com.hamed.microserviceinventory.service.InventoryService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode ){
        return inventoryService.isInStock(skuCode);
    }
}
