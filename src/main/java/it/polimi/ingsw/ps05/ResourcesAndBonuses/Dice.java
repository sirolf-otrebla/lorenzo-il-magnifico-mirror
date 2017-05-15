package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.Color;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class Dice implements Resource {

		Color color;
		int value;
		
		// 
		private void lanciaDado(){
			//TODO
		}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException{
	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
	}
}
