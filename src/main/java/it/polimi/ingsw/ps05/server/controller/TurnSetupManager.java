package it.polimi.ingsw.ps05.server.controller;

import java.util.*;
import java.util.concurrent.Semaphore;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.*;

/**this class takes care of creating Turns Object and extracting informations from them
 *
 */
public class TurnSetupManager extends Observable{

	private Turn turn; //vecchio turno
	private ArrayList<Turn> turnHistory = new ArrayList<>();
	private  Board board;
	private ArrayList<Player> playersConnected; //variabile monouso solo per il primo turno
	private ArrayList<Resource> startResource;
	private Semaphore excommSemaphore = new Semaphore(0);

	/**
	 *
	 * @param playersConnected an arraylist filled by players that are currently in game
	 * @param board			a related board
	 * @param startingResource an arraylist representing the resoruces each one has at the start of the game
	 */
	public TurnSetupManager(ArrayList<Player> playersConnected, Board board, ArrayList<Resource> startingResource){
		this.board = board;
		this.playersConnected = playersConnected;
		this.startResource = startingResource;
		this.turn = setupFirstTurn();

	}

	/**
	 *  this method is called when a player has chosen if take the excommunication or not, then a semaphore
	 *  is released
	 */
	public void releaseExcommSem(){
		excommSemaphore.release();
	}

	/**
	 * this method updates the player order according on the council occupants
	 * @param onCouncil an arraylist representing all players that are occupying council space
	 * @param next the next turn, where the new order will be set.
	 */
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

	/**reset the boards before a new turn.
	 *
	 * @param next the next turn
	 */
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
			o.setOccupied(false);
			for (TowerTileInterface a : o.getTiles().values()){
				a.removeTowerCard();
				a.reset();
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

	/**
	 * this method resets the familymember's position and updates their dice.
	 * @param currentTurn this is the current turn, and is needed for obtain dice values
	 */
	private void updateFamiliar(Turn currentTurn){
		for (Player o : currentTurn.getPlayerOrder()){
			for (Familiar f : o.getFamilyList()){
				for (Dice d : currentTurn.getDice()){
					if (f.getColor() == d.getColor()){
						f.setDice(d);
						f.resetPosition();
						break;
					}
				}
			}
		}
	}

	/**
	 * this method set up the board, assigning to each tower tile an appropriate
	 * card
	 * @param currentTurn the current turn, used to obtain appropriate cards
	 */
	private void setUpBoardCard(Turn currentTurn){
		for (Tower o : board.getTowerList().values()){
			o.setCardInTile(currentTurn.getEpoch());
		}
	}

	/**
	 *  this method rolls all dice,
	 * @return an arraylist of newly rolled dice
	 */
	private ArrayList<Dice> rollDice(){
		ArrayList<Dice> dice = new ArrayList<Dice>();
		dice.add(new Dice(ColorEnumeration.Black));
		dice.add(new Dice(ColorEnumeration.Orange));
		dice.add(new Dice(ColorEnumeration.White));
		dice.add(new Dice(ColorEnumeration.Any, 0));
		return dice;
	}

	/**
	 * this method loads the next turn.
	 */
	public void loadNextTurn(){
		System.out.println("INIZIO CAMBIO TURNO");
		turnHistory.add(this.turn);
		Turn next = turn.getEmptyTurn();
		next.setDice(rollDice());
		resetBoard(next);
		next.setTurnNumber(turn.getTurnNumber() + 1);
		updateFamiliar(next);
		next.setNext(new Turn());
		if (next.getTurnNumber() == 3){
			
			if(board.getExcomCards() != null){
				this.setChanged();
				notifyObservers(turn.getEpoch());
				try {
					excommSemaphore.acquire(this.playersConnected.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				next.setEpoch(new Epoch(EpochEnumeration.SECOND, board.getExcomCards().get(1)));
			} else {
				next.setEpoch(new Epoch(EpochEnumeration.SECOND));
			}
		} else if (next.getTurnNumber() == 5){
			next.setEpoch(new Epoch(EpochEnumeration.THIRD));
			if(board.getExcomCards() != null){
				this.setChanged();
				notifyObservers(turn.getEpoch());
				try {
					excommSemaphore.acquire(this.playersConnected.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				next.setEpoch(new Epoch(EpochEnumeration.THIRD, board.getExcomCards().get(2)));
			} else {
				next.setEpoch(new Epoch(EpochEnumeration.THIRD));
			}
		} else {
			next.setEpoch(turn.getEpoch());
		}
		setUpBoardCard(next);
		this.turn = next;
		System.out.println("FINE CAMBIO TURNO");
	}

	private Turn setupFirstTurn(){
		Turn firstTurn = new Turn();
		firstTurn.setDice(rollDice());
		firstTurn.setPlayerOrder(setRandomPlayerOrder());
		firstTurn.setTurnNumber(1);
		if (board.getExcomCards() != null && board.getExcomCards().size() > 0){
			Epoch epoch = new Epoch(EpochEnumeration.FIRST, board.getExcomCards().get(0));
			firstTurn.setEpoch(epoch);
		} else {
			firstTurn.setEpoch(new Epoch(EpochEnumeration.FIRST));
		}
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
