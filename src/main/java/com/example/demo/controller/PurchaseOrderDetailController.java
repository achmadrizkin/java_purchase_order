package com.example.demo.controller;

import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.entity.Response;
import com.example.demo.service.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-order-details")
public class PurchaseOrderDetailController {

    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;

    // Get all purchase order details
    @GetMapping
    public ResponseEntity<Response<List<PurchaseOrderDetail>>> getAllPurchaseOrderDetails() {
        return purchaseOrderDetailService.getAllPurchaseOrderDetails();
    }

    // Get purchase order detail by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<PurchaseOrderDetail>> getPurchaseOrderDetailById(@PathVariable Long id) {
        return purchaseOrderDetailService.getPurchaseOrderDetailById(id);
    }

    // Create a new purchase order detail
    @PostMapping
    public ResponseEntity<Response<PurchaseOrderDetail>> createPurchaseOrderDetail(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        return purchaseOrderDetailService.createPurchaseOrderDetail(purchaseOrderDetail);
    }

    // Update purchase order detail by ID
    @PutMapping("/{id}")
    public ResponseEntity<Response<PurchaseOrderDetail>> updatePurchaseOrderDetail(
            @PathVariable Long id,
            @RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        return purchaseOrderDetailService.updatePurchaseOrderDetail(id, purchaseOrderDetail);
    }

    // Delete purchase order detail by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deletePurchaseOrderDetail(@PathVariable Long id) {
        return purchaseOrderDetailService.deletePurchaseOrderDetail(id);
    }
}
