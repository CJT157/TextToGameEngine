package player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import item.ItemBuilder;
import javafx.scene.paint.Color;
import res.ResourceLoader;
import textsystem.TalkingState;
import ui.GUI;

public class NPC extends Character{
	
	private String fileName;
	private ArrayList<String> greetingList = new ArrayList<String>();
	protected HashMap<String, DialogueChoice> dialogueList = new HashMap<String, DialogueChoice>();
	private String choiceHistory = "";
	private double sellMod;
	private double buyMod;
	
	public NPC() {
		this.canFight = true;
	}
	
	public void setInfo(String message) throws IOException {
		String[] newInfo = message.split(":", 2);
		switch(newInfo[0]) {
		case "name":
			setName(newInfo[1]);
			break;
		case "damage":
			setDamage(Integer.parseInt(newInfo[1]));
			break;
		case "defense":
			setDefense(Integer.parseInt(newInfo[1]));
			break;
		case "health":
			setHealth(Integer.parseInt(newInfo[1]));
			break;
		case "speed":
			setSpeed(Integer.parseInt(newInfo[1]));
			break;
		case "symbol":
			setSymbol(newInfo[1]);
			break;
		case "color":
			setColor(newInfo[1]);
			break;
		case "description":
			setDescription(newInfo[1]);
			break;
		case "money":
			setMoney(Integer.parseInt(newInfo[1]));
			break;
		case "sellMod":
			setSellMod(Double.parseDouble(newInfo[1]));
			break;
		case "buyMod":
			setBuyMod(Double.parseDouble(newInfo[1]));
			break;
		case "fileName":
			this.fileName = newInfo[1];
			this.getMemory();
			break;
		}
	}
	
	public void addGreeting(String message) {
		greetingList.add(message);
	}
	
	public String getGreeting() {
		Random r = new Random();
		String greeting = greetingList.get(r.nextInt(greetingList.size()));
		String message = this.getName() + "\n"+ 
			"-----------------------------\n" + 
			greeting +
			"\n-----------------------------\n" +
			this.getChoices();
		return message;
	}
	
	public void addDialogue(String message) {
		String[] newDialogue = message.split(":", 3);
		dialogueList.put(newDialogue[0], new DialogueChoice(newDialogue[1], newDialogue[2]));
	}
	
	public String getDialogue(String input) {
		if (!this.inv.isEmpty() && input.equals("S")) {
			GUI.getGUI().setTextSystem(TalkingState.SHOP, this);
			GUI.getGUI().npcClear();
			return printShop();
		} else if (this.inv.isEmpty() && input.equals("S")) {
			return "Sorry, I have nothing to buy!";
		} else if (input.equals("Fight") || input.equals("Fight")) {
			GUI.getGUI().setTextSystem(TalkingState.FIGHT, this);
			return "";
		}
		return dialogueList.get(input).getAnswer();
	}
	
	public String printShop() {
		String message = "-----------------------------\n";
		for (String i : this.getInv().getInvMap().keySet()) {
			InventorySlot item = this.getInv().getInvMap().get(i);
			message += item.getItem().getName() + " : Price [" + String.format("%.2f", item.getItem().getPrice() * (1.0 + buyMod)) + "] : " + item.getCurrStack() + "\n";
		}
		message += "-----------------------------\n";
		return message;
	}
	
	public void buyItem(String itemName, int amount) {
		if (this.getInv().hasItemAmount(itemName, amount)) {
			double price = new ItemBuilder().getItem(itemName).getPrice();
			double playerMoney = Player.getPlayer().getMoney();
			if (playerMoney >= (price * amount * (1.0 + buyMod))) {
				Player.getPlayer().setMoney(playerMoney - (price * amount * (1.0 + buyMod)));
				Player.getPlayer().getInv().addItem(itemName, amount);
				this.getInv().removeItem(itemName, amount);
				GUI.getGUI().npcClear();
				GUI.getGUI().npcPrint(printShop());
			} else {
				GUI.getGUI().npcPrint("Sorry, but you can't afford that.");
			}
		} else {
			GUI.getGUI().npcPrint("I don't have that in stock.");
		}
	}
	
	public void sellItem(String itemName, int amount) {
		if (Player.getPlayer().getInv().hasItemAmount(itemName, amount)) {
			double price = new ItemBuilder().getItem(itemName).getPrice();
			double npcMoney = this.getMoney();
			if (npcMoney >= (price * amount * (1.0 + sellMod))) {
				this.setMoney(npcMoney - (price * amount * (1.0 + sellMod)));
				this.getInv().addItem(itemName, amount);
				Player.getPlayer().getInv().removeItem(itemName, amount);
				GUI.getGUI().npcClear();
				GUI.getGUI().npcPrint(printShop());
			} else {
				GUI.getGUI().npcPrint("Sorry, but I can't afford that.");
			}
		} else {
			GUI.getGUI().npcPrint("I can't buy that if you don't have that.");
		}
	}

	public String getChoices() {
		Set<String> choiceList = dialogueList.keySet();
		String availableChoices = "";
		
		if (!this.inv.isEmpty()) {
			availableChoices += "[S] Shop\n";
		}
		
		for (String choice : choiceList) {
			if (choice.substring(0, choiceHistory.length()).equals(choiceHistory)) {
				availableChoices += "[" + choice + "] " + dialogueList.get(choice).getQuestion() + "\n";
			}
		}
		
		return availableChoices;
	}
	
	@Override
	public ArrayList<String> getDescription() {
		ArrayList<String> info = new ArrayList<String>();
		info.add("-----------------------------");
		if (this.name != null) {
			info.add("Name: " + this.name);
		}
		if (!this.inv.isEmpty()) {
			info.add("Services: Shop");
		}
		info.addAll(this.description);
		info.add("-----------------------------");
		return info;
	}
	
	public void getMemory() throws IOException {
		File file = ResourceLoader.getMemory(fileName);
		System.out.println(fileName);
	}
	
	public void setSellMod(double sellMod) {
		this.sellMod = sellMod;
	}
	
	public double getSellMod() {
		return sellMod;
	}
	
	public void setBuyMod(double buyMod) {
		this.buyMod = buyMod;
	}
	
	public double setSellMod() {
		return buyMod;
	}
}
