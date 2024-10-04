package com.example.demo.service;

import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.repo.PurchaseOrderDetailRepository;
import com.example.demo.repo.PurchaseOrderHeaderRepository; // Add this import
import com.example.demo.repo.ItemRepository; // Add this import
import com.example.demo.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderDetailService {

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository; // Add this
    @Autowired
    private ItemRepository itemRepository; // Add this

    // Get all purchase order details
    public ResponseEntity<Response<List<PurchaseOrderDetail>>> getAllPurchaseOrderDetails() {
        Response<List<PurchaseOrderDetail>> response = new Response<>();
        List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailRepository.findAll();

        if (purchaseOrderDetails.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(purchaseOrderDetails);
            response.setMessage("No purchase order details found.");
        } else {
            response.setStatusCode(200);
            response.setPayload(purchaseOrderDetails);
            response.setMessage("Purchase order details retrieved successfully.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Get purchase order detail by ID
    public ResponseEntity<Response<PurchaseOrderDetail>> getPurchaseOrderDetailById(Long id) {
        Optional<PurchaseOrderDetail> purchaseOrderDetail = purchaseOrderDetailRepository.findById(id);
        Response<PurchaseOrderDetail> response = new Response<>();

        if (purchaseOrderDetail.isPresent()) {
            response.setStatusCode(200);
            response.setPayload(purchaseOrderDetail.get());
            response.setMessage("Purchase order detail found successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Purchase order detail not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create a new purchase order detail
    public ResponseEntity<Response<PurchaseOrderDetail>> createPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        Response<PurchaseOrderDetail> response = new Response<>();

        // Check if purchaseOrderHeaderId exists
        if (!purchaseOrderHeaderRepository.existsById(purchaseOrderDetail.getPurchaseOrderHeaderId().longValue())) {
            response.setStatusCode(400);
            response.setPayload(null);
            response.setMessage("Purchase order header ID " + purchaseOrderDetail.getPurchaseOrderHeaderId() + " does not exist.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if itemId exists
        if (!itemRepository.existsById(purchaseOrderDetail.getItemId().longValue())) {
            response.setStatusCode(400);
            response.setPayload(null);
            response.setMessage("Item ID " + purchaseOrderDetail.getItemId() + " does not exist.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Set created and updated datetime
        purchaseOrderDetail.setCreatedDatetime(LocalDateTime.now());
        purchaseOrderDetail.setUpdatedDatetime(null);

        PurchaseOrderDetail savedPurchaseOrderDetail = purchaseOrderDetailRepository.save(purchaseOrderDetail);

        response.setStatusCode(201);
        response.setPayload(savedPurchaseOrderDetail);
        response.setMessage("Purchase order detail created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update purchase order detail by ID
    public ResponseEntity<Response<PurchaseOrderDetail>> updatePurchaseOrderDetail(Long id, PurchaseOrderDetail purchaseOrderDetailDetails) {
        Optional<PurchaseOrderDetail> existingPurchaseOrderDetail = purchaseOrderDetailRepository.findById(id);
        Response<PurchaseOrderDetail> response = new Response<>();

        if (existingPurchaseOrderDetail.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(purchaseOrderDetailDetails);
            response.setMessage("Purchase order detail not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Check if purchaseOrderHeaderId exists
        if (!purchaseOrderHeaderRepository.existsById(purchaseOrderDetailDetails.getPurchaseOrderHeaderId().longValue())) {
            response.setStatusCode(400);
            response.setPayload(null);
            response.setMessage("Purchase order header ID " + purchaseOrderDetailDetails.getPurchaseOrderHeaderId() + " does not exist.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if itemId exists
        if (!itemRepository.existsById(purchaseOrderDetailDetails.getItemId().longValue())) {
            response.setStatusCode(400);
            response.setPayload(null);
            response.setMessage("Item ID " + purchaseOrderDetailDetails.getItemId() + " does not exist.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Update existing purchase order detail
        PurchaseOrderDetail purchaseOrderDetail = existingPurchaseOrderDetail.get();
        purchaseOrderDetail.setItemId(purchaseOrderDetailDetails.getItemId());
        purchaseOrderDetail.setItemQty(purchaseOrderDetailDetails.getItemQty());
        purchaseOrderDetail.setItemCost(purchaseOrderDetailDetails.getItemCost());
        purchaseOrderDetail.setItemPrice(purchaseOrderDetailDetails.getItemPrice());
        purchaseOrderDetail.setUpdatedBy(purchaseOrderDetailDetails.getUpdatedBy());
        purchaseOrderDetail.setUpdatedDatetime(LocalDateTime.now());

        PurchaseOrderDetail updatedPurchaseOrderDetail = purchaseOrderDetailRepository.save(purchaseOrderDetail);

        response.setStatusCode(200);
        response.setPayload(updatedPurchaseOrderDetail);
        response.setMessage("Purchase order detail updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Delete purchase order detail by ID
    public ResponseEntity<Response<Void>> deletePurchaseOrderDetail(Long id) {
        Response<Void> response = new Response<>();

        Optional<PurchaseOrderDetail> purchaseOrderDetail = purchaseOrderDetailRepository.findById(id);
        if (purchaseOrderDetail.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Purchase order detail not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        purchaseOrderDetailRepository.deleteById(id);
        response.setStatusCode(200);
        response.setPayload(null);
        response.setMessage("Purchase order detail deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
