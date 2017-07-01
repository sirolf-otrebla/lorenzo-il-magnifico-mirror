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
	private CouncilSpaceWidget councilSpaceWidget = new CouncilSpaceWidget(1);
	private GraphicResources map = new GraphicResources();
	private Integer[] faithPath = new Integer[16];
	private Integer[] militaryPath = new Integer[6];
	private Integer[] greenCardsConversion = new Integer[6];
	private Integer[] blueCardsConversion = new  Integer[6];
	private ExcomWidget[] excomWidgets = new ExcomWidget[3]; // 1 per era

	private ProductionSpaceWidget productionSpace = new ProductionSpaceWidget(1);
	private HarvestingSpaceWidget harvestingSpace = new HarvestingSpaceWidget(2);
	// MultipleSpaceWidget secondaryHarvestingSpace = new MultipleSpaceWidget();



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


        /* Adding playable familiars */
		/*insertDraggableFamiliar(redFamiliar_black, board, 740, 140);
		insertDraggableFamiliar(redFamiliar_white, board, 790, 140);
		insertDraggableFamiliar(redFamiliar_orange, board, 840, 140);
		insertDraggableFamiliar(redFamiliar_neutral, board, 890, 140);
		*/
        /* Adding tower action spaces */
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

        /* Adding market action spaces */
		/* insertActionSpace(goldMarketSpace, board, 1, 317, 513); // gold
		insertActionSpace(servantsMarketSpace, board, 1, 371, 513); // servants
		insertActionSpace(militaryMarketSpace, board, 1, 423, 529); // military + gold
		insertActionSpace(privilegesMarketSpace, board, 1, 462, 568); // privileges
*/
        /* Adding harvest and production action spaces */
		insertActionSpace(productionSpace, board, 1, 31, 528); // production
		insertActionSpace(harvestingSpace, board, 1, 31, 599); // harvest

		/* Adding tower cards */
		//TODO

        /* Adding points markers */
        for (MarkerWidget[] track: this.markerWidgetList) {
        	int i = 0;
			int j = 0;
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

	public TowerTileWidget[][] getTowerTileWidgetList() {
		return towerTileWidgetList;
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
}

