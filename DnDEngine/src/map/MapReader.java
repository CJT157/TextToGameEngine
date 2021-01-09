package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import res.ResourceLoader;

public class MapReader {

	private static MapReader mapReader;
	private static HashMap<String, Map> maps = new HashMap<String, Map>();
	
	public static MapReader getInstance() throws IOException {
		if (mapReader == null) {
			mapReader = new MapReader();
		}
		return mapReader;
	}
	
	private MapReader() throws IOException {
		loadMaps();
	}
	
	public void loadMaps() throws IOException  {
		
		File folder = ResourceLoader.getFolder("maps");

		//work on multiple maps system eventually, honestly not important right now
		for (File file : folder.listFiles()) {
		    if (file.isFile() && (!file.getName().equals(".DS_Store"))) {
		    	maps.put(file.getName(), readMap(file));
		    }
		}
	}
	
	public HashMap<String, Map> getMaps() {
		return maps;
	}
	
	public Map getMap(String mapName) {
		return maps.get(mapName);
	}

	public Map readMap(File map) throws IOException {
		Scanner roomScanner = new Scanner(map);
		System.out.println("Loading room : " + map.getName());
		
		String[] dimensions = roomScanner.nextLine().split(" ");
		int length = Integer.parseInt(dimensions[0]);
		int width = Integer.parseInt(dimensions[1]);
		Map newMap = new Map(length, width);
		
		char[][] charLayout = new char[width][length];
		
		for (int i = width - 1; i >= 0; i--) {
			charLayout[i] = roomScanner.nextLine().toCharArray();
		}
		
		for (int i = width - 1; i >= 0; i--) {
			for (int j = 0; j < length; j++) {
				newMap.setLayout(i, j, charLayout[i][j]);
			}
		}
		
		String mapSection = "";
		
		//This could probably be redone, possibly with a builder or something
		while(roomScanner.hasNextLine()) {
			String nextLine = roomScanner.nextLine();
			
			if (nextLine.equals("loot")) {
				mapSection = "loot";
				continue;
			} else if (nextLine.equals("messages")) {
				mapSection = "messages";
				continue;
			} else if (nextLine.equals("levers")) {
				mapSection = "levers";
				continue;
			} else if (nextLine.equals("exits")) {
				mapSection = "exits";
				continue;
			} else if (nextLine.equals("npcs")) {
				mapSection = "npcs";
				continue;
			} else if (nextLine.equals("player-here")) {
				mapSection = "player-here";
				continue;
			} else if (nextLine.equals("")) {
				continue;
			}
			
			switch (mapSection) {
			case "loot":
				newMap.setLoot(nextLine);
				break;
			case "messages":
				newMap.setMessage(nextLine);
				break;
			case "levers":
				newMap.setLever(nextLine);
				break;
			case "exits":
				newMap.setExits(nextLine);
				break;
			case "npcs":
				newMap.setNPC(nextLine);
				break;
			default:
				break;
			}
		}
		System.out.println("Finished loading room : " + map.getName());
		return newMap;
	}
	
}
