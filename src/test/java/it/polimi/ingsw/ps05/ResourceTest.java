package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.net.Socket;
import java.util.*;

import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.server.controller.*;
import it.polimi.ingsw.ps05.server.net.FakeConnection;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.server.net.socket.SocketConn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class ResourceTest {

	ArrayList<Resource> resourceArrayList = new ArrayList<>();
	ArrayList<ActionResult> actionResults = new ArrayList<>();
	private final Integer ZERO = 0;
	private final Integer NUM_TO_SUM = 5;
	private ArrayList<Player> players = new ArrayList<>();
	private Game game;
	private Integer firstDice;
	private Integer id;
	private TowerTileInterface firstTile;
	private Board board;

	public ResourceTest(){

	}

	@Before
	public void setUp(){
		resourceArrayList.add(new GoldResource(0));
		resourceArrayList.add(new StoneResource(0));
		resourceArrayList.add(new WoodResource(0));
		resourceArrayList.add(new ServantResource(0));
		resourceArrayList.add(new MilitaryResource(0));
		resourceArrayList.add(new FaithResource(0));
		resourceArrayList.add(new VictoryResource(0));

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

		// setup per actionResult


		ColorEnumeration[] colorEnumeration = {
				ColorEnumeration.Yellow,
				ColorEnumeration.Green,
				ColorEnumeration.Red,
				ColorEnumeration.Blue
		};
		ArrayList<PlayerClient> playerClients = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			playerClients.add(new PlayerClient(new FakeConnection(), i));
		}
		game = new Game(false, false, 100, playerClients);
		for (int i = 0; i < 4; i++) {
			playerClients.get(i).BuildPlayer(colorEnumeration[i]);
		}
		for (PlayerClient client: playerClients) {
			players.add(client.getPlayer());
			client.setInGame(game);
		}
		GameSetup setup = new GameSetup(players, game);
		board = setup.getBoard();
		game.setgBoard(board);
		Iterator<TowerTileInterface> iterator = board.getTowerList().get(ColorEnumeration.Green).
				getTiles().values().iterator();
		firstTile = iterator.next();
		id = firstTile.getId();
		firstDice = firstTile.getDiceRequired().getValue();


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
			new PlayerClient(new FakeConnection(), i);
		}
		Game game = new Game(false, false, 100, playerClients);
		assertEquals(false, game.isUsingCompleteRules());
		for (PlayerClient client: playerClients) {
			assertEquals(true, client.isInGame());
		}
	}

	@Test
	public void allBonusTest() throws NoSuchMethodException {

		AllBonus actionResult = new AllBonus(); //allbonus
		actionResult.setGame(game);
		actionResult.setValue(2);
		actionResult.applyResult(players.get(1));
		assertEquals((int) firstDice -2, (int) firstTile.getDiceRequired().getValue());
		actionResult.resetResult(players.get(1));
		assertEquals((int) firstDice, (int) firstTile.getDiceRequired().getValue());


	}

	@Test
	public void GreenBonusTest() throws NoSuchMethodException{
		Iterator<TowerTileInterface> iterator = board.getTowerList().get(ColorEnumeration.Green).
				getTiles().values().iterator();
		firstTile = iterator.next();
		GreenBonus actionResult = new GreenBonus(); //allbonus
		actionResult.setGame(game);
		actionResult.setValue(3);
		actionResult.applyResult(players.get(1));
		assertEquals((int) firstDice -3, (int) firstTile.getDiceRequired().getValue());
		actionResult.resetResult(players.get(1));
		assertEquals((int) firstDice, (int) firstTile.getDiceRequired().getValue());

	}
	@Test
	public void YellowBonusTest() throws NoSuchMethodException{
		Iterator<TowerTileInterface> iterator = board.getTowerList().get(ColorEnumeration.Yellow).
				getTiles().values().iterator();
		firstTile = iterator.next();
		YellowBonus actionResult = new YellowBonus(); //allbonus
		actionResult.setGame(game);
		actionResult.setValue(2);
		actionResult.applyResult(players.get(1));
		assertEquals((int) firstDice -2, (int) firstTile.getDiceRequired().getValue());
		actionResult.resetResult(players.get(1));
		assertEquals((int) firstDice, (int) firstTile.getDiceRequired().getValue());

	}

	@Test
	public void VioletBonusTest() throws NoSuchMethodException{
		Iterator<TowerTileInterface> iterator = board.getTowerList().get(ColorEnumeration.Violet).
				getTiles().values().iterator();
		firstTile = iterator.next();
		VioletBonus actionResult = new VioletBonus(); //allbonus
		actionResult.setGame(game);
		actionResult.setValue(2);
		actionResult.applyResult(players.get(1));
		assertEquals((int) firstDice -2, (int) firstTile.getDiceRequired().getValue());
		actionResult.resetResult(players.get(1));
		assertEquals((int) firstDice, (int) firstTile.getDiceRequired().getValue());

	}

	@Test
	public void BlueBonusTest() throws NoSuchMethodException{
		Iterator<TowerTileInterface> iterator = board.getTowerList().get(ColorEnumeration.Blue).
				getTiles().values().iterator();
		firstTile = iterator.next();
		BlueBonus actionResult = new BlueBonus(); //allbonus
		actionResult.setGame(game);
		actionResult.setValue(2);
		actionResult.applyResult(players.get(1));
		assertEquals((int) firstDice -2, (int) firstTile.getDiceRequired().getValue());
		actionResult.resetResult(players.get(1));
		assertEquals((int) firstDice, (int) firstTile.getDiceRequired().getValue());

	}

	@Test
	public void DoubleBonusTest() throws NoSuchMethodException{
		for (int i = 0; i < 50; i++) {
			ArrayList<TowerTileInterface> blueTower = new ArrayList<>(
					board.getTowerList().get(ColorEnumeration.Blue).
					getTiles().values());
			Collections.shuffle(blueTower);
			ArrayList<TowerTileInterface> greenTower = new ArrayList<>(
					board.getTowerList().get(ColorEnumeration.Green).
							getTiles().values());
			Collections.shuffle(greenTower);
			ArrayList<TowerTileInterface> yellowTower = new ArrayList<>(
					board.getTowerList().get(ColorEnumeration.Yellow).
							getTiles().values());
			Collections.shuffle(yellowTower);
			ArrayList<TowerTileInterface> violetTower = new ArrayList<>(
					board.getTowerList().get(ColorEnumeration.Violet).
							getTiles().values());
			Collections.shuffle(violetTower);
			TowerTileInterface tile0 = blueTower.get((new Random()).nextInt(blueTower.size()));
			TowerTileInterface tile1 = greenTower.get((new Random()).nextInt(greenTower.size()));
			TowerTileInterface tile2 = yellowTower.get((new Random()).nextInt(yellowTower.size()));
			TowerTileInterface tile3 = violetTower.get((new Random()).nextInt(violetTower.size()));
			TowerCard card0 = tile0.getCard();
			TowerCard card1 = tile1.getCard();
			TowerCard card2 = tile2.getCard();
			TowerCard card3 = tile3.getCard();
			ArrayList<Resource> card0ResourceResultsBefore = new ArrayList<>();
			for (int j = 0; j < card0.getEffects().size() ; j++) {
				if (card0.getEffects().get(j) instanceof ImmediateEffect){
					ImmediateEffect effect = (ImmediateEffect) card0.getEffects().get(j);
					for (int k = 0; k < effect.getResultList().size() ; k++) {
						if(effect.getResultList().get(k) instanceof GoldResource ||
								effect.getResultList().get(k) instanceof WoodResource ||
								effect.getResultList().get(k) instanceof ServantResource ||
								effect.getResultList().get(k) instanceof StoneResource ){
							card0ResourceResultsBefore.add((Resource) effect.getResultList().get(k));
						}
					}
				}
			}

			DoubleBonus actionResult = new DoubleBonus(); //allbonus
			actionResult.setGame(game);
			actionResult.applyResult(players.get(1));
			ArrayList<Resource> card0ResourceResultsAfter = new ArrayList<>();
			for (int j = 0; j < card0.getEffects().size() ; j++) {
				if (card0.getEffects().get(j) instanceof ImmediateEffect){
					ImmediateEffect effect = (ImmediateEffect) card0.getEffects().get(j);
					for (int k = 0; k < effect.getResultList().size() ; k++) {
						if(effect.getResultList().get(k) instanceof GoldResource ||
								effect.getResultList().get(k) instanceof WoodResource ||
								effect.getResultList().get(k) instanceof ServantResource ||
								effect.getResultList().get(k) instanceof StoneResource ){
							card0ResourceResultsAfter.add((Resource) effect.getResultList().get(k));
						}
					}
				}
			}
			for (int j = 0; j < card0ResourceResultsBefore.size(); j++) {
				assertEquals( card0ResourceResultsBefore.get(j).getValue()*2,
						(int) card0ResourceResultsAfter.get(j).getValue());
			}
		}
	}


	@Test
	public void testProduction() throws NotEnoughResourcesException, IllegalMethodCallException {

		Player testPl = this.game.getPlayerClient(0).getPlayer();
		Collection<TowerTileInterface> yellow =
				board.getTowerList().get(ColorEnumeration.Yellow).getTiles().values();
		YellowCard card1  = (YellowCard) new ArrayList<TowerTileInterface>(yellow).get(0).getCard();
		YellowCard card2  = (YellowCard) new ArrayList<TowerTileInterface>(yellow).get(0).getCard();
		testPl.addYellowCard(card1);
		testPl.addYellowCard(card2);
		card1.setToBeActivated(true);
		card2.setToBeActivated(true);
		ProductionSpace productionSpace = new ProductionSpace();
		productionSpace.applyEffect(testPl.getFamilyMember(ColorEnumeration.Black));
		ArrayList<ActionResult> res1 = card1.getActivableEffectList().
				get(0).getResultList().get(0);
		for (int i = 0; i < res1.size() ; i++) {
			if (res1.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res1.get(0)).getID()).getValue();
				testPl.addResource(((Resource) res1.get(i)));
			}

		}
		ArrayList<ActionResult> res2 = card2.getActivableEffectList().
				get(0).getResultList().get(0);
		for (int i = 0; i < res1.size() ; i++) {
			if (res2.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res2.get(0)).getID()).getValue();
				testPl.addResource(((Resource) res2.get(i)));
			}

		}
		ArrayList<Resource> plRes = testPl.getResourceList();
		for (int i = 0; i < res1.size() ; i++) {
			if (res1.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res1.get(i)).getID()).getValue();
				testPl.getResource(((Resource) res1.get(i)).getID()).remove((Resource) res1.get(i));
			}

		}
		for (int i = 0; i < res2.size() ; i++) {
			if (res2.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res2.get(i)).getID()).getValue();
				testPl.getResource(((Resource) res2.get(i)).getID()).remove((Resource) res2.get(i));
			}

		}
		productionSpace.applyEffect(testPl.getFamilyMember(ColorEnumeration.Black));
		System.out.println(card1.getActivableEffectList().size());
		for (Resource r: plRes) {
			assertEquals(testPl.getResource(r.getID()).getValue(), r.getValue());
		}


	}
	@Test
	public void testHarvest() throws NotEnoughResourcesException, IllegalMethodCallException {

		Player testPl = this.game.getPlayerClient(0).getPlayer();
		Collection<TowerTileInterface> green =
				board.getTowerList().get(ColorEnumeration.Green).getTiles().values();
		GreenCard card1  = (GreenCard) new ArrayList<TowerTileInterface>(green).get(0).getCard();
		GreenCard card2  = (GreenCard) new ArrayList<TowerTileInterface>(green).get(0).getCard();
		testPl.addGreenCard(card1);
		testPl.addGreenCard(card2);
		card1.setToBeActivated(true);
		card2.setToBeActivated(true);
		HarvestingSpace har = new HarvestingSpace();
		har.applyEffect(testPl.getFamilyMember(ColorEnumeration.Black));
		ArrayList<ActionResult> res1 = card1.getActivableEffectList().
				get(0).getResultList().get(0);
		for (int i = 0; i < res1.size() ; i++) {
			if (res1.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res1.get(0)).getID()).getValue();
				testPl.addResource(((Resource) res1.get(i)));
			}

		}
		ArrayList<ActionResult> res2 = card2.getActivableEffectList().
				get(0).getResultList().get(0);
		for (int i = 0; i < res1.size() ; i++) {
			if (res2.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res2.get(0)).getID()).getValue();
				testPl.addResource(((Resource) res2.get(i)));
			}

		}
		ArrayList<Resource> plRes = testPl.getResourceList();
		for (int i = 0; i < res1.size() ; i++) {
			if (res1.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res1.get(i)).getID()).getValue();
				testPl.getResource(((Resource) res1.get(i)).getID()).remove((Resource) res1.get(i));
			}

		}
		for (int i = 0; i < res2.size() ; i++) {
			if (res2.get(i) instanceof Resource){
				Integer value =
						testPl.getResource(((Resource) res2.get(i)).getID()).getValue();
				testPl.getResource(((Resource) res2.get(i)).getID()).remove((Resource) res2.get(i));
			}

		}
		har.applyEffect(testPl.getFamilyMember(ColorEnumeration.Black));
		System.out.println(card1.getActivableEffectList().size());
		for (Resource r: plRes) {
			assertEquals(testPl.getResource(r.getID()).getValue(), r.getValue());
		}


	}

	@Test
	public void testCouncil() throws InterruptedException {
		CouncilSpace councilSpace = null;
		for ( ActionSpace space : this.game.getBoard().getActSpacesMap().values()){
			if (space instanceof CouncilSpace)
				councilSpace = (CouncilSpace ) space;
		}
		Collections.shuffle(players);
		for ( Player p : players) {
			councilSpace.setOccupied(p.getFamilyMember(ColorEnumeration.Black));
		}
		for (int i = 0; i < players.size(); i++) {
			assertEquals(players.get(i).getPlayerID(), councilSpace.getOccupantList().get(i).getRelatedPlayer().getPlayerID());
		}
	}

	@Test
	public  void testGameSetup(){
		ColorEnumeration[] colorEnumeration = {
				ColorEnumeration.Yellow,
				ColorEnumeration.Green,
				ColorEnumeration.Red,
				ColorEnumeration.Blue
		};
		ArrayList<PlayerClient> playerClients = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			playerClients.add(new PlayerClient(new FakeConnection(), i));
		}
		game = new Game(false, false, 100, playerClients);
		for (int i = 0; i < 4; i++) {
			playerClients.get(i).BuildPlayer(colorEnumeration[i]);
		}
		for (PlayerClient client: playerClients) {
			players.add(client.getPlayer());
			client.setInGame(game);
		}
		GameSetup setup = new GameSetup(players, game);
		board = setup.getBoard();
		game.setgBoard(board);

	}


}


