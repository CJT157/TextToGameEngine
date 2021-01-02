package map;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Color;
import player.Entity;
import player.Player;
import player.PushPull;

public abstract class MapPiece {
	
	protected int xCoor;
	protected int yCoor;
	protected String symbol;
	protected Color symbolColor;
	protected String[] description;
	protected Player player;
	protected Entity entity;
	protected PieceState pieceState;
	protected HashMap<String, Integer> lootList;
	
	public enum PieceState {
		SOLID, HOLLOW, PRESSURE
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	public void setDescription(String description) {
		this.description = description.split("`");
	}
	
	public String[] getDescription() {
		if (this.entity != null) {
			ArrayList<String> desc = entity.getDescription();
	        String str[] = new String[desc.size()]; 
	  
	        for (int j = 0; j < desc.size(); j++) { 
	            str[j] = desc.get(j); 
	        } 
	  
	        return str;
		}
		return this.description;
	}
	
	public int getXCoor() {
		return this.xCoor;
	}
	
	public int getYCoor() {
		return this.yCoor;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public PieceState getState() {
		return this.pieceState;
	}

	@Override
	public String toString() {
		if (player != null) {
			return "P";
		} else if (entity != null) {
			return String.valueOf(entity.getSymbol());
		}
		return symbol;
	}
	
	public boolean canMoveOnto() {
		if (pieceState == PieceState.SOLID) {
			return false;
		} else if (entity != null) {
			if (entity.getClass() == PushPull.class) {
				return true;
			}
			return true;
		}
		return true;
	}
	
	public void moveEntity(MapPiece movePiece, String object) {
		if (object.equals("entity")) {
			movePiece.setEntity(this.entity);
			this.entity.setSpace(movePiece.getYCoor(), movePiece.getXCoor());
			this.setEntity(null);
		} else if (object.equals("player")) {
			movePiece.setPlayer(this.player);
			this.player.setSpace(movePiece.getYCoor(), movePiece.getXCoor());
			this.setPlayer(null);
		}
		
		if (movePiece.getState() == PieceState.PRESSURE) {
			movePiece.toggle();
		}
		if (this.getState() == PieceState.PRESSURE) {
			this.toggle();
		}
	}
	
	public void setToggleList(ArrayList<MapPiece> list) {
	}
	
	public void toggle() {}
	
	public void setSpace(int y, int x) {
		xCoor = x;
		yCoor = y;
	}
	
	public void getSpace() {
		System.out.println(this.yCoor + " " + this.xCoor);
	}
	
	public void setLoot(String lootString) {
		String loot[] = lootString.split(",");
		for (String item : loot) {
			String itemInfo[] = item.split(" ");
			lootList.put(itemInfo[0], Integer.parseInt(itemInfo[1]));
		}
	}
	
	public HashMap<String, Integer> getLoot() {
		if (this.lootList != null) {
			return this.lootList;
		}
		return null;
	}
}
