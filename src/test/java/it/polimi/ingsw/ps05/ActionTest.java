package it.polimi.ingsw.ps05;

import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.exceptions.*;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.MarketSpace;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.controller.TurnSetupManager;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.server.controller.Game;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alberto on 25/05/2017.
 */
public class ActionTest extends TestCase {

	private Action testActl;
	private Player testPl;
	private Familiar testFm;
	private ArrayList<Effect> testEffectArrayList;
	private ArrayList<ArrayList<ActionResult>> actionResultEffectList;
	private GoldResource testGoldRes;
	private Dice testDiceReq;
	private MarketSpace testMarket;

	private static final int DICE_REQ_AMT = 5;
	private static final int PL_ID_TEST = 0x0dd1;
	public static final String PL_USERNAME_TEST = "romolo_augustolo";
	public static final ColorEnumeration PL_COLOR_TEST = ColorEnumeration.Yellow;
	public static final int AR_GOLD_AMNT = 0xCA220;

	public static final int PL_GOLD_AMNT = 0xCACCA;
	public static final int PL_WOOD_AMNT = 0X53CC0;
	public static final int PL_SERVANTS_AMNT = 0xA1B3;
	public static final int PL_StONE_AMNT = 0xF3DE;
	public static final int PL_FAITH_AMNT = 0x0001;
	public static final int PL_MILITARY_AMNT = 0x0002;
	public static final int PL_VICTORY_AMNT = 0x0003;

	public static final int PL_TEST_STONE = 30;
	public static final int PL_TEST_WOOD = 30;
	public static final int PL_TEST_FAITH = 30;
	public static final int PL_TEST_SERVANTS = 30;
	public static final int PL_TEST_GOLD = 30;
	public static final int PL_TEST_MILITARY = 30;
	public static final int PL_TEST_VICTORY = 30;
	public static final int B_TOWER_NUMBER = 4;
	public static final int T_TILE_NUMBER = 4;
	public static final int B_NUM_ACTIONSPACE_FOR2 = 5;
	public static final int B_NUM_ACTIONSPACE_FOR3 = 7;
	public static final int B_NUM_ACTIONSPACE_FOR4 = 9;
	public static final int B_NUM_ACTIONSPACE_FOR1_LESS = 1;
	public static final int B_NUM_ACTIONSPACE_FOR5_MORE = 9;
	public static final int B_FAITH_PATH = 16;
	public static final int B_MILITARY_PATH = 6;

	public ActionTest(){
		// what to put here?
	}

	public void setUp(){

		//TEST SETUP
		testPl = new Player(ActionTest.PL_ID_TEST, PL_USERNAME_TEST, PL_COLOR_TEST);
		testFm = new Familiar(testPl, ColorEnumeration.Black );
		ArrayList<Familiar> famList = new ArrayList<Familiar>();
		famList.add(this.testFm);
		try {
			testPl.setFamiliars(famList);
		} catch (RepeatedAssignmentException e) {
			
		}
		testFm.setDice(new Dice(ColorEnumeration.Black));
		testEffectArrayList = new ArrayList<Effect>();
		testEffectArrayList.add(new ImmediateEffect());
		testGoldRes = new GoldResource(ActionTest.AR_GOLD_AMNT);
		testDiceReq = new Dice(ActionTest.PL_COLOR_TEST, ActionTest.DICE_REQ_AMT);
		ArrayList<ActionResult> arl = new ArrayList<ActionResult>();
		arl.add(testGoldRes);

		// workaround (seteffectlist for all effect or just for immediateEffect??)
		ImmediateEffect eff = (ImmediateEffect) testEffectArrayList.get(0);
		eff.setEffectList(arl);

		testMarket = new MarketSpace(testDiceReq, testEffectArrayList);
		this.testActl = new Action(testFm, testMarket);

	}

