package it.polimi.ingsw.ps05.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;

/* See UML for description
 * 
 * 
 */

public class Board implements Serializable{


	/**
	 * 
	 */
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
	
	
	public Board(ArrayList<Tower> towerList, ArrayList<ActionSpace> actionSpaceArrayList,
			ArrayList<VictoryResource> faithPath, ArrayList<MilitaryResource> militaryPath,
			ArrayList<VictoryResource> blueConversion, ArrayList<VictoryResource> greenConversion) {
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
	}
	
	public ArrayList<VictoryResource> getGreenCardsConversion() {
		return greenCardsConversion;
	}
	
	public ArrayList<VictoryResource> getBlueCardsConversion() {
		return blueCardsConversion;
	}

	public HashMap<ColorEnumeration, Tower> getTowerList() {
		return towerHashMap;
	}

	public ArrayList<Player> getPlayerOnCouncil() {
		return playerOnCouncil;
	}

	public ArrayList<VictoryResource> getFaithPath() {
		return faithPath;
	}

	public ArrayList<MilitaryResource> getMilitaryPath() {
		return militaryPath;
	}

	public ArrayList<ExcommunicationCard> getExcomCards() { return excomCards; }
	
	public void setGreenCardsConversion (ArrayList<VictoryResource> conversion) throws RepeatedAssignmentException{
		if (this.greenCardsConversion == null) {
			this.greenCardsConversion = conversion;
		} else {
			throw new RepeatedAssignmentException();
		}
	}
	
	public void setBlueCardsConversion (ArrayList<VictoryResource> conversion) throws RepeatedAssignmentException {
		if (this.blueCardsConversion == null) {
			this.blueCardsConversion = conversion;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	public void setTowerList(HashMap<ColorEnumeration, Tower> towerList) throws RepeatedAssignmentException {
		if (this.towerHashMap == null) {
			this.towerHashMap = towerList;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	public void setActionSpace(ArrayList<ActionSpace> actionSpace) throws RepeatedAssignmentException {
		if (this.actionSpace == null) {
			this.actionSpace = actionSpace;
		}
		else {
			throw new RepeatedAssignmentException();
		}
	}

	public void setPlayerOnCouncil(ArrayList<Player> playerOnCouncil) {
		this.playerOnCouncil = playerOnCouncil;
	}

	public void setFaithPath(ArrayList<VictoryResource> faithPath) throws RepeatedAssignmentException {
		if (this.faithPath == null) {
			this.faithPath = faithPath;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	public void setMilitaryPath(ArrayList<MilitaryResource> militaryPath) throws RepeatedAssignmentException {
		if (this.militaryPath == null) {
			this.militaryPath = militaryPath;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	public void setExcomCards(ArrayList<ExcommunicationCard> excomCards) throws RepeatedAssignmentException {
			if (this.excomCards == null) {
				this.excomCards = excomCards;
			} else {
				throw new RepeatedAssignmentException();
			}
	}

	public HashMap<Integer, ActionSpace> getActSpacesMap() {
		return actSpacesMap;
	}

	public void setActSpacesMap(HashMap<Integer, ActionSpace> actSpacesMap) {
		this.actSpacesMap = actSpacesMap;
	}
}
