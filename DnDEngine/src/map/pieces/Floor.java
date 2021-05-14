package map.pieces;

import java.util.HashMap;

import javafx.scene.paint.Color;
import map.MapPiece;
import map.MapPiece.PieceState;

public class Floor extends MapPiece {
	
	public Floor() {
		this.symbol = " ";
		this.symbolColor = Color.BLACK;
		this.description = new String[] {"Just a floor. Nothing to see"};
		this.pieceState = PieceState.HOLLOW;
		this.lootList = new HashMap<String, Integer>();
	}
	
	@Override
	public String toString() {
		if (player != null) {
			return "P";
		} else if (entity != null) {
			return String.valueOf(entity.getSymbol());
		} else if (!lootList.isEmpty()) {
			return ".";
		}
		return symbol;
	}
	
}
