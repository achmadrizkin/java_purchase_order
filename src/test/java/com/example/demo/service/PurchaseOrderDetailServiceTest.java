package com.example.demo.service;

import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.entity.Response;
import com.example.demo.repo.ItemRepository;
import com.example.demo.repo.PurchaseOrderDetailRepository;
import com.example.demo.repo.PurchaseOrderHeaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PurchaseOrderDetailServiceTest {

    @Mock
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Mock
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test Get All Purchase Order Details - Success
    @Test
    void testGetAllPurchaseOrderDetails_Success() {
        List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();
        purchaseOrderDetails.add(new PurchaseOrderDetail());
        when(purchaseOrderDetailRepository.findAll()).thenReturn(purchaseOrderDetails);

        ResponseEntity<Response<List<PurchaseOrderDetail>>> responseEntity = purchaseOrderDetailService.getAllPurchaseOrderDetails();
        Response<List<PurchaseOrderDetail>> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.getPayload().size());
        assertEquals("Purchase order details retrieved successfully.", response.getMessage());
    }

    // Test Get All Purchase Order Details - Failure (No details)
    @Test
    void testGetAllPurchaseOrderDetails_Failure() {
        when(purchaseOrderDetailRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<Response<List<PurchaseOrderDetail>>> responseEntity = purchaseOrderDetailService.getAllPurchaseOrderDetails();
        Response<List<PurchaseOrderDetail>> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals(0, response.getPayload().size());
        assertEquals("No purchase order details found.", response.getMessage());
    }

    // Test Get Purchase Order Detail By ID - Success
    @Test
    void testGetPurchaseOrderDetailById_Success() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        when(purchaseOrderDetailRepository.findById(1L)).thenReturn(Optional.of(purchaseOrderDetail));

        ResponseEntity<Response<PurchaseOrderDetail>> responseEntity = purchaseOrderDetailService.getPurchaseOrderDetailById(1L);
        Response<PurchaseOrderDetail> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals(purchaseOrderDetail, response.getPayload());
        assertEquals("Purchase order detail found successfully.", response.getMessage());
    }

    // Test Get Purchase Order Detail By ID - Failure (Not Found)
    @Test
    void testGetPurchaseOrderDetailById_Failure() {
        when(purchaseOrderDetailRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Response<PurchaseOrderDetail>> responseEntity = purchaseOrderDetailService.getPurchaseOrderDetailById(1L);
        Response<PurchaseOrderDetail> response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals(null, response.getPayload());
        assertEquals("Purchase order detail not found with id 1", response.getMessage());
    }

    // Test Create Purchase Order Detail - Success
    @Test
    void testCreatePurchaseOrderDetail_Success() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setPurchaseOrderHeaderId(1);
        purchaseOrderDetail.setItemId(1);

        when(purchaseOrderHeaderRepository.existsById(1L)).thenReturn(true);
        when(itemRepository.existsById(1L)).thenReturn(true);
        when(purchaseOrderDetailRepository.save(purchaseOrderDetail)).thenReturn(purchaseOrderDetail);

        ResponseEntity<Response<PurchaseOrderDetail>> responseEntity = purchaseOrderDetailService.createPurchaseOrderDetail(purchaseOrderDetail);
        Response<PurchaseOrderDetail> response = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(201, response.getStatusCode());
        assertEquals(purchaseOrderDetail, response.getPayload());
        assertEquals("Purchase order detail created successfully.", response.getMessage());
    }

    // Test Create Purchase Order Detail - Failure (Invalid Header ID)
    @Test
    void testCreatePurchaseOrderDetail_InvalidHeaderId_Failure() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setPurchaseOrderHeaderId(1);
        purchaseOrderDetail.setItemId(1);

        when(purchaseOrderHeaderRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Response<PurchaseOrderDetail>> responseEntity = purchaseOrderDetailService.createPurchaseOrderDetail(purchaseOrderDetail);
        Response<PurchaseOrderDetail> response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(400, response.getStatusCode());
        assertEquals(null, response.getPayload());
        assertEquals("Purchase order header ID 1 does not exist.", response.getMessage());
    }

    // Test Delete Purchase Order Detail - Success
    @Test
    void testDeletePurchaseOrderDetail_Success() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        when(purchaseOrderDetailRepository.findById(1L)).thenReturn(Optional.of(purchaseOrderDetail));

        ResponseEntity<Response<Void>> responseEntity = purchaseOrderDetailService.deletePurchaseOrderDetail(1L);
        Response<Void> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, response.getStatusCode());
        assertEquals("Purchase order detail deleted successfully.", response.getMessage());
    }

    // Test Delete Purchase Order Detail - Failure (Not Found)
    @Test
    void testDeletePurchaseOrderDetail_Failure() {
        when(purchaseOrderDetailRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Response<Void>> responseEntity = purchaseOrderDetailService.deletePurchaseOrderDetail(1L);
        Response<Void> response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(404, response.getStatusCode());
        assertEquals("Purchase order detail not found with id 1", response.getMessage());
    }
}
