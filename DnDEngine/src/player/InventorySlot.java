package player;

import item.Item;

public class InventorySlot {
	
	private int maxStack;
	private int currStack;
	private Item item;
	
	public InventorySlot(Item item, int amount) {
		this.item = item;
		this.currStack = amount;
		this.maxStack = item.getMaxStack();
	}
	
	public int getCurrStack() {
		return this.currStack;
	}
	
	public void addCurrStack(int amount) {
		this.currStack += amount;
	}
	
	public void removeCurrStack(int amount) {
		this.currStack -= amount;
	}
	
	public int getMaxStack() {
		return this.maxStack;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	@Override
	public String toString() {
		return item + ": " + currStack;
	}
}
