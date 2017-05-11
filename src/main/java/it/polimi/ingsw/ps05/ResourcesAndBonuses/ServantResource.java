package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class ServantResource implements Resource {
	private int amount;
	
	public ServantResource(int amount){
		this.amount = amount;
	}
	
	public ServantResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
}
