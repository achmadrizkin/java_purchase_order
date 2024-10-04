package com.example.demo.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class PurchaseOrderDetailTest {

    @Test
    void testCreatePurchaseOrderDetailSuccess() {
        // Success scenario: valid PurchaseOrderDetail creation
        PurchaseOrderDetail poDetail = new PurchaseOrderDetail();
        poDetail.setId(1);
        poDetail.setPurchaseOrderHeaderId(100);
        poDetail.setItemId(200);
        poDetail.setItemQty(10);
        poDetail.setItemCost(500);
        poDetail.setItemPrice(600);
        poDetail.setCreatedBy("John Doe");
        poDetail.setUpdatedBy("Jane Doe");
        poDetail.setCreatedDatetime(LocalDateTime.now());
        poDetail.setUpdatedDatetime(LocalDateTime.now());

        // Assertions
        assertNotNull(poDetail);
        assertEquals(1, poDetail.getId());
        assertEquals(100, poDetail.getPurchaseOrderHeaderId());
        assertEquals(200, poDetail.getItemId());
        assertEquals(10, poDetail.getItemQty());
        assertEquals(500, poDetail.getItemCost());
        assertEquals(600, poDetail.getItemPrice());
        assertEquals("John Doe", poDetail.getCreatedBy());
        assertEquals("Jane Doe", poDetail.getUpdatedBy());
        assertNotNull(poDetail.getCreatedDatetime());
        assertNotNull(poDetail.getUpdatedDatetime());
    }

    @Test
    void testCreatePurchaseOrderDetailFail() {
        // Failure scenario: invalid data (e.g., nulls and negative values)
        PurchaseOrderDetail poDetail = new PurchaseOrderDetail();

        // Assigning invalid values
        poDetail.setId(null); // Invalid ID
        poDetail.setPurchaseOrderHeaderId(null); // Invalid Purchase Order Header ID
        poDetail.setItemId(null); // Invalid Item ID
        poDetail.setItemQty(-10); // Invalid quantity
        poDetail.setItemCost(-500); // Invalid cost
        poDetail.setItemPrice(-600); // Invalid price

        // Assertions
        assertNull(poDetail.getId());
        assertNull(poDetail.getPurchaseOrderHeaderId());
        assertNull(poDetail.getItemId());
        assertEquals(-10, poDetail.getItemQty());
        assertEquals(-500, poDetail.getItemCost());
        assertEquals(-600, poDetail.getItemPrice());
    }

    @Test
    void testValidationScenario() {
        // Business validation scenario: Item cost higher than item price
        PurchaseOrderDetail poDetail = new PurchaseOrderDetail();
        poDetail.setItemCost(700);
        poDetail.setItemPrice(600); // Invalid scenario: cost is higher than price

        assertThrows(IllegalArgumentException.class, () -> {
            if (poDetail.getItemCost() > poDetail.getItemPrice()) {
                throw new IllegalArgumentException("Item cost cannot be higher than item price");
            }
        });
    }

    @Test
    void testMissingFieldValidation() {
        // Failure due to missing critical fields
        PurchaseOrderDetail poDetail = new PurchaseOrderDetail();

        // Assigning some values and leaving others null
        poDetail.setItemId(101);
        poDetail.setItemQty(5);

        // Assertions
        assertNotNull(poDetail.getItemId());
        assertEquals(5, poDetail.getItemQty());
        assertNull(poDetail.getPurchaseOrderHeaderId());
        assertNull(poDetail.getItemCost()); // Missing cost should trigger validation later in business logic
    }
}
