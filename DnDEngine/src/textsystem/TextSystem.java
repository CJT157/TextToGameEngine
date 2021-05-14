package textsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import map.MapPiece;
import ui.GUI;
import player.Entity;
import player.NPC;
import player.Player;

public class TextSystem {

	private GUI gui = GUI.getGUI();
	private static TextSystem ts;
	private Player player = Player.getPlayer();
	private ArrayList<String> moveResponses = new ArrayList<String>(Arrays.asList("Left", "West", "Up", "North", "Right", "East", "Down", "South"));
	// get rid of the things below this
	private TalkingState talking;
	private NPC npcCurrent;
	private int helpHelpCounter;

	public static TextSystem getTextSystem() {
		if (ts == null) {
			ts = new TextSystem();
		}
		return ts;
	}

	private TextSystem() {}
	
	public void read(String input) throws InterruptedException {

		String[] response;

		if (!input.equals("")) {
			response = wordCasing(input).split(" ");
			
			if (talking == TalkingState.TALK) {
				gui.npcPrint(npcCurrent.getDialogue(response[0]));
			} else if (talking == TalkingState.SHOP) {
				switch (response[0]) {
				case "Buy":
					if (response.length > 2) {
						npcCurrent.buyItem(response[1], Integer.parseInt(response[2]));
					} else {
						npcCurrent.buyItem(response[1], 1);
					}
					break;
				case "Sell":
					if (response.length > 2) {
						npcCurrent.sellItem(response[1], Integer.parseInt(response[2]));
					} else {
						npcCurrent.sellItem(response[1], 1);
					}
					break;
				case "Back":
				case "Leave":
					this.setTalking(TalkingState.TALK, npcCurrent);
					break;
				}
			} else if (talking == TalkingState.FIGHT) {
				
			} else {
				switch (response[0]) {
				case "Look":
				case "L":
					if (moveResponses.contains(response[1])) {
						gui.getCurrentMap().lookDirection(response[1]);
					} else {
						gui.println(response[1] + " is not a direction, sorry");
					}
					break;
				case "Move":
				case "M":
					if (moveResponses.contains(response[1])) {
						gui.getCurrentMap().movePlayer(response[1]);
					} else {
						gui.println(response[1] + " is not a direction, sorry");
					}
					break;
				case "Take":
				case "T":
					for (int i = 1; i < response.length; i++) {
						MapPiece curentLocation = gui.getCurrentMap().getPiece(player.getYCoor(), player.getXCoor());
						if (curentLocation.getLoot() != null) {
							if (response[i].equals("All")) {
								for (Map.Entry<String, Integer> entry : curentLocation.getLoot().entrySet()) {
									player.takeItem(entry.getKey(), entry.getValue());
								}
								curentLocation.getLoot().clear();
								gui.updateInventory();
							} else if (!curentLocation.getLoot().containsKey(response[i])) {
								gui.println("Unknown item: " + response[i]);
								break;
							} else {
								try {
									int numItems = Integer.parseInt(response[i + 1]);
									player.takeItem(response[i], numItems);
									i++;
								} catch (Exception e) {
									player.takeItem(response[i], 1);
								}
							}
						}
					}
					break;
				case "Talkto":
					if (moveResponses.contains(response[1])) {
						gui.getCurrentMap().talkToNPC(response[1]);
					} else {
						gui.println(response[1] + " is not a direction, sorry");
					}
					break;
				case "Search":
				case "S":
					MapPiece curentLocation = gui.getCurrentMap().getPiece(player.getYCoor(), player.getXCoor());
					if (curentLocation.getLoot() != null) {
						if (curentLocation.getLoot().isEmpty()) {
							gui.println("Nothing here\n");
						} else {
							String str = "----------------\n";
							for (Map.Entry<String, Integer> entry : curentLocation.getLoot().entrySet()) {
								str += entry.getValue() + ": " + entry.getKey() + "\n";
							}
							str += "----------------";
							gui.println(str);
						}
					} else {
						gui.println("Nothing here\n");
					}
					break;
				case "Fight":
				case "F":
					player.fight();
					break;
				case "Use":
					if (!(response.length == 1)) {
						if (moveResponses.contains(response[1])) {
							gui.getCurrentMap().useCommand(response[1]);
						}else {
							gui.println(response[1] + " is not a direction, sorry");
						}
					} else {
						gui.getCurrentMap().useCommand("Under");
					}
					break;
				case "Drop":
				case "D":
					// doesn't work correctly
					// needs to be intertwined with map system
					for (int i = 1; i < response.length; i++) {
						if (response[i].equals("All")) {
							// not finished
							for (int j = 0; j < 1; j++) {
								gui.println("DROP ALL NOT FINISHED");
							}
						} else if (!player.getInv().hasItem(response[i])) {
							gui.println("Unknown item: " + response[i]);
							break;
						} else {
							try {
								int numItems = Integer.parseInt(response[i + 1]);
								player.getInv().removeItem(response[i], numItems);
								addToList(response[i], numItems);
								i++;
							} catch (Exception e) {
								player.getInv().removeItem(response[i], 1);
								addToList(response[i], 1);
							}
						}
					}
					break;
				case "Help":
				case "H":
					try {
						helpInfo(response[1]);
					} catch (Exception e) {
						gui.println("Possible commands:\n" + "take       talkto     use        f(ight)\n"
								+ "search     d(rop)     m(ove)		world\n\n" + "Type h or help [command] for more info.\n");
					}
					break;
				case "Exit":
				case "E":
					gui.resetGUI();
					break;
				}
			}
		}
	}

