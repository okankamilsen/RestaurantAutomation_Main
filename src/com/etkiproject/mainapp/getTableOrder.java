package com.etkiproject.mainapp;


public class getTableOrder {

	
	private String itemName;
	private int quantity;
	private double totalPrice;
	
	public getTableOrder(String itemName, int quantity, double totalPrice) {
		super();
		this.itemName = itemName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
