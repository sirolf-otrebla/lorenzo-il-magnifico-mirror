package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

import java.util.Observable;

public class GreenAction extends Observable implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public GreenAction(Integer value){
		this.value = value;
	}
	
	public GreenAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;

	}

	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub
		
	}
}
