package map;

import player.PushPull;

public class MapPieceBuilder {
	
	public MapPiece convertSymbol(char symbol, int y, int x) {
		if (symbol == '#') {
			Wall wall = new Wall();
			wall.setSpace(y, x);
			return wall;
		} else if (symbol == '!') {
			MessageBoard messageBoard = new MessageBoard();
			messageBoard.setSpace(y, x);
			return messageBoard;
		} else if (symbol == 'E') {
			Exit exit = new Exit();
			exit.setSpace(y, x);
			return exit;
		} else if (symbol == '/') {
			Lever lever = new Lever();
			lever.setSpace(y, x);
			return lever;
		} else if (symbol == ' ') {
			Floor floor = new Floor();
			floor.setSpace(y, x);
			return floor;
		} else if (symbol == '|') {
			Floor floor = new Floor();
			floor.setSpace(y, x);
			floor.setEntity(new PushPull());
			return floor;
		} else if (symbol == '_') {
			PressurePlate plate = new PressurePlate();
			plate.setSpace(y, x);
			return plate;
		}else {
			Wall notWall = new Wall(symbol);
			notWall.setSpace(y, x);
			return notWall;
		}
	}
}
