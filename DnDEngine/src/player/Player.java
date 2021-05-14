package player;

import javafx.scene.paint.Color;
import ui.GUI;

public class Player extends Character{
	
	private GUI gui = GUI.getGUI();
	private static Player player;
	
	public static Player getPlayer() {
		if (player == null) {
			player = new Player();
		}
		return player;
	}
	
	private Player() {
		this.money = 100;
		this.color = Color.AQUA;
		this.setDescription("Hey that's you!`Please stop checking yourself out.");
	}
	
	public void takeItem(String item, int numItems) {
		inv.addItem(item, numItems);
	}
	
	public void dropItem(String item, int numItems) {
		inv.removeItem(item, numItems);
	}
	
	public void fight() throws InterruptedException {
		gui.println("FIGHT NOT FINISHED");
	}
	
	public void use() throws InterruptedException {
		gui.println("USE NOT FINISHED");
	}
	
	public String printInv() {
		return inv.toString();
	}
}
