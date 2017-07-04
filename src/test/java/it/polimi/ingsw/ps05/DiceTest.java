package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

public class DiceTest {
	
	private final Integer DICE_VALUE = 5;
	private final Integer MIN_DICE = 1;
	private final Integer MAX_DICE = 6;
	private final ColorEnumeration c = ColorEnumeration.Any;
	private final Dice d = new Dice(c, DICE_VALUE);

	@Test
	public void diceConstructor() {
		assertEquals(DICE_VALUE, d.getValue());
		assertEquals(c, d.getColor());
	}
	
	@Test
	public void diceRandom(){
		for (int i = 0; i < 10000; i++){
			Dice testDice = new Dice(c);
			assertTrue(testDice.getValue() <= MAX_DICE && testDice.getValue() >= MIN_DICE);
		}
	}

}
