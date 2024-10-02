package com.example.demo.controller;

import com.example.demo.entity.PurchaseOrderHeader;
import com.example.demo.entity.Response;
import com.example.demo.service.PurchaseOrderHeaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PurchaseOrderHeaderControllerTest {

    @Mock
    private PurchaseOrderHeaderService purchaseOrderHeaderService;

    @InjectMocks
    private PurchaseOrderHeaderController purchaseOrderHeaderController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Get All Purchase Order Headers - Success
    @Test
    void testGetAllPurchaseOrderHeaders_Success() throws Exception {
        List<PurchaseOrderHeader> purchaseOrderHeaders = Arrays.asList(new PurchaseOrderHeader(), new PurchaseOrderHeader());
        Response<List<PurchaseOrderHeader>> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(purchaseOrderHeaders);
        response.setMessage("Purchase order headers retrieved successfully.");
        when(purchaseOrderHeaderService.getAllPurchaseOrderHeaders()).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<List<PurchaseOrderHeader>>> result = purchaseOrderHeaderController.getAllPurchaseOrderHeaders();

        assertEquals(200, result.getBody().getStatusCode());
        assertEquals("Purchase order headers retrieved successfully.", result.getBody().getMessage());
        assertEquals(2, result.getBody().getPayload().size());
    }

    // Test Get All Purchase Order Headers - Failure (Empty List)
    @Test
    void testGetAllPurchaseOrderHeaders_Failure() throws Exception {
        Response<List<PurchaseOrderHeader>> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(Collections.emptyList());
        response.setMessage("No data purchase order headers found.");
        when(purchaseOrderHeaderService.getAllPurchaseOrderHeaders()).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<List<PurchaseOrderHeader>>> result = purchaseOrderHeaderController.getAllPurchaseOrderHeaders();

        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("No data purchase order headers found.", result.getBody().getMessage());
        assertEquals(0, result.getBody().getPayload().size());
    }

    // Test Get Purchase Order Header By ID - Success
    @Test
    void testGetPurchaseOrderHeaderById_Success() throws Exception {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        Response<PurchaseOrderHeader> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(purchaseOrderHeader);
        response.setMessage("Purchase order header found successfully.");
        when(purchaseOrderHeaderService.getPurchaseOrderHeaderById(1L)).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<PurchaseOrderHeader>> result = purchaseOrderHeaderController.getPurchaseOrderHeaderById(1L);

        assertEquals(200, result.getBody().getStatusCode());
        assertEquals("Purchase order header found successfully.", result.getBody().getMessage());
        assertEquals(purchaseOrderHeader, result.getBody().getPayload());
    }

    // Test Get Purchase Order Header By ID - Failure (Not Found)
    @Test
    void testGetPurchaseOrderHeaderById_Failure() throws Exception {
        Response<PurchaseOrderHeader> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(null);
        response.setMessage("Purchase order header not found with id 1");
        when(purchaseOrderHeaderService.getPurchaseOrderHeaderById(1L)).thenReturn(ResponseEntity.status(404).body(response));

        ResponseEntity<Response<PurchaseOrderHeader>> result = purchaseOrderHeaderController.getPurchaseOrderHeaderById(1L);

        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("Purchase order header not found with id 1", result.getBody().getMessage());
        assertEquals(null, result.getBody().getPayload());
    }

    // Test Create Purchase Order Header - Success
    @Test
    void testCreatePurchaseOrderHeader_Success() throws Exception {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        Response<PurchaseOrderHeader> response = new Response<>();
        response.setStatusCode(201);
        response.setPayload(purchaseOrderHeader);
        response.setMessage("Purchase order header created successfully.");
        when(purchaseOrderHeaderService.createPurchaseOrderHeader(any(PurchaseOrderHeader.class)))
                .thenReturn(ResponseEntity.status(201).body(response));

        ResponseEntity<Response<PurchaseOrderHeader>> result = purchaseOrderHeaderController.createPurchaseOrderHeader(purchaseOrderHeader);

        assertEquals(201, result.getBody().getStatusCode());
        assertEquals("Purchase order header created successfully.", result.getBody().getMessage());
        assertEquals(purchaseOrderHeader, result.getBody().getPayload());
    }

    // Test Create Purchase Order Header - Failure (Validation Error)
    @Test
    void testCreatePurchaseOrderHeader_Failure() throws Exception {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();  // Missing required fields

        Response<PurchaseOrderHeader> response = new Response<>();
        response.setStatusCode(400);
        response.setPayload(null);
        response.setMessage("Validation error occurred.");
        when(purchaseOrderHeaderService.createPurchaseOrderHeader(any(PurchaseOrderHeader.class)))
                .thenReturn(ResponseEntity.status(400).body(response));

        ResponseEntity<Response<PurchaseOrderHeader>> result = purchaseOrderHeaderController.createPurchaseOrderHeader(purchaseOrderHeader);

        assertEquals(400, result.getBody().getStatusCode());
        assertEquals("Validation error occurred.", result.getBody().getMessage());
        assertEquals(null, result.getBody().getPayload());
    }

    // Test Update Purchase Order Header - Success
    @Test
    void testUpdatePurchaseOrderHeader_Success() throws Exception {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setTotalCost(200);

        Response<PurchaseOrderHeader> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(purchaseOrderHeader);
        response.setMessage("Purchase order header updated successfully.");
        when(purchaseOrderHeaderService.updatePurchaseOrderHeader(eq(1L), any(PurchaseOrderHeader.class)))
                .thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<PurchaseOrderHeader>> result = purchaseOrderHeaderController.updatePurchaseOrderHeader(1L, purchaseOrderHeader);

        assertEquals(200, result.getBody().getStatusCode());
        assertEquals("Purchase order header updated successfully.", result.getBody().getMessage());
        assertEquals(purchaseOrderHeader, result.getBody().getPayload());
    }

    // Test Update Purchase Order Header - Failure (Not Found)
    @Test
    void testUpdatePurchaseOrderHeader_Failure() throws Exception {
        Response<PurchaseOrderHeader> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(null);
        response.setMessage("Purchase order header not found with id 1");
        when(purchaseOrderHeaderService.updatePurchaseOrderHeader(eq(1L), any(PurchaseOrderHeader.class)))
                .thenReturn(ResponseEntity.status(404).body(response));

        ResponseEntity<Response<PurchaseOrderHeader>> result = purchaseOrderHeaderController.updatePurchaseOrderHeader(1L, new PurchaseOrderHeader());

        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("Purchase order header not found with id 1", result.getBody().getMessage());
        assertEquals(null, result.getBody().getPayload());
    }

    // Test Delete Purchase Order Header - Success
    @Test
    void testDeletePurchaseOrderHeader_Success() throws Exception {
        Response<Void> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(null);
        response.setMessage("Purchase order header deleted successfully.");
        when(purchaseOrderHeaderService.deletePurchaseOrderHeader(1L))
                .thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<Void>> result = purchaseOrderHeaderController.deletePurchaseOrderHeader(1L);

        assertEquals(200, result.getBody().getStatusCode());
        assertEquals("Purchase order header deleted successfully.", result.getBody().getMessage());
    }

    // Test Delete Purchase Order Header - Failure (Not Found)
    @Test
    void testDeletePurchaseOrderHeader_Failure() throws Exception {
        Response<Void> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(null);
        response.setMessage("Purchase order header not found with id 1");
        when(purchaseOrderHeaderService.deletePurchaseOrderHeader(1L))
                .thenReturn(ResponseEntity.status(404).body(response));

        ResponseEntity<Response<Void>> result = purchaseOrderHeaderController.deletePurchaseOrderHeader(1L);

        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("Purchase order header not found with id 1", result.getBody().getMessage());
    }
}
