package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.Utils.CommonJsonParser;
import it.polimi.ingsw.ps05.model.*;
import java.util.ArrayList;

public class GameSetup {

	Board board;
	ArrayList<LeaderCard> leaderCardList;
	ArrayList<Player> playConnected; // da generare casualmente all'inizio
	ArrayList<Deck> deck;
	
	public GameSetup(){
		//loadDeck();
		loadBoard();
	}
	
	/*private void loadDeck(){
		CommonJsonParser parser = new CommonJsonParser();
        //deck = parser.loadDeck("./src/main/res/cards.json");
	}*/
	
	private void loadBoard(){
		CommonJsonParser parser = new CommonJsonParser();
		board = parser.loadBoard("./src/main/res/board.json");
	}

}
