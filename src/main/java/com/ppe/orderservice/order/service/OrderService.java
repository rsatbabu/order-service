package com.ppe.orderservice.order.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.demo.orderevent.exception.ConsumerException;
import com.example.demo.orderevent.model.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppe.orderservice.order.entity.OrderDetailEntity;
import com.ppe.orderservice.order.entity.OrderEntity;
import com.ppe.orderservice.order.repository.OrderRepository;

//@Service
public class OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderRepository orderRepository;

	@KafkaListener(topics = "test1", groupId = "group_id_order_service")
	public void consume(String message) throws ConsumerException {
		logger.info("#### -> OrderService Consumed message -> {}", message);

		processMessage(message);

	}

	private void processMessage(String message) throws ConsumerException {
		Map<String, OrderEntity> orderCustomerMap = new HashMap<String, OrderEntity>();
		// Read from the Events and group them by cutomer
		// for each customer extract the product and quantity and
		// store them in the orderDetailEntity

		List<OrderEvent> orderEventList = getOrderEventList(message);

		for (OrderEvent orderEvent : orderEventList) {
			if (!orderCustomerMap.containsKey(orderEvent.getId())) {
				OrderEntity orderEntity = new OrderEntity();
				orderEntity.setCustomerId(orderEvent.getCustomerId());
				orderEntity.setId(orderEvent.getId());
				List<OrderDetailEntity> orderDetailEntities = new ArrayList<OrderDetailEntity>();
				extracted(orderEvent, orderEntity, orderDetailEntities);
				orderCustomerMap.put(orderEvent.getId(), orderEntity);
			} else {
				OrderEntity orderEntity = orderCustomerMap.get(orderEvent.getId());
				List<OrderDetailEntity> orderDetailEntities = orderEntity.getOrderDetailEntities();
				extracted(orderEvent, orderEntity, orderDetailEntities);
				orderCustomerMap.put(orderEvent.getId(), orderEntity);

			}

		}

		orderCustomerMap.forEach((customerId, orderEntity) -> {
			orderEntity = orderRepository.save(orderEntity);
		});

		// printOrders();
	}

	public List<OrderEvent> getOrderEventList(String message) throws ConsumerException {
		logger.info(String.format("#### -> Consumed message -> %s", message));
		OrderEvent[] orderEvents = null;
		try {
			orderEvents = objectMapper.readValue(message, OrderEvent[].class);
			logger.info("OrderEvent {}", orderEvents[0].getCustomerId());
		} catch (JsonProcessingException e) { // TODO Auto-generated catch block
			throw new ConsumerException("Invalid message");
		}
		return Arrays.asList(orderEvents);
	}

	private void printOrders() {
		List<OrderEntity> orders = orderRepository.findAll();

		for (OrderEntity orderEntity : orders) {
			logger.info("Customer Id {} ", orderEntity.getCustomerId());
			orderEntity.getOrderDetailEntities().forEach(orderDetailEntity -> logger.info("Product Id {} Quantity  {} ",
					orderDetailEntity.getProductId(), orderDetailEntity.getQuantity()));
		}
	}

	private void extracted(OrderEvent orderEvent, OrderEntity orderEntity,
			List<OrderDetailEntity> orderDetailEntities) {
		OrderDetailEntity orderDetailEnity = new OrderDetailEntity(orderEvent.getProductId(), orderEvent.getQuantity());
		orderDetailEnity.setOrderEntity(orderEntity);
		orderDetailEntities.add(orderDetailEnity);
		orderEntity.setOrderDetailEntities(orderDetailEntities);
	}
}