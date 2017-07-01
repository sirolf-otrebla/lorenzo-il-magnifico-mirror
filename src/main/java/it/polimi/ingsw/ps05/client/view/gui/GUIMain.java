package it.polimi.ingsw.ps05.client.view.gui;


import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.*;

import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarWidget.FAMILIAR_MIN_SIZE;

public class GUIMain extends Application {

	private Stage stage;

	private GraphicMap map = new GraphicMap();

	private FamiliarWidget redFamiliar_black = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/black.png");
	private FamiliarWidget redFamiliar_white = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/white.png");
	private FamiliarWidget redFamiliar_orange = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/orange.png");
	private FamiliarWidget redFamiliar_neutral = new FamiliarWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/neutral.png");

	private TowerTileWidget[][] towerTileWidgetList = new TowerTileWidget[4][4];
	private final VBox[] towerOccupationCircesArray = new VBox[4];
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

		stage = primaryStage;
		stage.setTitle("Lorenzo il Magnifico");
		stage.setResizable(false);

		final Pane root = new Pane();
		root.setId("root");
		root.setMinSize(1120, 640);

		final Pane board = new AnchorPane();
		board.setId("board");
		board.setMinSize(1120,640);


		root.getChildren().add(board);

        /* Adding playable familiars */
		insertDraggableFamiliar(redFamiliar_black, board, 740, 140);
		insertDraggableFamiliar(redFamiliar_white, board, 790, 140);
		insertDraggableFamiliar(redFamiliar_orange, board, 840, 140);
		insertDraggableFamiliar(redFamiliar_neutral, board, 890, 140);

        /* Adding tower action spaces */
        i = 0;
        j = 0;
		for (TowerTileWidget[] tower: this.towerTileWidgetList) {
			this.towerOccupationCircesArray[j] = new VBox(111 - FAMILIAR_MIN_SIZE);
			for (TowerTileWidget widget : tower) {
				widget = new TowerTileWidget(2*i +1);
				i++;
				towerOccupationCircesArray[j].getChildren().add(widget.getOccupationCircle());
				towerCardSpacesArray[j].getChildren().add(widget.getAssociatedCard().getCardImage());
			}
			j++;
		}

		/*
		insertActionSpace(greenTowerSpace7, board, 7, 102, 66);
		insertActionSpace(greenTowerSpace5, board, 5, 102, 177);
		insertActionSpace(greenTowerSpace3, board, 3, 102, 287);
		insertActionSpace(greenTowerSpace1, board, 1, 102, 398);

		insertActionSpace(blueTowerSpace7, board, 7, 224, 66);
		insertActionSpace(blueTowerSpace5, board, 5, 224, 177);
		insertActionSpace(blueTowerSpace3, board, 3, 224, 287);
		insertActionSpace(blueTowerSpace1, board, 1, 224, 398);

		insertActionSpace(yellowTowerSpace7, board, 7, 347, 66);
		insertActionSpace(yellowTowerSpace5, board, 5, 347, 177);
		insertActionSpace(yellowTowerSpace3, board, 3, 347, 287);
		insertActionSpace(yellowTowerSpace1, board, 1, 347, 398);

		insertActionSpace(violetTowerSpace7, board, 7, 469, 66);
		insertActionSpace(violetTowerSpace5, board, 5, 469, 177);
		insertActionSpace(violetTowerSpace3, board, 3, 469, 287);
		insertActionSpace(violetTowerSpace1, board, 1, 469, 398);
		*/

        /* Adding market action spaces */
		insertActionSpace(goldMarketSpace, board, 1, 317, 513); // gold
		insertActionSpace(servantsMarketSpace, board, 1, 371, 513); // servants
		insertActionSpace(militaryMarketSpace, board, 1, 423, 529); // military + gold
		insertActionSpace(privilegesMarketSpace, board, 1, 462, 568); // privileges

        /* Adding harvest and production action spaces */
		insertActionSpace(productionSpace, board, 1, 31, 528); // production
		insertActionSpace(harvestingSpace, board, 1, 31, 599); // harvest

		/* Adding tower cards */
		//TODO

        /* Adding points markers */
		i = 0;
		j = 0;

        for (MarkerWidget[] track: this.markerWidgetList) {
			if(i < 2) this.trackBoxesArray[i] = new HBox(20);
			else this.trackBoxesArray[i] = new VBox(20);
			board.getChildren().add(this.trackBoxesArray[i]);
			for (MarkerWidget playerMarker: track) {
				playerMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-"
						+ map.playerColorMap.get(j) + ".png");
				this.trackBoxesArray[i].getChildren().add(playerMarker.getMarkerCircle());
				j++;
			}
		}

		/*
		militaryBox.setLayoutX(537);
		militaryBox.setLayoutY(98);

		victoryBox.setLayoutX(675);
		victoryBox.setLayoutY(98);

		faithBox.setLayoutX(580);
		faithBox.setLayoutY(438);
		*/

		/* Adding player buttons */
		final HBox commands = new HBox(50);
		final Button showCardsButton = new Button("Carte sviluppo");
		final Button showLeaderButton = new Button("Carte Leader");
		commands.getChildren().addAll(showCardsButton, showLeaderButton);
		commands.setLayoutX(770);
		commands.setLayoutY(60);
		board.getChildren().add(commands);


		board.maxWidthProperty().bind(stage.widthProperty());
		board.maxHeightProperty().bind(stage.heightProperty());

		Scene mainScene = new Scene(root, 1120, 640);
		mainScene.getStylesheets().addAll(this.getClass().getResource("style-prova.css").toExternalForm());

		stage.setScene(mainScene);
		stage.show();
	}

	void insertDraggableFamiliar(FamiliarWidget familiar, Pane pane, double x, double y) {

		familiar.getImageElement().setX(x);
		familiar.getImageElement().setY(y);

		familiar.setupGestureSource();

		pane.getChildren().add(familiar.getImageElement());

	}

	void insertActionSpace(ActionSpaceWidget actionSpace, Pane pane, int minDice, double x, double y) {

		actionSpace.getOccupationCircle().setRadius(20);
		actionSpace.getOccupationCircle().setCenterX(x);
		actionSpace.getOccupationCircle().setCenterY(y);
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

