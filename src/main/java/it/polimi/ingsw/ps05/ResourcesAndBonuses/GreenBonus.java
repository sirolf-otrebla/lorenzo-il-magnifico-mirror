package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class GreenBonus implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public GreenBonus(Integer value){
		this.value = value;
	}
	
	public GreenBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
