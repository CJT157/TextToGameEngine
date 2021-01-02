package map;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import map.MapPiece.PieceState;
import player.Entity;
import player.NPC;
import player.NPCReader;
import player.Player;
import player.PushPull;
import textsystem.TalkingState;
import ui.GUI;

public class Map {

	private GUI gui = GUI.getGUI();
	private MapPiece[][] layout;
	private MapPieceBuilder builder = new MapPieceBuilder();
	private int width;
	private int length;
	private boolean playerPlaced = false;
	private Player player = Player.getPlayer();
	private boolean playerIsHere = false;

	public Map(int length, int width) {
		this.length = length;
		this.width = width;
		layout = new MapPiece[width][length];
	}

	public void displayMap() {
		System.out.println("DISPLAY MAP UNFINISHED");
	}

	@Override
	public String toString() {
		String mapString = "";
		for (int i = width - 1; i >= 0; i--) {
			for (int j = 0; j < length; j++) {
				mapString += layout[i][j];
			}
			mapString += "\n";
		}
		return mapString;
	}

	public void setLayout(int y, int x, char piece) {
		this.layout[y][x] = builder.convertSymbol(piece, y, x);
	}

	public MapPiece divideTextFileInput(String line) {
		String coordinates[] = line.split(" ");
		int y = Integer.parseInt(coordinates[0]);
		int x = Integer.parseInt(coordinates[1]);
		return getPiece(y, x);
	}

	public void setLoot(String lootString) {
		String info[] = lootString.split(":", 2);
		divideTextFileInput(info[0]).setLoot(info[1]);
	}

	public void setMessage(String message) {
		String info[] = message.split(":", 2);
		divideTextFileInput(info[0]).setDescription(info[1]);
	}

	public void setLever(String coordinates) {
		String info[] = coordinates.split(":", 2);
		MapPiece piece = divideTextFileInput(info[0]);
		String[] leverInfo = info[1].split(",");
		ArrayList<MapPiece> leverWallList = new ArrayList<MapPiece>();

		for (String coor : leverInfo) {
			String xAndY[] = coor.split(" ");
			int y = Integer.parseInt(xAndY[0]);
			int x = Integer.parseInt(xAndY[1]);
			FakeWall fakeWall = new FakeWall();
			fakeWall.setSpace(y, x);
			this.layout[y][x] = fakeWall;
			leverWallList.add((FakeWall) getPiece(y, x));
		}
		piece.setToggleList(leverWallList);
	}

	public void setExits(String message) {
		String info[] = message.split(":", 2);
		Exit exit = (Exit) divideTextFileInput(info[0]);
		String[] exitInfo = info[1].split(",");

		if (!exitInfo[0].equals("none")) {
			String[] xAndY = exitInfo[1].split(" ");
			int y = Integer.parseInt(xAndY[0]);
			int x = Integer.parseInt(xAndY[1]);
			int[] exitCoor = { y, x };
			exit.setMapExitTo(exitInfo[0], exitCoor);
			this.layout[exit.getYCoor()][exit.getXCoor()] = exit;

			if (playerPlaced != true && exitInfo.length == 3) {
				getPiece(exit.getYCoor(), exit.getXCoor()).setPlayer(this.player);
				player.setSpace(exit.getYCoor(), exit.getXCoor());
				playerPlaced = true;
				playerIsHere = true;
			}
		} else {
			exit.setMapExitTo("none");
			if (playerPlaced != true && exitInfo.length == 2) {
				getPiece(exit.getYCoor(), exit.getXCoor()).setPlayer(this.player);
				player.setSpace(exit.getYCoor(), exit.getXCoor());
				playerPlaced = true;
				playerIsHere = true;
			}
			this.layout[exit.getYCoor()][exit.getXCoor()] = exit;
		}
	}

