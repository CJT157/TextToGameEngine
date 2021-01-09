package saveSystem;

import java.util.HashMap;

public class Game {
	private HashMap<String, Object> gameValues = new HashMap<String, Object>();
	
	public void addValue(String valueName, Object value) {
		gameValues.put(valueName, value);
	}
	
	public Object getValue(String valueName) {
		return gameValues.get(valueName);
	}
}
