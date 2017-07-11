package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class YellowCard implements Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6487480709568191121L;
	Integer value;
	public static final String id = "Carta Gialla";
	
	public YellowCard(){
		
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		throw new IllegalMethodCallException();

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
		throw new IllegalMethodCallException();

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) throws NotEnoughResourcesException, DiceTooLowException {
		throw new NotEnoughResourcesException();

	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		if (playerFamiliar.getRelatedPlayer().getBlueCardList().size() >= value)
			return true;
		return false;
	}

	@Override
	public void setValue(Integer amount) {
		this.value = amount;

	}

	@Override
	public Integer getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String toString(){
		return "Carta gialla";
	}

}
