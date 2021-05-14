package player;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class PushPull extends Entity {
	
	public PushPull() {
		this.symbol = "|";
		this.color = Color.BROWN;
		this.description = new ArrayList<String>();
		this.description.add("Looks like I can push this.");
	}
}
