package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.utils.CommonJsonParser;
import it.polimi.ingsw.ps05.model.*;
import java.util.ArrayList;

public class GameSetup {

	Board board;
	ArrayList<LeaderCard> leaderCardList;
	ArrayList<Player> playerConnected; // da generare casualmente all'inizio
	ArrayList<Deck> deck;
	TurnSetupManager turnSetup;
	ArrayList<BonusTile> bonusTiles;
	CommonJsonParser parser;
	
	//TODO draft carte leader
	//TODO scelta scomuniche
	//TODO scelta pezzo aggiuntivo plancia
	//TODO scelta regole
	
	//si presuppone che sia il network adapter o chi per lui a chiamare questa classe
	public GameSetup(ArrayList<Player> players){
		//loadDeck();
		//load deck tolto perché le carte vengono inserite nelle torri alla loro creazione
		this.playerConnected = players;
		parser = new CommonJsonParser(playerConnected.size());
		createFamiliarForPlayers();
		loadBoard();
		turnSetup = new TurnSetupManager(playerConnected, board);
		loadBonusTiles();
	}
	
	private void createFamiliarForPlayers(){
		for (Player o : playerConnected){
			ArrayList<Familiar> familyList = new ArrayList<Familiar>();
			familyList.add(new Familiar(o, ColorEnumeration.Black));
			familyList.add(new Familiar(o, ColorEnumeration.Orange));
			familyList.add(new Familiar(o, ColorEnumeration.White));
			familyList.add(new Familiar(o, ColorEnumeration.Any));
			o.setFamiliars(familyList);
		}
	}
	
	/*private void loadDeck(){
		CommonJsonParser parser = new CommonJsonParser();
        //deck = parser.loadDeck("./src/main/res/cards.json");
	}*/
	
	private void loadBoard(){
		board = parser.loadBoard("./src/main/res/board.json");
	}
	
	private void loadBonusTiles(){
		//ora è settato a mano il parametro del tipo ma andrà preso dal setup iniziale della partita, deciso alla creazione
		bonusTiles = parser.loadBonusTiles("./src/main/res/bonusTile.json", BonusTileType.Custom);
	}
	
	public Board getBoard(){
		return board;
	}

}
