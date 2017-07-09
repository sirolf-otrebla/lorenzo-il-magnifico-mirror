package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.NoActionExcomm;

public class EpochTest {

	@Test
	public void test() {
		Epoch epoch = new Epoch(EpochEnumeration.FIRST);
		assertEquals(EpochEnumeration.FIRST,epoch.getID());
		assertNull(epoch.getExcomunicationCard());
		
		Epoch epoch1 = new Epoch();
		assertNull(epoch1.getID());
		assertNull(epoch1.getExcomunicationCard());
		
		ExcommunicationCard card = new ExcommunicationCard(new FaithResource(3),
				EpochEnumeration.FIRST,
				new NoActionExcomm());
		Epoch epoch2 = new Epoch(EpochEnumeration.FIRST, card);
		assertEquals(card,epoch2.getExcomunicationCard());
		assertEquals(EpochEnumeration.FIRST, epoch2.getID());
	}

}
