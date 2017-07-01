package it.polimi.ingsw.ps05.client.view.gui;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarWidget.FAMILIAR_MIN_SIZE;

public class GUIMain extends Application {

	private Stage stage;

	public static double stageWidth, stageHeight, resize;
	public static final int ORIGINAL_WIDTH = 1120;

	private GraphicMap map = new GraphicMap();

	private FamiliarWidget redFamiliar_black = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/black.png");
	private FamiliarWidget redFamiliar_white = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/white.png");
	private FamiliarWidget redFamiliar_orange = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/orange.png");
	private FamiliarWidget redFamiliar_neutral = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/neutral.png");

	private TowerTileWidget[][] towerTileWidgetList = new TowerTileWidget[4][4];
	private final VBox[] towerOccupationCirclesArray = new VBox[4];
	private final VBox[] towerCardSpacesArray = new VBox[4];

	private ProductionSpaceWidget productionSpace = new ProductionSpaceWidget();
	// MultipleSpaceWidget secondaryProductionSpace = new MultipleSpaceWidget();
	private HarvestingSpaceWidget harvestingSpace = new HarvestingSpaceWidget();
	// MultipleSpaceWidget secondaryHarvestingSpace = new MultipleSpaceWidget();


	private MarketSpaceWidget goldMarketSpace = new MarketSpaceWidget();
	private MarketSpaceWidget servantsMarketSpace = new MarketSpaceWidget();
	private MarketSpaceWidget militaryMarketSpace = new MarketSpaceWidget();
	private MarketSpaceWidget privilegesMarketSpace = new MarketSpaceWidget();

	// MultipleSpaceWidget councilSpace = new MultipleSpaceWidget();

	private MarkerWidget[][] markerWidgetList = new MarkerWidget[4][4];
	private final Pane[] trackBoxesArray = new Pane[4];

	private MarkerWidget redFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-red.png");
	private MarkerWidget greenFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-green.png");
	private MarkerWidget blueFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-blue.png");
	private MarkerWidget yellowFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-yellow.png");

	private MarkerWidget redVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-red.png");
	private MarkerWidget greenVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-green.png");
	private MarkerWidget blueVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-blue.png");
	private MarkerWidget yellowVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-yellow.png");

	private MarkerWidget redMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-red.png");
	private MarkerWidget greenMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-green.png");
	private MarkerWidget blueMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-blue.png");
	private MarkerWidget yellowMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-yellow.png");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		// Parent root = FXMLLoader.load(getClass().getResource("source.fxml"));
		// primaryStage.setTitle("Hello World");

