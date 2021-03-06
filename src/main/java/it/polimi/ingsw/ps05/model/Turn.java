package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

import java.io.Serializable;
import java.util.ArrayList;

public class Turn implements Period, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4399699310322553340L;
	private ArrayList<Player> order; // turn order
	private Turn next; 
	private ArrayList<Dice> dice;
	private Epoch epoch;
	private Integer turnNumber;
	private ArrayList<Action> actionHistory;
	
	public Turn(){
		actionHistory = new ArrayList<Action>();
	}
	
	public Epoch getEpoch(){
		return this.epoch;
	}
	
	public ArrayList<Player> getPlayerOrder(){
		return order;
	}
	
	public Turn getEmptyTurn(){
		return next;
	}
	
	public ArrayList<Dice> getDice(){
		return dice;
	}

	public Integer getTurnNumber() {
		return turnNumber;
	}

	public ArrayList<Action> getActionHistory() {
		return actionHistory;
	}
	
	
	
	public void setDice(ArrayList<Dice> dice){
		this.dice = dice;
	}
	
	public void setPlayerOrder(ArrayList<Player> orderPlayer){
		this.order = orderPlayer;
	}

	public void setNext(Turn next) {
		this.next = next;
	}

	public void setEpoch(Epoch epoch) {
		this.epoch = epoch;
	}

	public void setTurnNumber(Integer turnNumber) {
		this.turnNumber = turnNumber;
	}

	public void addActionToHistory(Action action) {
		this.actionHistory.add(action);
	}
	
}
