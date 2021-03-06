package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.cards.GreenCardDeck;
import it.polimi.ingsw.ps05.model.effects.Effect;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by miotto on 11/07/17.
 */
public class GreenCardTest {

    GreenCard greenCard;
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

    private ArrayList<it.polimi.ingsw.ps05.model.cards.GreenCard> greenArray = new ArrayList<>();
    private GreenCardDeck greenDeck;
    private it.polimi.ingsw.ps05.model.cards.GreenCard greenCard1 = new it.polimi.ingsw.ps05.model.cards.GreenCard(3, epochTest, ColorEnumeration.Blue, cardName, effectsTest);
    private it.polimi.ingsw.ps05.model.cards.GreenCard greenCard2 = new it.polimi.ingsw.ps05.model.cards.GreenCard(2, new Epoch(EpochEnumeration.THIRD), ColorEnumeration.Blue, "bagato", effectsTest);



    @Before
    public void setup() {
        greenCard = new GreenCard();
        greenCard.setValue(2);

        greenArray.add(greenCard1);
        greenArray.add(greenCard2);
        greenDeck = new GreenCardDeck(greenArray);

        player.addGreenCard(greenCard1);
    }



    /*
    @Test
    public void hasEnoughResources() throws Exception {
        assertEquals(false, greenCard.hasEnoughResources(player.getFamilyMember(ColorEnumeration.Black)));
    }
    */

    @Test
    public void setValue() throws Exception {
        assertEquals(2, (int)greenCard.getValue());
    }

    @Test
    public void getValue() throws Exception {
        greenCard.setValue(3);
        assertEquals(3, (int)greenCard.getValue());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Carta verde", greenCard.toString());
    }

}