package com.example.demo.controller;

import com.example.demo.entity.PurchaseOrderHeader;
import com.example.demo.entity.Response;
import com.example.demo.service.PurchaseOrderHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-order-headers")
public class PurchaseOrderHeaderController {

    @Autowired
    private PurchaseOrderHeaderService purchaseOrderHeaderService;

    // Get all purchase order headers
    @GetMapping
    public ResponseEntity<Response<List<PurchaseOrderHeader>>> getAllPurchaseOrderHeaders() {
        return purchaseOrderHeaderService.getAllPurchaseOrderHeaders();
    }

    // Get purchase order header by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<PurchaseOrderHeader>> getPurchaseOrderHeaderById(@PathVariable Long id) {
        return purchaseOrderHeaderService.getPurchaseOrderHeaderById(id);
    }

    // Create a new purchase order header
    @PostMapping
    public ResponseEntity<Response<PurchaseOrderHeader>> createPurchaseOrderHeader(@RequestBody PurchaseOrderHeader purchaseOrderHeader) {
        return purchaseOrderHeaderService.createPurchaseOrderHeader(purchaseOrderHeader);
    }

    // Update purchase order header by ID
    @PutMapping("/{id}")
    public ResponseEntity<Response<PurchaseOrderHeader>> updatePurchaseOrderHeader(
            @PathVariable Long id,
            @RequestBody PurchaseOrderHeader purchaseOrderHeader) {
        return purchaseOrderHeaderService.updatePurchaseOrderHeader(id, purchaseOrderHeader);
    }

    // Delete purchase order header by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deletePurchaseOrderHeader(@PathVariable Long id) {
        return purchaseOrderHeaderService.deletePurchaseOrderHeader(id);
    }
}
