package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import java.util.Random;

import it.polimi.ingsw.ps05.model.Color;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class Dice implements Resource, ActionResult {

	Color color;
	int value;
	
	public Dice(Color color) {
		super();
		this.color = color;
		this.value = lanciaDado();
	}

	private int lanciaDado(){
		Random randomNum = new Random();
		return randomNum.nextInt(6) + 1;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException{
	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
	}
}
