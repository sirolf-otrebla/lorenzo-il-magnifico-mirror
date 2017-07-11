package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.Turn;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.controller.TurnSetupManager;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

public class GameTest {
	
	Game game;
	GameSetup setup;
	ArrayList<Player> pList = new ArrayList<>();
	ArrayList<PlayerClient> pcList = new ArrayList<>();
	
	private final boolean LEADER = false;
	private final boolean TILE = false;
	private final int ID = 117;
	private final int ZERO = 0;

	@Test
	public void test() throws InterruptedException {
		pList.add(new Player(0, "luca", ColorEnumeration.Blue));
		pList.add(new Player(1, "alberto", ColorEnumeration.Green));
		pList.add(new Player(2, "andrea", ColorEnumeration.Violet));
		pList.add(new Player(3, "franco", ColorEnumeration.Yellow));
				
		game = new Game(LEADER, TILE, ID, pcList);
		game.start();
		
		assertEquals(LEADER, game.isUsingCompleteRules());
		assertEquals(TILE, game.isUsingCustomBonusTiles());
		assertNotNull(game.getBoard());
		assertNotNull(game.getExcommList());
		assertNotNull(game.getGameFlowctrl());
		assertNotNull(game.gettManager());
		assertEquals(ID, game.getGameId());
		assertNotNull(game.getPlayerInGame());
		assertEquals(game, game.getGameFlowctrl().getGame());
		
		//non posso testare roundController perché 
		// round richiede playerClient e questo richiede una connessione vera
		
		TurnSetupManager t = game.gettManager();
		assertNotNull(t.getTurn());
		Turn turn = t.getTurn();
		assertNotNull(turn.getDice());
		assertNotNull(turn.getPlayerOrder());
		//assertNotNull(turn.getPlayerOrder().get(0).getFamilyList());
		//fallisce perché crea giocatori da playerclient ma ovviamente non gli passo niente
		t.loadNextTurn();
		assertNotEquals(turn, t.getTurn());
		
	}

}
