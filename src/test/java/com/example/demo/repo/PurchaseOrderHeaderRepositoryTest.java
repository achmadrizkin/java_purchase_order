package com.example.demo.repo;

import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.entity.PurchaseOrderHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseOrderHeaderRepositoryTest {

    @Mock
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    @InjectMocks
    private PurchaseOrderHeaderRepositoryTest testClass;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPurchaseOrderWithDetailsAndItems_Success() {
        // Arrange
        Long purchaseOrderId = 1L;

        PurchaseOrderHeader poh = new PurchaseOrderHeader();
        poh.setDescription("Test Purchase Order");

        Item item1 = new Item();
        item1.setName("Item 1");

        PurchaseOrderDetail pod1 = new PurchaseOrderDetail();
        pod1.setPurchaseOrderHeaderId(poh.getId());
        pod1.setItemId(item1.getId());

        List<Object[]> mockResult = Collections.singletonList(new Object[]{poh, pod1, item1});

        when(purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(purchaseOrderId)).thenReturn(mockResult);

        // Act
        List<Object[]> result = purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(purchaseOrderId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty(), "The result should not be empty");

        Object[] firstRow = result.get(0);
        PurchaseOrderHeader pohResult = (PurchaseOrderHeader) firstRow[0];
        PurchaseOrderDetail podResult = (PurchaseOrderDetail) firstRow[1];
        Item itemResult = (Item) firstRow[2];

        assertEquals("Test Purchase Order", pohResult.getDescription());
        assertEquals("Item 1", itemResult.getName());

        // Verify the mock interaction
        verify(purchaseOrderHeaderRepository, times(1)).findPurchaseOrderWithDetailsAndItems(purchaseOrderId);
    }

    @Test
    void testFindPurchaseOrderWithDetailsAndItems_Failure_NoDataFound() {
        // Arrange
        Long nonExistentPurchaseOrderId = 999L;

        when(purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(nonExistentPurchaseOrderId))
                .thenReturn(Collections.emptyList());

        // Act
        List<Object[]> result = purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(nonExistentPurchaseOrderId);

        // Assert
        assertTrue(result.isEmpty(), "The result should be empty for a non-existent purchase order ID");

        // Verify the mock interaction
        verify(purchaseOrderHeaderRepository, times(1)).findPurchaseOrderWithDetailsAndItems(nonExistentPurchaseOrderId);
    }

    @Test
    void testFindPurchaseOrderWithDetailsAndItems_Failure_InvalidPurchaseOrderId() {
        // Arrange
        Long invalidPurchaseOrderId = null;

        when(purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(invalidPurchaseOrderId))
                .thenThrow(new IllegalArgumentException("purchaseOrderId must not be null!"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseOrderHeaderRepository.findPurchaseOrderWithDetailsAndItems(invalidPurchaseOrderId);
        });

        assertEquals("purchaseOrderId must not be null!", exception.getMessage());

        // Verify the mock interaction
        verify(purchaseOrderHeaderRepository, times(1)).findPurchaseOrderWithDetailsAndItems(invalidPurchaseOrderId);
    }
}
