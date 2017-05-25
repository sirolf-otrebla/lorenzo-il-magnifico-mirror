package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

public class YellowAction implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public YellowAction(Integer value){
		this.value = value;
	}
	
	public YellowAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub
		
	}
}
