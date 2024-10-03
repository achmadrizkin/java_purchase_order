package com.example.demo.repo;

import com.example.demo.entity.PurchaseOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Long> {
    @Query("SELECT poh, pod, i FROM PurchaseOrderHeader poh " +
            "JOIN PurchaseOrderDetail pod ON poh.id = pod.purchaseOrderHeaderId " +
            "JOIN Item i ON pod.itemId = i.id " +
            "WHERE poh.id = :purchaseOrderId")
    List<Object[]> findPurchaseOrderWithDetailsAndItems(@Param("purchaseOrderId") Long purchaseOrderId);
}
