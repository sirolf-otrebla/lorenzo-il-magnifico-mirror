package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class YellowBonus implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public YellowBonus(Integer value){
		this.value = value;
	}
	
	public YellowBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
