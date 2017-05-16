package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class RemoveTileEffect implements ActionResult {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public RemoveTileEffect(Integer value){
		this.value = value;
	}
	
	public RemoveTileEffect() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
