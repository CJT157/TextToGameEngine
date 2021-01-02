package map;

import javafx.scene.paint.Color;

public class FakeWall extends MapPiece {
	
	public boolean wallState;
	
	public FakeWall() {
		this.symbol = "#";
		this.symbolColor = Color.BLACK;
		this.description = new String[] {"Just a wall. Nothing to see here"};
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
