package com.oocl.parking.repositories;

import com.oocl.parking.domain.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Long> {
    List<ReturnOrder> findByStatus(String status);

    @Query(value = "SELECT * FROM RETURN_ORDER R WHERE (SELECT OWNED_BY_EMPLOYEE_ID FROM PARKING_ORDER P WHERE P.ID = R.PARKING_ORDER_ID) = ?1", nativeQuery = true)
    List<ReturnOrder> findByOwnedEmployeeId(Long parkingClerkId);
}
