package com.ppe.orderservice.order.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ppe.orderservice.order.entity.OrderEntity;





  public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
	  List<OrderEntity>  findAll();
	  
  
  }
 