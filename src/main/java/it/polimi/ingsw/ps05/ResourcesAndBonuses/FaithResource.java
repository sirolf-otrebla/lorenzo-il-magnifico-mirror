package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class FaithResource implements Resource {
	
	private int amount;
	
	public FaithResource(int amount){
		this.amount = amount;
	}
	
	public FaithResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
}
