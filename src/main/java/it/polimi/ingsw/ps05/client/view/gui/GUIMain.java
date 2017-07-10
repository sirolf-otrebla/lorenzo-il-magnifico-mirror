package it.polimi.ingsw.ps05.client.view.gui;


import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Pair;


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIMain extends Application {


	private Stage stage;

	public static double stageWidth, stageHeight, resize;
	public static final int ORIGINAL_WIDTH = 1120;
	public static final double FAMILIAR_MIN_SIZE = 40, CARD_RATIO = 294 / 436;
	public static final String path = "./src/main/res/img/";
	public int PLAYER_NUMBER, OPPONENTS_NUMBER;
	private Integer MOVE_TIMER;
	private String[] opponentNames;
	private LeaderDraftPopup leaderDraftPopup = new LeaderDraftPopup();

	private PlayerWidget player;
	private OpponentWidget[] opponentsArray = new OpponentWidget[3];

    private HashMap<ColorEnumeration, OpponentWidget> opponentsHashMap = new HashMap<>();
	private HashMap<ColorEnumeration, String> usernamesHashMap;

	private FamiliarWidget[][] familiarWidgetLists = new FamiliarWidget[3][4];
	private LeaderWidget[] playerLeaderWidgetList = new LeaderWidget[4];
	private HBox playerResourcesBox;
	private ResourcesWidget[] resourcesWidgetArray = new ResourcesWidget[4];

	private ExcomWidget[] excomWidgets = new ExcomWidget[3]; // 1 per era

	private HashMap<ColorEnumeration, TowerCardWidget[]> towerCardWidgetLists = new HashMap<>();
	private HashMap<ColorEnumeration, VBox> towerCardBoxesArray = new HashMap<>() ;
	private HashMap<ColorEnumeration, TowerTileWidget[]> towerTileWidgetLists = new HashMap<>();
	private HashMap<ColorEnumeration, VBox> towerTilesBoxes = new HashMap<>();
	private MarketSpaceWidget[] marketSpaceWidgets = new MarketSpaceWidget[4];
	private SingleProductionSpaceWidget singleProductionSpace = new SingleProductionSpaceWidget(1, player);
	private SingleHarvestingSpaceWidget singleHarvestingSpace = new SingleHarvestingSpaceWidget(1, player);
	private ProductionSpaceWidget productionSpace = new ProductionSpaceWidget(1, player);
	private HarvestingSpaceWidget harvestingSpace = new HarvestingSpaceWidget(1, player);
	private CouncilSpaceWidget councilSpaceWidget = new CouncilSpaceWidget(1, player);

	private ArrayList<ActionSpaceWidgetInterface> actionSpaces = new ArrayList<>();

	private DieWidget[] diceWidgetArray = new DieWidget[3];
	private HashMap<ColorEnumeration, DieWidget> diceHashMap = new HashMap<>();
	private MarkerWidget[][] markerWidgetList = new MarkerWidget[4][4];
	private final Pane[] trackBoxesArray = new Pane[4];
	private ExcomWidget[] excomWidgetArray = new ExcomWidget[3];
	private TimerWidget timerWidget = new TimerWidget();
	private ImageView zoomedCard = new ImageView();
	static ImageView zoomReference;
	private Label infoLabel;

	private GraphicResources graphicMap = new GraphicResources();

	private Integer[] faithPath = new Integer[16];
	private Integer[] militaryPath = new Integer[6];
	private Integer[] greenCardsConversion = new Integer[6];
	private Integer[] blueCardsConversion = new Integer[6];

	public static final ColorEnumeration[] playerColorArray = {
			ColorEnumeration.Red,
			ColorEnumeration.Green,
			ColorEnumeration.Blue,
			ColorEnumeration.Yellow
	};

	public static final ColorEnumeration[] towerColorArray = {
			ColorEnumeration.Green,
			ColorEnumeration.Blue,
			ColorEnumeration.Yellow,
			ColorEnumeration.Violet
	};

    public static final ColorEnumeration[] diceColorArray = {
            ColorEnumeration.Black,
            ColorEnumeration.White,
            ColorEnumeration.Orange
    };




	public static void main(String[] args) {
		launch(args);
	}

	public void setInitValues(ColorEnumeration thisPlayerColor, Integer opponentNumber, Integer timeout, HashMap<ColorEnumeration, String> usernamesHashMap){

	    // creo il giocatore
        player = new PlayerWidget(this, thisPlayerColor);
	    this.player.setPlayerColor(thisPlayerColor);

	    // setto variabili globali
		this.OPPONENTS_NUMBER = opponentNumber;
		this.MOVE_TIMER = timeout;


		this.usernamesHashMap = usernamesHashMap;
		int i = 0;

        // popolo la hashmap degli avversari
        for (ColorEnumeration color: usernamesHashMap.keySet())
            opponentsHashMap.put(color, new OpponentWidget(this, color));

        // aggiungo il nome
        for (ColorEnumeration color: usernamesHashMap.keySet()) {
            opponentsHashMap.get(color).setOpponentUsername(usernamesHashMap.get(color));
        }

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//opponentNames = {"Pippo", "Pluto", "Paperino"};
		int thisPlayerId = 0;
		int[] opponentIdArray = {1, 2, 3};




		zoomReference = zoomedCard;

		/* Create root pane */
		final Pane root = new Pane();
		root.setId("root");

		stage = primaryStage;
		stage.setTitle("Lorenzo il Magnifico");
		stage.setResizable(false);

		/* Retrieving screen bounds */
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double screenWidth = primaryScreenBounds.getWidth();
		double screenHeight = primaryScreenBounds.getHeight();
		double screenRatio = screenWidth / screenHeight;

		/* Set up window size */
		if (screenRatio <= 1.75) {
			stage.setX(primaryScreenBounds.getMinX());
			stage.setWidth(primaryScreenBounds.getWidth());
			stage.setHeight(primaryScreenBounds.getWidth() / 1.75);
			stageWidth = primaryScreenBounds.getWidth();
			stageHeight = stageWidth / 1.75;
		} else {
			stage.setY(primaryScreenBounds.getMinY());
			stage.setHeight(primaryScreenBounds.getHeight());
			stage.setWidth(primaryScreenBounds.getHeight() * 1.75);
			stageHeight = primaryScreenBounds.getHeight();
			stageWidth = stageHeight * 1.75;
		}
		resize = stageWidth / ORIGINAL_WIDTH;

		/* Bind root pane size with window size */
		root.minWidthProperty().bind(stage.widthProperty());
		root.minHeightProperty().bind(stage.heightProperty());
		root.prefWidthProperty().bind(stage.widthProperty());
		root.prefHeightProperty().bind(stage.heightProperty());
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());

		/* Add tower cards and tower action spaces */

		VBox towerCardBox; // box verticale contenente le carte
		TowerCardWidget[] towerCardWidgetArray; // array di carte dello stesso colore

		VBox towerActionBox; // box verticale contenente gli spazi azione della stessa torre
		TowerTileWidget[] towerTileWidgetArray; // array di spazi azione della stessa torre


		for (int i = 0; i < 4; i++) towerCardBoxesArray.put(towerColorArray[i], new VBox());
		for (int i = 0; i < 4; i++) {
			towerCardBox = towerCardBoxesArray.get(towerColorArray[i]);
			System.out.println(towerColorArray[i].toString());
			towerCardBox.setSpacing((1.8 / 100) * stageHeight); //TODO spacing di prova, controllare
			towerCardBox.setLayoutX((0.8684 + 10.95 * i) / 100 * stageWidth);
			towerCardBox.setLayoutY((2.7468 / 100) * stageHeight);
			// towerBox.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.8)); //TODO controllare se crea problemi
			root.getChildren().add(towerCardBox);
		}

		for (int i = 0; i < 4; i++) towerTilesBoxes.put(towerColorArray[i], new VBox());
		for (int i = 0; i < 4; i++) {
			towerActionBox = towerTilesBoxes.get(towerColorArray[i]);
			towerActionBox.setSpacing((10.95 / 100) * stageHeight);
			towerActionBox.setLayoutX(((7.3214 + 10.95 * i) / 100) * stageWidth);
			towerActionBox.setLayoutY((7.1875 / 100) * stageHeight);
			root.getChildren().add(towerActionBox);
		}

		for (int i = 0; i < 4; i++) towerTileWidgetLists.put(towerColorArray[i], new TowerTileWidget[4]);
		for (int i = 0; i < 4; i++) {
			//towerTileWidgetArray = towerTileWidgetLists.get(towerColorArray[i]);
			for (int j = 0; j < 4; j++) {
				towerTileWidgetLists.get(towerColorArray[i])[j] = new TowerTileWidget(7 - 2 * j);
				actionSpaces.add(towerTileWidgetLists.get(towerColorArray[i])[j]);
				towerTilesBoxes.get(towerColorArray[i]).getChildren().add(towerTileWidgetLists.get(towerColorArray[i])[j].getOccupationCircle());
			}
		}

		for (int i = 0; i < 4; i++) towerCardWidgetLists.put(towerColorArray[i], new TowerCardWidget[4]);
		for (int i = 0; i < 4; i++) {
			//towerCardWidgetArray = towerCardWidgetLists.get(towerColorArray[i]);
			for (int j = 0; j < 4; j++) {
				towerCardWidgetLists.get(towerColorArray[i])[j] = new TowerCardWidget(); // creating empty cards
				towerCardBoxesArray.get(towerColorArray[i]).getChildren().add(towerCardWidgetLists.get(towerColorArray[i])[j]); // placing empty cards on towers
			}
		}


		/* Instantiate player and opponents widgets */

		player = new PlayerWidget(this, GraphicResources.getPlayerColor(thisPlayerId));

		int i = 0;
		for (i = 0; i < OPPONENTS_NUMBER; i++) {
			opponentsArray[i] = new OpponentWidget(this, GraphicResources.getPlayerColor(opponentIdArray[i]));
		}

		for (i = 0; i < OPPONENTS_NUMBER; i++) {
			System.out.println(opponentsArray[i].getOpponentColor().toString());
		}


		/* Add player resources */

		resourcesWidgetArray[0] = new ResourcesWidget();
		playerResourcesBox = resourcesWidgetArray[0].setupThisPlayerResource();
		root.getChildren().add(playerResourcesBox);


        /* Add market action spaces */
		// Initialize
		for (i = 0; i < this.marketSpaceWidgets.length; i++)
			marketSpaceWidgets[i] = new MarketSpaceWidget(1);
		// Add to board
		insertActionSpace(marketSpaceWidgets[0], root, 1, 28.3036, 80.1562); // gold
		actionSpaces.add(marketSpaceWidgets[0]);
		insertActionSpace(marketSpaceWidgets[1], root, 1, 33.125, 80.1562); // servants
		actionSpaces.add(marketSpaceWidgets[1]);
		if(PLAYER_NUMBER >= 4) {
			insertActionSpace(marketSpaceWidgets[2], root, 1, 37.7679, 82.6562); // military + gold
			actionSpaces.add(marketSpaceWidgets[2]);
			insertActionSpace(marketSpaceWidgets[3], root, 1, 41.25, 88.75); // privileges
			actionSpaces.add(marketSpaceWidgets[3]);
		}

        /* Add harvest and production action spaces */
        if(PLAYER_NUMBER >= 3) {
			insertMultipleSpace(productionSpace, root, 1, 0.892, 79.2); // production
			actionSpaces.add(productionSpace);
			insertMultipleSpace(harvestingSpace, root, 1, 0.892, 90.3137); // harvest
			actionSpaces.add(harvestingSpace);
		} else {
        	insertActionSpace(singleProductionSpace, root, 1, 0.9, 79.3); // single production
			actionSpaces.add(singleProductionSpace);
			insertActionSpace(singleHarvestingSpace, root, 1, 0.9, 90.4); // single harvest
			actionSpaces.add(singleHarvestingSpace);
		}

		/* Add council space */
		insertMultipleSpace(councilSpaceWidget, root, 1, 51.5, 53.7); // council
		actionSpaces.add(councilSpaceWidget);


		/* Add points markers */
		for (i = 0; i < 4; i++) {
			if(i < 1)
				this.trackBoxesArray[i] = new VBox(12 * resize);
			else
				this.trackBoxesArray[i] = new HBox(12 * resize);
			root.getChildren().add(this.trackBoxesArray[i]);
			for (int j = 0; j < PLAYER_NUMBER; j++) {
				ColorEnumeration markerColor = GraphicResources.getPlayerColor(j); // ottiene il colore del segnalino
				String thisPath = path + "markers/marker-" + markerColor.toString().toLowerCase() + ".png";
				System.out.println(thisPath);
				markerWidgetList[i][j] = new MarkerWidget(thisPath);
				if(player.getPlayerColor() == markerColor)
					player.getPlayerMarkers()[i] = markerWidgetList[i][j];
				else {
					for (int k = 0; k < OPPONENTS_NUMBER; k++)
						if(opponentsArray[k].getOpponentColor() == markerColor)
							opponentsArray[k].getOpponentMarkers()[i] = markerWidgetList[i][j];
				}
				this.trackBoxesArray[i].getChildren().add(markerWidgetList[i][j].getMarker());
			}
		}

		/****** MODO 1 ******/
		// Play-order markers
		this.trackBoxesArray[0].setLayoutX((96.0 / 100) * stageWidth);
		this.trackBoxesArray[0].setLayoutY((63.0 / 100) * stageHeight);
		//this.trackBoxesArray[0].setMaxHeight((0.2 / 100) * stageHeight);
		// Faith markers
		this.trackBoxesArray[1].setLayoutX((50.0857 / 100) * stageWidth);
		this.trackBoxesArray[1].setLayoutY((66.3375 / 100) * stageHeight);
		// Military markers
		this.trackBoxesArray[2].setLayoutX((74.7664 / 100) * stageWidth);
		this.trackBoxesArray[2].setLayoutY((65.0 / 100) * stageHeight);
		// Victory markers
		this.trackBoxesArray[3].setLayoutX((74.7664 / 100) * stageWidth);
		this.trackBoxesArray[3].setLayoutY((77.0 / 100) * stageHeight);


		/* Add playable familiars */
		this.player.addFamiliarsToBoard(root);

        /* Initialize opponents familiars */
		for(ColorEnumeration color: opponentsHashMap.keySet()) {
		    for(i = 0; i < 4; i++) {
		        opponentsHashMap.get(color).getFamiliarWidgetList()[i] = new FamiliarWidget(color, GraphicResources.getFamiliarColor(i));
            }
        }


		/* Add player buttons */

		// Personal cards

		player.commands.setAlignment(Pos.CENTER);
		player.showCardsButton.setId("showCardsButton");
		player.showCardsButton.setMaxWidth(Double.MAX_VALUE);
		player.showCardsButton.setOnAction((ActionEvent e) -> {
			player.getPersonalBoard().display();
		});
		player.showLeaderButton.setId("showLeaderButton");
		player.showLeaderButton.setMaxWidth(Double.MAX_VALUE);
		player.showLeaderButton.setOnAction((ActionEvent e) -> {
			player.getLeaderPopup().display();
		});
        /****** MODO 1 ******/
        player.commands.setLayoutX((68.0 / 100) * stageWidth);
        player.commands.setLayoutY((6.2 / 100) * stageHeight);
		player.commands.getChildren().addAll(player.showCardsButton, player.showLeaderButton);
		root.getChildren().add(player.commands);

		// Other players boards
		final VBox personalBoardButtonsBox = new VBox(20 * resize);
		personalBoardButtonsBox.setAlignment(Pos.TOP_RIGHT);
		personalBoardButtonsBox.setLayoutX((87.0 / 100) * stageWidth);
		personalBoardButtonsBox.setLayoutY((6.2 / 100) * stageHeight);

		// set up buttons that open other players personal boards
        for(ColorEnumeration opponentColor: opponentsHashMap.keySet()) {
            setButtonClickGesture(opponentsHashMap.get(opponentColor).personalBoardButton, opponentsHashMap.get(opponentColor));
            personalBoardButtonsBox.getChildren().add(opponentsHashMap.get(opponentColor).personalBoardButton);
        }
		root.getChildren().add(personalBoardButtonsBox);


		/* Add excommunication widgets */
		for(i = 0; i < 3; i++) {
			excomWidgetArray[i] = new ExcomWidget();
			excomWidgetArray[i].setLayoutX(((50.2 + 4.4 * i) / 100) * stageWidth);
			root.getChildren().add(excomWidgetArray[i]);
		}
		excomWidgetArray[0].setLayoutY((84.4 / 100) * stageHeight);
		excomWidgetArray[1].setLayoutY((85.9 / 100) * stageHeight);
		excomWidgetArray[2].setLayoutY((84.4 / 100) * stageHeight);


		/* Add dice */
		final HBox diceBox = new HBox(34.5 * resize);
		diceBox.setLayoutX((26.3 / 100) * stageWidth);
		diceBox.setLayoutY((91.0 / 100) * stageHeight);
		diceBox.setPrefHeight((4.8 / 100) * stageHeight);

		// poplo la hashmap
		for(ColorEnumeration dieColor: diceColorArray)
		    diceHashMap.put(dieColor, new DieWidget(dieColor));

		for (ColorEnumeration dieColor: diceHashMap.keySet()) {
			diceBox.getChildren().add(diceHashMap.get(dieColor));
		}
		root.getChildren().add(diceBox);


		/* Add timer */
		timerWidget.setLayoutX((90.0 / 100) * stageWidth);
		timerWidget.setLayoutY((95.0 / 100) * stageHeight);
		timerWidget.setMaxHeight((3.0 / 100) * stageHeight);
		root.getChildren().add(timerWidget);


		/* Add card zoom */
		zoomedCard.setX((46.5 / 100) * stageWidth);
		zoomedCard.setY((4.9 / 100) * stageHeight);
		zoomedCard.setFitHeight((40.0 / 100) * stageHeight);
		zoomedCard.setPreserveRatio(true);
		root.getChildren().add(zoomedCard);


		/* Text info for the player */
		infoLabel = new Label();
		infoLabel.setId("infolabel");
		infoLabel.setLayoutX((72.0 / 100) * stageWidth);
		infoLabel.setLayoutY((95.0 / 100) * stageHeight);
		root.getChildren().add(infoLabel);


		/* Event filter */
		root.addEventFilter(MouseEvent.DRAG_DETECTED, mouseEvent -> {

			System.out.println("mouse drag detected! " + mouseEvent.getSource());

		});









		// TEST CARICAMENTO CARTE
		int[][] array = {{ 3,  6,  8,  9},
				         {50, 51, 52, 56},
				         {25, 27, 28, 30},
						 {80, 82, 84, 90}};
		insertCards(array[0], array[1], array[2], array[3]);

		Integer[] testLeaderIdArray = {2,8,10,15};

		Integer[] testDiceValues = {3, 5, 6};

		int testPrivilegeConversion = 2;

		Integer[] testExcomCards = {3, 12, 20};

		Pair<ColorEnumeration, Integer> test1 = new Pair<>(ColorEnumeration.Red, 67);
		Pair<ColorEnumeration, Integer> test2 = new Pair<>(ColorEnumeration.Green, 30);
		Pair<ColorEnumeration, Integer> test3 = new Pair<>(ColorEnumeration.Blue, 87);
		Pair<ColorEnumeration, Integer> test4 = new Pair<>(ColorEnumeration.Yellow, 8);

		ArrayList<Pair<ColorEnumeration, Integer>> testFinalResults = new ArrayList<>();
		testFinalResults.add(test1);
		testFinalResults.add(test2);
		testFinalResults.add(test3);
		testFinalResults.add(test4);







		/*
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());
		*/

		Scene mainScene = new Scene(root);


		File f = new File("./src/main/res/fx-style.css");
		mainScene.getStylesheets().add(f.toURI().toURL().toString());

		stage.setScene(mainScene);
		stage.sizeToScene();
		stage.show();

		//startBonusTileDraft();
		startLeaderDraft(testLeaderIdArray);
		//endLeaderDraft();
		//setDiceValues(testDiceValues);
		//timerWidget.setupTimer();
		//timerWidget.startTimer();
		//doPrivilegeConversion(testPrivilegeConversion);
		insertExcomCards(testExcomCards);
		updateInfoLabel("Buonaseeeera, tutto bbene? Enniende");
		//showEndGameResult(testFinalResults);


	}




	/**************************/
	/**   Metodi per albe    **/


	public void startBonusTileDraft() {
		// Show bonus tile draft window
		BonusTileDraftPopup.display();
	}

	public void updateBonusTileDraft(ColorEnumeration playerColor, Integer bonusTileId) {
        opponentsHashMap.get(playerColor).getPersonalBoard().setBonusTileWidget(new BonusTileWidget(bonusTileId));
    }

	public void endBonusTileDraft() {
		// Close draft window
		BonusTileDraftPopup.getPopupStage().close();

		// Update player bonus tile
		this.player.getPersonalBoard().setBonusTileWidget(BonusTileDraftPopup.getSelectedBonusTile());
	}

	public void startLeaderDraft(Integer[] referenceIdArray) {
		// Show leader draft window
		leaderDraftPopup.display(referenceIdArray);
	}

	public void endLeaderDraft(HashMap<ColorEnumeration, Integer[]> leadersDraftedIdArray) {
		updateLeadersAfterDraft(leadersDraftedIdArray);
		leaderDraftPopup.getPopup().close();
	}

	private void updateLeadersAfterDraft(HashMap<ColorEnumeration, Integer[]> leadersDraftedIdArray) {
		for(int i = 0; i < 4; i++) {
			this.player.getLeaderWidgetList()[i] = leaderDraftPopup.leadersDrafted[i];
		}

		for(ColorEnumeration opponentColor: leadersDraftedIdArray.keySet())
		    for(int i = 0; i < 4; i++)
		        opponentsHashMap.get(opponentColor).getPersonalBoard().getLeaderWidgets()[i] =
                        new LeaderWidget(leadersDraftedIdArray.get(opponentColor)[i]);

	}

	// aggiorna i leader attivabili dal giocatore
	public void updateActivableLeaders(Integer[] activableLeaderIdArray) {
		for(Integer activableLeaderId: activableLeaderIdArray) {
			for(LeaderWidget playerLeader: this.player.getLeaderWidgetList()) {
				if(playerLeader.getReferenceID() == activableLeaderId)
					playerLeader.setActivable(true);
				else
					playerLeader.setActivable(false);
			}
		}
	}

	public void insertCards(int[] greenCardIdArray, int[] blueCardIdArray, int[] yellowCardIdArray, int[] violetCardIdArray) {

		for(int i = 0; i < 4; i++) {
			towerCardWidgetLists.get(ColorEnumeration.Green)[i].addCardImage(greenCardIdArray[i]);
			towerTileWidgetLists.get(ColorEnumeration.Green)[i].setAssociatedCard(towerCardWidgetLists.get(ColorEnumeration.Green)[i]);
		}
		for(int i = 0; i < 4; i++) {
			towerCardWidgetLists.get(ColorEnumeration.Blue)[i].addCardImage(blueCardIdArray[i]);
			towerTileWidgetLists.get(ColorEnumeration.Blue)[i].setAssociatedCard(towerCardWidgetLists.get(ColorEnumeration.Blue)[i]);
		}
		for(int i = 0; i < 4; i++) {
			towerCardWidgetLists.get(ColorEnumeration.Yellow)[i].addCardImage(yellowCardIdArray[i]);
			towerTileWidgetLists.get(ColorEnumeration.Yellow)[i].setAssociatedCard(towerCardWidgetLists.get(ColorEnumeration.Yellow)[i]);
		}
		for(int i = 0; i < 4; i++) {
			towerCardWidgetLists.get(ColorEnumeration.Violet)[i].addCardImage(violetCardIdArray[i]);
			towerTileWidgetLists.get(ColorEnumeration.Violet)[i].setAssociatedCard(towerCardWidgetLists.get(ColorEnumeration.Violet)[i]);
		}

		/*
			TowerCardWidget newCard = new TowerCardWidget(referenceIdsArray[i][j]);
			int module = referenceIdsArray[i][j] % 24;
			towerCardBoxesArray[i].getChildren().add(newCard.getCardImage());
		}*/
	}

	public void updatePlayerResources(HashMap<String, Integer> thisPlayerResources, HashMap<ColorEnumeration, HashMap<String, Integer>> otherPlayerResources) {

		//TODO decidere come passare le risorse

		for(String newResource: thisPlayerResources.keySet()) {
			for(int i = 0; i < 4; i++) {
				player.getResourceWidget().setResource(newResource, thisPlayerResources.get(newResource));
			}
		}

		player.getResourceWidget().repaint();


		for(ColorEnumeration opponentColor: otherPlayerResources.keySet()) {
			for(OpponentWidget opponent: opponentsArray) {
				if(opponent.getOpponentColor() == opponentColor) {
					for (String id : otherPlayerResources.get(opponentColor).keySet()) {
						opponent.getPersonalBoard().getResourceWidget().setResource(id, otherPlayerResources.get(opponentColor).get(id));
					}
				}
			}
		}
	}



	public void updateFamiliarOnBoard(ColorEnumeration playerColor, ColorEnumeration familiarColor, ActionSpaceWidgetInterface actionSpaceWidgetInterface) {

		// get the familiar image

		String path = GraphicResources.getFamiliarPath(playerColor, familiarColor);
		File crDir = new File(path);
		try{
			Image familiarPlayedImage = new Image(crDir.toURI().toURL().toString(), FAMILIAR_MIN_SIZE * resize, FAMILIAR_MIN_SIZE * resize, true, true);
			ImageView familiarPlayed = new ImageView(familiarPlayedImage);
		} catch (MalformedURLException e){
			e.printStackTrace();
		}

		// add to the board

	}

	public void updatePlayerPoints(ColorEnumeration color, Integer[] newPoints) {
		if(player.getPlayerColor() == color)
			for(int i = 0; i < 3; i++) {
				Text pointsToUpdate = player.getPlayerMarkers()[i + 1].getPoints();
				pointsToUpdate.setText(newPoints[i].toString());
			}
		else
			for(int i = 0; i < OPPONENTS_NUMBER; i++) {
				if (opponentsArray[i].getOpponentColor() == color) {
					Text pointsToUpdate = opponentsArray[i].getOpponentMarkers()[i + 1].getPoints();
					pointsToUpdate.setText(newPoints.toString());
				}
			}
	}

	public void setPlayerActive() {
		this.getPlayer().setActive(true);
	}

	public void setPlayerInactive() {
		this.getPlayer().setActive(false);
	}

	public void setDiceValues(Integer[] newValues) {
		for (int i = 0; i < 3; i++) {
			this.diceWidgetArray[i].setValue(newValues[i]);
			this.diceWidgetArray[i].repaint();
		}
	}

	public ArrayList<Integer> doPrivilegeConversion(int privilegesToConvert) {
		return PrivilegePopup.display(privilegesToConvert);
	}

	public void insertExcomCards(Integer[] excomIdArray) { //TODO non mostra le scomuniche
		for(int i = 0; i < 3; i++) {
			try {
				excomWidgetArray[i].addExcom(excomIdArray[i]);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateInfoLabel(String info) {
		this.infoLabel.setText(info);
	}

	public void showEndGameResult(HashMap<ColorEnumeration, Integer> endGameResults) {
		EndGamePopup.display(endGameResults);
	}


	/**                       **/
	/***************************/




	void setButtonClickGesture(Button button, OpponentWidget opponentWidget) {
		button.setOnAction((ActionEvent e) -> {
			opponentWidget.getPersonalBoard().display();
		});
	}

	private void insertActionSpace(SingleOccupantActionSpaceWidget actionSpace, Pane parent, int minDie, double percX, double percY) {

		actionSpace.setMinDie(minDie);

		actionSpace.getOccupationCircle().setRadius(20 * resize);
		actionSpace.getOccupationCircle().setCenterX((percX / 100) * stageWidth);
		actionSpace.getOccupationCircle().setCenterY((percY / 100) * stageHeight);
		actionSpace.getOccupationCircle().setFill(Color.TRANSPARENT);

		actionSpace.setupGestureTarget();

		parent.getChildren().add(actionSpace.getOccupationCircle());

	}

	private void insertMultipleSpace(MultipleSpaceWidget multipleSpace, Pane parent, int minDie, double percX, double percY) {

		multipleSpace.setMinDie(minDie);

		multipleSpace.getScrollPane().setLayoutX((percX / 100) * stageWidth);
		multipleSpace.getScrollPane().setLayoutY((percY / 100) * stageHeight);

		parent.getChildren().add(multipleSpace.getScrollPane());
	}

	void insertMarker(MarkerWidget marker, Pane box) {

		box.getChildren().add(marker.getMarker());

	}

	void setupGestureTarget(Circle target) {

		target.setOnDragOver((DragEvent e) -> {

            /* source is dragged over the target */

			if (e.getGestureSource() != target && e.getDragboard().hasImage()) {
				e.acceptTransferModes(TransferMode.MOVE);
			}

			//e.consume();
		});

		target.setOnDragEntered((DragEvent e) -> {
			if (e.getGestureSource() != target && e.getDragboard().hasImage()) {
				target.setOpacity(0.4);
				target.setFill(Color.FORESTGREEN);
			}

			//e.consume();
		});

		target.setOnDragExited((DragEvent e) -> {

			target.setFill(Color.TRANSPARENT);
			//e.consume();

		});

		target.setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
			boolean success = false;

			System.out.println("starting if");
			if (e.getDragboard().hasImage()) {
				System.out.println("inside if");
				Image source = e.getDragboard().getImage();
				target.setOpacity(1);
				target.setFill(Color.ALICEBLUE/*new ImagePattern(source)*/);
				success = true;
			}

			e.setDropCompleted(success);

			//e.consume();
		});
	}

	void showAllowedActionSpaces() {

	}

	public TowerTileWidget[] getTowerTileWidgetList(ColorEnumeration towerColor) {
		return towerTileWidgetLists.get(towerColor);
	}

	public FamiliarWidget[][] getFamiliarWidgetLists() {
		return familiarWidgetLists;
	}

	public MarketSpaceWidget[] getMarketSpaceWidgets() {
		return marketSpaceWidgets;
	}

	public MarkerWidget[][] getMarkerWidgetList() {
		return markerWidgetList;
	}

	public ProductionSpaceWidget getProductionSpace() {
		return productionSpace;
	}

	public HarvestingSpaceWidget getHarvestingSpace() {
		return harvestingSpace;
	}

	public CouncilSpaceWidget getCouncilSpaceWidget() {
		return councilSpaceWidget;
	}

	public Integer[] getFaithPath() {
		return faithPath;
	}

	public void setFaithPath(Integer[] faithPath) {
		this.faithPath = faithPath;
	}

	public Integer[] getMilitaryPath() {
		return militaryPath;
	}

	public void setMilitaryPath(Integer[] militaryPath) {
		this.militaryPath = militaryPath;
	}

	public Integer[] getGreenCardsConversion() {
		return greenCardsConversion;
	}

	public void setGreenCardsConversion(Integer[] greenCardsConversion) {
		this.greenCardsConversion = greenCardsConversion;
	}

	public Integer[] getBlueCardsConversion() {
		return blueCardsConversion;
	}

	public void setBlueCardsConversion(Integer[] blueCardsConversion) {
		this.blueCardsConversion = blueCardsConversion;
	}

	public ExcomWidget[] getExcomWidgets() {
		return excomWidgets;
	}

	public void setExcomWidgets(ExcomWidget[] excomWidgets) {
		this.excomWidgets = excomWidgets;
	}

	public PlayerWidget getPlayer() {
		return player;
	}

	public OpponentWidget[] getOpponentsArray() {
		return opponentsArray;
	}


	public ColorEnumeration[] getTowerColorArray() {
		return towerColorArray;
	}
}




























