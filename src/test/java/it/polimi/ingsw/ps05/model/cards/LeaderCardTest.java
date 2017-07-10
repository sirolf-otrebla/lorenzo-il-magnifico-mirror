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
public class LeaderCardTest {

    private Player player = new Player(3, "andonio", ColorEnumeration.Green);
    private Familiar familiar = new Familiar(player, ColorEnumeration.Black);

    private int idprova = 2;
    private int referenceId = 11;
    private Epoch epochTest = new Epoch(EpochEnumeration.THIRD);
    private ColorEnumeration color = ColorEnumeration.White;
    private String cardName = "belleeesssimoXDXDXDX";
    private ArrayList<Resource> firstRequirement = new ArrayList<>();
    private ArrayList<Resource> secondRequirement = new ArrayList<>();
    private ArrayList<Resource> thirdRequirement = new ArrayList<>();
    private ArrayList<ArrayList<Resource>> requirementsTest1 = new ArrayList<>();
    private ArrayList<Effect> effectsTest = new ArrayList<>();

    private BonusDice bonusDice = new BonusDice(3, ColorEnumeration.Black, false);

    LeaderCard card;
    ArrayList<LeaderCard> playerLeaderCards = new ArrayList<>();

    @Before
    public void setup() {
        firstRequirement.add(new FaithResource(7));
        secondRequirement.add(new StoneResource(1));
        thirdRequirement.add(new ServantResource(3));

        requirementsTest1.add(firstRequirement);
        requirementsTest1.add(secondRequirement);

        effectsTest.add(new EndGameEffect());

        card = new LeaderCard(referenceId);
        card.setRequirements(requirementsTest1);
        card.setEffects(effectsTest);
        card.setActive(true);

        playerLeaderCards.add(card);
    }

    @Test
    public void getRequirements() throws Exception {
        assertEquals(requirementsTest1, card.getRequirements());
    }

    @Test
    public void getEffects() throws Exception {
        assertEquals(effectsTest, card.getEffects());
    }

    @Test
    public void isActive() throws Exception {
        assertEquals(true, card.isActive());
    }

    @Test
    public void discard() throws Exception {
        card.discard(player);
        assertEquals(0, player.getLeaderCardList().size());
    }

    @Test
    public void getEpoch() throws Exception {
        assertEquals(EpochEnumeration.NO_EPOCH, card.getEpoch());
    }

    @Test
    public void getName() throws Exception {
        card.setCardName(cardName);
        assertEquals(cardName, card.getCardName());
    }

    @Test
    public void setRequirements() throws Exception {
        assertEquals(requirementsTest1, card.getRequirements());
    }

    @Test
    public void setEffects() throws Exception {
        assertEquals(effectsTest, card.getEffects());
    }

    @Test
    public void setCardName() throws Exception {
        card.setCardName(cardName);
        assertEquals(cardName, card.getCardName());
    }

    @Test
    public void getReferenceID() throws Exception {
        assertEquals(referenceId, (int)card.getReferenceID());
    }

}