package com.ppe.orderservice.order.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "orderdetails")
public class OrderDetailEntity {


	public OrderDetailEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetailEntity( int productId, int quantity) {
		super();
		
		this.productId = productId;
		this.quantity = quantity;
	}

	@Id
	@GeneratedValue
	private Long id;
	private int productId;
	private int quantity;
	
    @ManyToOne
    @JoinColumn(name="order_id",referencedColumnName = "id")
    private OrderEntity orderEntity;
    
  

}
