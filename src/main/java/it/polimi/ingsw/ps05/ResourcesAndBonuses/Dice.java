package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import java.util.Random;

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
		int diff = playerFamiliar.getRelatedDice().getValue() - this.getValue();
		if ( diff > 0) return true;
		if (Math.abs(diff) < playerFamiliar.getRelatedPlayer().getServants().getValue()){
			try{
				playerFamiliar.getRelatedPlayer().getServants().remove(new ServantResource(Math.abs(diff)));
				return true;
			} catch (NotEnoughResourcesException e){
				System.out.println("SOMETHING REALLY BAD HAS HAPPENED!! THIS MESSAGE IT'S NOT MEANT TO BE DISPLAYED EVER");
			}
		}
		return false;
	}

    @Override
    public void setValue(Integer amount) {
        this.value = amount;
    }

    public Integer getValue(){
		return this.value;
	}

	public ColorEnumeration getColor(){
		return this.color;
	}
}
