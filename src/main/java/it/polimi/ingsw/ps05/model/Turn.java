package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class Turn implements Period {
	
	ArrayList<Player> order; // turn order
	Turn next; 
	ArrayList<Dice> dice;
	boolean finalTurn;
	
	// instantiate three Dice objects with random values
	public void lanciaDadi(){
		
	}
	
	public void setPlayerOrder(ArrayList<Player> orderPlayer){
		this.order = orderPlayer;
	}
}
