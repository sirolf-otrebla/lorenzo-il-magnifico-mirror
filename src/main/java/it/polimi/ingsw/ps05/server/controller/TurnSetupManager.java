package it.polimi.ingsw.ps05.server.controller;

import java.util.*;
import java.util.concurrent.Semaphore;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.*;

public class TurnSetupManager extends Observable{

	private Turn turn; //vecchio turno
	private ArrayList<Turn> turnHistory = new ArrayList<>();
	private  Board board;
	private ArrayList<Player> playersConnected; //variabile monouso solo per il primo turno
	private ArrayList<Resource> startResource;
	private Semaphore excommSemaphore = new Semaphore(0);
	
	public TurnSetupManager(ArrayList<Player> playersConnected, Board board, ArrayList<Resource> startingResource){
		this.board = board;
		this.playersConnected = playersConnected;
		this.startResource = startingResource;
		System.out.println("Starting resource: " + startingResource.size());
		System.out.println("Start resource: " + this.startResource.size());
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

		//todo cambiare nome a questa variabile
		Iterator<ActionSpace> stoCazzoIterator =
				board.getActSpacesMap().values().iterator();
		while(stoCazzoIterator.hasNext())
			stoCazzoIterator.next().reset();
		//todo da controllare
		//reset delle carte
		for (Tower o : board.getTowerList().values()){
			for (TowerTileInterface a : o.getTiles().values()){
				a.removeTowerCard();
			}
		}
		//council, serve spostare anche l'ordine dei giocatori
		ArrayList<Player> list = board.getPlayerOnCouncil();
		if (list == null){
			list = new ArrayList<>();
		}
		board.setPlayerOnCouncil(null);
		updatePlayerOrder(list,next);
	}
	
	private void updateFamiliar(Turn currentTurn){
		for (Player o : currentTurn.getPlayerOrder()){
			for (Familiar f : o.getFamilyList()){
				for (Dice d : currentTurn.getDice()){
					if (f.getColor() == d.getColor()){
						f.setDice(d);
						break;
					}
				}
			}
		}
	}
	
	private void setUpBoardCard(Turn currentTurn){
		for (Tower o : board.getTowerList().values()){
			o.setCardInTile(currentTurn.getEpoch());
		}
	}
	
	private ArrayList<Dice> rollDice(){
		ArrayList<Dice> dice = new ArrayList<Dice>();
		dice.add(new Dice(ColorEnumeration.Black));
		dice.add(new Dice(ColorEnumeration.Orange));
		dice.add(new Dice(ColorEnumeration.White));
		dice.add(new Dice(ColorEnumeration.Any, 0));
		return dice;
	}
	
	public void loadNextTurn(){
		turnHistory.add(this.turn);
		Turn next = turn.getEmptyTurn();
		next.setDice(rollDice());
		resetBoard(next);
		next.setTurnNumber(turn.getTurnNumber() + 1);
		next.setEpoch(new Epoch(EpochEnumeration.getEpoch(next.getTurnNumber() / 2)));
		if (!next.getEpoch().getID().equals(turn.getEpoch().getID())){
			this.setChanged();
			notifyObservers(turn.getEpoch());
			try {
				excommSemaphore.acquire(this.playersConnected.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		ArrayList<Player> plList = new ArrayList<>();
		plList.ensureCapacity(this.playersConnected.size());
		for( Player pl : playersConnected){
			plList.add(pl);
		}
		System.out.println("player order size in turn setup manager: " + plList.size());
		System.out.println("Start resource: " + startResource.size());
		Collections.shuffle(plList);
		for (int i = 0; i < plList.size(); i++){
			for (Resource r : startResource){
				System.out.println(r.toString() + " " + r.getValue());
				plList.get(i).addResource(r);
				if (r.getID().equals(GoldResource.id)){
					r.setValue(r.getValue() + 1);
				}
				
			}
		}
		return plList;
	}
	
	public Turn getTurn(){
		return this.turn;
	}
	
	
	
}
