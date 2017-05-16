package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class VioletBonus implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public VioletBonus(Integer value){
		this.value = value;
	}
	
	public VioletBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
