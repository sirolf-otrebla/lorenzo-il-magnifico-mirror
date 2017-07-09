package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;

public class FamiliarTest {

	private final ColorEnumeration c = ColorEnumeration.Black; 
	private final Dice d = new Dice(ColorEnumeration.Black, 1);
	private final Player p = new Player(0, "", ColorEnumeration.Blue);
	Familiar f = new Familiar(d,c,p);
	
	ActionSpace s = new ActionSpace() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9158194920457012395L;

		@Override
		public void acceptVisitor(ViewVisitorInterface vi) {
			// TODO Auto-generated method stub

		}

		@Override
		public ArrayList<Effect> getEffects() throws IllegalMethodCallException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void applyEffect(Familiar pl) {
			// TODO Auto-generated method stub

		}
	};

	@Test
	public void goodFamiliar() {
		assertNotEquals(f, null);
		assertEquals(d.getValue(),f.getRelatedDice().getValue());
		assertEquals(f.getColor(), c);
		assertNull(f.getPosition());
		f.setPosition(s);
		assertEquals(s,f.getPosition());
		assertTrue(f.isUsed());
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

	@Test
	public void getterSetterTest() throws RepeatedAssignmentException{
		
		Familiar f2 = new Familiar();
		f2.setPlayer(p);
		assertEquals(p,f2.getRelatedPlayer());
		assertEquals(p.getColor(), f2.getRelatedPlayerColor());
		
		Familiar f = new Familiar(p);
		assertEquals(p,f.getRelatedPlayer());
		assertEquals(ColorEnumeration.NOT_INITIALIZED, f.getColor());
		assertTrue(!f.isUsed());
		assertNull(f.getRelatedDice());
		assertNull(f.getPosition());
		f.setPosition(s);
		assertEquals(s,f.getPosition());
		assertTrue(f.isUsed());
		f.resetPosition();
		assertNull(f.getPosition());

		Familiar f1 = new Familiar(p, c);
		assertEquals(p,f1.getRelatedPlayer());
		assertEquals(c, f1.getColor());
		assertTrue(!f1.isUsed());
		assertNull(f1.getRelatedDice());
		assertNull(f1.getPosition());
		f.setPosition(s);
		assertEquals(s,f.getPosition());
		assertTrue(f.isUsed());
	}

}
