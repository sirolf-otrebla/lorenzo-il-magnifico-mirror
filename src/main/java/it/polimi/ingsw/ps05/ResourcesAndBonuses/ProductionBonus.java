package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class ProductionBonus implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public ProductionBonus(Integer value){
		this.value = value;
	}
	
	public ProductionBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
