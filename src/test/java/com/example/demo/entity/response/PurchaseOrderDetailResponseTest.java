package com.example.demo.entity.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOrderDetailResponseTest {

    @Test
    void testPurchaseOrderDetailResponse_Success() {
        // Arrange
        PurchaseOrderDetailResponse detailResponse = new PurchaseOrderDetailResponse();
        Integer id = 1;
        Integer purchaseOrderHeaderId = 101;
        Integer itemQty = 5;
        Integer itemCost = 100;
        Integer itemPrice = 150;
        String createdBy = "User1";
        String updatedBy = "User2";
        LocalDateTime createdDatetime = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedDatetime = LocalDateTime.now();

        ItemResponse item1 = new ItemResponse();
        item1.setName("Item 1");
        item1.setPrice(100);
        ItemResponse item2 = new ItemResponse();
        item2.setName("Item 2");
        item2.setPrice(150);
        List<ItemResponse> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        // Act
        detailResponse.setId(id);
        detailResponse.setPurchaseOrderHeaderId(purchaseOrderHeaderId);
        detailResponse.setItemQty(itemQty);
        detailResponse.setItemCost(itemCost);
        detailResponse.setItemPrice(itemPrice);
        detailResponse.setCreatedBy(createdBy);
        detailResponse.setUpdatedBy(updatedBy);
        detailResponse.setCreatedDatetime(createdDatetime);
        detailResponse.setUpdatedDatetime(updatedDatetime);
        detailResponse.setItems(items);

        // Assert
        assertEquals(id, detailResponse.getId());
        assertEquals(purchaseOrderHeaderId, detailResponse.getPurchaseOrderHeaderId());
        assertEquals(itemQty, detailResponse.getItemQty());
        assertEquals(itemCost, detailResponse.getItemCost());
        assertEquals(itemPrice, detailResponse.getItemPrice());
        assertEquals(createdBy, detailResponse.getCreatedBy());
        assertEquals(updatedBy, detailResponse.getUpdatedBy());
        assertEquals(createdDatetime, detailResponse.getCreatedDatetime());
        assertEquals(updatedDatetime, detailResponse.getUpdatedDatetime());
        assertEquals(items, detailResponse.getItems());
    }

    @Test
    void testPurchaseOrderDetailResponse_HandlesNullValues() {
        // Arrange
        PurchaseOrderDetailResponse detailResponse = new PurchaseOrderDetailResponse();

        // Act
        detailResponse.setId(null);
        detailResponse.setPurchaseOrderHeaderId(null);
        detailResponse.setItemQty(null);
        detailResponse.setItemCost(null);
        detailResponse.setItemPrice(null);
        detailResponse.setCreatedBy(null);
        detailResponse.setUpdatedBy(null);
        detailResponse.setCreatedDatetime(null);
        detailResponse.setUpdatedDatetime(null);
        detailResponse.setItems(null);

        // Assert - Ensure that the object handles null without throwing errors
        assertNull(detailResponse.getId());
        assertNull(detailResponse.getPurchaseOrderHeaderId());
        assertNull(detailResponse.getItemQty());
        assertNull(detailResponse.getItemCost());
        assertNull(detailResponse.getItemPrice());
        assertNull(detailResponse.getCreatedBy());
        assertNull(detailResponse.getUpdatedBy());
        assertNull(detailResponse.getCreatedDatetime());
        assertNull(detailResponse.getUpdatedDatetime());
        assertNull(detailResponse.getItems());
    }

    @Test
    void testPurchaseOrderDetailResponse_AddItems_Success() {
        // Arrange
        PurchaseOrderDetailResponse detailResponse = new PurchaseOrderDetailResponse();

        ItemResponse item1 = new ItemResponse();
        item1.setName("Item 1");
        item1.setPrice(100);

        ItemResponse item2 = new ItemResponse();
        item2.setName("Item 2");
        item2.setPrice(150);

        // Act
        detailResponse.addItem(item1);
        detailResponse.addItem(item2);

        // Assert
        assertEquals(2, detailResponse.getItems().size());
        assertEquals("Item 1", detailResponse.getItems().get(0).getName());
        assertEquals(100, detailResponse.getItems().get(0).getPrice());
        assertEquals("Item 2", detailResponse.getItems().get(1).getName());
        assertEquals(150, detailResponse.getItems().get(1).getPrice());
    }

    @Test
    void testPurchaseOrderDetailResponse_HandleNullItems() {
        // Arrange
        PurchaseOrderDetailResponse detailResponse = new PurchaseOrderDetailResponse();

        // Act
        detailResponse.addItem(null);  // Adding a null item

        // Assert
        assertEquals(0, detailResponse.getItems().size());  // Should ignore null items
    }
}
