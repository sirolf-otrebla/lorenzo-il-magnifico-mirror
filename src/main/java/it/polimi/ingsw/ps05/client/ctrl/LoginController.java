package it.polimi.ingsw.ps05.client.ctrl;

import java.io.IOException;

import it.polimi.ingsw.ps05.client.net.Connection;
import it.polimi.ingsw.ps05.client.net.ServerInterface;
import it.polimi.ingsw.ps05.client.net.socket.SocketConnection;
import it.polimi.ingsw.ps05.client.view.gui.Login;
import it.polimi.ingsw.ps05.net.message.LoginMessage;

public class LoginController {
	
	String serverAddress = null;
	String serverPort = null;
	String connection = null;
	Login l;
	Connection connToUse = null;
	String username = null;
	String password = null;
	
	public LoginController() {
		l = new Login();

		new Thread() {
			@Override
			public void run(){
				javafx.application.Application.launch(Login.class);
			}
		}.start();

		l.getUsername().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			username = newValue;
			if (password != null){
				tryLogin();
			}
		});

		l.getPassword().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			password = newValue;
			if (username != null){
				tryLogin();
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
	}
	
	private void openConnection(){
		if (connection.equals("Socket")){
			try {
				SocketConnection s = new SocketConnection(serverAddress + ":" + serverPort);
				connToUse = s;
				l.setConnected();
				ServerInterface i = ServerInterface.getInstance();
				i.setConnection(s);
			} catch (IOException e) {
				serverAddress = null;
				serverPort = null;
				e.printStackTrace();
			}
		}
	}
	
	private void tryLogin() {
		LoginMessage mess = new LoginMessage(username, password);
		connToUse.send(mess);
	}

}
