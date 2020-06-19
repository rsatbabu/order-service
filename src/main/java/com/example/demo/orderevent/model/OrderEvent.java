package com.example.demo.orderevent.model;

import lombok.Data;

@Data
public class OrderEvent {
	private String id;
	private String customerId;
	private int productId;
	private int quantity;
}
