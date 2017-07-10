package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.EndGameEffect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by miotto on 10/07/17.
 */
public class BlueCardTest {

    private Player player = new Player(1, "andonio", ColorEnumeration.Blue);
    private Familiar familiar = new Familiar(player, ColorEnumeration.Black);

    private int idprova = 56;
    private int referenceId = 4;
    private Epoch epochTest = new Epoch(EpochEnumeration.FIRST);
    private ColorEnumeration color = ColorEnumeration.Black;
    private String cardName = "belleeesssimoXDXDXDX";
    private ArrayList<Resource> firstRequirement = new ArrayList<>();
    private ArrayList<Resource> secondRequirement = new ArrayList<>();
    private ArrayList<Resource> thirdRequirement = new ArrayList<>();
    private ArrayList<ArrayList<Resource>> requirementsTest = new ArrayList<>();
    private ArrayList<Effect> effectsTest = new ArrayList<>();

    private BonusDice bonusDice = new BonusDice(3, ColorEnumeration.Blue, true);

    BlueCard card;
    ArrayList<BlueCard> playerBlueCards = new ArrayList<>();


    @Before
    public void setup() {
        firstRequirement.add(new FaithResource(3));
        //firstRequirement.add(new WoodResource(5));
        secondRequirement.add(new StoneResource(5));
        thirdRequirement.add(new ServantResource(10));

        requirementsTest.add(firstRequirement);
        requirementsTest.add(secondRequirement);

        //effectsTest.add();
        effectsTest.add(new EndGameEffect());

        card = new BlueCard(referenceId, epochTest, color, cardName,
                requirementsTest, effectsTest);
    }

    @Test
    public void constructorTest() throws Exception {
        assertEquals(referenceId, (int)card.getReferenceID());
        assertEquals(color, card.color);
        assertEquals(cardName, card.cardName);
        assertEquals(epochTest, card.epoch);
        for(int i = 0; i < 1; i++) {
            assertEquals(requirementsTest.get(i), card.getRequirements().get(i));
            assertEquals(effectsTest.get(i), card.getEffects().get(i));
        }
    }

    @Test
    public void moveToPlayer() throws Exception {
        card.moveToPlayer(player);
        playerBlueCards = player.getBlueCardList();
        assertEquals(card.getReferenceID(), playerBlueCards.get(0).getReferenceID());
    }

    @Test
    public void applyNonActivableEffects() throws Exception {
        card.applyNonActivableEffects(player);
        // assertEquals();
    }


}