/* Add tower cards and tower action spaces */
		/*

		for (i = 0; i < 4; i++) {
			// VBoxes of action spaces
			towerTilesBoxes[i] = new VBox((10.95 / 100) * stageHeight);
			towerTilesBoxes[i].setLayoutX(((7.3214 + 10.95 * i) / 100) * stageWidth);
			towerTilesBoxes[i].setLayoutY((7.1875 / 100) * stageHeight);
			root.getChildren().add(towerTilesBoxes[i]);

			// VBoxes of tower cards
			towerCardBoxesArray[i] = new VBox((40 / 100) * stageHeight); //TODO spacing di prova, controllare
			towerCardBoxesArray[i].setLayoutX((0.8684 + 10.9 * i) / 100 * stageWidth);
			towerCardBoxesArray[i].setLayoutY((1.4168 / 100) * stageHeight);
			towerCardBoxesArray[i].prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.8));
			root.getChildren().add(towerCardBoxesArray[i]);
			for (int j = 0; j < 4; j++) {
				// action spaces
				towerTileWidgetLists[i][j] = new TowerTileWidget(7 - 2 * j);
				towerTilesBoxes[i].getChildren().add(towerTileWidgetLists[i][j].getOccupationCircle());

				// tower cards
				towerCardWidgetLists[i][j] = new TowerCardWidget(); // creating empty cards
				towerCardBoxesArray[i].getChildren().add(towerCardWidgetLists[i][j].getCardImage()); // placing empty cards on towers
			}
		}

		*/



