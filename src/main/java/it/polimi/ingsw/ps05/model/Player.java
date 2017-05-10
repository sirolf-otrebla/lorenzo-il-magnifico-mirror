package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class Player {
	
	int PlayerID;
	String username;
	
	Familiar FamilyList;
	Color Color;
	BonusTile BonusTile;
	
	int FaithPts;
	int MilitaryPts;
	int VictoryPts;
	int Gold;
	int Wood;
	int Stone;
	int Slaves;
	
	ArrayList<Card> GreenCardList;
	ArrayList<Card> BlueCardList;
	ArrayList<Card> YellowCardList;
	ArrayList<Card> PurpleCardList;
	
}
