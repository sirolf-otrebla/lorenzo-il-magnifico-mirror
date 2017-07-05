package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;

public class PlayerTest {
	
	private final int ID_TEST = 12;
	private final String USR_TEST = "Luca";
	private final ColorEnumeration c = ColorEnumeration.Green;
	private final int ZERO = 0;
	private final int SETTE = 7;
	private final GoldResource RES_TEST = new GoldResource(SETTE);
	
	Player p = new Player(ID_TEST, USR_TEST, c);

	@Test
	public void playerConstructor() {
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
	}

}
