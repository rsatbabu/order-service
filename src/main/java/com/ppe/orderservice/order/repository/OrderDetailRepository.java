package com.ppe.orderservice.order.repository;


import org.springframework.data.repository.CrudRepository;

import com.ppe.orderservice.order.entity.OrderDetailEntity;




  public interface OrderDetailRepository extends CrudRepository<OrderDetailEntity,
  Long> {
  
  
  }
 