package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class FreeAction implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	
	public FreeAction(Integer value){
		this.value = value;
	}
	
	public FreeAction() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}
}
