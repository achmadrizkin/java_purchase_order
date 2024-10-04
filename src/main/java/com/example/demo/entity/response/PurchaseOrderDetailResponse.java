package com.example.demo.entity.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDetailResponse {
    private Integer id;
    private Integer purchaseOrderHeaderId;
    private List<ItemResponse> items = new ArrayList<>();  // Renamed from 'itemId' to 'items'
    private Integer itemQty;
    private Integer itemCost;
    private Integer itemPrice;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchaseOrderHeaderId() {
        return purchaseOrderHeaderId;
    }

    public void setPurchaseOrderHeaderId(Integer purchaseOrderHeaderId) {
        this.purchaseOrderHeaderId = purchaseOrderHeaderId;
    }

    public List<ItemResponse> getItems() {  // Renamed getter to 'getItems'
        return items;
    }

    public void setItems(List<ItemResponse> items) {  // Renamed setter to 'setItems'
        this.items = items;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public Integer getItemCost() {
        return itemCost;
    }

    public void setItemCost(Integer itemCost) {
        this.itemCost = itemCost;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public LocalDateTime getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(LocalDateTime updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    // New method to add items to the list
    public void addItem(ItemResponse itemResponse) {
        if (itemResponse != null) {
            this.items.add(itemResponse);
        }
    }
}
