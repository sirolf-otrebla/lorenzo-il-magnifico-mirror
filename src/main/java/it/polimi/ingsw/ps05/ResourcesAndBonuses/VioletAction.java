package it.polimi.ingsw.ps05.ResourcesAndBonuses;

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
	
	public Integer getAmount(){
		return this.value;
	}
}
