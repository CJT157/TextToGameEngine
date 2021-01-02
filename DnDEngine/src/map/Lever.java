package map;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Lever extends MapPiece {
	
	public boolean toggleState;
	public ArrayList<MapPiece> toggleList;
	
	public Lever() {
		this.symbol = "/";
		this.symbolColor = Color.BLACK;
		this.description = new String[] {"It's a switch, use it to see something happen!"};
		this.pieceState = PieceState.HOLLOW;
		this.toggleState = true;
	}
	
	public void setToggleList(ArrayList<MapPiece> list) {
		this.toggleList = list; 
	}
	
	public void toggle() {
		if (toggleState) {
			this.symbol = "\\";
			this.toggleState = false;
			for (MapPiece piece : toggleList) {
				piece.toggle();
			}
		} else {
			this.symbol = "/";
			this.toggleState = true;
			for (MapPiece piece : toggleList) {
				piece.toggle();
			}
		}
	}
}