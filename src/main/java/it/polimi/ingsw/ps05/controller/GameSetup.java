package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.Utils.CommonJsonParser;
import it.polimi.ingsw.ps05.model.*;
import java.util.ArrayList;

public class GameSetup {

	Board board;
	ArrayList<LeaderCard> leaderCardList;
	ArrayList<Player> playOrder; // da generare casualmente all'inizio
	ArrayList<Deck> deck;
	
	public GameSetup(){
		loadDeck();
	}
	
	private void loadDeck(){
		CommonJsonParser parser = new CommonJsonParser();
        deck = parser.loadDeck("./src/main/res/cards.json");
        board = parser.loadBoard("./src/main/res/board.json");
	}

}
