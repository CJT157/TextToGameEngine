package player;

public abstract class Character extends Entity {
	
	protected String name;
	protected int health;
	protected int damage;
	protected int defense;
	protected int speed;
	protected boolean isArmed;
	protected double money;
	protected Inventory inv = new Inventory();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double d) {
		this.money = d;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public Inventory getInv() {
		return this.inv;
	}
	
	public String toString() {
		return symbol + " " + name;
	}
}
