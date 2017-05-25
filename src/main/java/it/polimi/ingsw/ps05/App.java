package it.polimi.ingsw.ps05;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.controller.*;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Player p1 = new Player(0, "luca", ColorEnumeration.Blue);
    	Player p2 = new Player(1, "alberto", ColorEnumeration.Green);
    	Player p3 = new Player(2, "andrea", ColorEnumeration.Violet);
    	Player p4 = new Player(3, "franco", ColorEnumeration.Yellow);
    	ArrayList<Player> list = new ArrayList<Player>();
    	list.add(p1);
    	list.add(p2);
    	list.add(p3);
    	list.add(p4);
    	GameSetup setup = new GameSetup(list);
    }
}


