package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.EndGameEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by miotto on 10/07/17.
 */
public class GreenCardTest {

    private Player player = new Player(1, "andonio", ColorEnumeration.Blue);
    private Familiar familiar = new Familiar(player, ColorEnumeration.Black);

    private int idprova = 2;
    private int referenceId = 87;
    private Epoch epochTest = new Epoch(EpochEnumeration.SECOND);
    private ColorEnumeration color = ColorEnumeration.White;
    private String cardName = "belleeesssimoXDXDXDX";
    private ArrayList<Resource> firstRequirement = new ArrayList<>();
    private ArrayList<Resource> secondRequirement = new ArrayList<>();
    private ArrayList<Resource> thirdRequirement = new ArrayList<>();
    private ArrayList<ArrayList<Resource>> requirementsTest = new ArrayList<>();
    private ArrayList<Effect> effectsTest = new ArrayList<>();

    private BonusDice bonusDice = new BonusDice(3, ColorEnumeration.Black, false);

    GreenCard card;
    ArrayList<GreenCard> playerGreenCards = new ArrayList<>();

    @Before
    public void setup() {
        firstRequirement.add(new FaithResource(7));
        secondRequirement.add(new StoneResource(1));
        thirdRequirement.add(new ServantResource(3));

        requirementsTest.add(firstRequirement);
        requirementsTest.add(secondRequirement);

        effectsTest.add(new EndGameEffect());

        card = new GreenCard(referenceId, epochTest, color, cardName,
                requirementsTest, effectsTest);

        card.setToBeActivated(true);
    }

    @Test
    public void constructorTest() throws Exception {
        assertEquals(referenceId, (int)card.getReferenceID());
        assertEquals(color, card.color);
        assertEquals(cardName, card.cardName);
        assertEquals(epochTest, card.epoch);

        assertEquals(requirementsTest.get(0), card.getRequirements().get(0));
        assertEquals(effectsTest.get(0), card.getEffects().get(0));
    }

    @Test
    public void getRequirements() throws Exception {
        assertEquals(card.getRequirements(), requirementsTest);
    }

    @Test
    public void applyHarvestableEffects() throws Exception {
        card.setToBeActivated(true);

    }

    @Test
    public void getToBeActivated() throws Exception {
        assertEquals(true, card.getToBeActivated());
    }

    @Test
    public void setToBeActivated() throws Exception {
        boolean bool = false;
        card.setToBeActivated(bool);
        assertEquals(bool, card.getToBeActivated());
    }

    @Test
    public void moveToPlayer() throws Exception {
        card.moveToPlayer(player);
        playerGreenCards = player.getGreenCardList();
        assertEquals(card.getReferenceID(), playerGreenCards.get(0).getReferenceID());
    }

    @Test
    public void applyNonActivableEffects() throws Exception {
    }

    @Test
    public void getActivableEffectList() throws Exception {
    }

}