package map.pieces;

import javafx.scene.paint.Color;
import map.MapPiece;
import map.MapPiece.PieceState;
import ui.GUI;

public class Exit extends MapPiece {
	
	private String mapExitTo;
	private int[] mapExitToCoor;
	
	public Exit() {
		this.symbol = "E";
		this.symbolColor = Color.rgb(235, 134, 134);
		this.description = new String[] {"This is either the entrance or exit...", "depends on how you look at it."};
		this.pieceState = PieceState.HOLLOW;
	}
	
	public void setMapExitTo(String mapName, int[] mapExitToCoor) {
		this.mapExitTo = mapName + ".txt";
		this.mapExitToCoor = mapExitToCoor;
		this.description = new String[] {"This goes to " + mapName};
	}
	
	public void setMapExitTo(String mapName) {
		this.mapExitTo = mapName;
	}
	
	public void setMapExitTo(int[] mapExitToCoor) {
		this.mapExitToCoor = mapExitToCoor;
	}
	
	public String getMapExitName() {
		return this.mapExitTo;
	}
	
	public int[] getMapExitCoor() {
		return this.mapExitToCoor;
	}
	
	@Override
	public void toggle() {
		GUI.getGUI().setMap(mapExitTo, mapExitToCoor);
	}
}