/* Add buttons for other player board */

/*
		for (Button button : otherPlayersButtons) {
			button = new Button(); //TODO inserire i nomi dei giocatori
			button.setMaxWidth(Double.MAX_VALUE);
			setButtonClickGesture(button, opponentsArray[i]); // setting buttons actions on click (open relative personal board)
			personalBoardButtonsBox.getChildren().add(button);
			i++;
		}
		*/



/* Add points markers */
        /*
		i = 0;
		for (MarkerWidget[] track : this.markerWidgetList) {
			int j = 0;
			if (i < 2) {
				this.trackBoxesArray[i] = new HBox(12 * resize);
			} else {
				this.trackBoxesArray[i] = new VBox(12 * resize);
			}
			root.getChildren().add(this.trackBoxesArray[i]);
			for (MarkerWidget marker : track) {
				ColorEnumeration markerColor = GraphicResources.getPlayerColor(j);
				String thisPath = path + "marker-" + markerColor.toString().toLowerCase() + ".png";
				marker = new MarkerWidget(thisPath);
				if (player.getPlayerColor() == markerColor)
					player.getPlayerMarkers()[i] = marker;
				else {
					for (OpponentWidget opponent : opponentsArray)
						if (opponent.getOpponentColor() == markerColor)
							opponent.getOpponentMarkers()[i] = marker;
				}
				this.trackBoxesArray[i].getChildren().add(marker.getMarker());
				j++;
			}
			i++;
		}
		*/

	/*
		insertActionSpace(greenTowerSpace7, root, 7, 102, 66);
		insertActionSpace(greenTowerSpace5, root, 5, 102, 177);
		insertActionSpace(greenTowerSpace3, root, 3, 102, 287);
		insertActionSpace(greenTowerSpace1, root, 1, 102, 398);

		insertActionSpace(blueTowerSpace7, root, 7, 224, 66);
		insertActionSpace(blueTowerSpace5, root, 5, 224, 177);
		insertActionSpace(blueTowerSpace3, root, 3, 224, 287);
		insertActionSpace(blueTowerSpace1, root, 1, 224, 398);

		insertActionSpace(yellowTowerSpace7, root, 7, 347, 66);
		insertActionSpace(yellowTowerSpace5, root, 5, 347, 177);
		insertActionSpace(yellowTowerSpace3, root, 3, 347, 287);
		insertActionSpace(yellowTowerSpace1, root, 1, 347, 398);

		insertActionSpace(violetTowerSpace7, root, 7, 469, 66);
		insertActionSpace(violetTowerSpace5, root, 5, 469, 177);
		insertActionSpace(violetTowerSpace3, root, 3, 469, 287);
		insertActionSpace(violetTowerSpace1, root, 1, 469, 398);

		militaryBox.setLayoutX(537);
		militaryBox.setLayoutY(98);

		victoryBox.setLayoutX(675);
		victoryBox.setLayoutY(98);

		faithBox.setLayoutX(580);
		faithBox.setLayoutY(438);
		*/

	//TODO aggiungere binding
		/*
		towerTilesBoxes[0].setLayoutX(stageWidth * 0.091071); // green tower
		towerTilesBoxes[1].setLayoutX(stageWidth * 0.2); // blue tower
		towerTilesBoxes[2].setLayoutX(stageWidth * 0.309821); // yellow tower
		towerTilesBoxes[3].setLayoutX(stageWidth * 0.41875); // violet tower
		*/

/****** MARKERS MODO 2 (binding) ******/
	// Faith markers
	//this.trackBoxesArray[1].layoutXProperty().bind(stage.widthProperty().multiply(51.7857 / 100));
	//this.trackBoxesArray[1].layoutYProperty().bind(stage.heightProperty().multiply(68.4375 / 100));
	// Military markers
	//this.trackBoxesArray[2].layoutXProperty().bind(stage.widthProperty().multiply(47.9464 / 100));
	//this.trackBoxesArray[2].layoutYProperty().bind(stage.heightProperty().multiply(15.3125 / 100));
	// Victory markers
	//this.trackBoxesArray[3].layoutXProperty().bind(stage.widthProperty().multiply(60.2679 / 100));
	//this.trackBoxesArray[3].layoutYProperty().bind(stage.heightProperty().multiply(15.3125 / 100));

	/****** BUTTONS MODO 2 (meglio) ******/
	//commands.layoutXProperty().bind(stage.widthProperty().multiply(68.75 / 100));
	//commands.layoutYProperty().bind(stage.heightProperty().multiply(9.375 / 100));


