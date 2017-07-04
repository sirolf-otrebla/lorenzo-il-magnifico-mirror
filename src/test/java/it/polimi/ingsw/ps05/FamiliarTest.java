package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

public class FamiliarTest {
	
	private final ColorEnumeration c = ColorEnumeration.Black; 
	private final Dice d = new Dice(ColorEnumeration.Black, 1);
	private final Player p = new Player(0, "", ColorEnumeration.Blue);
	Familiar f = new Familiar(d,c,p);

	@Test
	public void goodFamiliar() {
		assertNotEquals(f, null);
		assertEquals(d.getValue(),f.getRelatedDice().getValue());
		assertEquals(f.getColor(), c);
	}
	
	@Test
	public void badTestFamiliar(){
		try {
			f.setColor(c);
			Assert.fail();
		} catch (RepeatedAssignmentException e) {
			assertEquals(RepeatedAssignmentException.class, e.getClass());
		}
	}

}
