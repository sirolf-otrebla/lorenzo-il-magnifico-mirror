package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.effects.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created by miotto on 09/07/17.
 */
public class TowerCardTest {

    private int idprova = 56;
    private int referenceId = 4;
    private Epoch epochTest = new Epoch(EpochEnumeration.FIRST);
    private ColorEnumeration color = ColorEnumeration.Black;
    private String cardName = "belleeesssimoXDXDXDX";
    private ArrayList<Resource> firstRequirement = new ArrayList<>();
    private ArrayList<Resource> secondRequirement = new ArrayList<>();
    private ArrayList<ArrayList<Resource>> requirementsTest = new ArrayList<>();
    private ArrayList<Effect> effectsTest = new ArrayList<>();
    GreenCard greenCard;

    @Before
    public void setup() {
        firstRequirement.add(new GoldResource(3));
        //firstRequirement.add(new WoodResource(5));
        secondRequirement.add(new MilitaryResource(5));

        requirementsTest.add(firstRequirement);
        requirementsTest.add(secondRequirement);

        effectsTest.add(new ImmediateEffect());
        effectsTest.add(new EndGameEffect());

        greenCard = new GreenCard(referenceId, epochTest, color, cardName,
                requirementsTest, effectsTest);
    }

    @Test
    public void TowerCardConstructor() throws Exception{
        assertEquals(referenceId, (int)greenCard.getReferenceID());
        assertEquals(color, greenCard.color);
        assertEquals(cardName, greenCard.cardName);
        assertEquals(epochTest, greenCard.epoch);
        for(int i = 0; i < 2; i++) {
            assertEquals(requirementsTest.get(i), greenCard.getRequirements().get(i));
            assertEquals(effectsTest.get(i), greenCard.getEffects().get(i));
        }
    }


    @Test
    public void addFalseResource() throws Exception {
        greenCard.addFalseResource();
        for(ArrayList<Resource> requirement: greenCard.getRequirements())
            assertEquals(AlwaysUnFullFilledResource.id, requirement.get(1).getID());
    }

    @Test
    public void removeFalseResource() throws Exception {
        /*
        greenCard.addFalseResource();
        greenCard.removeFalseResource();
        for(ArrayList<Resource> requirement: greenCard.getRequirements())
            assertNull(requirement.get(1));
        */
    }

    @Test
    public void getRequirements() throws Exception {
        assertEquals(greenCard.getRequirements(), requirementsTest);
    }

    @Test
    public void getEffects() throws Exception {
        assertEquals(effectsTest, greenCard.getEffects());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(cardName, greenCard.getName());
    }

    @Test
    public void getEpoch() throws Exception {
        assertEquals(epochTest.getID(), greenCard.getEpoch());
    }

    @Test
    public void applyNonActivableEffects() throws Exception {
        //TODO
    }

    @Test
    public void getReferenceID() throws Exception {
        assertEquals(referenceId, (int)greenCard.getReferenceID());
    }

    @Test
    public void moveToPlayer() throws Exception {

    }

    @Test
    public void setReferenceID() throws Exception {
        greenCard.setReferenceID(idprova);
        assertEquals(idprova, (int)greenCard.getReferenceID());
    }

}