package player;

import java.util.HashMap;
import java.util.Set;

import item.Item;
import item.ItemBuilder;
import ui.GUI;

public class Inventory {
	
	private GUI gui = GUI.getGUI();
	private HashMap<String, InventorySlot> inv = new HashMap<String, InventorySlot>();
	private ItemBuilder itemBuilder = new ItemBuilder();
	
	public void addItem(String item, int amount) {
		if (!inv.containsKey(item)) {
			inv.put(item, new InventorySlot(itemBuilder.getItem(item), amount));
		} else {
			// add way to check for the stack being full
			InventorySlot currSlot = inv.get(item);
			currSlot.addCurrStack(amount);
		}
	}
	
	public void removeItem(String item, int amount) {
		if (!inv.get(item).equals(null)) {
			if (inv.get(item).getCurrStack() <= amount) {
				inv.remove(item);
			} else {
				inv.get(item).removeCurrStack(amount);
			}
		} else {
			gui.println("'" + item + "' is not in your inventory.");
		}
	}
	
	public boolean hasItem(String name) {
		if (this.inv.containsKey(name)) {
			return true;
		}
		return false;
	}
	
	public boolean hasItemAmount(String name, int amount) {
		if (this.inv.containsKey(name)) {
			if (this.inv.get(name).getCurrStack() >= amount) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		return this.inv.isEmpty();
	}
	
	public HashMap<String, InventorySlot> getInvMap() {
		return this.inv;
	}
	
	public String toString() {
		String inventory = "";
		if (!inv.isEmpty()) {
			for (String item : inv.keySet()) {
				inventory += inv.get(item) + "\n";
			}
		} else {
			inventory += "Empty\n";
		}
		return inventory;
	}
}
