package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class GreenCardDeck implements Deck {
	
	ArrayList<GreenCard> cardList;
	
	public GreenCardDeck(ArrayList<GreenCard> cardList){
		this.cardList = cardList;
	}
}
