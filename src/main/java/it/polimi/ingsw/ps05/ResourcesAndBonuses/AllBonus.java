package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class AllBonus implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public AllBonus(Integer value){
		this.value = value;
	}
	
	public AllBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
