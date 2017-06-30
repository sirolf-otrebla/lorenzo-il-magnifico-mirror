package it.polimi.ingsw.ps05.client.view.gui;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application implements Observable{
	static boolean printed = false;

	static StringProperty serverString = new SimpleStringProperty();
	static StringProperty portString = new SimpleStringProperty();
	static StringProperty connection = new SimpleStringProperty("Socket");

	static StringProperty username = new SimpleStringProperty();
	static StringProperty password = new SimpleStringProperty();

	static boolean GUI = true;

	GridPane grid;
	Scene scene;
	TextField serverTextField;
	TextField portTextField;
	Button btn1;
	Label connesso;

	public void setConnected(){
		connesso.setText("Connesso");
		connesso.setTextFill(Color.GREEN);
		btn1.setDisable(true);
		serverTextField.setEditable(false);
		portTextField.setEditable(false);
	}


	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Login");
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		scene = new Scene(grid, 400, 500);
		primaryStage.setScene(scene);

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label server = new Label("Server:");
		grid.add(server, 0, 1);
		serverTextField = new TextField();
		grid.add(serverTextField, 1, 1);
		Label port = new Label("Port:");
		grid.add(port, 0, 2);
		portTextField = new TextField();
		grid.add(portTextField, 1, 2);

		Label connesso = new Label("Non connesso");
		connesso.setTextFill(Color.RED);
		grid.add(connesso, 0, 4);

		btn1 = new Button("Connetti");
		HBox hbBtn1 = new HBox(10);
		hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn1.getChildren().add(btn1);
		grid.add(hbBtn1, 1, 4);
		
		final ToggleGroup group1 = new ToggleGroup();

		RadioButton rb3 = new RadioButton("Socket");
		rb3.setToggleGroup(group1);
		rb3.setSelected(true);

		RadioButton rb4 = new RadioButton("RMI");
		rb4.setToggleGroup(group1);
		grid.add(rb3, 0, 3);
		grid.add(rb4, 1, 3);
		
		group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle old_toggle, Toggle new_toggle) {
				if (group1.getSelectedToggle() != null) {
					connection.set(((RadioButton)group1.getSelectedToggle()).getText());
				}                
			}
		});

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 6);

		TextField userTextField = new TextField();
		userTextField.setEditable(false);
		grid.add(userTextField, 1, 6);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 7);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 7);
		pwBox.setEditable(false);

		Button btn = new Button("Sign in");
		btn.setDisable(true);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 12);

		final ToggleGroup group = new ToggleGroup();

		RadioButton rb1 = new RadioButton("GUI");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);

		RadioButton rb2 = new RadioButton("CLI");
		rb2.setToggleGroup(group);
		grid.add(rb1, 0, 9);
		grid.add(rb2, 1, 9);
		rb1.setDisable(true);
		rb2.setDisable(true);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {

				}                
			}
		});


		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

			}
		});
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				serverString.set(serverTextField.getText());
				portString.set(portTextField.getText());
			}
		});

		primaryStage.show();
		printed = true;
	}

	public boolean printedCheck(){
		return printed;
	}

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void addListener(InvalidationListener listener) {

	}


	@Override
	public void removeListener(InvalidationListener listener) {

	}

	public StringProperty getUsername(){
		return username;
	}
	
	public StringProperty getPassword(){
		return password;
	}
	
	public StringProperty getServer(){
		return serverString;
	}
	
	public StringProperty getPort(){
		return portString;
	}
	
	public StringProperty getConnection(){
		return connection;
	}
}
