package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;

import javax.xml.crypto.KeySelector;

public class Player {
	
	// server information
	int playerID;
	String username;
	
	// game information
	private Color color;
	private ArrayList<Familiar> familyList;
	private BonusTile bonusTile;

	private FaithResource faithPts;
	private MilitaryResource militaryPts;
	private VictoryResource victoryPts;

	GoldResource gold;
	WoodResource wood;
	StoneResource stone;
	ServantResource servants;


	private ArrayList<GreenCard> greenCardList;
	private ArrayList<BlueCard> blueCardList;
	private ArrayList<YellowCard> yellowCardList;
	private ArrayList<VioletCard> purpleCardList;
	private ArrayList<LeaderCard> leaderCardList;

	private ArrayList<Effect> permanentEffectList;

	public Action doAction(Familiar familiar, ActionSpace position) throws OccupiedPositionException, RequirementsNotFullfilledException {

	    Action thisAction = new Action(this, familiar, position);
	    try {
	    	// run actions
		} catch (Exception e /* create more catch branch */ ){

		}
	    return thisAction;
    }

	

	public ArrayList<GreenCard> getGreenCardList() {
		return greenCardList;
	}

	public ArrayList<YellowCard> getYellowCardList() {
		return yellowCardList;
	}

	public ArrayList<BlueCard> getBlueCardList() {
		return blueCardList;
	}

	public ArrayList<VioletCard> getPurpleCardList() {
		return purpleCardList;
	}
}
