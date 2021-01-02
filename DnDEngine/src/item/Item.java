package item;

public class Item {
	protected String name;
	protected int stack;
	protected int maxStack;
	protected int rarity;
	protected int price;
	
	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public int getRarity() {
		return rarity;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxStack() {
		return this.maxStack;
	}

	public void setMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}

	public String toString() {
		return this.name;
	}
}
