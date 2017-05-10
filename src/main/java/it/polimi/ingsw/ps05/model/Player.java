package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;

public class Player {
	
	int PlayerID;
	String username;
	
	Familiar FamilyList;
	Color Color;
	BonusTile BonusTile;
	
	FaithResource FaithPts;
	MilitaryResource MilitaryPts;
	VictoryResource VictoryPts;
	GoldResource Gold;
	WoodResource Wood;
	StoneResource Stone;
	ServantResource Servants;
	
	ArrayList<Card> GreenCardList;
	ArrayList<Card> BlueCardList;
	ArrayList<Card> YellowCardList;
	ArrayList<Card> PurpleCardList;
	ArrayList<LeaderCard> LeaderCardList;
	ArrayList<Effect> PermanentEffectList;
	
}
