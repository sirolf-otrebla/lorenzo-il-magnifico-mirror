package it.polimi.ingsw.ps05.net.client;

public class Client {
	
	private boolean socket = false;
	private boolean gui = false;
	
	public Client(boolean socket, boolean gui){
		this.socket = socket;
		this.gui = gui;
		if (this.gui){
			//start gui, ask for server link and port
		} else {
			//start cli, ask for server link and port
		}
	}
}
