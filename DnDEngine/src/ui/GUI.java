package ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import map.Map;
import map.MapPiece;
import map.MapReader;
import player.Entity;
import player.NPCReader;
import player.Player;
import res.ResourceLoader;
import textsystem.TalkingState;
import textsystem.TextSystem;

public class GUI extends Application {

	private static int screenWidth = 1350;
	private static int screenHeight = 800;
	private static GUI gui;
	private static Stage primaryStage;
	private static Scene title;
	private static Scene main;
	private static Scene battle;
	private static TextSystem ts = TextSystem.getTextSystem();
	private static Player player = Player.getPlayer();
	private static MapReader mapReader;
	private static NPCReader npcReader;
	private static HashMap<String, Map> maps = new HashMap<String, Map>();
	private static Map currentMap;
	private TextField tf;
	private static TextArea textOutput;
	private static TextArea npcConversation;
	private static Label map;
	private static TextArea inventory;
	private static GridPane column0;
	private static GridPane column1;
	private static GridPane column2;
	private static GridPane lookGrid;

	public static GUI getGUI() {
		if (gui == null) {
			gui = new GUI();
		}
		return gui;
	}

	public void launchGUI(String[] args) throws InterruptedException {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		Random r = new Random();

		// To add new splash texts, add a comma at the end and then put the sentence you
		// want surrounded by ""
		String[] splashTexts = { "Fancy!!!", "Better than before!",
				"Contrary to popular belief, it won't steal your kidneys!", "Thanks for reading this!",
				"More interactive than a paper, but somehow worse!" };
		String splashText = splashTexts[r.nextInt(splashTexts.length)];

		primaryStage.setTitle("TextRPGEngine: " + splashText);

		title = titleScene();
		title.getStylesheets().add("global_v1.css");
		
		battle = battleScene();
		battle.getStylesheets().add("global_v1.css");

		primaryStage.setScene(title);
		primaryStage.show();
	}

	public Scene titleScene() {
		GridPane grid = createGridPane(screenWidth, screenHeight);

		Label title = new Label("Text Adventure");
		grid.add(title, 0, 0);
		
		ComboBox<String> comboBox = new ComboBox<String>();
		
		File gamesFolder = ResourceLoader.getAllGames();
		for (File file : gamesFolder.listFiles()) {
		    if (file.isDirectory() && (!file.getName().equals(".DS_Store"))) {
		    	comboBox.getItems().add(file.getName());
		    }
		}
		
		grid.add(comboBox, 0, 1);

		Button button = new Button("Start Game");
		button.setOnAction(e -> {
			ResourceLoader.setGameFolder(comboBox.getValue());
			try {
				main = mainScene();
				main.getStylesheets().add("global_v1.css");
			} catch (IOException e1) {
			}
			GUI.primaryStage.setScene(main);
			});
		grid.add(button, 0, 2);

		return new Scene(grid);
	}
	
	public Scene mainScene() throws IOException {
		GridPane head = createGridPane(1350, 800);

		// load maps and set the start map
		npcReader = NPCReader.getInstance();
		mapReader = MapReader.getInstance();
		maps = mapReader.getMaps();
		// please don't rely on this
		for (String name : maps.keySet()) {
			if (maps.get(name).isPlayerHere()) {
				currentMap = maps.get(name);
			}
		}

		head.add(columnZero(), 0, 0);
		head.add(columnOne(), 1, 0);
		head.add(columnTwo(), 2, 0);

		updateInventory();
		updateMap();
		npcConversation.setText("You aren't talking to anyone.\n\n"
				+ "Walk up to someone and type 'talkto [direction]' to start a conversation!\n");

		println("Hello there! Welcome to my little project!\n\n"
				+ "To get started, we'll need to get familiarized with the basics,\n"
				+ "so walk up to a '!' using WASD controls and press a direction button to look at it.\n"
				+ "to use something(like an exit or switch) press the spacebar!\n"
				+ "Click off of the input field(the field below this text area) to begin moving around!\n\n"
				+ "Also, sorry for the hard to read text, the graphical library has some odd bugs with it.\n"
				+ "Honestly I would recommend throwing the text into a Word/Google doc so it's readable.\n");
		
		return new Scene(head);
	}

	public Scene battleScene() {
		BorderPane border = new BorderPane();
		border.setPrefSize(screenWidth, screenHeight);
		GridPane playerGrid = createGridPane(800, 400);
		GridPane enemyGrid = createGridPane(800, 400);
		
		//health bars
		Pane playerHealthPane = new Pane();
		ProgressBar playerHealthBar = new ProgressBar();
		playerHealthBar.setProgress(player.getHealth());
		playerHealthPane.getChildren().add(playerHealthBar);
		playerGrid.add(playerHealthPane, 0, 0);
		
		Pane enemyHealthPane = new Pane();
		ProgressBar enemyHealthBar = new ProgressBar();
		enemyHealthPane.getChildren().add(enemyHealthBar);
		enemyGrid.add(enemyHealthPane, 0, 0);
		
		//battle info
		
		
		border.setTop(enemyGrid);
		border.setBottom(playerGrid);
		return new Scene(border);
	}
	
	public GridPane columnZero() {
		column0 = createGridPane(400, 800);

		npcConversation = createTextArea(500, 574);
		column0.add(npcConversation, 0, 1);

		return column0;
	}

