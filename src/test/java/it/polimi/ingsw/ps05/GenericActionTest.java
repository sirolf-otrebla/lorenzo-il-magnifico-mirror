package it.polimi.ingsw.ps05;

import java.util.ArrayList;

import junit.framework.*;

import org.junit.Test;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.ActionSpaceWithEffect;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

public class GenericActionTest extends TestCase {
	Game game;
	GameSetup setup;
	ArrayList<Player> pList = new ArrayList<>();
	ArrayList<PlayerClient> pcList = new ArrayList<>();
	Board board;
	
	
	public void setUp() throws InterruptedException{
		pList.add(new Player(0, "luca", ColorEnumeration.Blue));
		pList.add(new Player(1, "alberto", ColorEnumeration.Green));
		pList.add(new Player(2, "andrea", ColorEnumeration.Violet));
		pList.add(new Player(3, "franco", ColorEnumeration.Yellow));
				
		game = new Game(false, false, 0, pcList);
		game.start();
		setup = new GameSetup(pList, game);
		pList = setup.getTurnSetupManager().getTurn().getPlayerOrder();
		
		board = setup.getBoard();
	}

	@Test
	public void testActionOnTower() {
		Familiar f = (Familiar)pList.get(0).getFamilyList().toArray()[0];
		assertNotNull(f);
		for (Familiar f1 : pList.get(0).getFamilyList()){
			if (f1.getRelatedDice().getValue() > f.getRelatedDice().getValue()){
				f = f1;
			}
		}
		
		for (Tower t : board.getTowerList().values()){
			for (TowerTileInterface tile : t.getTiles().values()){
				try {
					Action a = pList.get(0).doAction(f, (ActionSpace)tile, 0);
					if (a.isLegal()){
						assertEquals(a.getFamiliar(), f);
						assertTrue(f.isUsed());
						assertNotNull(f.getPosition());
						assertEquals(a.getPosition(), (ActionSpace)tile);
						assertNotNull(tile.getOccupant());
						assertTrue(tile.isOccupied());
						assertTrue(a.getPosition().isOccupied());
						break;
					}
				} catch (OccupiedPositionException e) {
					assertTrue(tile.isOccupied());
				} catch (RequirementsNotFullfilledException e) {
					
				}
			}
		}
	}
	
	@Test
	public void testActionOnActionSpace(){
		
	}

}
