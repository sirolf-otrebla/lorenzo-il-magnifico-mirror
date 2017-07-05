package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
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
	
	public void setUp() throws InterruptedException{
		pList.add(new Player(0, "luca", ColorEnumeration.Blue));
		pList.add(new Player(1, "alberto", ColorEnumeration.Green));
		pList.add(new Player(2, "andrea", ColorEnumeration.Violet));
		pList.add(new Player(3, "franco", ColorEnumeration.Yellow));
				
		game = new Game(false, false, 0, pcList);
		game.start();
		setup = new GameSetup(pList, game);
		
		board = setup.getBoard();
	}

	@Test
	public void testConstructor() {
		assertEquals(N_SPACE, board.getActSpacesMap().size());
		assertEquals(N_EXCOMM,board.getExcomCards().size());
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

}
