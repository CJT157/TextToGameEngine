package ui;

import java.io.FileNotFoundException;

import saveSystem.Save;

public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		GUI gui = GUI.getGUI();
		Save.readSave();
		
		gui.launchGUI(args);
	}

}
