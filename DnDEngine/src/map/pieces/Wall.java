package map.pieces;

import javafx.scene.paint.Color;
import map.MapPiece;
import map.MapPiece.PieceState;

public class Wall extends MapPiece {
	
	public Wall() {
		this.symbol = "#";
		this.symbolColor = Color.WHITE;
		this.description = new String[] {"Just a wall. Nothing to see here"};
		this.pieceState = PieceState.SOLID;
	}
	
	public Wall(char symbol) {
		this();
		this.symbol = String.valueOf(symbol);
	}
}
