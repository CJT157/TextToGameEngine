package player;

import java.util.ArrayList;
import java.util.HashMap;

public class Entity {

	protected ArrayList<String> description = new ArrayList<String>();
	protected boolean canFight = false;
	protected String symbol;
	protected int xCoor;
	protected int yCoor;
	
	public void setSpace(int y, int x) {
		xCoor = x;
		yCoor = y;
	}
	
	public boolean getCanFight() {
		return canFight;
	}
	
	public void setCanFight(boolean canFight) {
		this.canFight = canFight;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void setXCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getXCoor() {
		return this.xCoor;
	}
	
	public void setYCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	public int getYCoor() {
		return this.yCoor;
	}
	
	public ArrayList<String> getDescription() {
		return this.description;
	}
	
}
