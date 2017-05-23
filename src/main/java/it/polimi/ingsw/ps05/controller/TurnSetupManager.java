package it.polimi.ingsw.ps05.controller;

import java.util.ArrayList;
import java.util.Random;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.*;

public class TurnSetupManager {

	Turn turn; //vecchio turno
	ArrayList<Turn> turnHistory;
	ArrayList<Dice> dice;
	Board board;
	ArrayList<Deck> deckList;
	ArrayList<Player> playersConnected; //variabile monouso solo per il primo turno
	
	public TurnSetupManager(ArrayList<Player> playersConnected, Board board){
		this.board = board;
		this.playersConnected = playersConnected;
	}
	
	public void updatePlayerOrder(ArrayList<Player> onCouncil){
		
		ArrayList<Player> newOrder = new ArrayList<Player>();
		for (Player o : onCouncil){
			newOrder.add(o);
		}
		if (newOrder.size() != turn.getPlayerOrder().size()){
			turn.getPlayerOrder().removeAll(newOrder);
			newOrder.addAll(turn.getPlayerOrder());
		}
		turn.getNextTurn().setPlayerOrder(newOrder);
	}
	
	public void resetBoard(){
		//reset degli spazi generici in elenco
		for (ActionSpace o : board.getActionSpace()){
			o.reset();
		}
		//reset delle carte
		for (Tower o : board.getTowerList()){
			for (TowerTileInterface a : o.getTiles()){
				a.removeTowerCard();
			}
		}
		//council, serve spostare anche l'ordine dei giocatori
		ArrayList<Player> list = board.getPlayerOnCouncil();
		board.setPlayerOnCouncil(null);
		updatePlayerOrder(list);
	}
	
	public void updateFamiliar(){
		
	}
	
	public void setUpBoardCard(){
		for (Tower o : board.getTowerList()){
			o.setCardInTile(turn.getEpoch());
		}
	}
	
	public void rollDice(){
		dice.add(new Dice(new Color(ColorEnumeration.Black)));
		dice.add(new Dice(new Color(ColorEnumeration.Orange)));
		dice.add(new Dice(new Color(ColorEnumeration.White)));
	}
	
	public void loadNextTurn(){
		
	}
	
	public void setupFirstTurn(){
		Turn firstTurn = new Turn();
		rollDice();
		firstTurn.setDice(dice);
		firstTurn.setPlayerOrder(setRandomPlayerOrder());
		firstTurn.setTurnNumber(1);
		firstTurn.setEpoch(new Epoch(EpochEnumeration.FIRST));
		firstTurn.setNext(new Turn());
	}
	
	public ArrayList<Player> setRandomPlayerOrder(){
		ArrayList<Player> orderToSet = new ArrayList<Player>();
		for (int i = 0; i < playersConnected.size(); i++){
			orderToSet.add(playersConnected.get(new Random().nextInt(playersConnected.size())));
			playersConnected.remove(orderToSet.get(i));
		}
		
		return orderToSet;
	}
	
	
	
}
