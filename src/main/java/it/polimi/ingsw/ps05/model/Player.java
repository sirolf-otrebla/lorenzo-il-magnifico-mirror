package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;

public class Player {
	
	// server information
	int playerID;
	String username;
	
	// game information
	private Color color;
	private Familiar familyList;
	private BonusTile bonusTile;

	private FaithResource faithPts;
	private MilitaryResource militaryPts;
	private VictoryResource victoryPts;

	GoldResource gold;
	WoodResource wood;
	StoneResource stone;
	ServantResource servants;


	private ArrayList<Card> greenCardList;
	private ArrayList<Card> blueCardList;
	private ArrayList<Card> yellowCardList;
	private ArrayList<Card> purpleCardList;
	private ArrayList<LeaderCard> leaderCardList;

	private ArrayList<Effect> permanentEffectList;

	public Action doAction(Familiar familiar, ActionSpace position) throws OccupiedPositionException, RequirementsNotFullfilledException {

	    Action thisAction = new Action(this, familiar, position);
	    thisAction.execute();
	    return thisAction;
    }


	
}
