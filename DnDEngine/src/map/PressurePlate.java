package map;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class PressurePlate extends MapPiece {
	
	public boolean toggleState;
	public ArrayList<MapPiece> toggleList = new ArrayList<MapPiece>();
	
	public PressurePlate() {
		this.symbol = "_";
		this.symbolColor = Color.BLACK;
		this.description = new String[] {"It's a pressure plate, stand on it."};
		this.pieceState = PieceState.PRESSURE;
		this.toggleState = false;
	}
	
	public void setToggleList(ArrayList<MapPiece> list) {
		this.toggleList = list; 
	}
	
	public void toggle() {
		for (MapPiece piece : toggleList) {
			piece.toggle();
		}
	}
}
