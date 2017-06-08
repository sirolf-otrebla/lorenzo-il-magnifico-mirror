package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

public class VioletAction implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public VioletAction(Integer value){
		this.value = value;
	}
	
	public VioletAction() {
		
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
