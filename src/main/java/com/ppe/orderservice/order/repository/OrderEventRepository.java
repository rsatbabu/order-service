package com.ppe.orderservice.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ppe.orderservice.order.entity.OrderEventEntity;



public interface OrderEventRepository extends CrudRepository<OrderEventEntity, Long> {

    List<OrderEventEntity> findByOrderconsumedFalse();
    List<OrderEventEntity> findByInventoryconsumedFalse();
    List<OrderEventEntity> findByEmailconsumedFalse();
}
