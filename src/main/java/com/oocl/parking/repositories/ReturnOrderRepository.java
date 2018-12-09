package com.oocl.parking.repositories;

import com.oocl.parking.domain.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Long> {
    List<ReturnOrder> findByStatus(String status);
}
