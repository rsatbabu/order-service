package com.ppe.orderservice.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




import com.ppe.orderservice.order.entity.OrderDetailEntity;
import com.ppe.orderservice.order.entity.OrderEntity;
import com.ppe.orderservice.order.entity.OrderEventEntity;
import com.ppe.orderservice.order.repository.OrderDetailRepository;
import com.ppe.orderservice.order.repository.OrderEventRepository;
import com.ppe.orderservice.order.repository.OrderRepository;

@RestController
public class OrderController {

	
	  @Autowired private OrderEventRepository orderEventRepository;
	 

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/processOrderEvents")
	public void processOrderEvents() {
		
		// Simulate a Consumer reading from MQ 
		List<OrderEventEntity> orderEventEntities = orderEventRepository.findByOrderconsumedFalse();
		orderEventEntities.stream().forEach(System.out::println);

		Map<String, OrderEntity> orderCustomerMap = new HashMap<String, OrderEntity>();
		// Read from the Events and group them by orderId
		// for each customer extract the product and quantity and 
		// store them in the orderDetailEntity
		for (OrderEventEntity orderEventEntity : orderEventEntities) {
			if (!orderCustomerMap.containsKey(orderEventEntity.getOrderId())) {
				OrderEntity orderEntity = new OrderEntity();
				orderEntity.setCustomerId(orderEventEntity.getCustomerId());
				orderEntity.setId(orderEventEntity.getOrderId());
				List<OrderDetailEntity> orderDetailEntities = new ArrayList<OrderDetailEntity>();
				extracted(orderEventEntity, orderEntity, orderDetailEntities);
				orderCustomerMap.put(orderEventEntity.getOrderId(), orderEntity);
			} else {
				OrderEntity orderEntity = orderCustomerMap.get(orderEventEntity.getOrderId());
				List<OrderDetailEntity> orderDetailEntities = orderEntity.getOrderDetailEntities();
				extracted(orderEventEntity, orderEntity, orderDetailEntities);
				orderCustomerMap.put(orderEventEntity.getOrderId(), orderEntity);

			}
			orderEventEntity.setOrderconsumed(true);
		}
		
		orderCustomerMap.forEach((orderId,orderEntity)-> {
			orderEntity = orderRepository.save(orderEntity);
		});
		
		// simulate a MQ commit, so guaranteeing one time delivery
		orderEventRepository.saveAll(orderEventEntities);
		
		printOrders();

	}
	private void printOrders() {
		List<OrderEntity> orders = orderRepository.findAll();
		System.out.println(orders.size());
		System.out.println(orders.get(0).getOrderDetailEntities().size());
		System.out.println(orders.get(0).getOrderDetailEntities().get(0).getProductId());
		for(OrderEntity orderEntity: orders) {
			System.out.println("Customer Id"+orderEntity.getCustomerId());
			orderEntity.getOrderDetailEntities().forEach(orderDetailEntity -> System.out.println( "Product Id " +  orderDetailEntity.getProductId() + "Quantity "+ orderDetailEntity.getQuantity()));
		}
	}
	private void extracted(OrderEventEntity orderEventEntity, OrderEntity orderEntity,
			List<OrderDetailEntity> orderDetailEntities) {
		OrderDetailEntity orderDetailEnity = new OrderDetailEntity(orderEventEntity.getProductId(),
				orderEventEntity.getQuantity());
		orderDetailEnity.setOrderEntity(orderEntity);
		orderDetailEntities.add(orderDetailEnity);
		orderEntity.setOrderDetailEntities(orderDetailEntities);
	}
}
