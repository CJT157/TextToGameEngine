package ui;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		GUI gui = GUI.getGUI();
		
		gui.launchGUI(args);
	}

}