	public void setNPC(String message) throws IOException {
		String info[] = message.split(":", 2);
		String coordinates[] = info[0].split(" ");
		int y = Integer.parseInt(coordinates[0]);
		int x = Integer.parseInt(coordinates[1]);
		MapPiece piece = getPiece(y, x);

		NPC npc = NPCReader.getInstance().getNPC(info[1]);
		npc.setSpace(y, x);

		piece.setEntity(npc);
	}

	public MapPiece getPiece(int y, int x) {
		return this.layout[y][x];
	}

	public void lookDirection(String direction) {
		MapPiece piece = getPieceDirection(direction);

		for (String line : piece.getDescription()) {
			gui.println(line);
		}
	}

	public void useCommand(String direction) {
		getPieceDirection(direction).toggle();
	}

	public void movePlayer(String direction) {
		int playerX = player.getXCoor();
		int playerY = player.getYCoor();
		MapPiece piece = getPieceDirection(direction);

		if (piece.canMoveOnto()) {
			if (piece.getEntity() != null && piece.getEntity().getClass() == PushPull.class) {
				MapPiece pushPiece = null;
				if (direction.equals("Left") || direction.equals("West") || direction.equals("A")) {
					pushPiece = getPiece(piece.getYCoor(), piece.getXCoor() - 1);
				} else if (direction.equals("Up") || direction.equals("North") || direction.equals("W")) {
					pushPiece = getPiece(piece.getYCoor() + 1, piece.getXCoor());
				} else if (direction.equals("Right") || direction.equals("East") || direction.equals("D")) {
					pushPiece = getPiece(piece.getYCoor(), piece.getXCoor() + 1);
				} else if (direction.equals("Down") || direction.equals("South") || direction.equals("S")) {
					pushPiece = getPiece(piece.getYCoor() - 1, piece.getXCoor());
				}
				if (pushPiece.canMoveOnto()) {
					piece.moveEntity(pushPiece, "entity");
				}
			} else {
				getPiece(playerY, playerX).moveEntity(piece, "player");
				gui.setTextSystem(TalkingState.COMMAND, null);
			}
		}
	}

	public void talkToNPC(String direction) {
		MapPiece piece = getPieceDirection(direction);
		try {
			if (piece.getEntity().getClass() == NPC.class) {
				NPC npc = (NPC) piece.getEntity();

				gui.setTextSystem(TalkingState.TALK, npc);
				gui.npcPrint(npc.getGreeting());
			}
		} catch (NullPointerException e) {
			gui.println("There's nobody to talk to.");
		}
	}

	public MapPiece getPieceDirection(String direction) {
		int playerX = player.getXCoor();
		int playerY = player.getYCoor();

		MapPiece piece = null;
		if (direction.equals("Left") || direction.equals("West") || direction.equals("A")) {
			piece = getPiece(playerY, playerX - 1);
		} else if (direction.equals("Up") || direction.equals("North") || direction.equals("W")) {
			piece = getPiece(playerY + 1, playerX);
		} else if (direction.equals("Right") || direction.equals("East") || direction.equals("D")) {
			piece = getPiece(playerY, playerX + 1);
		} else if (direction.equals("Down") || direction.equals("South") || direction.equals("S")) {
			piece = getPiece(playerY - 1, playerX);
		} else if (direction.equals("Under")) {
			piece = getPiece(playerY, playerX);
		}

		return piece;
	}

	public MapPiece findPieceAround() {
		int playerX = player.getXCoor();
		int playerY = player.getYCoor();

		MapPiece piece = null;

		piece = getPiece(playerY, playerX - 1);
		piece = getPiece(playerY + 1, playerX);
		piece = getPiece(playerY, playerX + 1);
		piece = getPiece(playerY - 1, playerX);
		piece = getPiece(playerY, playerX);

		return piece;
	}

	public void setPlayerHere(boolean state) {
		this.playerIsHere = state;
	}

	public boolean isPlayerHere() {
		return this.playerIsHere;
	}
}
