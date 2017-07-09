package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.MilitaryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.VictoryResource;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import junit.framework.TestCase;

public class BoardTest extends TestCase {
	
	Game game;
	GameSetup setup;
	ArrayList<Player> pList = new ArrayList<>();
	ArrayList<PlayerClient> pcList = new ArrayList<>();
	Board board;
	
	
	private final int N_SPACE = 9;
	private final int N_TILE = 4; 
	private final int N_EXCOMM = 3;
	private final int N_LEADER = 20;
	private final int N_TOWER = 4;
	private boolean COMPLETE = false;
	
	public void setUp() throws InterruptedException{
		pList.add(new Player(0, "luca", ColorEnumeration.Blue));
		pList.add(new Player(1, "alberto", ColorEnumeration.Green));
		pList.add(new Player(2, "andrea", ColorEnumeration.Violet));
		pList.add(new Player(3, "franco", ColorEnumeration.Yellow));
				
		game = new Game(COMPLETE, false, 0, pcList);
		game.start();
		setup = new GameSetup(pList, game);
		
		board = setup.getBoard();
	}

	@Test
	public void testConstructor() {
		assertEquals(N_SPACE, board.getActSpacesMap().size());
		if (COMPLETE){
			assertNotNull(board.getExcomCards());
			assertEquals(N_EXCOMM,board.getExcomCards().size());
		} else {
			assertTrue(board.getExcomCards() == null);
		}
		
		assertEquals(N_LEADER,board.getLeaderCardsList().size());
		assertEquals(N_TOWER,board.getTowerList().size());
		for (Tower t : board.getTowerList().values()){
			assertEquals(N_TILE,t.getTiles().size());
			for (TowerTileInterface tile : t.getTiles().values()){
				assertNotNull(tile.getCard());
				assertTrue(!tile.isOccupied());
			}
		}
	}
	
	@Test
	public void testGetterSetter() throws InterruptedException, RepeatedAssignmentException{
		Board b = board;
		
		assertNotNull(b.getActSpacesMap());
		for(Integer i : b.getActSpacesMap().keySet()){
			assertEquals(b.getActionSpace(i),b.getActSpacesMap().get(i));
		}
		
		assertNotNull(b.getBlueCardsConversion());
		ArrayList<VictoryResource> bluList = new ArrayList<>();
		bluList.add(new VictoryResource(15));
		b.setBlueCardsConversion(bluList);
		assertEquals(bluList,b.getBlueCardsConversion());
		
		assertNull(b.getExcomCards());
		
		assertNotNull(b.getFaithPath());
		ArrayList<VictoryResource> faithList = new ArrayList<>();
		faithList.add(new VictoryResource(10));
		try{
			b.setFaithPath(faithList);
			fail();
		} catch (Exception e){
			assertEquals(RepeatedAssignmentException.class, e.getClass());
		}
		
		assertNotNull(b.getGreenCardsConversion());
		ArrayList<VictoryResource> greenList = new ArrayList<>();
		greenList.add(new VictoryResource(10));
		b.setGreenCardsConversion(greenList);
		assertEquals(greenList,b.getGreenCardsConversion());
		
		assertNotNull(b.getLeaderCardsList());
		
		assertNotNull(b.getMilitaryPath());
		ArrayList<MilitaryResource> milList = new ArrayList<>();
		milList.add(new MilitaryResource(10));
		b.setMilitaryPath(milList);
		assertEquals(milList,b.getMilitaryPath());
		
		assertNull(b.getPlayerOnCouncil());
		b.setPlayerOnCouncil(pList);
		assertTrue(b.getPlayerOnCouncil().size() == pList.size());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
