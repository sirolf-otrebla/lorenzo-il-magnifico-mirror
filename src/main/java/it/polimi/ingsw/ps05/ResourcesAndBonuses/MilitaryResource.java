package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class MilitaryResource implements Resource {
	private int amount;
	
	public MilitaryResource(int amount){
		this.amount = amount;
	}
	
	public MilitaryResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
}
