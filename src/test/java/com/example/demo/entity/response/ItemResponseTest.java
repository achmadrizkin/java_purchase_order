package com.example.demo.entity.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemResponseTest {

    @Test
    void testItemResponse_Success() {
        // Arrange
        ItemResponse itemResponse = new ItemResponse();
        String name = "Test Item";
        String description = "Test Item Description";
        int price = 100;
        int cost = 80;
        String createdBy = "User1";
        LocalDateTime createdDatetime = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedDatetime = LocalDateTime.now();

        // Act
        itemResponse.setName(name);
        itemResponse.setDescription(description);
        itemResponse.setPrice(price);
        itemResponse.setCost(cost);
        itemResponse.setCreatedBy(createdBy);
        itemResponse.setCreatedDatetime(createdDatetime);
        itemResponse.setUpdatedDatetime(updatedDatetime);

        // Assert
        assertEquals(name, itemResponse.getName());
        assertEquals(description, itemResponse.getDescription());
        assertEquals(price, itemResponse.getPrice());
        assertEquals(cost, itemResponse.getCost());
        assertEquals(createdBy, itemResponse.getCreatedBy());
        assertEquals(createdDatetime, itemResponse.getCreatedDatetime());
        assertEquals(updatedDatetime, itemResponse.getUpdatedDatetime());
    }

    @Test
    void testItemResponse_HandlesNullValues() {
        // Arrange
        ItemResponse itemResponse = new ItemResponse();

        // Act
        itemResponse.setName(null);
        itemResponse.setDescription(null);
        itemResponse.setPrice(null);
        itemResponse.setCost(null);
        itemResponse.setCreatedBy(null);
        itemResponse.setCreatedDatetime(null);
        itemResponse.setUpdatedDatetime(null);

        // Assert - Ensure that the object handles null without throwing errors
        assertNull(itemResponse.getName());
        assertNull(itemResponse.getDescription());
        assertNull(itemResponse.getPrice());
        assertNull(itemResponse.getCost());
        assertNull(itemResponse.getCreatedBy());
        assertNull(itemResponse.getCreatedDatetime());
        assertNull(itemResponse.getUpdatedDatetime());
    }
}
