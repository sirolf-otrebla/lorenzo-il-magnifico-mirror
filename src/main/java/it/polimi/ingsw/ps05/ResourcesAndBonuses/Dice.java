package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import java.util.Random;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.Color;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class Dice implements Resource {

	ColorEnumeration color;
	private int value;
	
	public Dice(ColorEnumeration color) {
		super();
		this.color = color;
		this.value = lanciaDado();
	}

	public Dice(ColorEnumeration color, Integer diceValue){
	    this.value = diceValue;
	    this.color = color;
    }
	private int lanciaDado(){
		Random randomNum = new Random();
		return randomNum.nextInt(6) + 1;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) throws DiceTooLowException{
		Dice playerDice = playerFamiliar.getRelatedDice();
		if((playerDice.value - this.value)<0) throw new DiceTooLowException(playerDice.value - this.value);

	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		return false;
	}

	public int getValue(){
		return this.value;
	}

	public Color getColor(){
		return this.color;
	}
}
