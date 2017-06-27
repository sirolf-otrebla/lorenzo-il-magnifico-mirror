package it.polimi.ingsw.ps05.client.view;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.client.view.cli.CLIMain;
import it.polimi.ingsw.ps05.client.view.gui.GUIMain;

public class View {
	
	
	
	public View(){
		
	}
	
	public void instanceGUI(){
		GUIMain gui = new GUIMain();
		Thread t = new Thread(gui);
		t.setDaemon(true);
		t.start();
	}
	
	public void instanceCLI(Board board, Player player, ArrayList<Player> playersList){
		System.out.println("Instance");
		CLIMain cli = new CLIMain(board, player, playersList);
		Thread t = new Thread(cli);
		t.setDaemon(true);
		t.run();
	}

}