package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.cards.GreenCardDeck;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by miotto on 11/07/17.
 */
public class NoColorBonusTest {

    Game game;
    GameSetup setup;
    ArrayList<Player> pList = new ArrayList<>();
    ArrayList<PlayerClient> pcList = new ArrayList<>();

    private final boolean LEADER = false;
    private final boolean TILE = false;
    private final int ID = 117;
    private final int ZERO = 0;
    private Player player = new Player(1, "andonio", ColorEnumeration.Blue);
    private Familiar familiar = new Familiar(player, ColorEnumeration.Any);

    private int idprova = 2;
    private int referenceId = 87;
    private Epoch epochTest = new Epoch(EpochEnumeration.FIRST);
    private ColorEnumeration color = ColorEnumeration.White;
    private String cardName = "belleeesssimoXDXDXDX";
    private ArrayList<Resource> firstRequirement = new ArrayList<>();
    private ArrayList<Resource> secondRequirement = new ArrayList<>();
    private ArrayList<Resource> thirdRequirement = new ArrayList<>();
    private ArrayList<ArrayList<Resource>> requirementsTest = new ArrayList<>();
    private ArrayList<Effect> effectsTest = new ArrayList<>();

    private BonusDice bonusDice = new BonusDice(3, ColorEnumeration.Black, false);

    private ArrayList<it.polimi.ingsw.ps05.model.cards.GreenCard> greenArray = new ArrayList<>();
    private GreenCardDeck greenDeck;
    private it.polimi.ingsw.ps05.model.cards.GreenCard greenCard1 = new it.polimi.ingsw.ps05.model.cards.GreenCard(3, epochTest, ColorEnumeration.Blue, cardName, effectsTest);
    private it.polimi.ingsw.ps05.model.cards.GreenCard greenCard2 = new it.polimi.ingsw.ps05.model.cards.GreenCard(2, new Epoch(EpochEnumeration.THIRD), ColorEnumeration.Blue, "bagato", effectsTest);

    private NoColorBonus noColorBonus;

    @Before
    public void setup() throws Exception{
        noColorBonus = new NoColorBonus(3);
        pList.add(new Player(0, "luca", ColorEnumeration.Blue));
        pList.add(new Player(1, "alberto", ColorEnumeration.Green));
        pList.add(new Player(2, "andrea", ColorEnumeration.Violet));
        pList.add(new Player(3, "franco", ColorEnumeration.Yellow));



        game = new Game(LEADER, TILE, ID, pcList);
        game.start();
    }

    @Test
    public void constructorTest() throws Exception {
        int i;

        i = noColorBonus.getValue();
        assertEquals(3, i);

    }

    @Test
    public void setValue() throws Exception {
        noColorBonus.setValue(5);
        assertEquals(5, (int)noColorBonus.getValue());
    }

    @Test
    public void getValue() throws Exception {
        noColorBonus.setValue(6);
        assertEquals(6, (int)noColorBonus.getValue());
    }

    @Test
    public void applyResult() throws Exception {
    }

    @Test
    public void setGame() throws Exception {
        noColorBonus.setGame(game);
        assertEquals(game, noColorBonus.getGame());
    }

    @Test
    public void getGame() throws Exception {
        noColorBonus.setGame(game);
        assertEquals(game, noColorBonus.getGame());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Bonus neutro", noColorBonus.toString());
    }

    /*
    @Test
    public void resetResult() throws Exception {
        familiar.setDice(new Dice(ColorEnumeration.Any, 5));
        player.getFamilyList().add(familiar);
        noColorBonus.resetResult(player);
        assertEquals(2, (int)player.getFamilyMap().get(ColorEnumeration.Any).getRelatedDice().getValue());
    }
    */

}