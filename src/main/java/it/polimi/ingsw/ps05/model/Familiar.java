package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Dice;

public class Familiar implements PlayerRelated{

	private ActionSpace position;
	Dice relatedDice;
	ColorEnumeration color;
	private Player relatedPlayer;
	
	public Familiar(){
		
	}
	
	public Familiar (Player player){
		this.relatedPlayer = player;
	}
	
	public Familiar (Player player, ColorEnumeration color){
		this.relatedPlayer = player;
		this.color = color;
	}
	
	public Familiar(Dice dice, ColorEnumeration color, Player player){
		this.relatedPlayer = player;
		this.relatedDice = dice;
		this.color = color;
	}

	public Dice  getRelatedDice(){

		return relatedDice;
	}

	public ActionSpace getPosition() {
		return position;
	}

	public boolean isUsed(){
		if (position != null) return true;
		return false;
	}


	public void setPosition(ActionSpace position) {
		this.position = position;
	}

	public void resetPosition(){
	    this.setPosition(null);
    }

    public void setDice(Dice dice){
	    this.relatedDice = dice;
    }
	@Override
	public Player getRelatedPlayer(){
		return this.relatedPlayer;
	}
	
	public void setColor(ColorEnumeration color){
		this.color = color;
	}
	
	public void setPlayer(Player player){
		this.relatedPlayer = player;
	}
	
	public ColorEnumeration getColor(){
		return this.color;
	}
}
