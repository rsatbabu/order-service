package com.ppe.orderservice.order.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Table(name = "orderevents")
@Entity
@AllArgsConstructor
public class OrderEventEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderId;
	private String customerId;
	private int productId;
	private int quantity;
	private boolean orderconsumed;
	private boolean emailconsumed;
	private boolean inventoryconsumed;

	public OrderEventEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderEventEntity(String orderId,String customerId, int productId, int quantity) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public OrderEventEntity(Long id, String customerId, int productId, int quantity) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.quantity = quantity;
	}

}
