package com.ppe.orderservice.order.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.Id;

import lombok.Data;

//
@Entity
@Data
@Table(name = "orders")
public class OrderEntity {
	@Id
	private String id;
	private String customerId;
	private int status;

	@OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL)
	private List<OrderDetailEntity> orderDetailEntities;

}
