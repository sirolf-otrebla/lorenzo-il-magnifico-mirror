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
public class YellowCardTest {

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

    YellowCard card;
    ArrayList<YellowCard> playerYellowCards = new ArrayList<>();

    private int selectedEffects = 1;

    @Before
    public void setup() {
        firstRequirement.add(new FaithResource(7));
        secondRequirement.add(new StoneResource(1));
        thirdRequirement.add(new ServantResource(3));

        requirementsTest.add(firstRequirement);
        requirementsTest.add(secondRequirement);

        effectsTest.add(new EndGameEffect());

        card = new YellowCard(3, epochTest, ColorEnumeration.Yellow, cardName, effectsTest);
        card.setSelectedEffects(selectedEffects);

    }

    @Test
    public void getSelectedEffects() throws Exception {
        assertEquals(selectedEffects, card.getSelectedEffects());
    }

    @Test
    public void setSelectedEffects() throws Exception {
        card.setSelectedEffects(3);
        assertEquals(3, card.getSelectedEffects());
    }

    @Test
    public void applyProductionEffects() throws Exception {
    }

    @Test
    public void getToBeActivated() throws Exception {
    }

    @Test
    public void setToBeActivated() throws Exception {
    }

    @Test
    public void moveToPlayer() throws Exception {
    }

    @Test
    public void applyNonActivableEffects() throws Exception {
    }

    @Test
    public void getActivableEffectList() throws Exception {
    }

}