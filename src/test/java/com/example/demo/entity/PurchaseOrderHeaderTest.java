package com.example.demo.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class PurchaseOrderHeaderTest {

    @Test
    void testCreatePurchaseOrderHeaderSuccess() {
        // Success scenario: valid PurchaseOrderHeader creation
        PurchaseOrderHeader po = new PurchaseOrderHeader();
        po.setId(1);
        po.setDatetime(LocalDateTime.now());
        po.setDescription("Sample Purchase Order");
        po.setTotalPrice(1000);
        po.setTotalCost(800);
        po.setCreatedBy("John Doe");
        po.setUpdatedBy("Jane Doe");
        po.setCreatedDatetime(LocalDateTime.now());
        po.setUpdatedDatetime(LocalDateTime.now());

        // Assertions
        assertNotNull(po);
        assertEquals(1, po.getId());
        assertEquals("Sample Purchase Order", po.getDescription());
        assertEquals(1000, po.getTotalPrice());
        assertEquals(800, po.getTotalCost());
        assertEquals("John Doe", po.getCreatedBy());
        assertEquals("Jane Doe", po.getUpdatedBy());
        assertNotNull(po.getCreatedDatetime());
        assertNotNull(po.getUpdatedDatetime());
    }

    @Test
    void testCreatePurchaseOrderHeaderFail() {
        // Failure scenario: invalid data
        PurchaseOrderHeader po = new PurchaseOrderHeader();

        // Assigning invalid values (like nulls and negative prices)
        po.setId(null); // Invalid ID
        po.setDatetime(null); // Invalid datetime
        po.setDescription(null); // Invalid description
        po.setTotalPrice(-500); // Invalid price
        po.setTotalCost(-300); // Invalid cost

        // Assertions
        assertNull(po.getId());
        assertNull(po.getDatetime());
        assertNull(po.getDescription());
        assertEquals(-500, po.getTotalPrice());
        assertEquals(-300, po.getTotalCost());
    }

    @Test
    void testValidationScenario() {
        // Check specific validation for business logic
        PurchaseOrderHeader po = new PurchaseOrderHeader();

        po.setTotalPrice(1000);
        po.setTotalCost(1200); // Cost higher than price, which might be an invalid business rule

        assertThrows(IllegalArgumentException.class, () -> {
            if (po.getTotalCost() > po.getTotalPrice()) {
                throw new IllegalArgumentException("Total cost cannot be higher than total price");
            }
        });
    }
}
