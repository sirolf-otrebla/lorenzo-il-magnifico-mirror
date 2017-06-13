package it.polimi.ingsw.ps05.net.client;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps05.view.View;

public class Client implements Observer {
	private boolean gui = false;
	View view = new View();
	
	public Client(boolean gui){
		this.gui = gui;
		if (this.gui){
			//start gui, ask for server link and port
			view.instanceGUI();
		} else {
			//start cli, ask for server link and port
			view.instanceCLI();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
