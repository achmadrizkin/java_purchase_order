package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    // Get all item
    @GetMapping
    public ResponseEntity<Response<List<Item>>> getAllItem() {
        return itemService.getAllItem();
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<Item>> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    // Create a new item
    @PostMapping
    public ResponseEntity<Response<Item>> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    // Update item by ID
    @PutMapping("/{id}")
    public ResponseEntity<Response<Item>> updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    // Delete item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }
}
