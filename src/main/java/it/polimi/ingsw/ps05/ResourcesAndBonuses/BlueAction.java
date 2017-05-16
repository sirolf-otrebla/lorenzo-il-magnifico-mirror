package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class BlueAction implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	//Ex. value 5 è da Integerendersi come un azione con dado 5 sulla colonna blu
	
	public BlueAction(Integer value){
		this.value = value;
	}
	
	public BlueAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}

