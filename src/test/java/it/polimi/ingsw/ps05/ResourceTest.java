package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.spaces.Tile;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.controller.Server;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.server.net.socket.SocketConn;
import org.junit.Assert;
import org.junit.Test;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class ResourceTest {

	ArrayList<Resource> resourceArrayList = new ArrayList<>();
	ArrayList<ActionResult> actionResults = new ArrayList<>();
	private final Integer ZERO = 0;
	private final Integer NUM_TO_SUM = 5;

	public ResourceTest(){

	}

	public void setUp(){
		resourceArrayList.add(new GoldResource(0));
		resourceArrayList.add(new StoneResource(0));
		resourceArrayList.add(new WoodResource(0));
		resourceArrayList.add(new ServantResource(0));
		resourceArrayList.add(new MilitaryResource(0));
		resourceArrayList.add(new FaithResource(0));
		resourceArrayList.add(new VictoryResource(0));
		resourceArrayList.add(new AlwaysUnFullFilledResource());

		actionResults.add(new AllBonus());
		actionResults.add(new BlueAction());
		actionResults.add(new BlueBonus());
		actionResults.add(new BonusDice(1, ColorEnumeration.Black, true));
		actionResults.add(new BonusDice(1, ColorEnumeration.Orange, true));
		actionResults.add(new BonusDice(1, ColorEnumeration.White, true));
		actionResults.add(new BonusDice(1, ColorEnumeration.Any, true));
		actionResults.add(new FreeAction());
		actionResults.add(new GreenAction());
		actionResults.add(new GreenBonus());


	}

	@Test
	public void goodResourceTest(){
		for (Resource res : resourceArrayList){
			res.setValue(res.getValue() + NUM_TO_SUM);
			assertEquals(NUM_TO_SUM, res.getValue());
			try {
				res.remove(NUM_TO_SUM);
				assertEquals(ZERO, res.getValue());
			} catch (NotEnoughResourcesException | IllegalMethodCallException e) {
				Assert.fail();
			}

		}
	}

	@Test
	public void notEnoughResourcesTest(){
		for (Resource res : resourceArrayList){
			try {
				res.remove(NUM_TO_SUM);
				Assert.fail();
			} catch (NotEnoughResourcesException | IllegalMethodCallException e) {
				assertEquals(NotEnoughResourcesException.class, e.getClass());
			}
		}
	}
	
	@Test
	public void illegalMethodCallException(){
		for (int i = 0; i < resourceArrayList.size() - 1; i++){
			try{
				resourceArrayList.get(i).remove(resourceArrayList.get(i+1));
			} catch(NotEnoughResourcesException | IllegalMethodCallException e){
				assertEquals(IllegalMethodCallException.class, e.getClass());
			}
		}
	}

	@Test
	public void buildGameTest(){
		int WaitingTime = 9000;
		ArrayList<PlayerClient> playerClients = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			new PlayerClient(new SocketConn(new Socket()), i);
		}
		Game game = new Game(false, false, 100, playerClients);
		assertEquals(false, game.isUsingCompleteRules());
		for (PlayerClient client: playerClients) {
			assertEquals(true, client.isInGame());
		}
	}

	@Test
	public void buildBoardTest() throws NoSuchMethodException {

		ColorEnumeration[] colorEnumeration = {
				ColorEnumeration.Yellow,
				ColorEnumeration.Green,
				ColorEnumeration.Red,
				ColorEnumeration.Blue
		};
		ArrayList<PlayerClient> playerClients = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			new PlayerClient(new SocketConn(new Socket()), i);
		}
		Game game = new Game(false, false, 100, playerClients);
		for (int i = 0; i < 4; i++) {
			playerClients.get(i).BuildPlayer(colorEnumeration[i]);
		}
		ArrayList<Player> players = new ArrayList<>();
		for (PlayerClient client: playerClients) {
			players.add(client.getPlayer());
			client.setInGame(game);
		}
		GameSetup setup = new GameSetup(players, game);
		Board board = setup.getBoard();
		Iterator<TowerTileInterface> iterator = board.getTowerList().get(ColorEnumeration.Green).
				getTiles().values().iterator();
		TowerTileInterface firstTile = iterator.next();
		Integer id = firstTile.getId();
		Integer firstDice = firstTile.getDiceRequired().getValue();
		ActionResult actionResult = actionResults.get(0); //allbonus
		actionResult.setValue(2);
		actionResult.applyResult(players.get(1));
		assertEquals((int) firstDice +2, (int) firstTile.getDiceRequired().getValue());


	}
}
