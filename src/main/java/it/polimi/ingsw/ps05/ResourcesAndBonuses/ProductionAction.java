package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class ProductionAction implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public ProductionAction(Integer value){
		this.value = value;
	}
	
	public ProductionAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
