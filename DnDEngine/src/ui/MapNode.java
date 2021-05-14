package ui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import map.MapPiece;

public class MapNode extends Text {
	
	private MapPiece piece;
	
	public MapNode(MapPiece piece) {
		this.piece = piece;
		this.setText(piece.toString());
		this.setFill(piece.getColor());
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	if (piece.distanceFromPlayer() > 3) {
	        		GUI.getGUI().println("I can't tell what that is...");
	        	} else {
	        		GUI.getGUI().println(piece.getDescription());
	        	}
	        }
	    });
	}

	public MapNode() {}

	
}
