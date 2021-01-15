package res;

import java.io.File;

public class ResourceLoader {
	
	private static final String FOLDERNAME = "files/Games";
	private static String gameFolder = ""; 
	private static String NPCSFOLDER = gameFolder + "/npcs";
	private static String MAPSFOLDER = gameFolder + "/maps";
	private static String ITEMSFOLDER = gameFolder + "/items";
	private static String MEMORYFOLDER = gameFolder + "/memory";
	
	private static String userDirectory = System.getProperty("user.dir");
	 
	public static void setGameFolder(String folderName) {
		gameFolder = FOLDERNAME + "/" + folderName;  
		NPCSFOLDER = gameFolder + "/npcs";
		MAPSFOLDER = gameFolder + "/maps";
		ITEMSFOLDER = gameFolder + "/items";
		MEMORYFOLDER = gameFolder + "/memory";
	}
	 
	public static File getFolder(String type) {
		switch (type) {
		case "games":
			System.out.println("Getting file " + userDirectory+"/"+FOLDERNAME);
			return new File(userDirectory + "/" + FOLDERNAME);
		case "npcs":
			System.out.println("Getting file " + userDirectory+"/"+NPCSFOLDER);
			return new File(userDirectory+"/"+NPCSFOLDER);
		case "maps":
			System.out.println("Getting file " + userDirectory+"/"+MAPSFOLDER);
			return new File(userDirectory+"/"+MAPSFOLDER);
		case "items":
			System.out.println("Getting file " + userDirectory+"/"+ITEMSFOLDER);
			return new File(userDirectory+"/"+ITEMSFOLDER);
		}
		return null;
	}
	 
	public static File getMemory(String fileName){
		System.out.println("Getting file " + userDirectory+"/"+MEMORYFOLDER+"/"+fileName);

		return new File(userDirectory+"/"+MEMORYFOLDER+"/"+fileName);
	}
}
