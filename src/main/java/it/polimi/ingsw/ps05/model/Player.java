package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;

public class Player {
	
	// server information
	int playerID;
	String username;
	
	// game information
	Color color;
	Familiar familyList;
	BonusTile bonusTile;
	
	FaithResource faithPts;
	MilitaryResource militaryPts;
	VictoryResource victoryPts;
	
	GoldResource gold;
	WoodResource wood;
	StoneResource stone;
	ServantResource servants;
	
	ArrayList<Card> greenCardList;
	ArrayList<Card> blueCardList;
	ArrayList<Card> yellowCardList;
	ArrayList<Card> purpleCardList;
	ArrayList<LeaderCard> leaderCardList;
	
	ArrayList<Effect> permanentEffectList;
	
}
