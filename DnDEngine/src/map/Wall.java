package map;

import javafx.scene.paint.Color;

public class Wall extends MapPiece {
	
	public Wall() {
		this.symbol = "#";
		this.symbolColor = Color.BLACK;
		this.description = new String[] {"Just a wall. Nothing to see here"};
		this.pieceState = PieceState.SOLID;
	}
	
	public Wall(char symbol) {
		this();
		this.symbol = String.valueOf(symbol);
	}
}
