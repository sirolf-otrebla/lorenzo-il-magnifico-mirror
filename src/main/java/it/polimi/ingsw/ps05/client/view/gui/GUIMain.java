package it.polimi.ingsw.ps05.client.view.gui;


import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;


import java.io.File;

public class GUIMain extends Application {


	private Stage stage;

	public static double stageWidth, stageHeight, resize;
	public static final int ORIGINAL_WIDTH = 1120;
	public static final double CARD_RATIO = 294 / 436;
	public static final String path = "./src/main/res/img/";

	private PlayerWidget player;
	private OpponentWidget[] opponentsArray = new OpponentWidget[3];

	private FamiliarWidget[][] familiarWidgetLists = new FamiliarWidget[3][4];
	private LeaderWidget[] playerLeaderWidgetList = new LeaderWidget[4];
	private HBox playerResourcesBox;
	private ResourcesWidget[] resourcesWidgetArray = new ResourcesWidget[4];

	private Integer[] faithPath = new Integer[16];
	private Integer[] militaryPath = new Integer[6];
	private Integer[] greenCardsConversion = new Integer[6];
	private Integer[] blueCardsConversion = new Integer[6];
	private ExcomWidget[] excomWidgets = new ExcomWidget[3]; // 1 per era

	private ColorEnumeration[] playerColorArray = {
			ColorEnumeration.Red,
			ColorEnumeration.Green,
			ColorEnumeration.Blue,
			ColorEnumeration.Yellow
	};

	private TowerTileWidget[][] towerTileWidgetLists = new TowerTileWidget[4][4];
	private CardOnBoardWidget[][] cardOnBoardWidgetLists = new CardOnBoardWidget[4][4];
	private final VBox[] towerOccupationCirclesBoxes = new VBox[4];
	private final VBox[] towerCardSpacesArray = new VBox[4];
	private MarketSpaceWidget[] marketSpaceWidgets = new MarketSpaceWidget[4];
	private CouncilSpaceWidget councilSpaceWidget = new CouncilSpaceWidget(1);

	private ProductionSpaceWidget productionSpace = new ProductionSpaceWidget(1);
	private HarvestingSpaceWidget harvestingSpace = new HarvestingSpaceWidget(1);
	private CouncilSpaceWidget councilSpace = new CouncilSpaceWidget(1);

	// private PersonalBoardWindow playerPersonalBoardWindow = new PersonalBoardWindow(this);
	// private PersonalBoardWindow[] personalBoardWindowArray = new PersonalBoardWindow[3];

	private MarkerWidget[][] markerWidgetList = new MarkerWidget[4][4];
	private final Pane[] trackBoxesArray = new Pane[4];
	private TimerWidget timerWidget = new TimerWidget();

	private GraphicResources graphicMap = new GraphicResources();


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		int i;
		int thisPlayerId = 0;
		int[] opponentIdArray = {1, 2, 3}; //TODO valori di test

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

		/* Create root pane */
		final Pane root = new Pane();
		root.setId("root");

