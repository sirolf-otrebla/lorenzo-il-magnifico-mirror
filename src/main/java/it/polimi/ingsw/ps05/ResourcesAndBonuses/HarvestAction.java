package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class HarvestAction implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public HarvestAction(Integer value){
		this.value = value;
	}
	
	public HarvestAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
