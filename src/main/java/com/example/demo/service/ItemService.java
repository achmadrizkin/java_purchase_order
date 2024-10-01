package com.example.demo.service;

import com.example.demo.entity.Response;
import com.example.demo.entity.Item;
import com.example.demo.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // Get all Item
    public ResponseEntity<Response<List<Item>>> getAllItem() {
        Response<List<Item>> response = new Response<>();
        List<Item> itemResponse = itemRepository.findAll();

        if (itemResponse.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(itemResponse);
            response.setMessage("Item data is empty");
        } else {
            response.setStatusCode(200);
            response.setPayload(itemResponse);
            response.setMessage("Item retrieved successfully.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Get Item by ID
    public ResponseEntity<Response<Item>> getItemById(Long id) {
        Optional<Item> Item = itemRepository.findById(id);
        Response<Item> response = new Response<>();

        if (Item.isPresent()) {
            response.setStatusCode(200);
            response.setPayload(Item.get());
            response.setMessage("Item found successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Item not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create a new Item with validation
    public ResponseEntity<Response<Item>> createItem(Item item) {
        Response<Item> response = new Response<>();

        // Set createdDatetime only during creation
        item.setCreatedDatetime(LocalDateTime.now());
        item.setCreatedBy(item.getCreatedBy());
        item.setUpdatedDatetime(null);

        Item savedItem = itemRepository.save(item);

        response.setStatusCode(201);
        response.setPayload(savedItem);
        response.setMessage("Item created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update Item by ID with validation
    public ResponseEntity<Response<Item>> updateItem(Long id, Item itemDetails) {
        Optional<Item> existingItem = itemRepository.findById(id);
        Response<Item> response = new Response<>();

        if (existingItem.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Item not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Item itemToUpdate = existingItem.get();

        itemToUpdate.setName(itemDetails.getName());
        itemToUpdate.setDescription(itemDetails.getDescription());
        itemToUpdate.setPrice(itemDetails.getPrice());
        itemToUpdate.setCost(itemDetails.getCost());
        itemToUpdate.setUpdatedBy(itemDetails.getUpdatedBy());
        itemToUpdate.setUpdatedDatetime(LocalDateTime.now());

        // Save the updated item
        Item updatedItem = itemRepository.save(itemToUpdate);

        response.setStatusCode(200);
        response.setPayload(updatedItem);
        response.setMessage("Item updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    // Delete Item by ID
    public ResponseEntity<Response<Void>> deleteItem(Long id) {
        Response<Void> response = new Response<>();

        Optional<Item> Item = itemRepository.findById(id);
        if (Item.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("Item not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        itemRepository.deleteById(id);
        response.setStatusCode(200);
        response.setPayload(null);
        response.setMessage("Item deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
