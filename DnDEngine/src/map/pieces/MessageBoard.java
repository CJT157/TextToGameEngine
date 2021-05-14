package map.pieces;

import javafx.scene.paint.Color;
import map.MapPiece;
import map.MapPiece.PieceState;

public class MessageBoard extends MapPiece {
	
	public MessageBoard() {
		this.symbol = "!";
		this.symbolColor = Color.BISQUE;
		this.pieceState = PieceState.SOLID;
	}
}
