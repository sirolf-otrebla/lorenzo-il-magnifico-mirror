package it.polimi.ingsw.ps05.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;


public class Board implements Serializable, VisitableFromView {

	private static final long serialVersionUID = 3802284891664073914L;

	public final static int MAX_PLAYERS = 4;
	public final static int EPOCHS_NUMBER = 3;
	public final static int RESOURCE_CONVERSION_RATIO = 5;

	private HashMap<ColorEnumeration, Tower> towerHashMap;
	private HashMap<Integer, ActionSpace> actSpacesMap;
	private ArrayList<ActionSpace> actionSpace = null; //da confermare utilizzo lista
	private ArrayList<Player> playerOnCouncil;
	private ArrayList<VictoryResource> faithPath = null; //array da 16? elementi che dice quanti punti vittoria vengono assegnati all'i-esimo posto del tracciato
	private ArrayList<MilitaryResource> militaryPath = null; //array da 6 elementi che dice quanti punti militare servono per poter avere "i" territori
	private ArrayList<ExcommunicationCard> excomCards = null; //array da 3 elementi che contiene i riferimenti alle carte scomunica pescate ad inizio partita
	private ArrayList<VictoryResource> greenCardsConversion = null; //array da 6 elementi che dice quant ipunti vittoria vengono assegnati per "i" carte
	private ArrayList<VictoryResource> blueCardsConversion = null; //array da 6 elementi che dice quant ipunti vittoria vengono assegnati per "i" carte
	//serializzabile
	private ArrayList<LeaderCard> leaderCardsList = null;
	
	/**
	 * The board is the place that contains every single ActionSpace where users can put Familiars.
	 * @param towerList the array of towers. Each tower contains tiles and each tile contains card.
	 * @param actionSpaceArrayList the array of spaces different from towers.
	 * @param faithPath is an array of victoryResource used to convert the amount of faith points into victory points.
	 * @param militaryPath is an array of militaryResource used to check how many green cards you can take.
	 * @param blueConversion is an array of VictoryResource used to convert the number of blue card into victory points.
	 * @param greenConversion is an array of VictoryResource used to convert the number of green card into victory points.
	 * @param leaderCardList is the list of the Leader cards.
	 */
	public Board(ArrayList<Tower> towerList, ArrayList<ActionSpace> actionSpaceArrayList,
			ArrayList<VictoryResource> faithPath, ArrayList<MilitaryResource> militaryPath,
			ArrayList<VictoryResource> blueConversion, ArrayList<VictoryResource> greenConversion,
			ArrayList<LeaderCard> leaderCardList) {
		super();

		this.towerHashMap = new HashMap<>();
		for (Tower tower : towerList){
			this.towerHashMap.put(tower.getColor(), tower);
		}
		this.actSpacesMap = new HashMap<>();
		for (ActionSpace actSpace : actionSpaceArrayList){
			actSpacesMap.put(actSpace.getId(),actSpace);
		}
		this.actionSpace = actionSpaceArrayList;
		this.faithPath = faithPath;
		this.militaryPath = militaryPath;
		this.greenCardsConversion = greenConversion;
		this.blueCardsConversion = blueConversion;
		this.leaderCardsList = leaderCardList;
	}
	
	/**
	 * @return the array that contains the value to convert the number of green cards into victory points.
	 */
	public ArrayList<VictoryResource> getGreenCardsConversion() {
		return greenCardsConversion;
	}

	/**
	 * @return the array that contains the value to convert the number of blue cards into victory points.
	 */
	public ArrayList<VictoryResource> getBlueCardsConversion() {
		return blueCardsConversion;
	}

	/**
	 * @return the hashmap of towers. Each tower is identified by its color.
	 */
	public HashMap<ColorEnumeration, Tower> getTowerList() {
		return towerHashMap;
	}

	/**
	 * 
	 * @return the list of players into Council space.
	 */
	public ArrayList<Player> getPlayerOnCouncil() {
		return playerOnCouncil;
	}

	/**
	 * 
	 * @return the array which contains all the leader cards loaded.
	 */
	public ArrayList<LeaderCard> getLeaderCardsList(){
		return leaderCardsList;
	}

	/**
	 * @return an array that contains the value to convert the number of faith points into victory points.
	 */
	public ArrayList<VictoryResource> getFaithPath() {
		return faithPath;
	}
	
	/**
	 * @return an array of militaryResource used to check how many green cards you can take.
	 */
	public ArrayList<MilitaryResource> getMilitaryPath() {
		return militaryPath;
	}

	/**
	 * 
	 * @return an array of excommunication cards (there are only three cards, one for epoch)
	 */
	public ArrayList<ExcommunicationCard> getExcomCards() { return excomCards; }
	
	/**
	 * 
	 * @param conversion is the arrayList that contains the conversion amount.
	 */
	public void setGreenCardsConversion (ArrayList<VictoryResource> conversion) throws RepeatedAssignmentException{
		this.greenCardsConversion = conversion;
	}

	/**
	 * 
	 * @param conversion is the arrayList that contains the conversion amount.
	 */
	public void setBlueCardsConversion (ArrayList<VictoryResource> conversion) throws RepeatedAssignmentException {
		this.blueCardsConversion = conversion;
	}

	/**sets a list of players as council occupants
	 * 
	 * @param playerOnCouncil it is an ArrayList of Player, its reference will be saved inside the board
	 */
	public void setPlayerOnCouncil(ArrayList<Player> playerOnCouncil) {
		this.playerOnCouncil = playerOnCouncil;
	}

	/** 
	 * Each element's index represents how many victory points are assigned with the index 
	 * value of Faith points. for example, the third element of the array contains how many 
	 * victory points are assigned for 3 faith points
	 *  
	 * @param faithPath is an array of victory resource
	 * @throws RepeatedAssignmentException if you try to re-assign or change a value of the path
	 */
	public void setFaithPath(ArrayList<VictoryResource> faithPath) throws RepeatedAssignmentException {
		if (this.faithPath == null) {
			this.faithPath = faithPath;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	/**
	 * Each element's represents how many military points you must have to have "index" green cards. For example
	 * if you want to have 3 green cards you have to check at index 3 how many military points are requested. 
	 * @param militaryPath is an array of military resource
	 */
	public void setMilitaryPath(ArrayList<MilitaryResource> militaryPath) {
		this.militaryPath = militaryPath;
	}

	/**
	 * This method is used to set the excommunication that you can receive during the game.
	 * @param excomCards is an array with the cards that represent the excommunication. The array must have size 3
	 * @throws RepeatedAssignmentException if you try to re-assign the excommunication.
	 */
	public void setExcomCards(ArrayList<ExcommunicationCard> excomCards) throws RepeatedAssignmentException {
		if (this.excomCards == null) {
			this.excomCards = excomCards;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	/**
	 * 
	 * @return the hashmap of the actionspace istanciated in the board
	 */
	public HashMap<Integer, ActionSpace> getActSpacesMap() {
		return actSpacesMap;
	}

	/**
	 * This method is used to get the hashmap of the action space
	 * @return the hashmap of actionspaces istantiated in the board. the key for each actionspace is its ID
	 */
	public ActionSpace getActionSpace(Integer referenceID){
		return this.getActSpacesMap().get(referenceID);
	}


	@Override
	public void acceptVisitor(ViewVisitorInterface vi) {

		//TODO
	}

}
