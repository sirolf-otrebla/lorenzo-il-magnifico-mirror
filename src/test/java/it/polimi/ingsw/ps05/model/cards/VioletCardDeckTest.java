package it.polimi.ingsw.ps05.model.cards;

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
public class VioletCardDeckTest {

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

    private ArrayList<VioletCard> violetArray = new ArrayList<>();
    private VioletCardDeck violetCardDeck;
    private VioletCard violetCard1 = new VioletCard(3, epochTest, ColorEnumeration.Blue, cardName, effectsTest);
    private VioletCard violetCard2 = new VioletCard(2, new Epoch(EpochEnumeration.THIRD), ColorEnumeration.Blue, "bagato", effectsTest);


    @Before
    public void setup() {
        violetArray.add(violetCard1);
        violetArray.add(violetCard2);
        violetCardDeck = new VioletCardDeck(violetArray);
    }

    @Test
    public void constructorTest() {
        assertEquals(violetArray, violetCardDeck.cardList);
    }

    @Test
    public void getCard() throws Exception {
        assertEquals((new Epoch(EpochEnumeration.FIRST)).getID(), violetCardDeck.getCard(epochTest).getEpoch());
    }

}