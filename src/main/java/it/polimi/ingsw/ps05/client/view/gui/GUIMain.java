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
	private static final String path = "./src/main/res/img/";

	private FamiliarWidget[] thisPlayerFamiliarWidgetList = new FamiliarWidget[4];
	private ColorEnumeration thisPlayerColor = ColorEnumeration.Red; // TODO solo per testing
	private FamiliarWidget[][] familiarWidgetLists = new FamiliarWidget[3][4];
	private LeaderWidget[] thisPlayerLeaderWidgetList = new LeaderWidget[4];
	private HBox thisPlayerResourcesBox;
	private ResourcesWidget[] resourcesWidgetArray = new ResourcesWidget[4];


	private GraphicResources map = new GraphicResources();
	private Integer[] faithPath = new Integer[16];
	private Integer[] militaryPath = new Integer[6];
	private Integer[] greenCardsConversion = new Integer[6];
	private Integer[] blueCardsConversion = new  Integer[6];
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
	// MultipleSpaceWidget secondaryHarvestingSpace = new MultipleSpaceWidget();
	// MultipleSpaceWidget councilSpace = new MultipleSpaceWidget();

	private PersonalBoardWindow thisPlayerPersonalBoardWindow = new PersonalBoardWindow(this);
	private PersonalBoardWindow[] personalBoardWindowArray = new PersonalBoardWindow[3];

	private MarkerWidget[][] markerWidgetList = new MarkerWidget[4][4];
	private final Pane[] trackBoxesArray = new Pane[4];






	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{

		int i;

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double screenWidth = primaryScreenBounds.getWidth();
		double screenHeight = primaryScreenBounds.getHeight();
		double screenRatio = screenWidth / screenHeight;

		stage = primaryStage;
		stage.setTitle("Lorenzo il Magnifico");
		stage.setResizable(false);

		/* Setting window size */
		if(screenRatio <= 1.75) {
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

		/* Creating root pane */
		final Pane root = new Pane();
		root.setId("root");

		root.minWidthProperty().bind(stage.widthProperty());
		root.minHeightProperty().bind(stage.heightProperty());
		root.prefWidthProperty().bind(stage.widthProperty());
		root.prefHeightProperty().bind(stage.heightProperty());
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());


        /* Adding playable familiars */
        /*
		for (FamiliarWidget[] playerFamiliars: this.familiarWidgetLists) {
			int i = 0;
			int j = 0;
			for (FamiliarWidget familiar: playerFamiliars) {
				familiar = new FamiliarWidget("../main/res/img/redpl/" + map.getDiceColor(i) + ".png"); //TODO: da cambiare, è solo per testing
				i++;
			}
			j++;
		}
		*/

		thisPlayerFamiliarWidgetList[0] = new FamiliarWidget(path + thisPlayerColor.toString().toLowerCase() + "pl/black.png");
		thisPlayerFamiliarWidgetList[1] = new FamiliarWidget(path + thisPlayerColor.toString().toLowerCase() + "pl/white.png");
		thisPlayerFamiliarWidgetList[2] = new FamiliarWidget(path + thisPlayerColor.toString().toLowerCase() + "pl/orange.png");
		thisPlayerFamiliarWidgetList[3] = new FamiliarWidget(path + thisPlayerColor.toString().toLowerCase() + "pl/neutral.png");
		insertDraggableFamiliar(thisPlayerFamiliarWidgetList[0], root, 66.07, 21.87);
		insertDraggableFamiliar(thisPlayerFamiliarWidgetList[1], root, 70.5357, 21.87);
		insertDraggableFamiliar(thisPlayerFamiliarWidgetList[2], root, 75, 21.87);
		insertDraggableFamiliar(thisPlayerFamiliarWidgetList[3], root, 79.4643, 21.87);

        for (i = 0; i < familiarWidgetLists.length; i++)
            for (int j = 0; j < familiarWidgetLists[i].length; j++)
                familiarWidgetLists[i][j] = new FamiliarWidget(
                        ColorEnumeration.values()[1+i], ColorEnumeration.values()[j+5]);

        for (i = 0; i < this.marketSpaceWidgets.length; i++) marketSpaceWidgets[i] =
				new MarketSpaceWidget(1);

        /* Adding market action spaces  (Si adda pure quello che vuoi ma ricordati di inizializzare prima... )*/
		insertActionSpace(marketSpaceWidgets[0], root, 1, 28.3036, 80.1562); // gold
		insertActionSpace(marketSpaceWidgets[1], root, 1, 33.125, 80.1562); // servants
		insertActionSpace(marketSpaceWidgets[2], root, 1, 37.7679, 82.6562); // military + gold
		insertActionSpace(marketSpaceWidgets[3], root, 1, 41.25, 88.75); // privileges

        /* Adding harvest and production action spaces */
        /*
		insertActionSpace(productionSpace, root, 1, 2.7679, 82.5); // production
		insertActionSpace(harvestingSpace, root, 1, 2.7679, 93.5937); // harvest
		*/

		/* Adding tower cards and tower action spaces */
		for(i = 0; i < 4; i++) {
			// action spaces
			towerOccupationCirclesBoxes[i] = new VBox((10.95 / 100) * stageHeight); // 11.0937 = distanza verticale % tra action space
			towerOccupationCirclesBoxes[i].setLayoutX(((7.3214 + 10.95 * i) / 100) * stageWidth); // 10.9 = distanza orizzantale % tra le colonne di action space
			towerOccupationCirclesBoxes[i].setLayoutY((7.1875 / 100) * stageHeight);
			root.getChildren().add(towerOccupationCirclesBoxes[i]);

			// tower cards
			towerCardSpacesArray[i] = new VBox((0.06 / 100) * resize); //TODO 0.06 valore di prova, controllare distanza
			towerCardSpacesArray[i].setLayoutX((0.8684 + 10.9 * i) / 100 * stageWidth);
			towerCardSpacesArray[i].setLayoutY((1.4168 / 100) * stageHeight);
			root.getChildren().add(towerCardSpacesArray[i]);
			for(int j = 0; j < 4; j++) {
				// instantiating widgets
				towerTileWidgetLists[i][j] = new TowerTileWidget(7 - 2*j);
				towerOccupationCirclesBoxes[i].getChildren().add(towerTileWidgetLists[i][j].getOccupationCircle());

				cardOnBoardWidgetLists[i][j] = new CardOnBoardWidget(); //TODO capire se popolare subito con le immagini o se si aggiungono dopo
				// towerCardSpacesArray[i].getChildren().add(cardOnBoardWidgetLists[i][j].getCardImage());
			}
		}


        /* Adding points markers */
		i = 0;
        for (MarkerWidget[] track: this.markerWidgetList) {
			int j = 0;
			if(i < 2) {
				this.trackBoxesArray[i] = new HBox(12 * resize);
			} else {
				this.trackBoxesArray[i] = new VBox(12 * resize);
			}
			root.getChildren().add(this.trackBoxesArray[i]);
			for (MarkerWidget playerMarker: track) {
				String thisPath = path + "marker-" + this.playerColorArray[i].toString() + ".png";
				playerMarker = new MarkerWidget(thisPath);
				this.trackBoxesArray[i].getChildren().add(playerMarker.getMarkerCircle());
				j++;
			}
			i++;
		}

		/****** MODO 1 ******/
		// Play order markers
		this.trackBoxesArray[0].setLayoutX((48.1 / 100) * stageWidth);
		this.trackBoxesArray[0].setLayoutY((43.5 / 100) * stageHeight);
		// Faith markers
		this.trackBoxesArray[1].setLayoutX((50.1857 / 100) * stageWidth);
		this.trackBoxesArray[1].setLayoutY((66.0375 / 100) * stageHeight);
		// Military markers
		this.trackBoxesArray[2].setLayoutX((46.8064 / 100) * stageWidth);
		this.trackBoxesArray[2].setLayoutY((14.4125 / 100) * stageHeight);
		// Victory markers
		this.trackBoxesArray[3].setLayoutX((59.2079 / 100) * stageWidth);
		this.trackBoxesArray[3].setLayoutY((14.4125 / 100) * stageHeight);

		/****** MODO 2 (binding) ******/
		// Faith markers
		//this.trackBoxesArray[1].layoutXProperty().bind(stage.widthProperty().multiply(51.7857 / 100));
		//this.trackBoxesArray[1].layoutYProperty().bind(stage.heightProperty().multiply(68.4375 / 100));
		// Military markers
		//this.trackBoxesArray[2].layoutXProperty().bind(stage.widthProperty().multiply(47.9464 / 100));
		//this.trackBoxesArray[2].layoutYProperty().bind(stage.heightProperty().multiply(15.3125 / 100));
		// Victory markers
		//this.trackBoxesArray[3].layoutXProperty().bind(stage.widthProperty().multiply(60.2679 / 100));
		//this.trackBoxesArray[3].layoutYProperty().bind(stage.heightProperty().multiply(15.3125 / 100));

		// Initializing personal boards of the other players
		for (PersonalBoardWindow personalBoard: personalBoardWindowArray) {
			personalBoard = new PersonalBoardWindow(this);
		}


		/* Adding player buttons */
		// Personal cards
		final VBox commands = new VBox(20 * resize);
		commands.setAlignment(Pos.CENTER);

		final Button showCardsButton = new Button("Carte sviluppo");
		showCardsButton.setId("showCardsButton");
		showCardsButton.setMaxWidth(Double.MAX_VALUE);
		showCardsButton.setOnAction((ActionEvent e) -> {
			this.thisPlayerPersonalBoardWindow.display(stageHeight);
		});
		//showCardsButton.requestFocus();

		final Button showLeaderButton = new Button("Carte Leader");
		showLeaderButton.setId("showLeaderButton");
		showLeaderButton.setMaxWidth(Double.MAX_VALUE);

		commands.getChildren().addAll(showCardsButton, showLeaderButton);

		/****** MODO 1 ******/
		commands.setLayoutX((68.0 / 100) * stageWidth);
		commands.setLayoutY((6.2 / 100) * stageHeight);
		/****** MODO 2 (meglio) ******/
		//commands.layoutXProperty().bind(stage.widthProperty().multiply(68.75 / 100));
		//commands.layoutYProperty().bind(stage.heightProperty().multiply(9.375 / 100));

		root.getChildren().add(commands);


		// Other players boards
		final VBox personalBoardButtonsBox = new VBox(20 * resize);
		personalBoardButtonsBox.setAlignment(Pos.TOP_RIGHT);
		personalBoardButtonsBox.setLayoutX((87.0 / 100) * stageWidth);
		personalBoardButtonsBox.setLayoutY((6.2 / 100) * stageHeight);

		final Button[] otherPlayersButtons = new Button[3];

		i = 0;
		// setting up buttons that open other players personal boards
		for(Button button: otherPlayersButtons) {
			button = new Button(); //TODO inserire i nomi dei giocatori
			button.setMaxWidth(Double.MAX_VALUE);
			setButtonClickGesture(button, personalBoardWindowArray[i]); // setting buttons actions on click (open relative personal board)
			personalBoardButtonsBox.getChildren().add(button);
			i++;
		}

		root.getChildren().add(personalBoardButtonsBox);



		/* Adding player resources */
		for(i = 0; i < 4; i++) {
			resourcesWidgetArray[i] = new ResourcesWidget();
		}
		thisPlayerResourcesBox = resourcesWidgetArray[0].setupPersonalResource();
		root.getChildren().add(thisPlayerResourcesBox);



		/* Adding dice */
		HBox diceBox = new HBox(20 * resize);
		diceBox.setLayoutX((25.0 / 100) * stageWidth);
		diceBox.setLayoutY((94.0 / 100) * stageHeight);
		diceBox.setPrefHeight((4.8 / 100) * stageHeight);

		DieWidget[] diceWidgetArray = new DieWidget[3];
		for(DieWidget die: diceWidgetArray) {
			die = new DieWidget();
			diceBox.getChildren().add(die.getValueLabel());
		}





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





		/* Adding tower action spaces */

		/*
		for (int j = 0; j < this.towerTileWidgetLists.length ; j++) {
			TowerTileWidget[] tower = towerTileWidgetLists[j];
			this.towerOccupationCirclesBoxes[j] = new VBox(((9.9107 / 100) - FAMILIAR_MIN_SIZE) * stageHeight);
			towerOccupationCirclesBoxes[j].setLayoutY(stageHeight * 0.103125); //TODO aggiungere binding
			for (i = 0; i < tower.length; i++){
				tower[i] = new TowerTileWidget(2*i + 1);
				towerOccupationCirclesBoxes[j].getChildren().add(tower[i].getOccupationCircle());
				//XXX eccezione in questa riga, ho popolato towerCardSpacesArray, ma widget non ha nessuna carta
				//towerCardSpacesArray[j].getChildren().add(widget.getAssociatedCard().getCardImage());
			}
		}
		*/
	}

	void insertDraggableFamiliar(FamiliarWidget familiar, Pane pane, double percX, double percY) {

		familiar.getImageElement().setX((percX / 100) * stageWidth);
		familiar.getImageElement().setY((percY / 100) * stageHeight);

		familiar.setupGestureSource();

		pane.getChildren().add(familiar.getImageElement());

	}


	void setButtonClickGesture(Button button, PersonalBoardWindow windowToBeOpened) {
		button.setOnAction((ActionEvent e) -> {
			windowToBeOpened.display(stageHeight);
		});
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

	void insertActionSpace(SingleOccupantActionSpaceWidget actionSpace, Pane pane, int minDie, double percX, double percY) {

		actionSpace.setMinDie(minDie);

		actionSpace.getOccupationCircle().setRadius(20 * resize);
		actionSpace.getOccupationCircle().setCenterX((percX / 100) * stageWidth);
		actionSpace.getOccupationCircle().setCenterY((percY / 100) * stageHeight);
		actionSpace.getOccupationCircle().setFill(Color.TRANSPARENT);

		actionSpace.setupGestureTarget();

		pane.getChildren().add(actionSpace.getOccupationCircle());

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
			if(e.getDragboard().hasImage()) {
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

	void showAllowedActionSpaces () {

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

    public ColorEnumeration getThisPlayerColor() {
        return thisPlayerColor;
    }

    public void setThisPlayerColor(ColorEnumeration thisPlayerColor) {
        this.thisPlayerColor = thisPlayerColor;
    }

    public FamiliarWidget[] getThisPlayerFamiliarWidgetList() {
        return thisPlayerFamiliarWidgetList;
    }

    public void setThisPlayerFamiliarWidgetList(FamiliarWidget[] thisPlayerFamiliarWidgetList) {
        this.thisPlayerFamiliarWidgetList = thisPlayerFamiliarWidgetList;
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


}

