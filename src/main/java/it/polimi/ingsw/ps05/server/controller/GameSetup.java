package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.cards.Deck;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
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
	
	//TODO draft carte leaders
	//TODO scelta pezzo aggiuntivo plancia
	//TODO scelta regole
	
	//si presuppone che sia il network adapter o chi per lui a chiamare questa classe
	public GameSetup(ArrayList<Player> players, Game game){
		this.playerConnected = players;
		parser = new CommonJsonParser(playerConnected.size(), game);
		createFamiliarForPlayers();
		loadBoard();
		loadBonusTiles(game.isUsingCustomBonusTiles());
		if (game.isUsingCompleteRules()){
			ArrayList<ExcommunicationCard> excomm = loadExcommEffect();
			try {
				board.setExcomCards(excomm);
			} catch (RepeatedAssignmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Resource> l = parser.loadStartingResources();
		System.out.println("resource loaded in gameSetup " + l.size());
		turnSetup = new TurnSetupManager(playerConnected, board, l);
		game.setPrivilegeConvResAlternatives(parser.loadPrivilegeConversion());
		game.setAct_waiting_time_ms(parser.loadActionWaitingTimer());
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
				System.out.println("familiari già creati");
			}
		}
	}
	
	private ArrayList<ExcommunicationCard> loadExcommEffect(){
		return parser.loadExcommunicationCard("./src/main/res/excom.json");
	}
	
	private void loadBoard(){
		board = parser.loadBoard("./src/main/res/board.json");
	}
	
	private void loadBonusTiles(boolean custom){
		//ora è settato a mano il parametro del tipo ma andrà preso dal setup iniziale della partita, deciso alla creazione
		bonusTiles = parser.loadBonusTiles("./src/main/res/bonusTile.json", custom ? BonusTileType.Custom : BonusTileType.Default);
		System.out.println(bonusTiles.size());
		if (custom){
			this.board.setBonusTileArrayList(bonusTiles);
		} else {
			for (int i = 0; i < playerConnected.size(); i++) {
				playerConnected.get(i).setBonusTile(bonusTiles.get(i));
			}
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