	@Test
	public void testBoard() throws InterruptedException {
		ArrayList<Player> list = new ArrayList<Player>();
		Random randomNum = new Random();
		int numP = randomNum.nextInt(5);
		for (int i = 0; i < numP; i++){
			list.add(new Player(i, "Giocatore " + i, ColorEnumeration.values()[i]));
		}
		Game game = new Game(true, true, numP, null ); //TODO SISTEMARE
		try {
			game.start();
		} catch (InterruptedException exc) {
			//TODO gestire eccezione
			exc.printStackTrace();
		}
		GameSetup gameSetup = new GameSetup(list,game);
		Board board = gameSetup.getBoard();
		assertEquals(ActionTest.B_TOWER_NUMBER, board.getTowerList().size());
		for (int i = 0; i < board.getTowerList().size(); i++){
			assertEquals(ActionTest.T_TILE_NUMBER,board.getTowerList().get(i).getTiles().size());
			for (TowerTileInterface t : board.getTowerList().get(i).getTiles().values()){
				assertNotNull(t.getParentTower());
			}
		}
		if (numP == 2){
			assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR2,board.getActSpacesMap().values().size());
		} else if (numP == 3){
			assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR3,board.getActSpacesMap().values().size());
		} else if (numP == 4){
			assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR4,board.getActSpacesMap().values().size());
		} else if (numP > 4){
			assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR5_MORE,board.getActSpacesMap().values().size());
		} else if (numP < 2){
			assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR1_LESS,board.getActSpacesMap().values().size());
		}
		assertEquals(ActionTest.B_FAITH_PATH,board.getFaithPath().size());
		assertEquals(ActionTest.B_MILITARY_PATH,board.getMilitaryPath().size());

	}

	@Test //il tile non restituisce la carta, o se lo fa è da verificare e da applicare gli effetti
	public void testActionOnTower() throws InterruptedException { //tested just with gold
		ArrayList<Player> list = new ArrayList<Player>();
		Random randomNum = new Random();
		int numP = randomNum.nextInt(4) + 1;
		for (int i = 0; i < numP; i++){
			list.add(new Player(i, "Giocatore " + i, ColorEnumeration.values()[i]));
		}
		Game game = new Game(true, true, numP, null);
		try {
			game.start();
		} catch (InterruptedException exc) {
			//TODO gestire eccezione
			exc.printStackTrace();
		}
		GameSetup gameSetup = new GameSetup(list,game);
		Board board = gameSetup.getBoard();
		TurnSetupManager turnSetup = gameSetup.getTurnSetupManager();
		Turn turn = turnSetup.getTurn();
		Integer gold = turn.getPlayerOrder().get(0).getResource(GoldResource.id).getValue();
		Action action = new Action((Familiar) turn.getPlayerOrder().get(0).getFamilyList().toArray()[0],
				(ActionSpace)board.getTowerList().get(2).getTiles().get(2));
		System.out.println("islegal: " + action.isLegal());
		if (action.isLegal()){
			try {
				action.run(randomNum.nextInt(action.getSuitableReqAlternatives().size()));
				assertTrue(gold != turn.getPlayerOrder().get(0).getResource(GoldResource.id).getValue());
				assertTrue(((Familiar)turn.getPlayerOrder().get(0).getFamilyList().toArray()[0]).isUsed());
				assertTrue(((ActionSpace)board.getTowerList().get(2).getTiles().get(2)).isOccupied());
				assertTrue(turn.getPlayerOrder().get(0).getBlueCardList().size() != 0);
			} catch (IllegalActionException | NotEnoughResourcesException | DiceTooLowException e) {
				e.printStackTrace();
			} catch (IllegalMethodCallException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testActionOnSpace() throws InterruptedException {
		ArrayList<Player> list = new ArrayList<Player>();
		Random randomNum = new Random();
		int numP = randomNum.nextInt(4) + 1;
		for (int i = 0; i < numP; i++){
			list.add(new Player(i, "Giocatore " + i, ColorEnumeration.values()[i]));
		}

		Game game = new Game(true, true, numP, null);
		try {
			game.start();
		} catch (InterruptedException exc) {
			//TODO gestire eccezione
			exc.printStackTrace();
		}
		GameSetup gameSetup = new GameSetup(list,game);
		TurnSetupManager turnSetup = gameSetup.getTurnSetupManager();
		Turn turn = turnSetup.getTurn();
		//ActionSpace space = Board.getInstance().getActSpacesMap().values().get(0);
		//for(int i = 0; i < Board.getInstance().getActSpacesMap().values().size(); i++){
		//	space = Board.getInstance().getActSpacesMap().get(i);
		//	if (Board.getInstance().getActSpacesMap().values().get(i) instanceof MarketSpace){
		//		break;
		//	}
		// }
		// RISISTEMARE STA ROBA SU
		/* System.out.println(space.getClass().toString());
		Action action = new Action(turn.getPlayerOrder().get(0).getFamilyList().get(0), space);
		if (action.isLegal()){
			System.out.println("isLegal");
			try {
				//fallisce perché non ha niente da produrre in marketspace o productspace
				//applyeffect di councilspace è vuoto
				action.run(randomNum.nextInt(action.getSuitableReqAlternatives().size()));

			} catch (IllegalActionException | NotEnoughResourcesException | DiceTooLowException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}

	@Test
	public void testEffects(){
		// 
	}

	@Test
	public void testActionIsLegal(){
		testPl.addResource(new ServantResource(ActionTest.PL_SERVANTS_AMNT));
		assertTrue(this.testActl.isLegal());
	}

	public void testMarket(){

		assertNotNull(this.testMarket.getRequirements());
	}

}
