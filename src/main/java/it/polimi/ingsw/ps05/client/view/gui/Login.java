package it.polimi.ingsw.ps05.client.view.gui;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Login extends Application implements Observable {
	static boolean printed = false;

	static StringProperty serverString = new SimpleStringProperty();
	static StringProperty portString = new SimpleStringProperty();
	static StringProperty connection = new SimpleStringProperty();

	static StringProperty username = new SimpleStringProperty();
	static StringProperty password = new SimpleStringProperty();
	static StringProperty UI = new SimpleStringProperty();
	static StringProperty registration = new SimpleStringProperty();

	static GridPane grid;
	static Scene scene;
	static TextField serverTextField;
	static TextField portTextField;
	static Label connesso;
	static RadioButton rb3, rb1, rb2;
	static RadioButton rb4;
	static TextField userTextField;
	static TextField pwBox;
	static Button btn, btn1, btn2;
	static ListView<String> userListView;
	static Label userList;

	Stage primaryStage;

	public void setConnected() {
		System.out.println("aggiorno");
		connesso.setText("Connesso");
		connesso.setTextFill(Color.GREEN);
		btn1.setDisable(true);
		serverTextField.setEditable(false);
		portTextField.setEditable(false);
		rb3.setDisable(true);
		rb4.setDisable(true);
		rb1.setDisable(false);
		rb2.setDisable(false);
		userTextField.setEditable(true);
		pwBox.setEditable(true);
		btn.setDisable(false);
		btn2.setDisable(false);
	}

	public void setRegistered() {
		connesso.setText("Registrato!!!!!!!! DIOPORCO");
		connesso.setTextFill(Color.BLUEVIOLET);
	}


	@Override
	public void start(Stage primaryStage) {
		userListView = new ListView<>();
		userList = new Label("Utenti in attesa");
		userList.setVisible(false);
		userListView.setVisible(false);
		primaryStage.setTitle("Login");
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		this.primaryStage = primaryStage;

		scene = new Scene(grid, 400, 500);
		primaryStage.setScene(scene);

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label server = new Label("Server:");
		grid.add(server, 0, 1);
		serverTextField = new TextField("localhost");
		grid.add(serverTextField, 1, 1);
		Label port = new Label("Port:");
		grid.add(port, 0, 2);
		portTextField = new TextField("11717");
		grid.add(portTextField, 1, 2);

		connesso = new Label("Non connesso");
		connesso.setTextFill(Color.RED);
		grid.add(connesso, 0, 4);

		btn1 = new Button("Connetti");
		HBox hbBtn1 = new HBox(10);
		hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn1.getChildren().add(btn1);
		grid.add(hbBtn1, 1, 4);

		final ToggleGroup group1 = new ToggleGroup();

		rb3 = new RadioButton("Socket");
		rb3.setToggleGroup(group1);
		rb3.setSelected(true);
		connection.set("Socket");

		rb4 = new RadioButton("RMI");
		rb4.setToggleGroup(group1);
		grid.add(rb3, 0, 3);
		grid.add(rb4, 1, 3);

		group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov,
								Toggle old_toggle, Toggle new_toggle) {
				if (group1.getSelectedToggle() != null) {
					connection.set(((RadioButton) group1.getSelectedToggle()).getText());
				}
			}
		});

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 6);

		userTextField = new TextField();
		userTextField.setEditable(false);
		grid.add(userTextField, 1, 6);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 7);

		pwBox = new PasswordField();
		grid.add(pwBox, 1, 7);
		pwBox.setEditable(false);

		btn = new Button("Sign in");
		btn.setDisable(true);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 12);

		btn2 = new Button("Register");
		btn2.setDisable(true);
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn2.getChildren().add(btn2);
		grid.add(hbBtn2, 0, 12);
		grid.add(userList, 0, 13);
		grid.add(userListView, 1, 13);
		final ToggleGroup group = new ToggleGroup();

		rb1 = new RadioButton("GUI");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		UI.set("GUI");


		rb2 = new RadioButton("CLI");
		rb2.setToggleGroup(group);
		grid.add(rb1, 0, 9);
		grid.add(rb2, 1, 9);
		rb1.setDisable(true);
		rb2.setDisable(true);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov,
								Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					UI.set(((RadioButton) group.getSelectedToggle()).getText());
				}
			}
		});


		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				username.set(userTextField.getText());
				password.set(pwBox.getText());
				registration.set("Login");
			}
		});
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				serverString.set(serverTextField.getText());
				portString.set(portTextField.getText());
			}
		});
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				registration.set("Registration");
				username.set(userTextField.getText());
				password.set(pwBox.getText());


			}
		});

		primaryStage.show();
		printed = true;
	}

	public boolean printedCheck() {
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

	public StringProperty getUsername() {
		return username;
	}

	public StringProperty getPassword() {
		return password;
	}

	public StringProperty getServer() {
		return serverString;
	}

	public StringProperty getPort() {
		return portString;
	}

	public StringProperty getConnection() {
		return connection;
	}

	public StringProperty getRegistration() {
		return registration;
	}

	public StringProperty getUI() {
		return UI;
	}

	public void setLobbyUsernamesList(ObservableList list) {
		this.userListView.setItems(list);
	}

	public void setLobbyVisble() {
		userList.setVisible(true);
		userListView.setVisible(true);
	}

	public void close(){
		primaryStage.close();
	}

}
