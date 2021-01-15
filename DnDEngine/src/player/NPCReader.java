package player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import map.Map;
import res.ResourceLoader;

public class NPCReader {
	
	private static NPCReader npcReader;
	private static HashMap<String, NPC> npcs = new HashMap<String, NPC>();
	
	public static NPCReader getInstance() throws IOException {
		if (npcReader == null) {
			npcReader = new NPCReader();
		}
		return npcReader;
	}
	
	private NPCReader() throws IOException {
	}
	
	public void loadNPCs() throws IOException {
		
		File file = ResourceLoader.getFolder("npcs");
		for (File f : file.listFiles()) {
		    if (f.isFile()) {
		    	npcs.put(f.getName(), readNPC(f));
		    }
		}
	}
	
	public HashMap<String, NPC> getMaps() {
		return npcs;
	}
	
	public NPC getNPC(String npcName) {
		return npcs.get(npcName);
	}
	
	public NPC readNPC(File file) throws IOException {
		Scanner npcScanner = new Scanner(file);
		String npcSection = "";
		System.out.println("Loading npc : " + file.getName());
		
		NPC newNPC = new NPC();
		
		//This could probably be redone, possibly with a builder or something
		while(npcScanner.hasNextLine()) {
			String nextLine = npcScanner.nextLine();
			
			if (nextLine.equals("info")) {
				npcSection = "info";
				continue;
			} else if (nextLine.equals("greeting")) {
				npcSection = "greeting";
				continue;
			} else if (nextLine.equals("dialogue")) {
				npcSection = "dialogue";
				continue;
			} else if (nextLine.equals("inventory")) {
				npcSection = "inventory";
				continue;
			} else if (nextLine.equals("cannotFight")) {
				newNPC.setCanFight(false);
				continue;
			} else if (nextLine.equals("")) {
				continue;
			}
			
			switch (npcSection) {
			case "info":
				newNPC.setInfo(nextLine);
				break;
			case "greeting":
				newNPC.addGreeting(nextLine);
				break;
			case "dialogue":
				newNPC.addDialogue(nextLine);
				break;
			case "inventory":
				String[] info = nextLine.split(" ");
				newNPC.getInv().addItem(info[0], Integer.parseInt(info[1]));
				break;
			default:
				break;
			}
		}
		newNPC.setInfo("fileName:" + file.getName());
		System.out.println("Finished loading npc : " + file.getName());
		return newNPC;
	}
}
