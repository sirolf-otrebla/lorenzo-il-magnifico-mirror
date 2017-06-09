package it.polimi.ingsw.ps05.controller;

import java.util.ArrayList;
import java.util.Random;

import it.polimi.ingsw.ps05.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.*;

public class TurnSetupManager {

	Turn turn; //vecchio turno
	ArrayList<Turn> turnHistory;
	Board board;
	ArrayList<Player> playersConnected; //variabile monouso solo per il primo turno
	
	private final static int goldAmountStart = 5;
	private final static int woodAmountStart = 2;
	private final static int stoneAmountStart = 2;
	private final static int servantsAmountStart = 3;
	
	public TurnSetupManager(ArrayList<Player> playersConnected, Board board){
		this.board = board;
		this.playersConnected = playersConnected;
		this.turn = setupFirstTurn();
	}
	
	private void updatePlayerOrder(ArrayList<Player> onCouncil,Turn next){
		
		ArrayList<Player> newOrder = new ArrayList<Player>();
		for (Player o : onCouncil){
			newOrder.add(o);
		}
		if (newOrder.size() != turn.getPlayerOrder().size()){
			turn.getPlayerOrder().removeAll(newOrder);
			newOrder.addAll(turn.getPlayerOrder());
		}
		next.setPlayerOrder(newOrder);
	}
	
	private void resetBoard(Turn next){
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
		updatePlayerOrder(list,next);
	}
	
	private void updateFamiliar(Turn currentTurn){
		for (Player o : currentTurn.getPlayerOrder()){
			for (Familiar f : o.getFamilyList()){
				for (Dice d : currentTurn.getDice()){
					if (f.getColor().equals(d.getColor())){
						f.setDice(d);
						break;
					}
				}
			}
		}
	}
	
	private void setUpBoardCard(Turn currentTurn){
		for (Tower o : board.getTowerList()){
			o.setCardInTile(currentTurn.getEpoch());
		}
	}
	
	private ArrayList<Dice> rollDice(){
		ArrayList<Dice> dice = new ArrayList<Dice>();
		dice.add(new Dice(ColorEnumeration.Black));
		dice.add(new Dice(ColorEnumeration.Orange));
		dice.add(new Dice(ColorEnumeration.White));
		return dice;
	}
	
	public void loadNextTurn(){
		turnHistory.add(this.turn);
		Turn next = turn.getNextTurn();
		next.setDice(rollDice());
		resetBoard(next);
		next.setTurnNumber(turn.getTurnNumber() + 1);
		next.setEpoch(new Epoch(EpochEnumeration.getEpoch(next.getTurnNumber() / 2)));
		if (!next.getEpoch().getEpoch().equals(turn.getEpoch().getEpoch())){
			//triggera azione scomunica
		}
		updateFamiliar(next);
		next.setNext(new Turn());
		setUpBoardCard(next);
		this.turn = next;
	}
	
	private Turn setupFirstTurn(){
		Turn firstTurn = new Turn();
		firstTurn.setDice(rollDice());
		firstTurn.setPlayerOrder(setRandomPlayerOrder());
		firstTurn.setTurnNumber(1);
		firstTurn.setEpoch(new Epoch(EpochEnumeration.FIRST));
		firstTurn.setNext(new Turn());
		updateFamiliar(firstTurn);
		setUpBoardCard(firstTurn);
		return firstTurn;
	}
	
	private ArrayList<Player> setRandomPlayerOrder(){
		ArrayList<Player> orderToSet = new ArrayList<Player>();
		for (int i = 0; i < playersConnected.size(); i++){
			orderToSet.add(playersConnected.get(new Random().nextInt(playersConnected.size())));
			playersConnected.remove(orderToSet.get(i));
			
		}
		for (int i = 0; i < orderToSet.size(); i++){
			Player player = orderToSet.get(i);
			player.addResource(new GoldResource(goldAmountStart + i));
			player.addResource(new WoodResource(woodAmountStart));
			player.addResource(new StoneResource(stoneAmountStart));
			player.addResource(new ServantResource(servantsAmountStart));
		}
		return orderToSet;
	}
	
	public Turn getTurn(){
		return this.turn;
	}
	
	
	
}
