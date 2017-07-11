package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.EpochTest;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.BonusDice;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by miotto on 11/07/17.
 */
public class BlueCardDeckTest {

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

    private ArrayList<BlueCard> blueArray = new ArrayList<>();
    private BlueCardDeck blueDeck;
    private BlueCard blueCard1 = new BlueCard(3, epochTest, ColorEnumeration.Blue, cardName, effectsTest);
    private BlueCard blueCard2 = new BlueCard(2, new Epoch(EpochEnumeration.THIRD), ColorEnumeration.Blue, "bagato", effectsTest);


    @Before
    public void setup() {
        blueArray.add(blueCard1);
        blueArray.add(blueCard2);
        blueDeck = new BlueCardDeck(blueArray);
    }

    @Test
    public void constructorTest() {
        assertEquals(blueArray, blueDeck.cardList);
    }

    @Test
    public void getCard() throws Exception {
        assertEquals((new Epoch(EpochEnumeration.FIRST)).getID(), blueDeck.getCard(epochTest).getEpoch());
    }

}