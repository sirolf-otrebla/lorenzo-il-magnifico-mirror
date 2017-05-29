package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;

/* See UML for description
 * 
 * 
 */
public class Board {
	private static Board instance;

	public final static int MAX_PLAYERS = 4;
	public final static int EPOCHS_NUMBER = 3;

	private ArrayList<Tower> towerList;
	private ArrayList<ActionSpace> actionSpace; //da confermare utilizzo lista
	private ArrayList<Player> playerOnCouncil;
	private ArrayList<VictoryResource> faithPath; //array da 16? elementi che dice quanti punti vittoria vengono assegnati all'i-esimo posto del tracciato
	private ArrayList<MilitaryResource> militaryPath; //array da 6 elementi che dice quanti punti militare servono per poter avere "i" territori
	private ArrayList<ExcommunicationCard> excomCards; //array da 3 elementi che contiene i riferimenti alle carte scomunica pescate ad inizio partita

	private Board(ArrayList<Tower> towerList, ArrayList<ActionSpace> actionSpace,
			ArrayList<VictoryResource> faithPath, ArrayList<MilitaryResource> militaryPath, ArrayList<ExcommunicationCard> excomCards) {
		super();
		this.towerList = towerList;
		this.actionSpace = actionSpace;
		this.faithPath = faithPath;
		this.militaryPath = militaryPath;
	}

	public static Board getInstance() {
		return instance;
	}

	public static Board initBoard(ArrayList<Tower> towerList, ArrayList<ActionSpace> actionSpace, ArrayList<VictoryResource> faithPath,
									ArrayList<MilitaryResource> militaryPath, ArrayList<ExcommunicationCard> excomCards) {
		if(instance == null) {
			instance = new Board(towerList, actionSpace, faithPath, militaryPath, excomCards);
		}
		return instance;
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

	public void setTowerList(ArrayList<Tower> towerList) {
		this.towerList = towerList;
	}

	public void setActionSpace(ArrayList<ActionSpace> actionSpace) {
		this.actionSpace = actionSpace;
	}

	public void setPlayerOnCouncil(ArrayList<Player> playerOnCouncil) {
		this.playerOnCouncil = playerOnCouncil;
	}

	public void setFaithPath(ArrayList<VictoryResource> faithPath) {
		this.faithPath = faithPath;
	}

	public void setMilitaryPath(ArrayList<MilitaryResource> militaryPath) {
		this.militaryPath = militaryPath;
	}

	public void setExcomCards(ArrayList<ExcommunicationCard> excomCards) { this.excomCards = excomCards; }


}
