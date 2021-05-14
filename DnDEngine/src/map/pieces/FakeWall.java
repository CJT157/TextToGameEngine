package map.pieces;

import javafx.scene.paint.Color;
import map.MapPiece;
import map.MapPiece.PieceState;

public class FakeWall extends MapPiece {
	
	public boolean wallState;
	
	public FakeWall() {
		this.symbol = "#";
		this.symbolColor = Color.WHITE;
		this.description = new String[] {"Just a wall, nothing to see here."};
		this.pieceState = PieceState.SOLID;
		this.wallState = true;
	}
	
	public void toggle() {
		if (wallState) {
			this.symbol = " ";
			this.pieceState = PieceState.HOLLOW;
			this.wallState = false;
		} else {
			this.symbol = "#";
			this.pieceState = PieceState.SOLID;
			this.wallState = true;
		}
	}
}
