package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class BlueCard extends TowerCard {

	public BlueCard(Epoch epoch, Color color, String cardName, ArrayList<ArrayList<Resource>> effects) {
		super(epoch, color, cardName, effects);
		// TODO Auto-generated constructor stub
	}
	
	public BlueCard(Epoch epoch, Color color, String cardName,  ArrayList<ArrayList<Resource>> requirements,
			ArrayList<ArrayList<Resource>> effects) {
		super(epoch, color, cardName, requirements, effects);
	}

}
