package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class YellowCardDeck implements Deck {

	ArrayList<YellowCard> cardList;
	
	public YellowCardDeck(ArrayList<YellowCard> cardList){
		this.cardList = cardList;
	}
}
