package res;

import java.io.File;

public class ResourceLoader {
	
	 private static final String FOLDERNAME = "files";
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
	 
	 public static File getAllGames() {
		 System.out.println("Gathering files from " + userDirectory +"/"+ FOLDERNAME);
		 
	     return new File(userDirectory + "/" + FOLDERNAME);
	 }
    
	 public static File getNPCSFolder(){
		 System.out.println("Gathering files from " + userDirectory +"/"+NPCSFOLDER);
	
	     return new File(userDirectory+"/"+NPCSFOLDER);
	 }
	 
	 public static File getMemory(String fileName){
		 System.out.println("Getting file " + userDirectory+"/"+MEMORYFOLDER+"/"+fileName);
	
	     return new File(userDirectory+"/"+MEMORYFOLDER+"/"+fileName);
	 }
    
	 public static File getMapsFolder(){
		 System.out.println("Gathering files from " + userDirectory+"/"+MAPSFOLDER); 
	
	     return new File(userDirectory+"/"+MAPSFOLDER);
	 }
	 
	 public static File getItemsFolder(){
		 System.out.println("Gathering files from " + userDirectory+"/"+ITEMSFOLDER); 
	
	     return new File(userDirectory+"/"+ITEMSFOLDER);
	 }
}
