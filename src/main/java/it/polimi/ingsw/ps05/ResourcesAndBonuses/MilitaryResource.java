package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class MilitaryResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	private Integer value;
	
	public MilitaryResource(Integer amount){
		this.amount = amount;
	}
	
	public MilitaryResource() {
		
	}
	
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	public Integer getAmount(){
		return this.amount;
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(Integer value){
		return this.value;
	}

	@Override
	public void remove(Resource res) {

	}

	@Override
	public void remove(Integer amount) {

	}
}