		/* Bind root pane size with window size */
		root.minWidthProperty().bind(stage.widthProperty());
		root.minHeightProperty().bind(stage.heightProperty());
		root.prefWidthProperty().bind(stage.widthProperty());
		root.prefHeightProperty().bind(stage.heightProperty());
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());

		/* Instantiate player widgets */
		player = new PlayerWidget(this, GraphicResources.getPlayerColor(thisPlayerId)); //TODO colore da settare tramite ID fornito dal model
		i = 0;
		for (OpponentWidget opponent : opponentsArray)
			opponent = new OpponentWidget(this, GraphicResources.getPlayerColor(opponentIdArray[i]));

        /* Add market action spaces */
		// Initialize
		for (i = 0; i < this.marketSpaceWidgets.length; i++)
			marketSpaceWidgets[i] =
					new MarketSpaceWidget(1);
		// Add to board
		insertActionSpace(marketSpaceWidgets[0], root, 1, 28.3036, 80.1562); // gold
		insertActionSpace(marketSpaceWidgets[1], root, 1, 33.125, 80.1562); // servants
		insertActionSpace(marketSpaceWidgets[2], root, 1, 37.7679, 82.6562); // military + gold
		insertActionSpace(marketSpaceWidgets[3], root, 1, 41.25, 88.75); // privileges

        /* Add harvest and production action spaces */
		insertMultipleSpace(productionSpace, root, 1, 2.7679, 82.5); // production
		insertMultipleSpace(harvestingSpace, root, 1, 2.7679, 93.5937); // harvest

		/* Add tower cards and tower action spaces */
		for (i = 0; i < 4; i++) {
			// VBoxes of action spaces
			towerOccupationCirclesBoxes[i] = new VBox((10.95 / 100) * stageHeight);
			towerOccupationCirclesBoxes[i].setLayoutX(((7.3214 + 10.95 * i) / 100) * stageWidth);
			towerOccupationCirclesBoxes[i].setLayoutY((7.1875 / 100) * stageHeight);
			root.getChildren().add(towerOccupationCirclesBoxes[i]);

			// VBoxes of tower cards
			towerCardSpacesArray[i] = new VBox((0.06 / 100) * resize); //TODO spacing di prova, controllare
			towerCardSpacesArray[i].setLayoutX((0.8684 + 10.9 * i) / 100 * stageWidth);
			towerCardSpacesArray[i].setLayoutY((1.4168 / 100) * stageHeight);
			root.getChildren().add(towerCardSpacesArray[i]);
			for (int j = 0; j < 4; j++) {
				// action spaces
				towerTileWidgetLists[i][j] = new TowerTileWidget(7 - 2 * j);
				towerOccupationCirclesBoxes[i].getChildren().add(towerTileWidgetLists[i][j].getOccupationCircle());

				// tower cards
				cardOnBoardWidgetLists[i][j] = new CardOnBoardWidget(); //TODO capire se popolare subito con le immagini o se si aggiungono dopo
				// towerCardSpacesArray[i].getChildren().add(cardOnBoardWidgetLists[i][j].getCardImage());
			}
		}

        /* Add points markers */
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
				this.trackBoxesArray[i].getChildren().add(marker.getMarkerCircle());
				j++;
			}
			i++;
		}

		/****** MODO 1 ******/
		// Play-order markers
		this.trackBoxesArray[0].setLayoutX((48.1 / 100) * stageWidth);
		this.trackBoxesArray[0].setLayoutY((43.5 / 100) * stageHeight);
		// Faith markers
		this.trackBoxesArray[1].setLayoutX((47.4857 / 100) * stageWidth);
		this.trackBoxesArray[1].setLayoutY((66.0375 / 100) * stageHeight);
		// Military markers
		this.trackBoxesArray[2].setLayoutX((46.8064 / 100) * stageWidth);
		this.trackBoxesArray[2].setLayoutY((14.4125 / 100) * stageHeight);
		// Victory markers
		this.trackBoxesArray[3].setLayoutX((59.2079 / 100) * stageWidth);
		this.trackBoxesArray[3].setLayoutY((14.4125 / 100) * stageHeight);

		/* Add playable familiars */
		this.player.addFamiliarToBoard(root);

        /* Initialize opponents familiars */
		i = 0;
		for (OpponentWidget opponent : opponentsArray) {
			int j = 0;
			for (FamiliarWidget familiar : opponent.getFamiliarWidgetList()) {
				familiar = new FamiliarWidget(opponent.getOpponentColor(), GraphicResources.getDiceColor(j));
				j++;
			}
			i++;
		}

		/* Add player buttons */
		// Personal cards
		final VBox commands = new VBox(20 * resize);
		commands.setAlignment(Pos.CENTER);
		final Button showCardsButton = new Button("Carte sviluppo");
		showCardsButton.setId("showCardsButton");
		showCardsButton.setMaxWidth(Double.MAX_VALUE);
		showCardsButton.setOnAction((ActionEvent e) -> {
			this.player.getPersonalBoard().display();
		});
		final Button showLeaderButton = new Button("Carte Leader"); // showLeaderButton is not clickable because the draft is not done yet
		showLeaderButton.setId("showLeaderButton");
		showLeaderButton.setMaxWidth(Double.MAX_VALUE);

		commands.getChildren().addAll(showCardsButton, showLeaderButton);

		/****** MODO 1 ******/
		commands.setLayoutX((68.0 / 100) * stageWidth);
		commands.setLayoutY((6.2 / 100) * stageHeight);

		root.getChildren().add(commands);


		// Other players boards
		final VBox personalBoardButtonsBox = new VBox(20 * resize);
		personalBoardButtonsBox.setAlignment(Pos.TOP_RIGHT);
		personalBoardButtonsBox.setLayoutX((87.0 / 100) * stageWidth);
		personalBoardButtonsBox.setLayoutY((6.2 / 100) * stageHeight);

		final Button[] otherPlayersButtons = new Button[3];
		i = 0;
		// set up buttons that open other players personal boards
		for (Button button : otherPlayersButtons) {
			button = new Button(); //TODO inserire i nomi dei giocatori
			button.setMaxWidth(Double.MAX_VALUE);
			setButtonClickGesture(button, opponentsArray[i]); // setting buttons actions on click (open relative personal board)
			personalBoardButtonsBox.getChildren().add(button);
			i++;
		}

		root.getChildren().add(personalBoardButtonsBox);


		/* Add player resources */
		for (i = 0; i < 4; i++) {
			resourcesWidgetArray[i] = new ResourcesWidget();
		}
		playerResourcesBox = resourcesWidgetArray[0].setupPersonalResource();
		root.getChildren().add(playerResourcesBox);


		/* Add dice */
		HBox diceBox = new HBox(20 * resize);
		diceBox.setLayoutX((25.0 / 100) * stageWidth);
		diceBox.setLayoutY((94.0 / 100) * stageHeight);
		diceBox.setPrefHeight((4.8 / 100) * stageHeight);

		DieWidget[] diceWidgetArray = new DieWidget[3];
		for (DieWidget die : diceWidgetArray) {
			die = new DieWidget();
			diceBox.getChildren().add(die.getValueLabel());
		}

		/* Add timer */
		timerWidget.setLayoutX((90.0 / 100) * stageWidth);
		timerWidget.setLayoutY((95.0 / 100) * stageHeight);

		/*
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());
		*/

		Scene mainScene = new Scene(root);

		File file = new File("./src/main/res/structure.fxml");
		System.out.println(file.exists());

		File f = new File("./src/main/res/fx-style.css");
		mainScene.getStylesheets().add(f.toURI().toURL().toString());
		stage.setScene(mainScene);
		stage.sizeToScene();
		stage.show();


	}




	/**************************/
	/**  Metodi per Sirolfo  **/


	public void startBonusTileDraft() {
		// Show bonus tile draft window
		BonusTileDraftPopup.display();
	}

	public void endBonusTileDraft() {
		// Close draft window
		BonusTileDraftPopup.getPopupStage().close();

		// Update player bonus tile
		this.player.setBonusTileWidget(BonusTileDraftPopup.getSelectedBonusTile());
	}

	public void startLeaderDraft(Integer[] referenceIdArray) {
		// Show leader draft window
		LeaderDraftPopup.display(referenceIdArray);
	}

	public void updateLeadersAfterDraft() {
		int i = 0;
		for (LeaderWidget leader : this.player.getLeaderWidgetList()) {
			leader = LeaderDraftPopup.leadersDrafted[i];
		}
	}

	/**                        **/
	/***************************/




	void setButtonClickGesture(Button button, OpponentWidget opponentWidget) {
		button.setOnAction((ActionEvent e) -> {
			opponentWidget.getPersonalBoard().display();
		});
	}

	private void insertActionSpace(ActionSpaceWidget actionSpace, Pane parent, int minDie, double percX, double percY) {

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

		box.getChildren().add(marker.getMarkerCircle());

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

	public TowerTileWidget[][] getTowerTileWidgetList() {
		return towerTileWidgetLists;
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

	public TowerTileWidget[][] getTowerTileWidgetLists() {
		return towerTileWidgetLists;
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
}










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
		towerOccupationCirclesBoxes[0].setLayoutX(stageWidth * 0.091071); // green tower
		towerOccupationCirclesBoxes[1].setLayoutX(stageWidth * 0.2); // blue tower
		towerOccupationCirclesBoxes[2].setLayoutX(stageWidth * 0.309821); // yellow tower
		towerOccupationCirclesBoxes[3].setLayoutX(stageWidth * 0.41875); // violet tower
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


