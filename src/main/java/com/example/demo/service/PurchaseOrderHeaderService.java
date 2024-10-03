package com.example.demo.service;

import com.example.demo.entity.PurchaseOrderHeader;
import com.example.demo.entity.Response;
import com.example.demo.utils.Logic;
import com.example.demo.entity.response.PurchaseOrderResponse;
import com.example.demo.repo.PurchaseOrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderHeaderService {

    @Autowired
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    // Get all purchase order headers
    public ResponseEntity<Response<List<PurchaseOrderHeader>>> getAllPurchaseOrderHeaders() {
        Response<List<PurchaseOrderHeader>> response = new Response<>();
        List<PurchaseOrderHeader> purchaseOrderHeaders = purchaseOrderHeaderRepository.findAll();

        if (purchaseOrderHeaders.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(purchaseOrderHeaders);
            response.setMessage("No data purchase order headers found.");
        } else {
            response.setStatusCode(200);
            response.setPayload(purchaseOrderHeaders);
            response.setMessage("Purchase order headers retrieved successfully.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Get purchase order header by ID
    public ResponseEntity<Response<PurchaseOrderHeader>> getPurchaseOrderHeaderById(Long id) {
        Optional<PurchaseOrderHeader> purchaseOrderHeader = purchaseOrderHeaderRepository.findById(id);
        Response<PurchaseOrderHeader> response = new Response<>();

        if (purchaseOrderHeader.isPresent()) {
            response.setStatusCode(200);
            response.setPayload(purchaseOrderHeader.get());
            response.setMessage("Purchase order header found successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Purchase order header not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create a new purchase order header
    public ResponseEntity<Response<PurchaseOrderHeader>> createPurchaseOrderHeader(PurchaseOrderHeader purchaseOrderHeader) {
        Response<PurchaseOrderHeader> response = new Response<>();

        // Set created and updated datetime
        purchaseOrderHeader.setCreatedDatetime(LocalDateTime.now());
        purchaseOrderHeader.setUpdatedDatetime(null);

        PurchaseOrderHeader savedPurchaseOrderHeader = purchaseOrderHeaderRepository.save(purchaseOrderHeader);

        response.setStatusCode(201);
        response.setPayload(savedPurchaseOrderHeader);
        response.setMessage("Purchase order header created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update purchase order header by ID
    public ResponseEntity<Response<PurchaseOrderHeader>> updatePurchaseOrderHeader(Long id, PurchaseOrderHeader purchaseOrderHeaderDetails) {
        Optional<PurchaseOrderHeader> existingPurchaseOrderHeader = purchaseOrderHeaderRepository.findById(id);
        Response<PurchaseOrderHeader> response = new Response<>();

        if (existingPurchaseOrderHeader.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(purchaseOrderHeaderDetails);
            response.setMessage("Purchase order header not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Update existing purchase order header
        PurchaseOrderHeader purchaseOrderHeader = existingPurchaseOrderHeader.get();
        purchaseOrderHeader.setDatetime(purchaseOrderHeaderDetails.getDatetime());
        purchaseOrderHeader.setDescription(purchaseOrderHeaderDetails.getDescription());
        purchaseOrderHeader.setTotalPrice(purchaseOrderHeaderDetails.getTotalPrice());
        purchaseOrderHeader.setTotalCost(purchaseOrderHeaderDetails.getTotalCost());
        purchaseOrderHeader.setUpdatedBy(purchaseOrderHeaderDetails.getUpdatedBy());
        purchaseOrderHeader.setUpdatedDatetime(LocalDateTime.now());

        PurchaseOrderHeader updatedPurchaseOrderHeader = purchaseOrderHeaderRepository.save(purchaseOrderHeader);

        response.setStatusCode(200);
        response.setPayload(updatedPurchaseOrderHeader);
        response.setMessage("Purchase order header updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Delete purchase order header by ID
    public ResponseEntity<Response<Void>> deletePurchaseOrderHeader(Long id) {
        Response<Void> response = new Response<>();

        Optional<PurchaseOrderHeader> purchaseOrderHeader = purchaseOrderHeaderRepository.findById(id);
        if (purchaseOrderHeader.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Purchase order header not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        purchaseOrderHeaderRepository.deleteById(id);
        response.setStatusCode(200);
        response.setPayload(null);
        response.setMessage("Purchase order header deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Get purchase order with details and items by ID
    public ResponseEntity<Response<PurchaseOrderResponse>> getPurchaseOrderWithDetailsAndItems(Long purchaseOrderId) {
        Response<PurchaseOrderResponse> response = new Response<>();
        List<Object[]> result = purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(purchaseOrderId);

        if (result.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Purchase order with id " + purchaseOrderId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            PurchaseOrderResponse purchaseOrderResponse = Logic.mapResultToPurchaseOrderResponse(result);
            response.setStatusCode(200);
            response.setPayload(purchaseOrderResponse);
            response.setMessage("Purchase order all details retrieved successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
