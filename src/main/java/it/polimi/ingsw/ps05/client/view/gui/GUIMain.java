package it.polimi.ingsw.ps05.client.view.gui;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class GUIMain extends Application {

	Stage stage;

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

		Pane root = new Pane();
		root.setId("root");
		root.setMinSize(1120, 640);

		Pane board = new AnchorPane();
		board.setId("board");
		board.setMinSize(1120,640);


		root.getChildren().add(board);

		HBox commands = new HBox(50);
		Button showCardsButton = new Button("Carte sviluppo");
		Button showLeaderButton = new Button("Carte Leader");
		commands.getChildren().addAll(showCardsButton, showLeaderButton);
		commands.setLayoutX(770);
		commands.setLayoutY(60);

        /*
        HBox commands = new HBox();
        Button quitButton = new Button("Esci");
        Button chatButton = new Button("Chat");
        commands.getChildren().addAll(quitButton, chatButton);


        AnchorPane.setRightAnchor(commands, 10.0);
        AnchorPane.setBottomAnchor(commands, 10.0);
        board.getChildren().add(commands);
        */

		board.maxWidthProperty().bind(stage.widthProperty());
		board.maxHeightProperty().bind(stage.heightProperty());

        /* Adding playable familiars */
		insertDraggableFamiliar(new Image("source/img/redfamily-black.png", 40, 40, true, true), board, 740, 140);
		insertDraggableFamiliar(new Image("source/img/redfamily-white.png", 40, 40, true, true), board, 790, 140);
		insertDraggableFamiliar(new Image("source/img/redfamily-orange.png", 40, 40, true, true), board, 840, 140);
		insertDraggableFamiliar(new Image("source/img/redfamily-neutral.png", 40, 40, true, true), board, 890, 140);

        /* Adding tower action spaces */
		insertActionSpace(new Circle(), board, 7, 102, 66);
		insertActionSpace(new Circle(), board, 7, 224, 66);
		insertActionSpace(new Circle(), board, 7, 347, 66);
		insertActionSpace(new Circle(), board, 7, 469, 66);

		insertActionSpace(new Circle(), board, 5, 102, 177);
		insertActionSpace(new Circle(), board, 5, 224, 177);
		insertActionSpace(new Circle(), board, 5, 347, 177);
		insertActionSpace(new Circle(), board, 5, 469, 177);

		insertActionSpace(new Circle(), board, 3, 102, 287);
		insertActionSpace(new Circle(), board, 3, 224, 287);
		insertActionSpace(new Circle(), board, 3, 347, 287);
		insertActionSpace(new Circle(), board, 3, 469, 287);

		insertActionSpace(new Circle(), board, 1, 102, 398);
		insertActionSpace(new Circle(), board, 1, 224, 398);
		insertActionSpace(new Circle(), board, 1, 347, 398);
		insertActionSpace(new Circle(), board, 1, 469, 398);

        /* Adding market action spaces */
		insertActionSpace(new Circle(), board, 1, 317, 513); // gold
		insertActionSpace(new Circle(), board, 1, 371, 513); // servants
		insertActionSpace(new Circle(), board, 1, 423, 529); // military + gold
		insertActionSpace(new Circle(), board, 1, 462, 568); // privileges

        /* Adding harvest and production action spaces */
		insertActionSpace(new Circle(), board, 1, 31, 528); // production
		insertActionSpace(new Circle(), board, 1, 31, 599); // harvest

        /* Adding points markers */
		// Military
		insertMarker(new Circle(), "source/img/marker-green.png", board, 537, 98);
		insertMarker(new Circle(), "source/img/marker-red.png", board, 537, 138);
		insertMarker(new Circle(), "source/img/marker-blue.png", board, 537, 178);
		insertMarker(new Circle(), "source/img/marker-yellow.png", board, 537, 218);
		// Victory
		insertMarker(new Circle(), "source/img/marker-green.png", board, 675, 98);
		insertMarker(new Circle(), "source/img/marker-red.png", board, 675, 138);
		insertMarker(new Circle(), "source/img/marker-blue.png", board, 675, 178);
		insertMarker(new Circle(), "source/img/marker-yellow.png", board, 675, 218);
		// Faith
		insertMarker(new Circle(), "source/img/marker-green.png", board, 580, 438);
		insertMarker(new Circle(), "source/img/marker-red.png", board, 615, 438);
		insertMarker(new Circle(), "source/img/marker-blue.png", board, 650, 438);
		insertMarker(new Circle(), "source/img/marker-yellow.png", board, 685, 438);

		board.getChildren().add(commands);

		Scene mainScene = new Scene(root, 1120, 640);
		mainScene.getStylesheets().addAll(this.getClass().getResource("fx-style.css").toExternalForm());

		stage.setScene(mainScene);
		stage.show();
	}

	void insertDraggableFamiliar(Image i, Pane pane, double x, double y) {

		ImageView imageElement = new ImageView();
		imageElement.setImage(i);
		imageElement.setX(x);
		imageElement.setY(y);

		setupGestureSource(imageElement);

		pane.getChildren().add(imageElement);

	}

	void insertActionSpace(Circle actionSpace, Pane pane, int minDice, double x, double y) {

		actionSpace.setRadius(20);
		actionSpace.setCenterX(x);
		actionSpace.setCenterY(y);
		actionSpace.setFill(Color.TRANSPARENT);

		setupGestureTarget(actionSpace);

		pane.getChildren().add(actionSpace);

	}

	void insertMarker(Circle marker, String imagePath, Pane pane, double x, double y) {

		int radius = 14;

		Image markerImage = new Image(imagePath, radius*2, radius*2, true, true);
		marker.setRadius(radius);
		marker.setCenterX(x);
		marker.setCenterY(y);
		marker.setFill(new ImagePattern(markerImage));

		pane.getChildren().add(marker);
	}


	void setupGestureSource(ImageView source) {

		source.setOnDragDetected((MouseEvent e) -> {

			source.setCursor(Cursor.CLOSED_HAND);
            /* drag was detected, start a drag-and-drop gesture */
            /* allow MOVE transfer mode */
			Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

            /* Put the image on the dragboard */
			ClipboardContent content = new ClipboardContent();
			Image sourceImage = source.getImage();
			content.putImage(sourceImage);
			db.setContent(content);

            /* Check which targets are allowed */
			//TODO: per controllare quali posti azione sono utilizzabili bisogna passare oltre all'immagine del familiare
			//TODO: anche il valore del dado (volendo anche le risorse così si controlla oltre al dado anche il costo delle carta
			showAllowedActionSpaces();

			//e.consume();

		});

		source.setOnMouseEntered((MouseEvent e) -> {
			source.setCursor(Cursor.HAND);
		});

		source.setOnDragDone((DragEvent e) -> {
            /* Actions to be performed after the drag is completed */
			if (e.getTransferMode() == TransferMode.MOVE) {
				source.setImage(null);
			}

			//e.consume();
		});

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

			if(e.getDragboard().hasImage()) {
				Image source = e.getDragboard().getImage();
				target.setOpacity(1);
				target.setFill(new ImagePattern(source));
				success = true;
			}

			e.setDropCompleted(success);

			//e.consume();
		});
	}

	void showAllowedActionSpaces () {

	}

}

