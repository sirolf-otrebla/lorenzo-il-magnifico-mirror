package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

/* See UML for description
 * 
 * 
 */
public class Board {
	private static Board instance;

	public final static int MAX_PLAYERS = 4;
	public final static int EPOCHS_NUMBER = 3;

	private ArrayList<Tower> towerList = null;
	private ArrayList<ActionSpace> actionSpace = null; //da confermare utilizzo lista
	private ArrayList<Player> playerOnCouncil;
	private ArrayList<VictoryResource> faithPath = null; //array da 16? elementi che dice quanti punti vittoria vengono assegnati all'i-esimo posto del tracciato
	private ArrayList<MilitaryResource> militaryPath = null; //array da 6 elementi che dice quanti punti militare servono per poter avere "i" territori
	private ArrayList<ExcommunicationCard> excomCards = null; //array da 3 elementi che contiene i riferimenti alle carte scomunica pescate ad inizio partita

	private Board(ArrayList<Tower> towerList, ArrayList<ActionSpace> actionSpace,
			ArrayList<VictoryResource> faithPath, ArrayList<MilitaryResource> militaryPath, ArrayList<ExcommunicationCard> excomCards) {
		super();
		this.towerList = towerList;
		this.actionSpace = actionSpace;
		this.faithPath = faithPath;
		this.militaryPath = militaryPath;
	}

	public ArrayList<Tower> getTowerList() {
		return towerList;
	}

	public ArrayList<ActionSpace> getActionSpace() {
		return actionSpace;
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

	public void setTowerList(ArrayList<Tower> towerList) throws RepeatedAssignmentException {
		if (this.towerList == null) {
			this.towerList = towerList;
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

}
