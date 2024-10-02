package com.example.demo.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

public class ItemTest {

    @Test
    public void testItemSuccess() {
        // Arrange
        Item item = new Item();
        LocalDateTime now = LocalDateTime.now();

        // Act
        item.setId(1L);
        item.setName("Item Name");
        item.setDescription("Item Description");
        item.setPrice(100);
        item.setCost(50);
        item.setCreatedBy("User A");
        item.setUpdatedBy("User B");
        item.setCreatedDatetime(now);
        item.setUpdatedDatetime(now);

        // Assert
        assertThat(item.getId()).isEqualTo(1L);
        assertThat(item.getName()).isEqualTo("Item Name");
        assertThat(item.getDescription()).isEqualTo("Item Description");
        assertThat(item.getPrice()).isEqualTo(100);
        assertThat(item.getCost()).isEqualTo(50);
        assertThat(item.getCreatedBy()).isEqualTo("User A");
        assertThat(item.getUpdatedBy()).isEqualTo("User B");
        assertThat(item.getCreatedDatetime()).isEqualTo(now);
        assertThat(item.getUpdatedDatetime()).isEqualTo(now);
    }

    @Test
    public void testItemFailure() {
        // Arrange
        Item item = new Item();

        // Act
        item.setId(null); // intentionally failing
        item.setName(null);
        item.setDescription(null);
        item.setPrice(null);
        item.setCost(null);
        item.setCreatedBy(null);
        item.setUpdatedBy(null);
        item.setCreatedDatetime(null);
        item.setUpdatedDatetime(null);

        // Assert - ensure null values are returned
        assertThat(item.getId()).isNull();
        assertThat(item.getName()).isNull();
        assertThat(item.getDescription()).isNull();
        assertThat(item.getPrice()).isNull();
        assertThat(item.getCost()).isNull();
        assertThat(item.getCreatedBy()).isNull();
        assertThat(item.getUpdatedBy()).isNull();
        assertThat(item.getCreatedDatetime()).isNull();
        assertThat(item.getUpdatedDatetime()).isNull();
    }

    @Test
    public void testNegativePrice() {
        // Arrange
        Item item = new Item();

        // Act & Assert - setting a negative price to see if it allows
        item.setPrice(-1);
        assertThat(item.getPrice()).isEqualTo(-1);  // This should pass as the entity does not have validation
    }
}
