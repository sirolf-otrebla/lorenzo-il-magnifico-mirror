package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class VictoryResource implements Resource {
	private int amount;
	
	public VictoryResource(int amount){
		this.amount = amount;
	}
	
	public VictoryResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
}