	public GridPane columnOne() {
		column1 = createGridPane(400, 800);

		map = createLabel(400, 400);
		map.setFont(Font.font("Monospace", FontWeight.BOLD, 16));
		map.setAlignment(Pos.CENTER);
		inventory = createTextArea(400, 200);

		column1.add(map, 0, 0);
		column1.add(inventory, 0, 1);

		return column1;
	}

	public GridPane columnTwo() {
		column2 = createGridPane(500, 800);

		textOutput = createTextArea(500, 574);
		column2.add(textOutput, 0, 0);

		tf = new TextField();
		tf.setPromptText("type here");
		column2.add(tf, 0, 1);
		tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				try {
					textInput(keyEvent);
				} catch (InterruptedException e) {

				}
				updateInventory();
				updateMap();
			}
		});

		// creating the look grid layout
		lookGrid = createGridPane(500, 50);
		lookGrid.add(interactButton("Left", "look"), 0, 1);
		lookGrid.add(interactButton("Right", "look"), 2, 1);
		lookGrid.add(interactButton("Up", "look"), 1, 0);
		lookGrid.add(interactButton("Down", "look"), 1, 2);
		lookGrid.add(interactButton("Under", "use"), 1, 1);

		column2.add(lookGrid, 0, 2);

		return column2;
	}

	public Button interactButton(String direction, String type) {
		Button interactButton = new Button(direction);
		if (type.equals("use")) {
			interactButton.setText("Use");
		}
		interactButton.setPrefSize(100, 100);
		interactButton.addEventFilter(KeyEvent.KEY_PRESSED, moveHandler());
		interactButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (type.equals("look")) {
					getCurrentMap().lookDirection(direction);
					updateMap();
				} else if (type.equals("use")) {
					getCurrentMap().useCommand(direction);
					updateMap();
				}
			}
		});

		return interactButton;
	}

	public EventHandler<KeyEvent> moveHandler() {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.A || event.getCode() == KeyCode.S
						|| event.getCode() == KeyCode.D) {
					getCurrentMap().movePlayer(event.getCode().getName());
				} else if (event.getCode() == KeyCode.SPACE) {
					getCurrentMap().useCommand("Under");
				} else if (event.getCode() == KeyCode.SHIFT) {
					try {
						ts.read("S");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else if (event.getCode() == KeyCode.E) {
					try {
						ts.read("t all");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				updateMap();
				event.consume();
			}
		};
	}

	public void textInput(KeyEvent e) throws InterruptedException {

		if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.ENTER) {
			String text = tf.getText().trim();
			println(text);
			ts.read(text);
			tf.clear();
		}
	}

	public GridPane createGridPane(int width, int height) {
		GridPane grid = new GridPane();
		grid.setPrefSize(width, height);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		return grid;
	}

	public TextArea createTextArea(int width, int height) {
		TextArea textArea = new TextArea();
		textArea.setPrefSize(width, height);
		textArea.setPrefColumnCount(15);
		textArea.setEditable(false);
		textArea.setFocusTraversable(false);
		textArea.setWrapText(true);
		textArea.addEventFilter(KeyEvent.KEY_PRESSED, moveHandler());
		textArea.setCache(false);
		return textArea;
	}

	public Label createLabel(int width, int height) {
		Label textArea = new Label();
		textArea.setPrefSize(width, height);
		textArea.setFocusTraversable(false);
		return textArea;
	}

	public void println(String text) {
		textOutput.appendText(text + "\n");
	}

	public void npcPrint(String text) {
		npcConversation.appendText(text + "\n");
	}

	public void npcClear() {
		npcConversation.clear();
	}

	public void resetGUI() {
		println("Exiting the game...");
		textOutput.clear();
		npcClear();
	}

	public void updateInventory() {
		inventory.setText("Inventory\n" + player.getInv().toString());
	}

	public void updateMap() {
		map.setText(currentMap.toString());
	}

	/**
	 * Returns the current map
	 * 
	 * @return Map object currently loaded
	 */
	public Map getCurrentMap() {
		return currentMap;
	}

	/**
	 * Method to update the GUI when the player exits a map.
	 * 
	 * @param mapName  : name saved within the maps folder
	 * @param exitCoor : coordinates of the corresponding exit piece on the new map
	 */
	public void setMap(String mapName, int[] exitCoor) {
		// Making sure the new map name is in the loaded maps hashmap
		if (maps.containsKey(mapName)) {
			currentMap.setPlayerHere(false);
			MapPiece oldPiece = currentMap.getPiece(player.getYCoor(), player.getXCoor());
			currentMap = maps.get(mapName);
			currentMap.setPlayerHere(true);

			MapPiece piece = currentMap.getPiece(exitCoor[0], exitCoor[1]);
			oldPiece.moveEntity(piece, "player");
		}

		updateMap();
	}

	public void setTextSystem(TalkingState state, Entity npc) {
		ts.setTalking(state, npc);
		if (state == TalkingState.COMMAND) {
			npcClear();
			npcConversation.setText("You aren't talking to anyone.\n\n"
					+ "Walk up to someone and type 'talkto [direction]' to start a conversation!\n");
		} else {
			npcClear();
		}
	}
}
