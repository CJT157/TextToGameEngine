package res;

import java.io.File;

public class ResourceLoader {
	
	 private static final String FOLDERNAME = "files";
	 private static final String NPCSFOLDER = FOLDERNAME + "/npcs";
	 private static final String MAPSFOLDER = FOLDERNAME + "/maps";
	 private static final String ITEMSFOLDER = FOLDERNAME + "/items";
	 private static final String MEMORYFOLDER = FOLDERNAME + "/memory";

    
	 public static File getNPCSFolder(){
		 String userDirectory = System.getProperty("user.dir");
		 System.out.println("Gathering files from " + userDirectory+"/"+NPCSFOLDER); 
	
	     return new File(userDirectory+"/"+NPCSFOLDER);
	 }
	 
	 public static File getMemory(String fileName){
		 String userDirectory = System.getProperty("user.dir");
		 System.out.println("Getting file " + userDirectory+"/"+MEMORYFOLDER+"/"+fileName);
	
	     return new File(userDirectory+"/"+MEMORYFOLDER+"/"+fileName);
	 }
    
	 public static File getMapsFolder(){
		 String userDirectory = System.getProperty("user.dir");
		 System.out.println("Gathering files from " + userDirectory+"/"+MAPSFOLDER); 
	
	     return new File(userDirectory+"/"+MAPSFOLDER);
	 }
	 
	 public static File getItemsFolder(){
		 String userDirectory = System.getProperty("user.dir");
		 System.out.println("Gathering files from " + userDirectory+"/"+ITEMSFOLDER); 
	
	     return new File(userDirectory+"/"+ITEMSFOLDER);
	 }
}