		int i = 0, j = 0;

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
			this.stageWidth = primaryScreenBounds.getWidth();
			this.stageHeight = stageWidth / 1.75;
		} else {
			stage.setY(primaryScreenBounds.getMinY());
			stage.setHeight(primaryScreenBounds.getHeight());
			stage.setWidth(primaryScreenBounds.getHeight() * 1.75);
			this.stageHeight = primaryScreenBounds.getHeight();
			this.stageWidth = stageHeight * 1.75;
		}
		resize = this.stageWidth / ORIGINAL_WIDTH;

		final Pane root = new Pane();
		root.setId("root");

		root.minWidthProperty().bind(stage.widthProperty());
		root.minHeightProperty().bind(stage.heightProperty());
		root.prefWidthProperty().bind(stage.widthProperty());
		root.prefHeightProperty().bind(stage.heightProperty());
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());

        /* Adding playable familiars */
		insertDraggableFamiliar(redFamiliar_black, root, 66.07, 21.87);
		insertDraggableFamiliar(redFamiliar_white, root, 70.5357, 21.87);
		insertDraggableFamiliar(redFamiliar_orange, root, 75, 21.87);
		insertDraggableFamiliar(redFamiliar_neutral, root, 79.4643, 21.87);

        /* Adding tower action spaces */
        i = 0;
        j = 0;
		for (TowerTileWidget[] tower: this.towerTileWidgetList) {
			this.towerOccupationCirclesArray[j] = new VBox(((9.9107 / 100) - FAMILIAR_MIN_SIZE) * stageHeight);
			towerOccupationCirclesArray[j].setLayoutY(stageHeight * 0.103125); //TODO aggiungere binding
			for (TowerTileWidget widget : tower) {
				widget = new TowerTileWidget(2*i + 1);
				i++;
				towerOccupationCirclesArray[j].getChildren().add(widget.getOccupationCircle());
				towerCardSpacesArray[j].getChildren().add(widget.getAssociatedCard().getCardImage());
			}
			j++;
		}

		//TODO aggiungere binding
		towerOccupationCirclesArray[0].setLayoutX(stageWidth * 0.091071); // green tower
		towerOccupationCirclesArray[1].setLayoutX(stageWidth * 0.2); // blue tower
		towerOccupationCirclesArray[2].setLayoutX(stageWidth * 0.309821); // yellow tower
		towerOccupationCirclesArray[3].setLayoutX(stageWidth * 0.41875); // violet tower

		/* righe mantenute perché contengono le coordinate dei posti azione

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

		*/

        /* Adding market action spaces */
		insertActionSpace(goldMarketSpace, root, 1, 28.3036, 80.1562); // gold
		insertActionSpace(servantsMarketSpace, root, 1, 33.125, 80.1562); // servants
		insertActionSpace(militaryMarketSpace, root, 1, 37.7679, 82.6562); // military + gold
		insertActionSpace(privilegesMarketSpace, root, 1, 41.25, 88.75); // privileges

        /* Adding harvest and production action spaces */
		insertActionSpace(productionSpace, root, 1, 2.7679, 82.5); // production
		insertActionSpace(harvestingSpace, root, 1, 2.7679, 93.5937); // harvest

		/* Adding tower cards */
		//TODO

        /* Adding points markers */
		i = 0;
		j = 0;

        for (MarkerWidget[] track: this.markerWidgetList) {
			if(i < 2) {
				this.trackBoxesArray[i] = new HBox(20);
			} else {
				this.trackBoxesArray[i] = new VBox(20);
			}
			root.getChildren().add(this.trackBoxesArray[i]);
			for (MarkerWidget playerMarker: track) {
				playerMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-"
						+ map.playerColorMap.get(j) + ".png");
				this.trackBoxesArray[i].getChildren().add(playerMarker.getMarkerCircle());
				j++;
			}
		}

		/****** MODO 1 ******/
		// Faith markers
		this.trackBoxesArray[1].setLayoutX((51.7857 / 100) * stageWidth);
		this.trackBoxesArray[1].setLayoutY((68.4375 / 100) * stageHeight);
		// Military markers
		this.trackBoxesArray[2].setLayoutX((47.9464 / 100) * stageWidth);
		this.trackBoxesArray[2].setLayoutY((15.3125 / 100) * stageHeight);
		// Victory markers
		this.trackBoxesArray[3].setLayoutX((60.2679 / 100) * stageWidth);
		this.trackBoxesArray[3].setLayoutY((15.3125 / 100) * stageHeight);

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


		/*
		militaryBox.setLayoutX(537);
		militaryBox.setLayoutY(98);

		victoryBox.setLayoutX(675);
		victoryBox.setLayoutY(98);

		faithBox.setLayoutX(580);
		faithBox.setLayoutY(438);
		*/

		/* Adding player buttons */
		final HBox commands = new HBox(50 * resize);
		final Button showCardsButton = new Button("Carte sviluppo");
		final Button showLeaderButton = new Button("Carte Leader");
		commands.getChildren().addAll(showCardsButton, showLeaderButton);

		/****** MODO 1 ******/
		commands.setLayoutX((68.75 / 100) * stageWidth);
		commands.setLayoutY((9.375 / 100) * stageHeight);
		/****** MODO 2 (meglio) ******/
		//commands.layoutXProperty().bind(stage.widthProperty().multiply(68.75 / 100));
		//commands.layoutYProperty().bind(stage.heightProperty().multiply(9.375 / 100));

		root.getChildren().add(commands);

		/*
		root.maxWidthProperty().bind(stage.widthProperty());
		root.maxHeightProperty().bind(stage.heightProperty());
		*/

		Scene mainScene = new Scene(root);
		mainScene.getStylesheets().addAll(this.getClass().getResource("style-prova.css").toExternalForm());

		stage.setScene(mainScene);
		stage.sizeToScene();
		stage.show();
	}

	void insertDraggableFamiliar(FamiliarWidget familiar, Pane pane, double percX, double percY) {

		familiar.getImageElement().setX(percX * stageWidth);
		familiar.getImageElement().setY(percY * stageHeight);

		familiar.setupGestureSource();

		pane.getChildren().add(familiar.getImageElement());

	}

	void insertActionSpace(ActionSpaceWidget actionSpace, Pane pane, int minDice, double percX, double percY) {

		actionSpace.getOccupationCircle().setRadius(20);
		actionSpace.getOccupationCircle().setCenterX(percX * stageWidth);
		actionSpace.getOccupationCircle().setCenterY(percY * stageHeight);
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

}

