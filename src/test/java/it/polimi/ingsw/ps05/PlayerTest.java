package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.BonusTile;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.cards.BlueCard;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.cards.VioletCard;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.BlueBonus;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;

public class PlayerTest {
	
	private final int ID_TEST = 12;
	private final String USR_TEST = "Luca";
	private final ColorEnumeration c = ColorEnumeration.Green;
	private final int ZERO = 0;
	private final int UNO = 1;
	private final int SETTE = 7;
	private final GoldResource RES_TEST = new GoldResource(SETTE);
	
	Player p = new Player(ID_TEST, USR_TEST, c);

	@Test
	public void playerConstructor() throws RepeatedAssignmentException {
		assertEquals(ID_TEST, p.getPlayerID());
		assertEquals(USR_TEST, p.getUsername());
		assertEquals(c, p.getColor());
		
		assertEquals(ZERO, p.getBlueCardList().size());
		assertEquals(ZERO, p.getFamilyList().size());
		assertEquals(ZERO, p.getGreenCardList().size());
		assertEquals(ZERO, p.getYellowCardList().size());
		assertEquals(ZERO, p.getVioletCardList().size());
		assertEquals(ZERO, p.getLeaderCardList().size());
		assertEquals(SETTE, p.getResourceList().size());
		RES_TEST.applyResult(p);
		assertEquals(SETTE, (int) p.getResource(RES_TEST.getID()).getValue());
		assertEquals(ZERO, p.getPermanentBonusList().size());
		assertNull(p.getBonusTile());
		Familiar f = p.createGhostFamiliar(5);
		assertNotNull(f);
		assertEquals(5,(int)f.getRelatedDice().getValue());
		assertEquals(ColorEnumeration.Ghost, f.getColor());
		assertEquals(p,f.getRelatedPlayer());
		
		BonusTile bonus = new BonusTile();
		assertNull(p.getBonusTile());
		p.setBonusTile(bonus);
		assertEquals(bonus,p.getBonusTile());
		
		BlueBonus b = new BlueBonus();
		p.addPermanentEffectRes(b);
		assertTrue( UNO == p.getPermanentBonusList().size());
		assertEquals(b,p.getPermanentBonusList().get(0));
		
		BlueCard blueCard = new BlueCard();
		blueCard.setReferenceID(5);
		p.addBlueCard(blueCard);
		assertTrue( UNO == p.getBlueCardList().size());
		assertTrue(UNO == p.getBlueCardHashMap().size());
		assertEquals(blueCard,p.getBlueCardList().get(0));
		assertEquals(blueCard,p.getBlueCardHashMap().get(blueCard.getReferenceID()));
		p.removeBlueCard();
		assertTrue(ZERO == p.getBlueCardList().size());
		assertTrue(ZERO == p.getBlueCardHashMap().size());
		
		GreenCard greenCard = new GreenCard();
		greenCard.setReferenceID(5);
		p.addGreenCard(greenCard);
		assertTrue( UNO == p.getGreenCardList().size());
		assertTrue(UNO == p.getGreenCardHashMap().size());
		assertEquals(greenCard,p.getGreenCardList().get(0));
		assertEquals(greenCard,p.getGreenCardHashMap().get(greenCard.getReferenceID()));
		p.removeGreenCard();
		assertTrue(ZERO == p.getGreenCardList().size());
		assertTrue(ZERO == p.getGreenCardHashMap().size());
		
		YellowCard yellowCard = new YellowCard();
		yellowCard.setReferenceID(5);
		p.addYellowCard(yellowCard);
		assertTrue( UNO == p.getYellowCardList().size());
		assertTrue(UNO == p.getYellowCardHashMap().size());
		assertEquals(yellowCard,p.getYellowCardList().get(0));
		assertEquals(yellowCard,p.getYellowCardHashMap().get(yellowCard.getReferenceID()));
		
		VioletCard violetCard = new VioletCard();
		violetCard.setReferenceID(5);
		p.addVioletCard(violetCard);
		assertTrue( UNO == p.getVioletCardList().size());
		assertTrue(UNO == p.getYellowCardHashMap().size());
		assertEquals(violetCard,p.getVioletCardList().get(0));
		assertEquals(violetCard,p.getVioletCardHashMap().get(violetCard.getReferenceID()));
		
		Familiar f1 = new Familiar(p,ColorEnumeration.Black);
		ArrayList<Familiar> fList = new ArrayList<>();
		fList.add(f1);
		p.setFamiliars(fList);
		assertEquals(UNO, p.getFamilyList().size());
		assertNotNull(p.getFamilyMap().get(f1.getColor()));
		assertNotNull(p.getFamilyMember(f1.getColor()));
		assertEquals(f1,p.getFamilyMap().get(f1.getColor()));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
