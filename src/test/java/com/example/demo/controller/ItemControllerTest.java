package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.entity.Response;
import com.example.demo.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private Item item;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setDescription("Test Description");
        item.setPrice(100);
        item.setCost(50);
    }

    @Test
    public void testGetAllItem_Success() {
        // Arrange
        Response<List<Item>> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(Arrays.asList(item));
        response.setMessage("Items retrieved successfully.");
        when(itemService.getAllItem()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(response));

        // Act
        ResponseEntity<Response<List<Item>>> result = itemController.getAllItem();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals(1, result.getBody().getPayload().size());
        assertEquals("Items retrieved successfully.", result.getBody().getMessage());
    }

    @Test
    public void testGetAllItem_Failure_NoItems() {
        // Arrange
        Response<List<Item>> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(List.of());
        response.setMessage("No items found.");
        when(itemService.getAllItem()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));

        // Act
        ResponseEntity<Response<List<Item>>> result = itemController.getAllItem();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals(0, result.getBody().getPayload().size());
        assertEquals("No items found.", result.getBody().getMessage());
    }

    @Test
    public void testGetItemById_Success() {
        // Arrange
        Response<Item> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(item);
        response.setMessage("Item retrieved successfully.");
        when(itemService.getItemById(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(response));

        // Act
        ResponseEntity<Response<Item>> result = itemController.getItemById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals(item, result.getBody().getPayload());
        assertEquals("Item retrieved successfully.", result.getBody().getMessage());
    }

    @Test
    public void testGetItemById_Failure_NotFound() {
        // Arrange
        Response<Item> response = new Response<>();
        response.setStatusCode(404);
        response.setPayload(null);
        response.setMessage("Item not found with id 999.");
        when(itemService.getItemById(999L)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));

        // Act
        ResponseEntity<Response<Item>> result = itemController.getItemById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals(null, result.getBody().getPayload());
        assertEquals("Item not found with id 999.", result.getBody().getMessage());
    }

    @Test
    public void testCreateItem_Success() {
        // Arrange
        Response<Item> response = new Response<>();
        response.setStatusCode(201);
        response.setPayload(item);
        response.setMessage("Item created successfully.");
        when(itemService.createItem(any(Item.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        // Act
        ResponseEntity<Response<Item>> result = itemController.createItem(item);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(201, result.getBody().getStatusCode());
        assertEquals(item, result.getBody().getPayload());
        assertEquals("Item created successfully.", result.getBody().getMessage());
    }

    @Test
    public void testCreateItem_Failure_InvalidItem() {
        // Arrange
        Item invalidItem = new Item();
        invalidItem.setName(null); // Invalid as name is null

        Response<Item> response = new Response<>();
        response.setStatusCode(400);
        response.setPayload(invalidItem);
        response.setMessage("Invalid item data.");
        when(itemService.createItem(any(Item.class))).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));

        // Act
        ResponseEntity<Response<Item>> result = itemController.createItem(invalidItem);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(400, result.getBody().getStatusCode());
        assertEquals(invalidItem, result.getBody().getPayload());
        assertEquals("Invalid item data.", result.getBody().getMessage());
    }

    @Test
    public void testUpdateItem_Success() {
        // Arrange
        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Item");
        updatedItem.setDescription("Updated Description");

        Response<Item> response = new Response<>();
        response.setStatusCode(200);
        response.setPayload(updatedItem);
        response.setMessage("Item updated successfully.");
        when(itemService.updateItem(eq(1L), any(Item.class))).thenReturn(ResponseEntity.status(HttpStatus.OK).body(response));

        // Act
        ResponseEntity<Response<Item>> result = itemController.updateItem(1L, updatedItem);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals(updatedItem, result.getBody().getPayload());
        assertEquals("Item updated successfully.", result.getBody().getMessage());
    }

    @Test
    public void testUpdateItem_Failure_NotFound() {
        // Arrange
        Item itemUpdate = new Item();
        itemUpdate.setName("Updated Name");

        Response<Item> response = new Response<>();
        response.setStatusCode(404);
        response.setMessage("Item not found with id 999.");
        when(itemService.updateItem(eq(999L), any(Item.class))).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));

        // Act
        ResponseEntity<Response<Item>> result = itemController.updateItem(999L, itemUpdate);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("Item not found with id 999.", result.getBody().getMessage());
    }

    @Test
    public void testDeleteItem_Success() {
        // Arrange
        Response<Void> response = new Response<>();
        response.setStatusCode(200);
        response.setMessage("Item deleted successfully.");
        when(itemService.deleteItem(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(response));

        // Act
        ResponseEntity<Response<Void>> result = itemController.deleteItem(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(200, result.getBody().getStatusCode());
        assertEquals("Item deleted successfully.", result.getBody().getMessage());
    }

    @Test
    public void testDeleteItem_Failure_NotFound() {
        // Arrange
        Response<Void> response = new Response<>();
        response.setStatusCode(404);
        response.setMessage("Item not found with id 999.");
        when(itemService.deleteItem(999L)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));

        // Act
        ResponseEntity<Response<Void>> result = itemController.deleteItem(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(404, result.getBody().getStatusCode());
        assertEquals("Item not found with id 999.", result.getBody().getMessage());
    }
}
