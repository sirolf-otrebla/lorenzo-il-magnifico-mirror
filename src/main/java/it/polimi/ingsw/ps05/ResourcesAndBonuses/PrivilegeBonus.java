package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

public class PrivilegeBonus implements ActionResult {
	private Integer value; //con value si Integerende il numero di privilegi conferiti dalla carta
						//ricordandosi che i privilegi non possono mai essere uguali tra loro
	
	public PrivilegeBonus(Integer value){
		this.value = value;
	}
	
	public PrivilegeBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}

	public void applyResult(PlayerRelated playerR){
		//TODO
	}
}
