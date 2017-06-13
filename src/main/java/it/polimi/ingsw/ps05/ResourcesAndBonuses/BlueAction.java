package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

public class BlueAction implements ActionResult {
	private Integer value; //con value si intende il valore del bonus conferito dalla carta
	//Ex. value 5 è da intrendersi come un azione con dado 5 sulla colonna blu
	
	public BlueAction(Integer value){
		this.value = value;
	}
	
	public BlueAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
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

