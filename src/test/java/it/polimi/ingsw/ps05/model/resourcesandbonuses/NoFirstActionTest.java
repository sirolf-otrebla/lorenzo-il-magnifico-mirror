package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.cards.GreenCardDeck;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.spaces.*;
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
public class NoFirstActionTest {

    Game game;
    GameSetup setup;
    ArrayList<Player> pList = new ArrayList<>();
    ArrayList<PlayerClient> pcList = new ArrayList<>();
    Board board;

    private Tower tower1 = new BlueTower();
    private Tower tower2 = new GreenTower();
    private Tower tower3 = new YellowTower();
    private Tower tower4 = new VioletTower();
    private final int N_SPACE = 9;
    private final int N_TILE = 4;
    private final int N_EXCOMM = 3;
    private final int N_LEADER = 20;
    private final int N_TOWER = 4;
    private boolean COMPLETE = false;
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

    private NoFirstAction noFirstAction;

    @Before
    public void setup() throws Exception{
        pList.add(new Player(0, "luca", ColorEnumeration.Blue));
        pList.add(new Player(1, "alberto", ColorEnumeration.Green));
        pList.add(new Player(2, "andrea", ColorEnumeration.Violet));
        pList.add(new Player(3, "franco", ColorEnumeration.Yellow));

        game = new Game(COMPLETE, false, 0, pcList);
        game.start();
        setup = new GameSetup(pList, game);

        board = setup.getBoard();
        game.setgBoard(board);

        noFirstAction = new NoFirstAction();
        noFirstAction.setGame(game);
    }
    @Test
    public void applyResult() throws Exception {
    }

    @Test
    public void setGame() throws Exception {
        assertEquals(game, noFirstAction.getGame());
    }

    @Test
    public void getGame() throws Exception {
        assertEquals(game, noFirstAction.getGame());
    }

    @Test
    public void resetResult() throws Exception {
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Non esegui la prima azione del turno", noFirstAction.toString());
    }

}