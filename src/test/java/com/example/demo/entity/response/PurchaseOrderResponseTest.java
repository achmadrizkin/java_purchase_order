package com.example.demo.entity.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOrderResponseTest {

    @Test
    void testPurchaseOrderResponse_Success() {
        // Arrange
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();
        Integer id = 1;
        LocalDateTime datetime = LocalDateTime.now();
        String description = "Test Purchase Order";
        Integer totalPrice = 1000;
        Integer totalCost = 800;
        String createdBy = "User1";
        String updatedBy = "User2";
        LocalDateTime createdDatetime = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedDatetime = LocalDateTime.now();

        // Creating sample PurchaseOrderDetailResponses
        PurchaseOrderDetailResponse detail1 = new PurchaseOrderDetailResponse();
        detail1.setId(1);
        detail1.setItemQty(5);
        PurchaseOrderDetailResponse detail2 = new PurchaseOrderDetailResponse();
        detail2.setId(2);
        detail2.setItemQty(10);
        List<PurchaseOrderDetailResponse> details = new ArrayList<>();
        details.add(detail1);
        details.add(detail2);

        // Act
        purchaseOrderResponse.setId(id);
        purchaseOrderResponse.setDatetime(datetime);
        purchaseOrderResponse.setDescription(description);
        purchaseOrderResponse.setTotalPrice(totalPrice);
        purchaseOrderResponse.setTotalCost(totalCost);
        purchaseOrderResponse.setCreatedBy(createdBy);
        purchaseOrderResponse.setUpdatedBy(updatedBy);
        purchaseOrderResponse.setCreatedDatetime(createdDatetime);
        purchaseOrderResponse.setUpdatedDatetime(updatedDatetime);
        purchaseOrderResponse.setPurchaseOrderDetails(details);

        // Assert
        assertEquals(id, purchaseOrderResponse.getId());
        assertEquals(datetime, purchaseOrderResponse.getDatetime());
        assertEquals(description, purchaseOrderResponse.getDescription());
        assertEquals(totalPrice, purchaseOrderResponse.getTotalPrice());
        assertEquals(totalCost, purchaseOrderResponse.getTotalCost());
        assertEquals(createdBy, purchaseOrderResponse.getCreatedBy());
        assertEquals(updatedBy, purchaseOrderResponse.getUpdatedBy());
        assertEquals(createdDatetime, purchaseOrderResponse.getCreatedDatetime());
        assertEquals(updatedDatetime, purchaseOrderResponse.getUpdatedDatetime());
        assertEquals(details, purchaseOrderResponse.getPurchaseOrderDetails());
    }

    @Test
    void testPurchaseOrderResponse_HandlesNullValues() {
        // Arrange
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();

        // Act
        purchaseOrderResponse.setId(null);
        purchaseOrderResponse.setDatetime(null);
        purchaseOrderResponse.setDescription(null);
        purchaseOrderResponse.setTotalPrice(null);
        purchaseOrderResponse.setTotalCost(null);
        purchaseOrderResponse.setCreatedBy(null);
        purchaseOrderResponse.setUpdatedBy(null);
        purchaseOrderResponse.setCreatedDatetime(null);
        purchaseOrderResponse.setUpdatedDatetime(null);
        purchaseOrderResponse.setPurchaseOrderDetails(null);

        // Assert - Ensure that the object handles null without throwing errors
        assertNull(purchaseOrderResponse.getId());
        assertNull(purchaseOrderResponse.getDatetime());
        assertNull(purchaseOrderResponse.getDescription());
        assertNull(purchaseOrderResponse.getTotalPrice());
        assertNull(purchaseOrderResponse.getTotalCost());
        assertNull(purchaseOrderResponse.getCreatedBy());
        assertNull(purchaseOrderResponse.getUpdatedBy());
        assertNull(purchaseOrderResponse.getCreatedDatetime());
        assertNull(purchaseOrderResponse.getUpdatedDatetime());
        assertNull(purchaseOrderResponse.getPurchaseOrderDetails());
    }

    @Test
    void testPurchaseOrderResponse_AddDetail_Success() {
        // Arrange
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();
        PurchaseOrderDetailResponse detail = new PurchaseOrderDetailResponse();
        detail.setId(1);
        detail.setItemQty(5);

        // Act
        purchaseOrderResponse.addDetail(detail);

        // Assert
        assertEquals(1, purchaseOrderResponse.getPurchaseOrderDetails().size());
        assertEquals(5, purchaseOrderResponse.getPurchaseOrderDetails().get(0).getItemQty());
    }

    @Test
    void testPurchaseOrderResponse_AddDetail_HandlesNull() {
        // Arrange
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();

        // Act
        purchaseOrderResponse.addDetail(null);  // Adding a null detail

        // Assert
        assertEquals(0, purchaseOrderResponse.getPurchaseOrderDetails().size());  // Null should not be added
    }
}
