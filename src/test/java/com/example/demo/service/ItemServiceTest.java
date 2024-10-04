package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.Response;
import com.example.demo.repo.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        item = new Item();
        item.setId(1);
        item.setName("Item 1");
        item.setDescription("Description of item 1");
        item.setPrice(100);
        item.setCost(50);
        item.setCreatedBy("Admin");
        item.setCreatedDatetime(LocalDateTime.now());
    }

    @Test
    public void testGetAllItem_Success() {
        // Arrange
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item));

        // Act
        ResponseEntity<Response<List<Item>>> response = itemService.getAllItem();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(1, response.getBody().getPayload().size());
        assertEquals("Item retrieved successfully.", response.getBody().getMessage());
    }

    @Test
    public void testGetAllItem_EmptyList() {
        // Arrange
        when(itemRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<Response<List<Item>>> response = itemService.getAllItem();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("Item data is empty", response.getBody().getMessage());
    }

    @Test
    public void testGetItemById_Success() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Act
        ResponseEntity<Response<Item>> response = itemService.getItemById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(item, response.getBody().getPayload());
        assertEquals("Item found successfully.", response.getBody().getMessage());
    }

    @Test
    public void testGetItemById_NotFound() {
        // Arrange
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Response<Item>> response = itemService.getItemById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("Item not found with id 999", response.getBody().getMessage());
    }

    @Test
    public void testCreateItem_Success() {
        // Arrange
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // Act
        ResponseEntity<Response<Item>> response = itemService.createItem(item);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(201, response.getBody().getStatusCode());
        assertEquals(item, response.getBody().getPayload());
        assertEquals("Item created successfully.", response.getBody().getMessage());
    }

    @Test
    public void testUpdateItem_Success() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item updatedItem = new Item();
        updatedItem.setId(1);
        updatedItem.setName("Updated Item");
        updatedItem.setDescription("Updated description");
        updatedItem.setPrice(200);
        updatedItem.setCost(100);
        updatedItem.setUpdatedBy("Admin");

        // Act
        ResponseEntity<Response<Item>> response = itemService.updateItem(1L, updatedItem);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(updatedItem.getName(), response.getBody().getPayload().getName());
        assertEquals("Item updated successfully.", response.getBody().getMessage());
    }

    @Test
    public void testUpdateItem_NotFound() {
        // Arrange
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        Item itemDetails = new Item();

        // Act
        ResponseEntity<Response<Item>> response = itemService.updateItem(999L, itemDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("Item not found with id 999", response.getBody().getMessage());
    }

    @Test
    public void testDeleteItem_Success() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).deleteById(1L);

        // Act
        ResponseEntity<Response<Void>> response = itemService.deleteItem(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals("Item deleted successfully.", response.getBody().getMessage());
    }

    @Test
    public void testDeleteItem_NotFound() {
        // Arrange
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Response<Void>> response = itemService.deleteItem(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("Item not found with id 999", response.getBody().getMessage());
    }
}
