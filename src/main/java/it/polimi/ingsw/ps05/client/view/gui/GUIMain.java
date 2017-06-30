package it.polimi.ingsw.ps05.client.view.gui;


import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;

public class GUIMain extends Application {

	private Stage stage;

	TowerTileWidget[][] towerTileWidgetList = new TowerTileWidget[4][4];
	private VBox[] towerOccupationCircesArray = new VBox[4];
	private VBox[] towerCardSpacesArray = new VBox[4];
	private FamiliarWidget[][] familiarWidgetLists = new FamiliarWidget[4][4];
	private MarketSpaceWidget[] marketSpaceWidgets = new MarketSpaceWidget[4];

	ProductionSpaceWidget productionSpace = new ProductionSpaceWidget();
	// MultipleSpaceWidget secondaryProductionSpace = new MultipleSpaceWidget();
	HarvestingSpaceWidget harvestingSpace = new HarvestingSpaceWidget();
	// MultipleSpaceWidget secondaryHarvestingSpace = new MultipleSpaceWidget();

	MarketSpaceWidget goldMarketSpace = new MarketSpaceWidget();
	MarketSpaceWidget servantsMarketSpace = new MarketSpaceWidget();
	MarketSpaceWidget militaryMarketSpace = new MarketSpaceWidget();
	MarketSpaceWidget privilegesMarketSpace = new MarketSpaceWidget();

	// MultipleSpaceWidget councilSpace = new MultipleSpaceWidget();

	MarkerWidget redFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-red.png");
	MarkerWidget greenFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-green.png");
	MarkerWidget blueFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-blue.png");
	MarkerWidget yellowFaithMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-yellow.png");

	MarkerWidget redVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-red.png");
	MarkerWidget greenVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-green.png");
	MarkerWidget blueVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-blue.png");
	MarkerWidget yellowVictoryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-yellow.png");

	MarkerWidget redMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-red.png");
	MarkerWidget greenMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-green.png");
	MarkerWidget blueMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-blue.png");
	MarkerWidget yellowMilitaryMarker = new MarkerWidget("main/java/it.polimi.ingsw.ps05/client/view/gui/img/marker-yellow.png");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		// Parent root = FXMLLoader.load(getClass().getResource("source.fxml"));
		// primaryStage.setTitle("Hello World");
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
		for (TowerTileWidget[] tower: this.towerTileWidgetList) {
			int i = 0;
			int j = 0;
			this.towerOccupationCircesArray[j] = new VBox();
			for (TowerTileWidget widget : tower) {
				widget = new TowerTileWidget(2*i +1);
				i++;
				towerOccupationCircesArray[j].getChildren().add(widget.getOccupationCircle());
				towerCardSpacesArray[j].getChildren().add(widget.getAssociatedCard().getCardImage());
			}
			j++;
		}

		for (int i = 0; i < familiarWidgetLists.length; i++)
			for (int j = 0; i < familiarWidgetLists[i].length; j++)
				familiarWidgetLists[i][j] = new FamiliarWidget(
						ColorEnumeration.values()[1+i], ColorEnumeration.values()[j+5]);

		for (int i = 0;)

		final HBox commands = new HBox(50);
		final Button showCardsButton = new Button("Carte sviluppo");
		final Button showLeaderButton = new Button("Carte Leader");
		commands.getChildren().addAll(showCardsButton, showLeaderButton);
		commands.setLayoutX(770);
		commands.setLayoutY(60);

		board.maxWidthProperty().bind(stage.widthProperty());
		board.maxHeightProperty().bind(stage.heightProperty());

        /* Adding playable familiars */
		insertDraggableFamiliar(redFamiliar_black, board, 740, 140);
		insertDraggableFamiliar(redFamiliar_white, board, 790, 140);
		insertDraggableFamiliar(redFamiliar_orange, board, 840, 140);
		insertDraggableFamiliar(redFamiliar_neutral, board, 890, 140);

        /* Adding tower action spaces */
		/* insertActionSpace(greenTowerSpace7, board, 7, 102, 66);
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
		insertActionSpace(violetTowerSpace1, board, 1, 469, 398); */

        /* Adding market action spaces */
		insertActionSpace(goldMarketSpace, board, 1, 317, 513); // gold
		insertActionSpace(servantsMarketSpace, board, 1, 371, 513); // servants
		insertActionSpace(militaryMarketSpace, board, 1, 423, 529); // military + gold
		insertActionSpace(privilegesMarketSpace, board, 1, 462, 568); // privileges

        /* Adding harvest and production action spaces */
		insertActionSpace(productionSpace, board, 1, 31, 528); // production
		insertActionSpace(harvestingSpace, board, 1, 31, 599); // harvest

        /* Adding points markers */
		// Military
		insertMarker(greenMilitaryMarker, board, 537, 98);
		insertMarker(redMilitaryMarker, board, 537, 138);
		insertMarker(blueMilitaryMarker, board, 537, 178);
		insertMarker(yellowMilitaryMarker, board, 537, 218);
		// Victory
		insertMarker(greenVictoryMarker, board, 675, 98);
		insertMarker(redVictoryMarker, board, 675, 138);
		insertMarker(blueVictoryMarker, board, 675, 178);
		insertMarker(yellowVictoryMarker, board, 675, 218);
		// Faith
		insertMarker(greenFaithMarker, board, 580, 438);
		insertMarker(redFaithMarker, board, 615, 438);
		insertMarker(blueFaithMarker, board, 650, 438);
		insertMarker(yellowFaithMarker, board, 685, 438);

		board.getChildren().add(commands);

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

	void insertMarker(MarkerWidget marker, Pane pane, double x, double y) {

		marker.getMarkerCircle().setCenterX(x);
		marker.getMarkerCircle().setCenterY(y);

		pane.getChildren().add(marker.getMarkerCircle());

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

