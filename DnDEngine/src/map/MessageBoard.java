package map;

import javafx.scene.paint.Color;

public class MessageBoard extends MapPiece {
	
	public MessageBoard() {
		this.symbol = "!";
		this.symbolColor = Color.BLACK;
		this.pieceState = PieceState.SOLID;
	}
}
