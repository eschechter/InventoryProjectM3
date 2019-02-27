
package com.m3.training.inventoryfinal;

import com.m3.training.inventoryfinal.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {

	private HashMap<String, Item> itemMap;
	protected DatabaseReader reader;
	protected DatabaseWriter writer;

	private int MAX_BACKORDER_PER_ITEM = -15;

	// TODO We want Inventory() to not need to take anything (like itemArray).
	public Inventory() {
		itemMap = new HashMap<>();
		reader = new DatabaseReader();
		writer = new DatabaseWriter();
		ArrayList<Item> invList = reader.readDatabase();
		for (Item item : invList) {
			String name = item.getName();
			itemMap.put(name, item);
		}
	}

	public String toString() {
		return itemMap.toString();
	}

	public Item addItem(String name, int stock) {
		if (stock >= -15) {
			Item item = new Item(name, stock);
			itemMap.put(name, item);
			writer.addItem(name, stock);
			return item;
		}
		// TODO Make this exception better
		throw (new RuntimeException());
	}

	public Item changeStock(String name, int stockChange) {
		if (itemMap.containsKey(name)) {
			Item item = itemMap.get(name);
			int currentStock = item.getStock();
			if (currentStock + stockChange >= MAX_BACKORDER_PER_ITEM) {
				int updatedStock = currentStock + stockChange;
				item.setStock(updatedStock);
				writer.changeStock(name, updatedStock);
				// TODO Here, write to the database object
				return item;
			}
			// TODO Make this exception better
			throw (new RuntimeException());
		}
		throw (new RuntimeException());
	}

	public static void main(String[] args) {

		Inventory i = new Inventory();
		System.out.println(i);
		
		i.addItem("Bag of glass", 100);

	}
}
