package it.polimi.ingsw.ps05;

import it.polimi.ingsw.ps05.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.controller.GameSetup;
import it.polimi.ingsw.ps05.model.*;
import junit.framework.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alberto on 25/05/2017.
 */
public class ActionTest extends TestCase{

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
		testPl.setFamiliars(famList);
		testFm.setDice(new Dice(ColorEnumeration.Black));
		testEffectArrayList = new ArrayList<Effect>();
		testEffectArrayList.add(new ImmediateEffect());
		testGoldRes = new GoldResource(ActionTest.AR_GOLD_AMNT);
		testDiceReq = new Dice(ActionTest.PL_COLOR_TEST, ActionTest.DICE_REQ_AMT);
		ArrayList<ActionResult> arl = new ArrayList<ActionResult>();
		arl.add(testGoldRes);
		actionResultEffectList = new ArrayList<>();
		actionResultEffectList.add(arl);

		// workaround (seteffectlist for all effect or just for immediateEffect??)
		ImmediateEffect eff = (ImmediateEffect) testEffectArrayList.get(0);
		eff.setEffectList(actionResultEffectList);

		testMarket = new MarketSpace(testDiceReq, testEffectArrayList);
		this.testActl = new Action(testFm, testMarket);
	}

	@Test
	public void testBoard(){
		//for (int j = 0; j < 20; j++){

			ArrayList<Player> list = new ArrayList<Player>();
			Random randomNum = new Random();
			int numP = randomNum.nextInt(8);
			for (int i = 0; i < numP; i++){
				list.add(new Player(i, "Giocatore " + i, ColorEnumeration.values()[i]));
			}
			GameSetup gameSetup = new GameSetup(list);
			Board board = gameSetup.getBoard();
			assertEquals(ActionTest.B_TOWER_NUMBER, board.getTowerList().size());
			for (int i = 0; i < board.getTowerList().size(); i++){
				assertEquals(ActionTest.T_TILE_NUMBER,board.getTowerList().get(i).getTiles().size());
			}
			if (numP == 2){
				assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR2,board.getActionSpace().size());
			} else if (numP == 3){
				assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR3,board.getActionSpace().size());
			} else if (numP == 4){
				assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR4,board.getActionSpace().size());
			} else if (numP > 4){
				assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR5_MORE,board.getActionSpace().size());
			} else if (numP < 2){
				assertEquals(ActionTest.B_NUM_ACTIONSPACE_FOR1_LESS,board.getActionSpace().size());
			}
			assertEquals(ActionTest.B_FAITH_PATH,board.getFaithPath().size());
			assertEquals(ActionTest.B_MILITARY_PATH,board.getMilitaryPath().size());

		//}
	}

	@Test
	public  void testPlayer(){

		assertEquals(ActionTest.PL_ID_TEST, testPl.getPlayerID());
		assertEquals(ActionTest.PL_USERNAME_TEST, testPl.getUsername());
		assertSame(this.testFm, testPl.getFamilyList().get(0));
		assertEquals(new Integer(0), (Integer) testPl.getResource("Pietra").getValue());
		assertEquals(0, (int) testPl.getResource("Oro").getValue());
		assertEquals(0, (int) testPl.getResource("Fede").getValue());
		assertEquals(0, (int) testPl.getResource("Legno").getValue());
		assertEquals(0, (int) testPl.getResource("Vittoria").getValue());

		WoodResource wood = new WoodResource(ActionTest.PL_TEST_WOOD);
		wood.applyResult(testPl);
		StoneResource stone = new StoneResource(ActionTest.PL_TEST_STONE);
		stone.applyResult(testPl);
		FaithResource faith = new FaithResource(ActionTest.PL_TEST_FAITH);
		faith.applyResult(testPl);
		MilitaryResource military = new MilitaryResource(ActionTest.PL_TEST_MILITARY);
		military.applyResult(testPl);
		VictoryResource victory = new VictoryResource(ActionTest.PL_TEST_MILITARY);
		victory.applyResult(testPl);
		ServantResource servant = new ServantResource(ActionTest.PL_TEST_MILITARY);
		servant.applyResult(testPl);
		GoldResource gold = new GoldResource(ActionTest.PL_TEST_MILITARY);
		gold.applyResult(testPl);

		assertEquals(ActionTest.PL_TEST_WOOD, (int) testPl.getResource("Legno").getValue());
		assertEquals(ActionTest.PL_TEST_STONE, (int) testPl.getResource("Pietra").getValue());
		assertEquals(ActionTest.PL_TEST_FAITH, (int) testPl.getResource("Fede").getValue());
		assertEquals(ActionTest.PL_TEST_MILITARY, (int) testPl.getResource("Militare").getValue());
		assertEquals(ActionTest.PL_TEST_VICTORY, (int) testPl.getResource("Vittoria").getValue());
		assertEquals(ActionTest.PL_TEST_SERVANTS, (int) testPl.getResource("Servitori").getValue());
		assertEquals(ActionTest.PL_TEST_GOLD, (int) testPl.getResource("Oro").getValue());
	}

	@Test
	public void testFamiliar(){

		// todo ?

	}

	@Test
	public void testEffects(){
		// todo
	}

	@Test
	public void testDices(){
		// todo
		assertEquals(ActionTest.DICE_REQ_AMT,(int) this.testDiceReq.getValue());
		assertEquals(ActionTest.PL_COLOR_TEST,this.testDiceReq.getColor());
		for (int i = 0; i < 1000000; i++){
			Dice dice = new Dice(ActionTest.PL_COLOR_TEST);
			assertTrue(1 <= (int)dice.getValue() && (int)dice.getValue() <= 6);
		}
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
