package it.polimi.ingsw.ps05.client.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import it.polimi.ingsw.ps05.client.net.Connection;
import it.polimi.ingsw.ps05.client.net.ServerInterface;
import it.polimi.ingsw.ps05.client.net.socket.SocketConnection;
import it.polimi.ingsw.ps05.client.view.gui.Login;
import it.polimi.ingsw.ps05.net.message.LoginMessage;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.message.RegistrationMessage;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class LoginController {
	public static final int STATUS_WAIT_LOGIN = 0;
	public static final int STATUS_WAIT_REG = 1;
	String serverAddress = null;
	String serverPort = null;
	String connection = null;
	Login l;
	Connection connToUse = null;
	String username = null;
	String password = null;
	String registration = "Login";
	ServerInterface dioporc;
	private Semaphore semaphore;
	private int status = -1;


	public LoginController() {
		l = new Login();
		semaphore = new Semaphore(0);


		new Thread() {
			@Override
			public void run(){
				javafx.application.Application.launch(Login.class);
			}
		}.start();

		l.getUsername().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			username = newValue;
			if (password != null && registration.equals("Login")){
				tryLogin();
			} else if (password != null && registration.equals("Registration")){
				tryRegistration();
			}
		});

		l.getPassword().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			password = newValue;
			if (username != null && registration.equals("Login")){
				tryLogin();
			}else if (username != null && registration.equals("Registration")){
				tryRegistration();
			}
		});

		l.getServer().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			serverAddress = newValue;
			if (serverPort != null && connection != null){
				openConnection();
			}
		});
		l.getConnection().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			connection = newValue;
			if (serverPort != null && serverAddress != null){
				openConnection();
			}
		});

		l.getPort().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			serverPort = newValue;
			if (serverAddress != null && connection != null){
				openConnection();
			}
		});
		
		l.getRegistration().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			registration = newValue;
			if (username != null && password != null){
				tryRegistration();
			}
		});
	}
	
	private void openConnection(){
		if (connection.equals("Socket")){
			try {
				SocketConnection s = new SocketConnection(serverAddress, new Integer(serverPort));
				connToUse = s;
				l.setConnected();
				dioporc = ServerInterface.getInstance();
				dioporc.setConnection(s);
				Thread dioporcThread = new Thread(dioporc);
				dioporcThread.start();
			} catch (IOException e) {
				serverAddress = null;
				serverPort = null;
				System.out.println("connessione fallita");
				e.printStackTrace();
			}
		} else {
			
		}
	}
	
	private synchronized void tryLogin() {
		LoginMessage mess = new LoginMessage(username, password);
		this.status = LoginController.STATUS_WAIT_LOGIN;
		connToUse.send(mess);
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		NetMessage recMess = connToUse.getInputMessage();
		l.setLobbyVisble();
	}
	
	private synchronized void tryRegistration() {
		RegistrationMessage mess = new RegistrationMessage(username, password);
		this.status = LoginController.STATUS_WAIT_REG;
		System.out.println("sto per inviare");
		connToUse.send(mess);
		try {
			semaphore.acquire();
			l.setRegistered();
			l.setLobbyVisble();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		NetMessage recMess = connToUse.getInputMessage();
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public int getStatus() {
		return status;
	}

	public void setLobbyUsernames(ArrayList<String> usernames){

		ObservableList<String> list = FXCollections.observableArrayList(usernames);
		l.setLobbyUsernamesList(list);


	}

	public void setLobby(){
		l.setLobbyVisble();
	}
}
