package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class WoodResource implements Resource {
	private int amount;
	
	public WoodResource(int amount){
		this.amount = amount;
	}
	
	public WoodResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
}
