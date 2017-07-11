package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.cards.GreenCardDeck;
import it.polimi.ingsw.ps05.model.cards.YellowCardDeck;
import it.polimi.ingsw.ps05.model.effects.Effect;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by miotto on 11/07/17.
 */
public class YellowCardTest {

    YellowCard yellowCard;
    private Player player = new Player(1, "andonio", ColorEnumeration.Blue);
    private Familiar familiar = new Familiar(player, ColorEnumeration.Black);

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

    private ArrayList<it.polimi.ingsw.ps05.model.cards.YellowCard> yellowArray = new ArrayList<>();
    private YellowCardDeck yellowCardDeck;
    private it.polimi.ingsw.ps05.model.cards.YellowCard yellowCard1 = new it.polimi.ingsw.ps05.model.cards.YellowCard(3, epochTest, ColorEnumeration.Blue, cardName, effectsTest);
    private it.polimi.ingsw.ps05.model.cards.YellowCard yellowCard2 = new it.polimi.ingsw.ps05.model.cards.YellowCard(2, new Epoch(EpochEnumeration.THIRD), ColorEnumeration.Blue, "bagato", effectsTest);

    @Before
    public void setup() {
        yellowCard = new YellowCard();
        yellowCard.setValue(2);

        yellowArray.add(yellowCard1);
        yellowArray.add(yellowCard2);
        yellowCardDeck = new YellowCardDeck(yellowArray);

        player.addYellowCard(yellowCard1);
    }


    @Test
    public void hasEnoughResources() throws Exception {
    }

    @Test
    public void setValue() throws Exception {
        assertEquals(2, (int)yellowCard.getValue());
    }

    @Test
    public void getValue() throws Exception {
        yellowCard.setValue(3);
        assertEquals(3, (int)yellowCard.getValue());
    }

    @Test
    public void getID() throws Exception {
        assertEquals("Carta Gialla", yellowCard.getID());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Carta gialla", yellowCard.toString());
    }

}