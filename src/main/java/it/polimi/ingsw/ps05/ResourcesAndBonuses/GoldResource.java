package it.polimi.ingsw.ps05.ResourcesAndBonuses;

public class GoldResource implements Resource {
	private int amount;
	
	public GoldResource(int amount){
		this.amount = amount;
	}
	
	public GoldResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
}
