package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class MilitaryResourceToSub implements Resource, ActionResult {

	private Integer amount;
	
	public MilitaryResourceToSub(Integer amount){
		this.amount = amount;
	}
	
	public MilitaryResourceToSub() {
		
	}
	
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	public Integer getAmount(){
		return this.amount;
	}

	@Override
	public void remove(Resource res) {

	}

	@Override
	public void remove(Integer amount) {

	}
}
