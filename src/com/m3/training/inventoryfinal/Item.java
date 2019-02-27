package com.m3.training.inventoryfinal;

public class Item {

	private String itemName;
	private int itemStock;
	
	public Item(String itemName, int itemStock) {
		 this.itemName = itemName;
		 this.itemStock = itemStock;
	}


	public String getName() {
		return itemName;
	}

	public int getStock() {
		return itemStock;
	}

	public void setStock(int itemStock) {
		this.itemStock = itemStock;
	}
	
	public String toString() {
		return itemName + ", " + itemStock;
	}
}
