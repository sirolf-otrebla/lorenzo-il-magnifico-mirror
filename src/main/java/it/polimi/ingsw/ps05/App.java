package it.polimi.ingsw.ps05;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.server.controller.*;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.database.Database;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.client.ctrl.LoginController;
import it.polimi.ingsw.ps05.client.view.View;
import it.polimi.ingsw.ps05.client.view.gui.GUIMain;
import it.polimi.ingsw.ps05.client.view.gui.Login;

/**
 * Hello world!
 *
 */

public class App {
	public static String[] serverAliases = {
			"server",
			"Server",
			"s",
			"S",
			"limServer",
			"LimServer",
			"Limserver",
			"limserver"
	};
	public static String[] clientAliases = {
			"client",
			"Client",
			"C",
			"c",
			"limClient",
			"Limclient",
			"limclient",
			"LimClient"
	};


	public static void main(String[] args) {
		/*System.out.println("Which one do you want to start?");
		System.out.println("LimServer \t LimClient");
		Scanner scanner = new Scanner(System.in);
		String in = scanner.nextLine();
		boolean started = false;
		for (String s : App.serverAliases) {
			if (s.equals(in)) {
				//TODO START SERVER
				started = true;
				System.out.println("use complete rules? ");
				in = scanner.nextLine();
				boolean useCompleteRules = true;
				boolean useCustomBonusTiles = false;
				if (in.equals("y") || in.equals("yes")) {
					 useCompleteRules = true;
				}
				else {
					useCompleteRules = false;
				}
				System.out.println("use custom Bonus Tiles? ");
				in = scanner.nextLine();
				if (in.equals("y") || in.equals("yes")) {
					useCustomBonusTiles = true;
				}
				else {
					useCustomBonusTiles = false;
				}
				System.out.println("please insert maximum lobby timeout");
				in = scanner.nextLine();
				Server server = Server.getInstance();
				server.startServer(new Integer(in), useCompleteRules, useCustomBonusTiles);
			}
		}
		if (!started) {
			for (String s : App.clientAliases) {
				if (s.equals(in)) {
					Client.getInstance();
					CliTest test = new CliTest();
					test.setUp();
					test.TestAdicazzo();
					// Login.main(null);
					//TODO START CLIENT
				}

			}
		}*/Scanner keyboard = new Scanner(System.in);
    	
    	String string = new String("C");
    	while(!string.equals("C") && !string.equals("G")){
    		System.out.println("Vuoi avviare interfaccia GUI (G) o CLI (C)?");
    		string = keyboard.nextLine();
    		System.out.println(string);
    	}
    	keyboard.close();
    	PlayerClient a = new PlayerClient(null, 0);
    	PlayerClient b = new PlayerClient(null, 1);
    	PlayerClient c = new PlayerClient(null, 2);
    	PlayerClient d = new PlayerClient(null, 3);
    	Player p1 = new Player(0, "luca", ColorEnumeration.Blue);
    	Player p2 = new Player(1, "alberto", ColorEnumeration.Green);
    	Player p3 = new Player(2, "andrea", ColorEnumeration.Violet);
    	Player p4 = new Player(3, "franco", ColorEnumeration.Yellow);
    	ArrayList<Player> list = new ArrayList<Player>();
    	ArrayList<PlayerClient> list1 = new ArrayList<PlayerClient>();
    	list.add(p1);
    	list.add(p2);
    	list.add(p3);
    	list.add(p4);
    	list1.add(a);
    	list1.add(b);
    	list1.add(c);
    	list1.add(d);
    	Game game = new Game(false,false,0, list1);
    	try {
			game.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	GameSetup setup = new GameSetup(list, game);
    	View view = new View();
    	if (string.equals("C")){
    		//istanzia la cli
    		view.instanceCLI(setup.getBoard(), p1, setup.getTurnSetup().getTurn().getPlayerOrder());
    	} else {
    		//istanzia la gui
    		view.instanceGUI();
    	}
		
	}
}


































/*    	//LoginController l = new LoginController();
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
    	Game game = new Game(true,true,0, null);
    	game.start();
    	GameSetup setup = new GameSetup(list, game);
    	View view = new View();
    	if (string.equals("C")){
    		//istanzia la cli
    		view.instanceCLI(setup.getBoard(), p1, setup.getTurnSetup().getTurn().getPlayerOrder());
    	} else {
    		//istanzia la gui
    		view.instanceGUI();
    	}
*/



