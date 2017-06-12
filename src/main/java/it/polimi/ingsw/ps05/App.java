package it.polimi.ingsw.ps05;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.controller.*;
import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.Turn;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalActionException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;

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
    	}
    }
}


