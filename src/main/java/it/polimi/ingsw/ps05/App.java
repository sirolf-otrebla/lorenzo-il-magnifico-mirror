package it.polimi.ingsw.ps05;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.ps05.server.controller.*;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.client.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Scanner keyboard = new Scanner(System.in);
    	
    	String string = new String("C");
    	while(!string.equals("C") && !string.equals("G")){
    		System.out.println("Vuoi avviare interfaccia GUI (G) o CLI (C)?");
    		string = keyboard.nextLine();
    		System.out.println(string);
    	}
    	keyboard.close();
    	Player p1 = new Player(0, "luca", ColorEnumeration.Blue);
    	Player p2 = new Player(1, "alberto", ColorEnumeration.Green);
    	Player p3 = new Player(2, "andrea", ColorEnumeration.Violet);
    	Player p4 = new Player(3, "franco", ColorEnumeration.Yellow);
    	ArrayList<Player> list = new ArrayList<Player>();
    	list.add(p1);
    	list.add(p2);
    	list.add(p3);
    	list.add(p4);
    	GameSetup setup = new GameSetup(list, new Game(true,true,0));
    	View view = new View();
    	if (string.equals("C")){
    		//istanzia la cli
    		view.instanceCLI(setup.getBoard(), p1, setup.getTurnSetup().getTurn().getPlayerOrder());
    	} else {
    		//istanzia la gui
    		view.instanceGUI();
    	}
    	
    	
    	/*
    	Scanner keyboard = new Scanner(System.in);
    	
    	String string = new String();
    	while(!string.equals("C") && !string.equals("S")){
    		System.out.println("Vuoi avviare il server (S) o il client (C)?");
    		string = keyboard.nextLine();
    		System.out.println(string);
    	}
    	
    	if (string.equals("C")){
    		
    	} else {
    		int port = 0;
    		boolean exc = false;
    		ServerApp server;
    		while(port == 0 || exc == true){
    			exc = false;
    			System.out.println("Inserisci la porta in cui il server accetterà le connessioni: ");
        		port = keyboard.nextInt();
        		try {
					server = new ServerApp(port);
				} catch (IOException e) {
					exc = true;
					e.printStackTrace();
				}
    		}
    		
    	}*/
    	/*Player p1 = new Player(0, "luca", ColorEnumeration.Blue);
    	Player p2 = new Player(1, "alberto", ColorEnumeration.Green);
    	Player p3 = new Player(2, "andrea", ColorEnumeration.Violet);
    	Player p4 = new Player(3, "franco", ColorEnumeration.Yellow);
    	ArrayList<Player> list = new ArrayList<Player>();
    	list.add(p1);
    	list.add(p2);
    	list.add(p3);
    	list.add(p4);
    	GameSetup setup = new GameSetup(list);
    	TurnSetupManager manager = setup.getTurnSetupManager();
    	Turn turn = manager.getTurn();
    	Action action = new Action(turn.getPlayerOrder().get(0).getFamilyList().get(0), (ActionSpace)Board.getInstance().getTowerList().get(2).getTiles().get(2));
    	if (action.isLegal()){
    		action.getSuitableReqAlternatives();
    		//mostri quelli
    		try {
				action.run(0);
			} catch (IllegalActionException | NotEnoughResourcesException | DiceTooLowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}*/
    }
}


