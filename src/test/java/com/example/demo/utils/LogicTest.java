package com.example.demo.utils;

import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.entity.PurchaseOrderHeader;
import com.example.demo.entity.response.ItemResponse;
import com.example.demo.entity.response.PurchaseOrderDetailResponse;
import com.example.demo.entity.response.PurchaseOrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogicTest {

    private PurchaseOrderHeader purchaseOrderHeader;
    private PurchaseOrderDetail purchaseOrderDetail;
    private Item item;

    @BeforeEach
    void setUp() {
        purchaseOrderHeader = mock(PurchaseOrderHeader.class);
        purchaseOrderDetail = mock(PurchaseOrderDetail.class);
        item = mock(Item.class);

        // Mock PurchaseOrderHeader
        when(purchaseOrderHeader.getId()).thenReturn(1);
        when(purchaseOrderHeader.getDatetime()).thenReturn(LocalDateTime.now());
        when(purchaseOrderHeader.getDescription()).thenReturn("Sample PO Description");
        when(purchaseOrderHeader.getTotalPrice()).thenReturn(1000);
        when(purchaseOrderHeader.getTotalCost()).thenReturn(900);
        when(purchaseOrderHeader.getCreatedBy()).thenReturn("User1");
        when(purchaseOrderHeader.getUpdatedBy()).thenReturn("User2");
        when(purchaseOrderHeader.getCreatedDatetime()).thenReturn(LocalDateTime.now().minusDays(1));
        when(purchaseOrderHeader.getUpdatedDatetime()).thenReturn(LocalDateTime.now());

        // Mock PurchaseOrderDetail
        when(purchaseOrderDetail.getId()).thenReturn(101);
        when(purchaseOrderDetail.getPurchaseOrderHeaderId()).thenReturn(1);
        when(purchaseOrderDetail.getItemQty()).thenReturn(5);
        when(purchaseOrderDetail.getItemCost()).thenReturn(200);
        when(purchaseOrderDetail.getItemPrice()).thenReturn(250);
        when(purchaseOrderDetail.getCreatedBy()).thenReturn("User1");
        when(purchaseOrderDetail.getUpdatedBy()).thenReturn("User2");
        when(purchaseOrderDetail.getCreatedDatetime()).thenReturn(LocalDateTime.now().minusDays(1));
        when(purchaseOrderDetail.getUpdatedDatetime()).thenReturn(LocalDateTime.now());

        // Mock Item
        when(item.getName()).thenReturn("Sample Item");
        when(item.getDescription()).thenReturn("Sample Item Description");
        when(item.getPrice()).thenReturn(500);
        when(item.getCost()).thenReturn(400);
        when(item.getCreatedBy()).thenReturn("User1");
        when(item.getCreatedDatetime()).thenReturn(LocalDateTime.now().minusDays(1));
    }

    @Test
    void testMapResultToPurchaseOrderResponse_Success() {
        // Arrange
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[]{purchaseOrderHeader, purchaseOrderDetail, item});

        // Act
        PurchaseOrderResponse response = Logic.mapResultToPurchaseOrderResponse(result);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Sample PO Description", response.getDescription());
        assertEquals(1000, response.getTotalPrice());
        assertEquals(900, response.getTotalCost());
        assertEquals(1, response.getPurchaseOrderDetails().size());

        PurchaseOrderDetailResponse detailResponse = response.getPurchaseOrderDetails().get(0);
        assertEquals(101, detailResponse.getId());
        assertEquals(5, detailResponse.getItemQty());
        assertEquals(250, detailResponse.getItemPrice());

        ItemResponse itemResponse = detailResponse.getItems().get(0);
        assertEquals("Sample Item", itemResponse.getName());
        assertEquals(500, itemResponse.getPrice());
    }

    @Test
    void testMapResultToPurchaseOrderResponse_Failure_InvalidCast() {
        // Arrange: Insert invalid data in the list
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[]{new String("Invalid data"), purchaseOrderDetail, item}); // wrong type

        // Act & Assert
        assertThrows(ClassCastException.class, () -> {
            Logic.mapResultToPurchaseOrderResponse(result);
        });
    }

    @Test
    void testMapResultToPurchaseOrderResponse_Failure_NullValues() {
        // Arrange: Insert null values in the list
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[]{null, purchaseOrderDetail, item});  // null PurchaseOrderHeader

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            Logic.mapResultToPurchaseOrderResponse(result);
        });
    }

}
