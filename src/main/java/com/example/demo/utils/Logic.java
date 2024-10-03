package com.example.demo.utils;

import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseOrderDetail;
import com.example.demo.entity.PurchaseOrderHeader;
import com.example.demo.entity.response.PurchaseOrderDetailResponse;
import com.example.demo.entity.response.PurchaseOrderResponse;
import com.example.demo.entity.response.ItemResponse;

import java.util.List;

public class Logic {

    // Change to static if you want to call it like Logic.method()
    public static PurchaseOrderResponse mapResultToPurchaseOrderResponse(List<Object[]> result) {
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();

        // Loop through result to construct the response
        for (Object[] row : result) {
            PurchaseOrderHeader poh = (PurchaseOrderHeader) row[0];
            PurchaseOrderDetail pod = (PurchaseOrderDetail) row[1];
            Item item = (Item) row[2];

            // Set the header details (only needs to be set once)
            if (purchaseOrderResponse.getId() == null) {
                purchaseOrderResponse.setId(poh.getId());
                purchaseOrderResponse.setDatetime(poh.getDatetime());
                purchaseOrderResponse.setDescription(poh.getDescription());
                purchaseOrderResponse.setTotalPrice(poh.getTotalPrice());
                purchaseOrderResponse.setTotalCost(poh.getTotalCost());
                purchaseOrderResponse.setCreatedBy(poh.getCreatedBy());
                purchaseOrderResponse.setUpdatedBy(poh.getUpdatedBy());
                purchaseOrderResponse.setCreatedDatetime(poh.getCreatedDatetime());
                purchaseOrderResponse.setUpdatedDatetime(poh.getUpdatedDatetime());
            }

            // Build item list
            PurchaseOrderDetailResponse detailResponse = new PurchaseOrderDetailResponse();
            detailResponse.setId(pod.getId());
            detailResponse.setPurchaseOrderHeaderId(pod.getPurchaseOrderHeaderId());
            detailResponse.setItemQty(pod.getItemQty());
            detailResponse.setItemCost(pod.getItemCost());
            detailResponse.setItemPrice(pod.getItemPrice());
            detailResponse.setCreatedBy(pod.getCreatedBy());
            detailResponse.setUpdatedBy(pod.getUpdatedBy());
            detailResponse.setCreatedDatetime(pod.getCreatedDatetime());
            detailResponse.setUpdatedDatetime(pod.getUpdatedDatetime());

            // Add item details to the item list
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setName(item.getName());
            itemResponse.setDescription(item.getDescription());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setCost(item.getCost());
            itemResponse.setCreatedBy(item.getCreatedBy());
            itemResponse.setCreatedDatetime(item.getCreatedDatetime());

            detailResponse.addItem(itemResponse);
            purchaseOrderResponse.addDetail(detailResponse);
        }

        return purchaseOrderResponse;
    }
}
