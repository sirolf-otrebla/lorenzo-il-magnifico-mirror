package it.polimi.ingsw.ps05.model.excomm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps05.model.*;



import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.effects.EffectType;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.*;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.net.FakeConnection;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

public class ExcommTest {
	
	ArrayList<Resource> resourceArrayList = new ArrayList<>();
	ArrayList<ActionResult> actionResults = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	private Game game;
	private Board board;
	ArrayList<ExcommunicationCard> list ;
	
	@Before
	public void setUp(){
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

	@Test
	public void bluCardMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		BlueCardMalusExcomm m = new BlueCardMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		BlueBonus b = new BlueBonus(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void diceMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		DiceMalusExcomm m = new DiceMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		AllBonus b = new AllBonus(-1);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void goldMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		GoldMalusExcomm m = new GoldMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		GoldResource b = new GoldResource(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void greenCardMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		GreenCardMalusExcomm m = new GreenCardMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		GreenBonus b = new GreenBonus(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
	}
	
	@Test
	public void harvestMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		HarvestingMalusExcomm m = new HarvestingMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		HarvestBonus b = new HarvestBonus(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void militaryMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		MilitaryMalusExcomm m = new MilitaryMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		MilitaryResource b = new MilitaryResource(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void noActionMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		NoActionExcomm m = new NoActionExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		NoFirstAction b = new NoFirstAction();
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void noBluPointsTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		NoBlueVictoryPtsExcomm m = new NoBlueVictoryPtsExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		ArrayList<ActionResult> list = new ArrayList<>();
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());	
	}
	
	@Test
	public void noGreenPointsTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		NoGreenVictoryPtsExcomm m = new NoGreenVictoryPtsExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		ArrayList<ActionResult> list = new ArrayList<>();
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());	
	}
	
	@Test
	public void noMarketTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		NoMarketExcomm m = new NoMarketExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		NoMarket b = new NoMarket();
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void noVioletPointsTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		NoVioletVictoryPtsExcomm m = new NoVioletVictoryPtsExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		ArrayList<ActionResult> list = new ArrayList<>();
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void noProductionTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		ProductionMalusExcomm m = new ProductionMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		ProductionBonus b = new ProductionBonus(-2);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void servantsMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		ServantMalusExcomm m = new ServantMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		ServantResource b = new ServantResource(-1);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());	
	}
	
	@Test
	public void violetCardMaljusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		VioletCardMalusExcomm m = new VioletCardMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		VioletBonus b = new VioletBonus(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void woodStoneMalusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		WoodStoneMalusExcomm m = new WoodStoneMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		WoodResource b = new WoodResource(-1);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}
	
	@Test
	public void yellowCardMaljusTest() throws RepeatedAssignmentException {
		FaithResource f = new FaithResource(3);
		EpochEnumeration e = EpochEnumeration.FIRST;
		YellowCardMalusExcomm m = new YellowCardMalusExcomm();
		m.setGame(game);
		assertEquals(game,m.getGame());
		assertEquals(EffectType.EXCOM, m.getEffectType());
		YellowBonus b = new YellowBonus(-4);
		b.setGame(game);
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(b);
		m.setMalus(list);
		ExcommunicationCard card = new ExcommunicationCard(f, e, m);
		assertEquals(f,card.getFaithRequested());
		assertEquals(e, card.getEpochID());
		assertEquals(m,card.getExcommEffect());
		assertNotNull(m.toString());
			
	}

}
