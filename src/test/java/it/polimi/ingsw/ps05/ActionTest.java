package it.polimi.ingsw.ps05;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.*;
import junit.framework.*;
import org.junit.Test;

import java.util.ArrayList;

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

    private static final int DICE_REQ_AMT = 0x0A;
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





    public ActionTest(){
            // what to put here?
    }

    public void setUp(){

        //TEST SETUP
        testPl = new Player(this.PL_ID_TEST, PL_USERNAME_TEST, PL_COLOR_TEST);
        testFm = new Familiar(testPl, ColorEnumeration.Black );
        ArrayList<Familiar> famList = new ArrayList<Familiar>();
        famList.add(this.testFm);
        testPl.setFamiliars(famList);
        testFm.setDice(new Dice(ColorEnumeration.Black));
        testEffectArrayList = new ArrayList<Effect>();
        testEffectArrayList.add(new ImmediateEffect());
        testGoldRes = new GoldResource(this.AR_GOLD_AMNT);
        testDiceReq = new Dice(ColorEnumeration.Any, this.DICE_REQ_AMT);
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
    public  void testPlayer(){

        assertEquals(this.PL_ID_TEST, testPl.getPlayerID());
        assertEquals(this.PL_USERNAME_TEST, testPl.getUsername());
        assertSame(this.testFm, testPl.getFamilyList().get(0));
        assertEquals(new Integer(0), (Integer) testPl.getStone().getValue());
        assertEquals(0, (int) testPl.getGold().getValue());
        assertEquals(0, (int) testPl.getFaithPts().getValue());
        assertEquals(0, (int) testPl.getWood().getValue());
        assertEquals(0, (int) testPl.getVictoryPts().getValue());

        WoodResource wood = new WoodResource(this.PL_TEST_WOOD);
        wood.applyResult(testPl);

        assertEquals(this.PL_TEST_WOOD, (int) testPl.getWood().getValue());
        assertEquals(this.PL_TEST_STONE, (int) testPl.getStone().getValue());
        assertEquals(this.PL_TEST_FAITH, (int) testPl.getFaithPts().getValue());
        assertEquals(this.PL_TEST_MILITARY, (int) testPl.getMilitaryPts().getValue());
        assertEquals(this.PL_TEST_VICTORY, (int) testPl.getVictoryPts().getValue());
        assertEquals(this.PL_TEST_SERVANTS, (int) testPl.getServants().getValue());
        assertEquals(this.PL_TEST_GOLD, (int) testPl.getGold().getValue());
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
    }

    @Test
    public void testActionIsLegal(){
        testPl.addServant(new ServantResource(this.PL_SERVANTS_AMNT));
        assertTrue(this.testActl.isLegal());
    }

    public void testMarket(){

        assertNotNull(this.testMarket.getRequirements());
    }

}
