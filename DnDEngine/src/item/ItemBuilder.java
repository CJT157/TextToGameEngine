package item;

public class ItemBuilder {
	
	public Item getItem(String name) {
		switch (name) {
			case "Food":
				return new Food();
			case "Weapon":
				return new Weapon();
			case "Space-stone":
				return new InfinityStone("Space-stone");
			case "Mind-stone":
				return new InfinityStone("Mind-stone");
			case "Power-stone":
				return new InfinityStone("Power-stone");
			case "Reality-stone":
				return new InfinityStone("Reality-stone");
			case "Soul-stone":
				return new InfinityStone("Soul-stone");
			case "Time-stone":
				return new InfinityStone("Time-stone");
		}
		return null;
	}
}
