package com.ecommerce.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
    @Query("SELECT o FROM OrderEntity o WHERE o.customerId = :customerId")
    List<OrderEntity> findByCustomerId(@Param("customerId") String customerId);
}