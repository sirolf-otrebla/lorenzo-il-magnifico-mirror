package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;

/* See UML for description
 * 
 * 
 */
public class Board {
	private ArrayList<Tower<TowerCard>> towerList;
	private ArrayList<ActionSpace> actionSpace; //da confermare utilizzo lista
	private ArrayList<Player> playerOnCouncil;
	private ArrayList<VictoryResource> faithPath; //array da 16? elementi che dice quanti punti vittoria vengono assegnati all'i-esimo posto del tracciato
	private ArrayList<MilitaryResource> militaryPath; //array da 6 elementi che dice quanti punti militare servono per poter avere "i" territori
	
	public Board(ArrayList<Tower<TowerCard>> towerList, ArrayList<ActionSpace> actionSpace,
			ArrayList<VictoryResource> faithPath, ArrayList<MilitaryResource> militaryPath) {
		super();
		this.towerList = towerList;
		this.actionSpace = actionSpace;
		this.faithPath = faithPath;
		this.militaryPath = militaryPath;
	}

	public ArrayList<Tower<TowerCard>> getTowerList() {
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

	public void setTowerList(ArrayList<Tower<TowerCard>> towerList) {
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
	
	
}
