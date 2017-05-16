package it.polimi.ingsw.ps05.ResourcesAndBonuses;

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
}
