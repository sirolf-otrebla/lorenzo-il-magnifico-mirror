package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class StoneResourceCostBonus implements ActionResult, Resource {
	
	private Integer value;
	
	public StoneResourceCostBonus(){
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub

	}

}
