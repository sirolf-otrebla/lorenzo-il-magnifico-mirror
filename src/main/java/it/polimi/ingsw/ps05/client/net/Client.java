package it.polimi.ingsw.ps05.client.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps05.client.view.View;

public class Client implements Observer {
	private boolean gui = false;
	View view = new View();
	Socket socket;
	
	public Client(boolean gui){
		this.gui = gui;
		if (this.gui){
			//start gui, ask for server link and port
			view.instanceGUI();
		} else {
			//start cli, ask for server link and port
			//view.instanceCLI();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void openConnection(String address, int port) throws UnknownHostException, IOException{
		socket = new Socket(address, port);
	}
	
	
}
