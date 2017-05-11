package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class StoneResource implements Resource {
	private int amount;
	
	public StoneResource(int amount){
		this.amount = amount;
	}
	
	public StoneResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
}
