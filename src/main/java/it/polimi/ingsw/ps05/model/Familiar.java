package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

import static it.polimi.ingsw.ps05.model.ColorEnumeration.NOT_INITIALIZED;

public class Familiar implements PlayerRelated{

	private ActionSpace position;
	private Dice relatedDice;
	private ColorEnumeration color = NOT_INITIALIZED;
	private Player relatedPlayer = null;
	
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

	public ActionSpace getPosition() {
		return position;
	}

	public boolean isUsed(){
		if (null == position) return false;
		return true;
	}

	public void setPosition(ActionSpace position) {
		this.position = position;
	}

	public void setDice(Dice dice){
		this.relatedDice = dice;
		//System.out.println(relatedPlayer.getPlayerID() + ": " + dice.getValue() + " setted on color: " + color.toString());
	}

	public void setColor(ColorEnumeration color) throws RepeatedAssignmentException {
		if (this.color == NOT_INITIALIZED) {
			this.color = color;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	public void setPlayer(Player player) throws RepeatedAssignmentException {
		if (this.relatedPlayer == null) {
			this.relatedPlayer = player;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	public void resetPosition(){
	    this.setPosition(null);
    }

    public Dice getRelatedDice() {
		return this.relatedDice;
	}

	public ColorEnumeration getColor(){
		return this.color;
	}

	@Override
	public Player getRelatedPlayer(){
		return this.relatedPlayer;
	}
}