	public void addToList(String input, int numItems) {
		MapPiece curentLocation = gui.getCurrentMap().getPiece(player.getYCoor(), player.getXCoor());
		if (curentLocation.getLoot().containsKey(input)) {
			curentLocation.getLoot().put(input, curentLocation.getLoot().get(input) + numItems);
		} else {
			curentLocation.getLoot().put(input, numItems);
		}
	}

	public void helpInfo(String command) throws InterruptedException {
		switch (command) {
		case "Colin":
			gui.println("Ooooh good job puting my name into the help thing,\n"
					+ "not sure why you'd want to but good for you.\n");
			break;
		case "Drop":
		case "D":
			gui.println("Allows user to drop items from their inventory.\n" + "drop [itemName] [numItems]\n"
					+ "EX: drop apple 1 stick 2\n" + "EX: d apple all\n");
			break;
		case "Exit":
		case "E":
			gui.println("Lets you exit to the title screen but doesn't close the window.\n" + "'exit'\n" + "EX: exit\n"
					+ "EX: e\n");
			break;
		case "Fight":
		case "F":
			gui.println("Starts a fight with the designated character\n" + "'fight [name]'\n" + "EX: fight rock\n"
					+ "EX: f rock\n");
			break;
		case "Help":
		case "H":
			this.helpHelpCounter++;
			if (this.helpHelpCounter == 1) {
				gui.println("No.\n");
			} else if (this.helpHelpCounter == 2) {
				gui.println("I already told you, no.\n");
			} else if (this.helpHelpCounter == 3) {
				gui.println("This really isn't funny, please don't do this.\n");
			} else if (this.helpHelpCounter == 10) {
				gui.println("Seriously? Please just stop already.\n");
			} else if (this.helpHelpCounter == 50) {
				gui.println("At this point you might as well play Cookie Clicker, so please stop and leave.\n");
			} else if (this.helpHelpCounter == 69) {
				gui.println("Please stop. Nice.\n");
			} else if (this.helpHelpCounter == 100) {
				gui.println("Congrats, you found an Easter Egg, stop now.\n");
			} else if (this.helpHelpCounter == 420) {
				gui.println("Please stop. Blazin.\n");
			} else if (this.helpHelpCounter == 1000) {
				gui.println("Okay cool, but like why?\n");
			} else {
				gui.println("Please stop.\n");
			}
			break;
		case "Look":
		case "L":
			gui.println("Allows you to inspect objects around the map.\n"
					+ "EX: look up\n"
					+ "EX: l south\n");
			break;
		case "Move":
		case "M":
			gui.println("Allows you to traverse around the map.\n" + "'move [direction]'\n" + "EX: move left\n"
					+ "EX: m south\n");
			break;
		case "Search":
		case "S":
			gui.println("Allows you to look at items around them.\n" + "'search'\n" + "EX: search\n" + "EX: s\n");
			break;
		case "Take":
		case "T":
			gui.println("Allows user to take items from the ground.\n" + "'take [itemName] [numItems]'\n"
					+ "EX: take apple 1 stick 2\n" + "EX: t apple all\n");
			break;
		case "Talkto":
			gui.println("Lets you talk to a nearby character.\n" + "'talkto [name]'\n" + "EX: talkto void\n");
			break;
		case "Use":
			gui.println("No functionality yet\n" + "Lets you use an item.\n" + "'use [item]'\n" + "EX: use apple\n"
					+ "EX: u apple\n");
			break;
		case "World":
			gui.println("There's a lot of different objects in this little room, so it's best to learn what they do.\n"
					+ "[#] : Wall, doesn't do much other than stopping you from moving.\n"
					+ "[.] : Loot, if you walk over it, you can search and take stuff from the pile.\n"
					+ "[!] : Message Board, use this to see how this game actually works as an assignment.\n");
			break;
		default:
			gui.println("Unknown command, did you enter it wrong?");
		}
	}

	/**
	 * Needed for allowing actions to be done in lower and upper case, defaults to
	 * upper case
	 * 
	 * @param input : user text input
	 * @return string with all words capitalized
	 */
	public String wordCasing(String input) {
		if (!input.equals("")) {
			String[] newInput = input.toLowerCase().split(" ");
			String result = "";
			for (int i = 0; i < newInput.length; i++) {
				result += newInput[i].substring(0, 1).toUpperCase() + newInput[i].substring(1) + " ";
			}

			return result;
		}
		return "";
	}
	
	public void setTalking(TalkingState state, Entity npc) {
		this.talking = state;
		this.npcCurrent = (NPC)npc;
	}
}
