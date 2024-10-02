package com.example.demo.controller;

import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.entity.Response;
import com.example.demo.service.PurchaseOrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PurchaseOrderDetailControllerTest {

    @Mock
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @InjectMocks
    private PurchaseOrderDetailController purchaseOrderDetailController;

    private PurchaseOrderDetail purchaseOrderDetail;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setItemQty(5);
        purchaseOrderDetail.setItemCost(100);
    }

    // Test Get All Purchase Order Details - Success
    @Test
    void testGetAllPurchaseOrderDetails_Success() {
        List<PurchaseOrderDetail> purchaseOrderDetails = Arrays.asList(purchaseOrderDetail, new PurchaseOrderDetail());
        Response<List<PurchaseOrderDetail>> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(purchaseOrderDetails);
        response.setMessage("Purchase order details retrieved successfully.");
        when(purchaseOrderDetailService.getAllPurchaseOrderDetails()).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<List<PurchaseOrderDetail>>> result = purchaseOrderDetailController.getAllPurchaseOrderDetails();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals(2, result.getBody().getPayload().size());
        assertEquals("Purchase order details retrieved successfully.", result.getBody().getMessage());
    }

    // Test Get All Purchase Order Details - Failure (Empty List)
    @Test
    void testGetAllPurchaseOrderDetails_Failure() {
        Response<List<PurchaseOrderDetail>> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(Collections.emptyList());
        response.setMessage("No purchase order details found.");
        when(purchaseOrderDetailService.getAllPurchaseOrderDetails()).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<List<PurchaseOrderDetail>>> result = purchaseOrderDetailController.getAllPurchaseOrderDetails();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals(0, result.getBody().getPayload().size());
        assertEquals("No purchase order details found.", result.getBody().getMessage());
    }

    // Test Get Purchase Order Detail By ID - Success
    @Test
    void testGetPurchaseOrderDetailById_Success() {
        Response<PurchaseOrderDetail> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(purchaseOrderDetail);
        response.setMessage("Purchase order detail found successfully.");
        when(purchaseOrderDetailService.getPurchaseOrderDetailById(1L)).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<PurchaseOrderDetail>> result = purchaseOrderDetailController.getPurchaseOrderDetailById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals(purchaseOrderDetail, result.getBody().getPayload());
        assertEquals("Purchase order detail found successfully.", result.getBody().getMessage());
    }

    // Test Get Purchase Order Detail By ID - Failure (Not Found)
    @Test
    void testGetPurchaseOrderDetailById_Failure() {
        Response<PurchaseOrderDetail> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(null);
        response.setMessage("Purchase order detail not found with id 1");
        when(purchaseOrderDetailService.getPurchaseOrderDetailById(1L)).thenReturn(ResponseEntity.status(404).body(response));

        ResponseEntity<Response<PurchaseOrderDetail>> result = purchaseOrderDetailController.getPurchaseOrderDetailById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals(null, result.getBody().getPayload());
        assertEquals("Purchase order detail not found with id 1", result.getBody().getMessage());
    }

    // Test Create Purchase Order Detail - Success
    @Test
    void testCreatePurchaseOrderDetail_Success() {
        Response<PurchaseOrderDetail> response = new Response<>();
        response.setStatusCode(201);
        response.setPayload(purchaseOrderDetail);
        response.setMessage("Purchase order detail created successfully.");
        when(purchaseOrderDetailService.createPurchaseOrderDetail(any(PurchaseOrderDetail.class)))
                .thenReturn(ResponseEntity.status(201).body(response));

        ResponseEntity<Response<PurchaseOrderDetail>> result = purchaseOrderDetailController.createPurchaseOrderDetail(purchaseOrderDetail);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(201, result.getBody().getStatusCode());
        assertEquals(purchaseOrderDetail, result.getBody().getPayload());
        assertEquals("Purchase order detail created successfully.", result.getBody().getMessage());
    }

    // Test Create Purchase Order Detail - Failure (Validation Error)
    @Test
    void testCreatePurchaseOrderDetail_Failure() {
        PurchaseOrderDetail invalidDetail = new PurchaseOrderDetail(); // Invalid as no fields set

        Response<PurchaseOrderDetail> response = new Response<>();
        response.setStatusCode(400);
        response.setPayload(null);
        response.setMessage("Validation error occurred.");
        when(purchaseOrderDetailService.createPurchaseOrderDetail(any(PurchaseOrderDetail.class)))
                .thenReturn(ResponseEntity.status(400).body(response));

        ResponseEntity<Response<PurchaseOrderDetail>> result = purchaseOrderDetailController.createPurchaseOrderDetail(invalidDetail);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(400, result.getBody().getStatusCode());
        assertEquals(null, result.getBody().getPayload());
        assertEquals("Validation error occurred.", result.getBody().getMessage());
    }

    // Test Update Purchase Order Detail - Success
    @Test
    void testUpdatePurchaseOrderDetail_Success() {
        Response<PurchaseOrderDetail> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(purchaseOrderDetail);
        response.setMessage("Purchase order detail updated successfully.");
        when(purchaseOrderDetailService.updatePurchaseOrderDetail(eq(1L), any(PurchaseOrderDetail.class)))
                .thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<PurchaseOrderDetail>> result = purchaseOrderDetailController.updatePurchaseOrderDetail(1L, purchaseOrderDetail);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals(purchaseOrderDetail, result.getBody().getPayload());
        assertEquals("Purchase order detail updated successfully.", result.getBody().getMessage());
    }

    // Test Update Purchase Order Detail - Failure (Not Found)
    @Test
    void testUpdatePurchaseOrderDetail_Failure() {
        Response<PurchaseOrderDetail> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(null);
        response.setMessage("Purchase order detail not found with id 1");
        when(purchaseOrderDetailService.updatePurchaseOrderDetail(eq(1L), any(PurchaseOrderDetail.class)))
                .thenReturn(ResponseEntity.status(404).body(response));

        ResponseEntity<Response<PurchaseOrderDetail>> result = purchaseOrderDetailController.updatePurchaseOrderDetail(1L, purchaseOrderDetail);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals(null, result.getBody().getPayload());
        assertEquals("Purchase order detail not found with id 1", result.getBody().getMessage());
    }

    // Test Delete Purchase Order Detail - Success
    @Test
    void testDeletePurchaseOrderDetail_Success() {
        Response<Void> response = new Response<>();
        response.setStatusCode(200);
        response.setMessage("Purchase order detail deleted successfully.");
        when(purchaseOrderDetailService.deletePurchaseOrderDetail(1L))
                .thenReturn(ResponseEntity.ok(response));

        ResponseEntity<Response<Void>> result = purchaseOrderDetailController.deletePurchaseOrderDetail(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals("Purchase order detail deleted successfully.", result.getBody().getMessage());
    }

    // Test Delete Purchase Order Detail - Failure (Not Found)
    @Test
    void testDeletePurchaseOrderDetail_Failure() {
        Response<Void> response = new Response<>();
        response.setStatusCode(404);
        response.setMessage("Purchase order detail not found with id 1");
        when(purchaseOrderDetailService.deletePurchaseOrderDetail(1L))
                .thenReturn(ResponseEntity.status(404).body(response));

        ResponseEntity<Response<Void>> result = purchaseOrderDetailController.deletePurchaseOrderDetail(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("Purchase order detail not found with id 1", result.getBody().getMessage());
    }
}
