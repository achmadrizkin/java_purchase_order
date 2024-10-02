package com.example.demo.service;

import com.example.demo.entity.PurchaseOrderHeader;
import com.example.demo.entity.Response;
import com.example.demo.repo.PurchaseOrderHeaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PurchaseOrderHeaderServiceTest {

    @Mock
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    @InjectMocks
    private PurchaseOrderHeaderService purchaseOrderHeaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test Get All Purchase Order Headers - Success
    @Test
    void testGetAllPurchaseOrderHeaders_Success() {
        List<PurchaseOrderHeader> purchaseOrderHeaders = new ArrayList<>();
        purchaseOrderHeaders.add(new PurchaseOrderHeader());
        when(purchaseOrderHeaderRepository.findAll()).thenReturn(purchaseOrderHeaders);

        ResponseEntity<Response<List<PurchaseOrderHeader>>> responseEntity = purchaseOrderHeaderService.getAllPurchaseOrderHeaders();
        Response<List<PurchaseOrderHeader>> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.getPayload().size());
        assertEquals("Purchase order headers retrieved successfully.", response.getMessage());
    }

    // Test Get All Purchase Order Headers - Failure (No Headers)
    @Test
    void testGetAllPurchaseOrderHeaders_Failure() {
        when(purchaseOrderHeaderRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<Response<List<PurchaseOrderHeader>>> responseEntity = purchaseOrderHeaderService.getAllPurchaseOrderHeaders();
        Response<List<PurchaseOrderHeader>> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals(0, response.getPayload().size());
        assertEquals("No data purchase order headers found.", response.getMessage());
    }

    // Test Get Purchase Order Header By ID - Success
    @Test
    void testGetPurchaseOrderHeaderById_Success() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        when(purchaseOrderHeaderRepository.findById(1L)).thenReturn(Optional.of(purchaseOrderHeader));

        ResponseEntity<Response<PurchaseOrderHeader>> responseEntity = purchaseOrderHeaderService.getPurchaseOrderHeaderById(1L);
        Response<PurchaseOrderHeader> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals(purchaseOrderHeader, response.getPayload());
        assertEquals("Purchase order header found successfully.", response.getMessage());
    }

    // Test Get Purchase Order Header By ID - Failure (Not Found)
    @Test
    void testGetPurchaseOrderHeaderById_Failure() {
        when(purchaseOrderHeaderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Response<PurchaseOrderHeader>> responseEntity = purchaseOrderHeaderService.getPurchaseOrderHeaderById(1L);
        Response<PurchaseOrderHeader> response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals(null, response.getPayload());
        assertEquals("Purchase order header not found with id 1", response.getMessage());
    }

    // Test Create Purchase Order Header - Success
    @Test
    void testCreatePurchaseOrderHeader_Success() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setCreatedDatetime(LocalDateTime.now());

        when(purchaseOrderHeaderRepository.save(purchaseOrderHeader)).thenReturn(purchaseOrderHeader);

        ResponseEntity<Response<PurchaseOrderHeader>> responseEntity = purchaseOrderHeaderService.createPurchaseOrderHeader(purchaseOrderHeader);
        Response<PurchaseOrderHeader> response = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(201, response.getStatusCode());
        assertEquals(purchaseOrderHeader, response.getPayload());
        assertEquals("Purchase order header created successfully.", response.getMessage());
    }

    // Test Update Purchase Order Header - Success
    @Test
    void testUpdatePurchaseOrderHeader_Success() {
        PurchaseOrderHeader existingPurchaseOrderHeader = new PurchaseOrderHeader();
        PurchaseOrderHeader updatedDetails = new PurchaseOrderHeader();
        updatedDetails.setDatetime(LocalDateTime.now());
        updatedDetails.setDescription("Updated Description");

        when(purchaseOrderHeaderRepository.findById(1L)).thenReturn(Optional.of(existingPurchaseOrderHeader));
        when(purchaseOrderHeaderRepository.save(any(PurchaseOrderHeader.class))).thenReturn(updatedDetails);

        ResponseEntity<Response<PurchaseOrderHeader>> responseEntity = purchaseOrderHeaderService.updatePurchaseOrderHeader(1L, updatedDetails);
        Response<PurchaseOrderHeader> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals(updatedDetails, response.getPayload());
        assertEquals("Purchase order header updated successfully.", response.getMessage());
    }

    // Test Update Purchase Order Header - Failure (Not Found)
    @Test
    void testUpdatePurchaseOrderHeader_Failure() {
        PurchaseOrderHeader updatedDetails = new PurchaseOrderHeader();
        when(purchaseOrderHeaderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Response<PurchaseOrderHeader>> responseEntity = purchaseOrderHeaderService.updatePurchaseOrderHeader(1L, updatedDetails);
        Response<PurchaseOrderHeader> response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals(updatedDetails, response.getPayload());
        assertEquals("Purchase order header not found with id 1", response.getMessage());
    }

    // Test Delete Purchase Order Header - Success
    @Test
    void testDeletePurchaseOrderHeader_Success() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        when(purchaseOrderHeaderRepository.findById(1L)).thenReturn(Optional.of(purchaseOrderHeader));

        ResponseEntity<Response<Void>> responseEntity = purchaseOrderHeaderService.deletePurchaseOrderHeader(1L);
        Response<Void> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals("Purchase order header deleted successfully.", response.getMessage());
    }

    // Test Delete Purchase Order Header - Failure (Not Found)
    @Test
    void testDeletePurchaseOrderHeader_Failure() {
        when(purchaseOrderHeaderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Response<Void>> responseEntity = purchaseOrderHeaderService.deletePurchaseOrderHeader(1L);
        Response<Void> response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals("Purchase order header not found with id 1", response.getMessage());
    }
}
