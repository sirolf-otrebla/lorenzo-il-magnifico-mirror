package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class HarvestBonus implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public HarvestBonus(Integer value){
		this.value = value;
	}
	
	public HarvestBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
