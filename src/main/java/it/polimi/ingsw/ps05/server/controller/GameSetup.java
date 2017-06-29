package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.cards.Deck;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.utils.CommonJsonParser;
import it.polimi.ingsw.ps05.model.*;
import java.util.ArrayList;

public class GameSetup {

	Board board;
	ArrayList<LeaderCard> leaderCardList;
	ArrayList<Player> playerConnected; // da generare casualmente all'inizio
	ArrayList<Deck> deck;
	private TurnSetupManager turnSetup;
	ArrayList<BonusTile> bonusTiles;
	CommonJsonParser parser;
	
	//TODO draft carte leader
	//TODO scelta pezzo aggiuntivo plancia
	//TODO scelta regole
	
	//si presuppone che sia il network adapter o chi per lui a chiamare questa classe
	public GameSetup(ArrayList<Player> players, Game game){
		this.playerConnected = players;
		parser = new CommonJsonParser(playerConnected.size(), game);
		createFamiliarForPlayers();
		loadBoard();
		loadBonusTiles(game.isUsingCustomBonusTiles());
		ArrayList<ExcommunicationCard> excomm = loadExcommEffect();
		try {
			board.setExcomCards(excomm);
		} catch (RepeatedAssignmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnSetup = new TurnSetupManager(playerConnected, board);
		
	}
	
	private void createFamiliarForPlayers(){
		for (Player p : playerConnected){
			ArrayList<Familiar> familyList = new ArrayList<Familiar>();
			familyList.add(new Familiar(p, ColorEnumeration.Black));
			familyList.add(new Familiar(p, ColorEnumeration.Orange));
			familyList.add(new Familiar(p, ColorEnumeration.White));
			familyList.add(new Familiar(p, ColorEnumeration.Any));
			try {
				p.setFamiliars(familyList);
			} catch (RepeatedAssignmentException e) {
				//TODO
			}
		}
	}
	
	private ArrayList<ExcommunicationCard> loadExcommEffect(){
		return parser.loadExcommunicationCard("./src/main/res/excommunications.json");
	}
	
	private void loadBoard(){
		board = parser.loadBoard("./src/main/res/board.json");
	}
	
	private void loadBonusTiles(boolean custom){
		//ora è settato a mano il parametro del tipo ma andrà preso dal setup iniziale della partita, deciso alla creazione
		bonusTiles = parser.loadBonusTiles("./src/main/res/bonusTile.json", custom ? BonusTileType.Custom : BonusTileType.Default);
		System.out.println(bonusTiles.size());
		for (int i = 0; i < playerConnected.size(); i++){
			playerConnected.get(i).setBonusTile(bonusTiles.get(i));
		}
	}

	public Board getBoard(){
		return board;
	}

	public TurnSetupManager getTurnSetupManager(){
		return turnSetup;
	}

	public TurnSetupManager getTurnSetup(){
		return turnSetup;
	}

}