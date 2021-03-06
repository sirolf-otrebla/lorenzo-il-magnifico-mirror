package it.polimi.ingsw.ps05.client.view.cli;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import it.polimi.ingsw.ps05.client.view.LimView;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.spaces.CouncilSpace;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.spaces.HarvestingSpace;
import it.polimi.ingsw.ps05.model.spaces.MarketSpace;
import it.polimi.ingsw.ps05.model.spaces.ProductionSpace;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.spaces.TileWithEffect;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.cards.VioletCard;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.BonusWithMultiplier;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.MilitaryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.effects.ActivableEffect;
import it.polimi.ingsw.ps05.model.cards.BlueCard;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class CLIMain implements LimView, Runnable{


	private Semaphore internalSemaphore;
	/* view objects */

	int currentRowBoard = 0;
	int currentColBoard = 0;
	int currentRowMyStats = 0;
	int currentColMyStats = 0;
	int currentRowPlayers = 0;
	int currentColPlayers = 0;
	ArrayList<ArrayList<TerminalPosition>> mapBoard;
	ArrayList<ArrayList<TerminalPosition>> mapMyStats;
	ArrayList<ArrayList<TerminalPosition>> mapPlayers;
	ArrayList<ArrayList<Integer>> offSet;
	float ratioWidth = 1;
	float ratioHeight = 1;
	boolean ratioSet = false;
	boolean inBoard = true;
	boolean inMyStats = false;
	boolean inPlayers = false;
	private static int PREFERRED_WIDTH = 200;
	private static int PREFERRED_HEIGHT = 100;
	private static final String OCCUPIED = "Occupato";
	private Board board;
	private ArrayList<MarketSpace> marketList = new ArrayList<MarketSpace>();
	private ArrayList<ProductionSpace> productionList = new ArrayList<ProductionSpace>();
	private ArrayList<HarvestingSpace> harvestList = new ArrayList<HarvestingSpace>();
	private CouncilSpace council;
	private Player player;
	private ArrayList<Player> playersList;
	private Terminal terminal = null;
	private KeyStroke lastMove;
	TextGraphics graphics;
	boolean meActive = true;
	int selectedFam = 0;
	int selectedOpt = 0;
	Player active = null;
	Familiar ghost = null;
	int undoMoveDone = 0;
	private ArrayList<ColorEnumeration> towerOrder = new ArrayList<ColorEnumeration>(){

		private static final long serialVersionUID = 1L;

		{
			add(ColorEnumeration.Green);
			add(ColorEnumeration.Blue);
			add(ColorEnumeration.Yellow);
			add(ColorEnumeration.Violet);

		}
	};
	private ArrayList<ArrayList<Integer>> tileIdForTower = new ArrayList<ArrayList<Integer>>();

	//TODO Aggiungeere turno corrente sopra lista giocatori

	//mettere nella wiki che per la CLI la risoluzione minima consigliata è 1280*800


	/**
	 * This is the constructor of the CLI. This method just setup the object the CLI works with.
	 * @param board is the place where everything happen. The CLI will show all the information about the board.
	 * @param player is who you are, the CLI needs to know this to provide you more info about your resources and cards.
	 * @param playersList is the list of the other player in game. the CLI needs this to provide you general info about other players resources ecc.
	 * 
	 * @author lucafala
	 */
	public CLIMain(Board board, Player player, ArrayList<Player> playersList){
		this.board = board;
		this.player = player;
		this.playersList = playersList;
		this.playersList.remove(this.player);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		int width1 = gd.getDisplayMode().getWidth();
		int height1 = gd.getDisplayMode().getHeight();
		Dimension screenSize = new Dimension(width1, height1);
		Double width = (200*screenSize.getWidth()/1280);
		PREFERRED_WIDTH = width.intValue();
		Double height = (100*screenSize.getHeight()/800);
		PREFERRED_HEIGHT = height.intValue();
		System.out.println(screenSize.getWidth());
		System.out.println(screenSize.getHeight());
		System.out.println(width + ": " + height);
		internalSemaphore = new Semaphore(0);
	}


	/**
	 *This is the method that shows the CLI on the screen. Is highly reccomended to run it on a different thread because he will block the execution to wait some user's input. 
	 */
	@Override
	public void run() {
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		defaultTerminalFactory.setTerminalEmulatorTitle("Lorenzo il Magnifico");
		defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		try {
			terminal = defaultTerminalFactory.createTerminal();
			System.out.println(terminal.getTerminalSize().toString());
			System.out.println("Creating terminal");
			//"private mode" is a separate buffer for the text content that does not support any scrolling.
			terminal.enterPrivateMode();
			terminal.clearScreen();
			terminal.setCursorVisible(true);

			TextGraphics textGraphics = terminal.newTextGraphics();
			graphics = textGraphics;
			textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);


			drawGraphics(terminal.getTerminalSize().getColumns(),terminal.getTerminalSize().getRows(),textGraphics);
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			terminal.flush();

			internalSemaphore.release(2);
			terminal.addResizeListener(new TerminalResizeListener() {
				@Override
				public void onResized(Terminal terminal, TerminalSize newSize) {

					try {
						if (!ratioSet){
							ratioWidth = PREFERRED_WIDTH / newSize.getColumns();
							ratioHeight = PREFERRED_HEIGHT / newSize.getRows();
							if (ratioHeight > 2) ratioHeight = 2;
							if (ratioWidth > 1) ratioWidth = (float) 1;
							ratioSet = true;
						}
						System.out.println("NEW SIZE " + newSize.toString());
						System.out.println("ratio width: " + ratioWidth + "ratio height: " + ratioHeight);
						terminal.clearScreen();
						drawGraphics(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);	
						printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
						if (inBoard){
							terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
						} else if (inMyStats) {
							terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
						} else if (inPlayers){
							terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
						}

						terminal.flush();
					}
					catch(IOException e) {
						// Not much we can do here

						try {
							terminal.clearScreen();
							drawGraphics(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);	
							printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
							if (inBoard){
								terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
							} else if (inMyStats) {
								terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
							} else if (inPlayers){
								terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
							}
							terminal.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
							System.exit(1);
						}

						throw new RuntimeException(e);
					}
				}
			});

			movePointer(textGraphics);

		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Once the CLI is instantiated and shown if you want to update its content you have to call this method.
	 * @param status is the actual game status that the CLI will show
	 */
	public void updateGame(GameStatus status){
		System.out.println("Start update game in cli");
		this.board = null;
		this.player = null;
		this.playersList = new ArrayList<Player>();

		this.board = status.getGameBoard();
		this.player = status.getThisPlayer();
		for (Player p : status.getPlayerHashMap().values()){
			this.playersList.add(p);
		}

		this.playersList.remove(this.player);
		try {
			terminal.clearScreen();
			drawGraphics(terminal.getTerminalSize().getColumns(),terminal.getTerminalSize().getRows(),graphics);
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),graphics);
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			terminal.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("End update game in cli");
	}

	/**
	 * This method is used to select the familiar to use and the payment option
	 * @param c is the character read from the keyboard
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @throws IOException if there are problems accessing the terminal
	 */
	private void analizeChar(Character c, TextGraphics textGraphics) throws IOException{
		if ((c == 'q' || c == 'Q') && ghost == null){
			selectedFam = 0;
			drawPlayerInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),
					Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		} else if ((c == 'w' || c == 'W') && ghost == null) {
			selectedFam = 1;
			drawPlayerInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),
					Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		} else if ((c == 'e' || c == 'E') && ghost == null) {
			selectedFam = 2;
			drawPlayerInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),
					Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		} else if ((c == 'r' || c == 'R') && ghost == null) {
			selectedFam = 3;
			drawPlayerInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),
					Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		} else if (c == '1') {
			selectedOpt = 0;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		} else if (c == '2') {
			selectedOpt = 1;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		} else if (c == 'c' || c == 'C'){
			//trova e mettimi in consiglio
			System.out.println("consiglio");
			if (playersList.size() == 1){
				currentColBoard = 2;
				currentRowBoard = 4;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			} else if (playersList.size() == 2){
				currentColBoard = 2;
				currentRowBoard = 4;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			}
		} else if (c == 'm' || c == 'm'){
			//trova e metti mercato
			if (playersList.size() == 1){
				currentColBoard = 1;
				currentRowBoard = 4;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			} else if (playersList.size() == 2){
				currentColBoard = 1;
				currentRowBoard = 4;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			}
		} else if (c == 'n' || c == 'N'){
			//trova e metti raccolto
			if (playersList.size() == 1){
				currentColBoard = 1;
				currentRowBoard = 5;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			} else if (playersList.size() == 2){
				currentColBoard = 3;
				currentRowBoard = 4;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			}
			
		} else if (c == 'b' || c == 'B'){
			//metti in 0,0
			currentColBoard = 0;
			currentRowBoard = 0;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
		}
		
		System.out.println("Current col: " + currentColBoard + " max: " + mapBoard.size());
		System.out.println("Current row: " + currentRowBoard + " max: " + mapBoard.get(currentColBoard).size());
		

	}

	/**
	 * This is the method that manage the user's input. Based on what it reads it does action.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @throws IOException if there are problems accessing the terminal
	 */
	private void movePointer(TextGraphics textGraphics) throws IOException{
		KeyStroke keyStroke = terminal.readInput();
		while(true) {
			lastMove = keyStroke;
			switch(keyStroke.getKeyType()){
			case Character:
				analizeChar(keyStroke.getCharacter(), textGraphics);
				break;
			case ArrowDown: 
				moveDown(textGraphics);
				break;
			case ArrowLeft:
				moveLeft(textGraphics);
				break;
			case ArrowRight:
				moveRight(textGraphics);
				break;
			case ArrowUp:
				moveUp(textGraphics);
				break;
			case Enter:
				System.out.println("meActive value: \t" + meActive);
				if (inPlayers){
					if (currentRowPlayers == 0) {
						visualCard(playersList.get(currentColPlayers).getGreenCardList(), textGraphics.getSize().getColumns());
					} else if (currentRowPlayers == 1) {
						visualCard(playersList.get(currentColPlayers).getBlueCardList(), textGraphics.getSize().getColumns());
					} else if (currentRowPlayers == 2) {
						visualCard(playersList.get(currentColPlayers).getYellowCardList(), textGraphics.getSize().getColumns());
					} else if (currentRowPlayers == 3) {
						visualCard(playersList.get(currentColPlayers).getVioletCardList(), textGraphics.getSize().getColumns());
					}
				} else if(meActive && inBoard){
					doActionForPlayer();
				} else if (inMyStats && currentRowMyStats == 5 && currentColMyStats == 1){
					selectLeaderCard();
				}
				break;
			case Tab:
				if (inBoard){
					inBoard = false;
					inMyStats = true;
					inPlayers = false;
					printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				} else if (inMyStats){
					inBoard = false;
					inMyStats = false;
					inPlayers = true;
					printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				} else if (inPlayers){
					inBoard = true;
					inMyStats = false;
					inPlayers = false;
					printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				}
				break;
			case Escape:
				CliPassActionViewObject obj = new CliPassActionViewObject();
				obj.notifyToObservers();
				break;
			case EOF:
				System.exit(0);
				break;
			default:
				System.out.println("Default");
				break;
			}
			if (inMyStats){
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			} else if (inPlayers){
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			} else if (inBoard){
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			}
			terminal.flush();
			keyStroke = terminal.readInput();
		}
	}

	/**
	 * This method tries to do action for player if the conditions are satisfied (resources, dice, free space)
	 * @throws IOException if there are problems accessing the terminal
	 * @see Action
	 */
	private void doActionForPlayer() throws IOException {
		System.out.println("Do action!!!!!");
		if (currentColBoard < board.getTowerList().size() && currentRowBoard < board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()){
			if (!((ActionSpace)board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard))).isOccupied()) {
				//NON OCCUPATO
				System.out.println("in board non occupato");
				Action ac = new Action(ghost != null ? ghost:((Familiar)this.player.getFamilyList().toArray()[selectedFam]),
						board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)));
				System.out.println("Action is legal? " + ac.isLegal());
				if (ac.isLegal()){
					TowerCard c = board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)).getCard();
					CliTileVIewObject a = new CliTileVIewObject(board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)),
							ghost != null ? ghost.getColor():((Familiar)this.player.getFamilyList().toArray()[selectedFam]).getColor(), 
									selectedOpt < c.getRequirements().size() ? selectedOpt : 0);
					System.out.println("notifyToActionHandler to observers");
					a.notifyToActionHandler();
				}
			}
		} else if(currentColBoard < board.getTowerList().size() &&
				currentRowBoard == board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()) {
			//MARKET
			System.out.println("Market");
			Action ac = new Action(ghost != null ? ghost:(Familiar)this.player.getFamilyList().toArray()[selectedFam],marketList.get(currentColBoard));
			System.out.println("Action is legal? " + ac.isLegal());
			if (ac.isLegal()){
				CliActionSpaceViewObject a = new CliActionSpaceViewObject(marketList.get(currentColBoard), ghost != null ? ghost.getColor():((Familiar)this.player.getFamilyList().toArray()[selectedFam]).getColor());
				a.notifyToActionHandler();
			}
		} else if (currentRowBoard == board.getTowerList().size() + 1 && currentColBoard < productionList.size()){
			//PRODUCTION
			System.out.println("Production");
			choseActivableProductionCard(terminal.getTerminalSize().getColumns());
		} else if (currentRowBoard == board.getTowerList().size() + 1 && 
				currentColBoard >= productionList.size() &&
				currentColBoard < productionList.size() + harvestList.size() ){
			//HARVEST
			System.out.println("Harvest");
			choseActivableHarvestCard(terminal.getTerminalSize().getColumns());
		} else {
			//CONSIGLIO
			System.out.println("Consiglio");
			Action action = new Action(ghost != null ? ghost:(Familiar)this.player.getFamilyList().toArray()[selectedFam],council);
			if (action.isLegal()){
				CliActionSpaceViewObject a = new CliActionSpaceViewObject(council,
						ghost != null ? ghost.getColor():((Familiar)this.player.getFamilyList().toArray()[selectedFam]).getColor());
				a.notifyToActionHandler();
			}

		}
		//meActive = false;
	}

	/**
	 * This method moves the cursor one position up.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @throws IOException if there are problems accessing the terminal
	 */
	private void moveUp(TextGraphics textGraphics) throws IOException {
		if (inBoard){
			try{
				currentRowBoard--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare su");
				currentRowBoard++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			}
		} else if (inMyStats){
			try{
				currentRowMyStats--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare su");
				currentRowMyStats++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			}
		} else if (inPlayers){
			try{
				currentRowPlayers--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare su");
				currentRowPlayers++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			}
		}
	}

	/**
	 * This method moves the cursor one position right.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @throws IOException if there are problems accessing the terminal
	 */
	private void moveRight(TextGraphics textGraphics) throws IOException {
		if (inBoard){
			try {
				currentColBoard++;
				//se è la riga finale di una colonna e quella dopo ha meno o più colonne, scorrendo a destra
				//mi posiziono sulla sua ultima
				if (currentRowBoard == mapBoard.get(currentColBoard-1).size()-1){ //è l'ultima riga?
					currentRowBoard = mapBoard.get(currentColBoard).size()-1; // si allora metto l'ultima riga della nuova colonna
				} else if (currentRowBoard > mapBoard.get(currentColBoard).size()-1 && currentColBoard == mapBoard.size()-1){ //la riga non c'è nella nuova colonna e la nuova colonna è l'ultima?
					currentRowBoard = mapBoard.get(currentColBoard).size()-1; //si allora imposto l'ultima riga disponibile della colonna
				}

				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare a destra");
				currentColBoard--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			}
		} else if (inMyStats){
			try {
				currentColMyStats++;
				//se è la riga finale di una colonna e quella dopo ha meno o più colonne, scorrendo a destra
				//mi posiziono sulla sua ultima
				if (currentRowMyStats == mapMyStats.get(currentColMyStats-1).size()-1){ //è l'ultima riga?
					System.out.println("ultima riga");
					currentRowMyStats = mapMyStats.get(currentColMyStats).size()-1; // si allora metto l'ultima riga della nuova colonna
				} else if (currentRowMyStats > mapMyStats.get(currentColMyStats).size()-1 && currentColMyStats == mapMyStats.size()-1){ //la riga non c'è nella nuova colonna e la nuova colonna è l'ultima?
					System.out.println("no riga a dx");
					currentRowMyStats = mapMyStats.get(currentColMyStats).size()-1; //si allora imposto l'ultima riga disponibile della colonna
				}
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare a destra");
				currentColMyStats--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			}
		} else if (inPlayers){
			try {
				currentColPlayers++;
				//se è la riga finale di una colonna e quella dopo ha meno o più colonne, scorrendo a destra
				//mi posiziono sulla sua ultima
				if (currentRowPlayers == mapPlayers.get(currentColPlayers-1).size()-1){ //è l'ultima riga?
					System.out.println("ultima riga");
					currentRowPlayers = mapPlayers.get(currentColPlayers).size()-1; // si allora metto l'ultima riga della nuova colonna
				} else if (currentRowPlayers > mapPlayers.get(currentColPlayers).size()-1 && currentColPlayers == mapPlayers.size()-1){ //la riga non c'è nella nuova colonna e la nuova colonna è l'ultima?
					System.out.println("no riga a dx");
					currentRowPlayers = mapPlayers.get(currentColPlayers).size()-1; //si allora imposto l'ultima riga disponibile della colonna
				}
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare a destra");
				currentColPlayers--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			}
		}
	}

	/**
	 * This method moves the cursor one position down.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @throws IOException if there are problems accessing the terminal
	 */
	private void moveDown(TextGraphics textGraphics) throws IOException{
		if (inBoard){
			try{
				currentRowBoard++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			} catch(IndexOutOfBoundsException e){
				System.out.println("Impossibile andare giù");
				currentRowBoard--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			}
		} else if (inMyStats){
			try{
				currentRowMyStats++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			} catch(IndexOutOfBoundsException e){
				System.out.println("Impossibile andare giù");
				currentRowMyStats--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			}
		} else if (inPlayers){
			try{
				currentRowPlayers++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			} catch(IndexOutOfBoundsException e){
				System.out.println("Impossibile andare giù");
				currentRowPlayers--;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			}
		}
	}

	/**
	 * This method moves the cursor one position left.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @throws IOException if there are problems accessing the terminal
	 */
	private void moveLeft(TextGraphics textGraphics) throws IOException {
		if (inBoard){
			try{
				currentColBoard--;
				if (currentRowBoard == mapBoard.get(currentColBoard+1).size()-1){
					currentRowBoard = mapBoard.get(currentColBoard).size()-1;
				}
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare a sinistra");
				currentColBoard++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			}
		} else if (inMyStats) {
			try{
				currentColMyStats--;
				if (currentColMyStats == mapMyStats.get(currentColMyStats+1).size()-1){
					currentColMyStats = mapMyStats.get(currentColMyStats).size()-1;
				}
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare a sinistra");
				currentColMyStats++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
			}
		} else if (inPlayers) {
			try{
				currentColPlayers--;
				if (currentColPlayers == mapPlayers.get(currentColPlayers+1).size()-1){
					currentColPlayers = mapPlayers.get(currentColPlayers).size()-1;
				}
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Impossibile andare a sinistra");
				currentColPlayers++;
				printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
				terminal.setCursorPosition(mapPlayers.get(currentColPlayers).get(currentRowPlayers));
			}
		}
	}

	/**
	 * This is the general method that gives the input for drawing all the graphics.
	 * @param width is the terminal width in which we will draw
	 * @param height is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void drawGraphics(int width, int height, TextGraphics textGraphics){
		mapBoard = new ArrayList<ArrayList<TerminalPosition>>();
		offSet = new ArrayList<ArrayList<Integer>>();
		marketList = new ArrayList<MarketSpace>();
		productionList = new ArrayList<ProductionSpace>();
		harvestList = new ArrayList<HarvestingSpace>();
		mapMyStats = new ArrayList<ArrayList<TerminalPosition>>();
		mapPlayers = new ArrayList<ArrayList<TerminalPosition>>();



		drawBoard(width,height,textGraphics);

		drawSquare(
				0,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)),
				4*height/16+1,textGraphics);

		Iterator<ActionSpace> iterator = board.getActSpacesMap().values().iterator();
		while(iterator.hasNext()){
			ActionSpace actionSpace = iterator.next();
			if (actionSpace instanceof MarketSpace){
				try {
					ArrayList<TerminalPosition> list = mapBoard.get(marketList.size());
					list.add(new TerminalPosition((marketList.size()+1)*width/16 - width/32,
							6*height/16 - height/32));
					if (actionSpace.isOccupied()){
						textGraphics.putString((marketList.size()+1)*width/16 - width/32,
								6*height/16 - height/32, "X", SGR.BOLD);
					}
					drawSquare(
							marketList.size()*width/16,
							5*height/16,
							(marketList.size()+1)*width/16,
							6*height/16,textGraphics
							);
					marketList.add((MarketSpace) actionSpace);
				} catch (IndexOutOfBoundsException e) {
					System.err.println("il market è andato out of bound");
				}

			} else if (actionSpace instanceof ProductionSpace){
				ArrayList<TerminalPosition> list = mapBoard.get(productionList.size());
				list.add(new TerminalPosition((productionList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (actionSpace.isOccupied()){
					textGraphics.putString((productionList.size()+1)*width/16 - width/32,
							7*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(
						productionList.size()*width/16,
						6*height/16+2,
						(productionList.size()+1)*width/16,
						7*height/16+2,
						textGraphics
						);
				productionList.add((ProductionSpace)actionSpace);
			}
		}

		// separati

		for (ActionSpace action : board.getActSpacesMap().values()){
			if (action instanceof HarvestingSpace){
				ArrayList<TerminalPosition> list = mapBoard.get(productionList.size()+harvestList.size());
				list.add(new TerminalPosition((productionList.size()+harvestList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (action.isOccupied()){
					textGraphics.putString((productionList.size() + harvestList.size()+1)*width/16 - width/32,
							7*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(
						(productionList.size()+harvestList.size())*width/16,
						6*height/16+2,
						(productionList.size()+harvestList.size()+1)*width/16,
						7*height/16+2,
						textGraphics
						);
				harvestList.add((HarvestingSpace)action);
			} else if (action instanceof CouncilSpace){
				council = (CouncilSpace)action;
				ArrayList<TerminalPosition> list;
				drawSquare(
						(Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16,
						5*height/16,
						(Math.max(marketList.size(), productionList.size()+harvestList.size())+2)*width/16,
						7*height/16+2,
						textGraphics
						);
				try {
					list = mapBoard.get(marketList.size());

					list.add(new TerminalPosition((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16 +
							(Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/32,
							6*height/16));
				} catch (IndexOutOfBoundsException e){
					list = new ArrayList<TerminalPosition>();
					list.add(new TerminalPosition((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16 +
							width/32,
							6*height/16));
					mapBoard.add(list);
				}

			}
		}

		drawPlayerInfo(width,height,textGraphics);

		drawExcomunication(width, height, textGraphics);

		drawPlayerStats(width,height,textGraphics);

		//Aggiungere scritta consiglio
		textGraphics.putString((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16
				,5*height/16-1, "Consiglio");
		textGraphics.putString((Math.max(marketList.size(), productionList.size()+harvestList.size())+3)*width/16 - width/32 
				,5*height/16-1, "Scoumnica");
		textGraphics.putString((marketList.size()/2)*width/16
				,5*height/16-1, "Mercato");
		textGraphics.putString((productionList.size()/2)*width/16
				,6*height/16+1, "Produzione");
		textGraphics.putString((int) (Math.ceil((productionList.size()+harvestList.size())/2.0))*width/16+1
				,6*height/16+1, "Raccolto");

		checkPositionCorrect();
		
		for (int i = 0; i < mapBoard.size(); i++){
			for (int j = 0; j < mapBoard.get(i).size(); j++){
				System.out.println(i + " " + j + " " + mapBoard.get(i).get(j));
			}
		}
	}

	/**
	 * Working with hashmap may happens that some lists are different from the expectation. This method corrects the position errors in the CLI
	 */
	private void checkPositionCorrect(){
		for (ArrayList<TerminalPosition> column : mapBoard){
			for (int i = 0; i < column.size() - 1; i++ ){
				if (column.get(i).getRow() > column.get(i + 1).getRow()){
					TerminalPosition infra = column.get(i);
					column.set(i, column.get(i + 1));
					column.set(i + 1, infra);
				}
			}
		}
	}

	/**
	 * This method draws the excommunication box and prints inside the info about the current excommunication
	 * @param width is the terminal width in which we will draw
	 * @param height is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void drawExcomunication(int width, int height, TextGraphics textGraphics){
		int x = (Math.max(marketList.size(), productionList.size()+harvestList.size())+3)*width/16 - width/32;
		drawSquare(
				(Math.max(marketList.size(), productionList.size()+harvestList.size())+3)*width/16 - width/32,
				5*height/16,
				(Math.max(marketList.size(), productionList.size()+harvestList.size())+4)*width/16 + width/32,
				7*height/16+2,
				textGraphics
				);
		//TODO
		String toWrite;
		if (board.getExcomCards() != null && board.getExcomCards().size() > 0){
			textGraphics.putString(x + 1, 5*height/16 + 1, board.getExcomCards().get((int)Math.floor((board.getTurnNumber()-1)/2)).getEpochID().toString());
			textGraphics.putString(x + 1, 5*height/16 + 2, board.getExcomCards().get(0).getFaithRequested().toString() + " " + 
					board.getExcomCards().get(0).getFaithRequested().getValue());
			toWrite = board.getExcomCards().get(0).getExcommEffect().toString();
		} else {
			toWrite = "Regole semplici selezionate. No scomuniche per questa partita";
		}



		int size = (Math.max(marketList.size(), productionList.size()+harvestList.size())+4)*width/16 + width/32 - x;
		int i = 0;
		do{
			if (toWrite.length() > size - 2 ){
				textGraphics.putString(x+1, 5*height/16 + 3 + i, toWrite.substring(0, size-2));
				toWrite = toWrite.substring(size-2,toWrite.length());
			} else {
				textGraphics.putString(x + 1, 5*height/16 + 3 + i, toWrite);
				toWrite = "";
			}
			i++;
		} while(toWrite.length() != 0);


	}

	/**
	 * This method draws the player box and prints inside the info about his resources and cards.
	 * @param width is the terminal width in which we will draw
	 * @param height is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void drawPlayerInfo(int width, int height, TextGraphics textGraphics){
		int chosenColStart = Math.max(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 6 + width/8 , 5*width/8);
		drawSquare(chosenColStart,0,width - 1, 4*height/16 + 1,textGraphics);
		TerminalPosition lastPos = new TerminalPosition(chosenColStart + 1, 0);
		if (active != null && active.getPlayerID() == player.getPlayerID()){
			textGraphics.setBackgroundColor(TextColor.ANSI.YELLOW);
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, player.getUsername());
		textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		ArrayList<TerminalPosition> firstColumn = new ArrayList<TerminalPosition>();
		for (Resource resource : player.getResourceList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, resource.toString() + " " + resource.getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			firstColumn.add(lastPos);
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		int startCardRow = lastPos.getRow();
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte verdi:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		for (GreenCard card : player.getGreenCardList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, card.getName());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			firstColumn.add(lastPos);
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 7 - player.getGreenCardList().size(), "Carte Blu:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+7);
		for (BlueCard card : player.getBlueCardList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, card.getName());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			firstColumn.add(lastPos);
		}
		mapMyStats.add(firstColumn);
		ArrayList<TerminalPosition> secondColumn = new ArrayList<TerminalPosition>();
		lastPos = new TerminalPosition((chosenColStart+width-1)/2 ,0);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Familiari:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		int i = 0;
		for (Familiar familiar : player.getFamilyList()){

			if (i == selectedFam && !familiar.isUsed() && ghost == null){
				textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
			}
			i++;
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, (familiar.isUsed() ? "-":"+") +
					familiar.getColor().toString() + " " + 
					familiar.getRelatedDice().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			secondColumn.add(lastPos);
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
		}
		if (ghost != null){
			textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, (ghost.isUsed() ? "-":"+") +
					ghost.getColor().toString() + " " + 
					ghost.getRelatedDice().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			//secondColumn.add(lastPos);
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
		}

		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Scomuniche");
		lastPos = new TerminalPosition(lastPos.getColumn() ,lastPos.getRow() + 1);
		secondColumn.add(lastPos);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte leaders");
		lastPos = new TerminalPosition(lastPos.getColumn() ,lastPos.getRow() + 1);
		secondColumn.add(lastPos);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Bonus Tile");
		lastPos = new TerminalPosition(lastPos.getColumn() ,lastPos.getRow() + 1);
		secondColumn.add(lastPos);
		lastPos = new TerminalPosition(lastPos.getColumn() ,startCardRow);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte Gialle:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		for (YellowCard card : player.getYellowCardList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, card.getName());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			secondColumn.add(lastPos);
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 7 - player.getYellowCardList().size(), "Carte Viola:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+7);
		for (VioletCard card : player.getVioletCardList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, card.getName());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			secondColumn.add(lastPos);
		}
		mapMyStats.add(secondColumn);
	}

	/**
	 * This method draws the board box and all the info about spaces
	 * @param width is the terminal width in which we will draw
	 * @param height is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void drawBoard(int width, int height, TextGraphics textGraphics){
		for (int a = 0; a < board.getTowerList().size(); a++){
			Tower tower = board.getTowerList().get(towerOrder.get(a));
			String toWrite = "Torre " + tower.getColor().toString();
			textGraphics.putString(a*width/8 + 1, 1, toWrite, SGR.BOLD);
			ArrayList<TerminalPosition> list = new ArrayList<TerminalPosition>();
			ArrayList<Integer> off = new ArrayList<Integer>();
			ArrayList<Integer> ids = new ArrayList<>();
			for (TowerTileInterface tile : tower.getTiles().values()){
				ids.add(((ActionSpace)tile).getId());
			}
			Comparator<Integer> comp = (Integer x, Integer y) -> {
				return x.compareTo(y);
			};
			ids.sort(comp);
			for (int b = 0; b < tower.getTiles().size(); b++){
				TowerTileInterface tile = tower.getTiles().get(ids.get(b));
				off.add(((ActionSpace)tile).isOccupied() ? 
						OCCUPIED.length() : 
							tile.getCard().getName().length());
				textGraphics.putString(a*width/8 + 1 + (a != 0 ? 1:0), 3 + b*height/16, ((ActionSpace)tile).isOccupied() ? OCCUPIED : tile.getCard().getName());
				textGraphics.putString(a*width/8 + 1 + (a != 0 ? 1:0), 4 + b*height/16, "Dado: " + tile.getDiceRequired().getValue());
				list.add(new TerminalPosition(a*width/8 + 1 + off.get(b) + (a!=0 ? 1:0), 3 + b*height/16));
				ids.add(((ActionSpace)tile).getId());
				if (tile instanceof TileWithEffect){
					for (ActionResult result : ((TileWithEffect)tile).getEffectOnPositioning()){
						try {
							textGraphics.putString(a*width/8 + 1 + (a!=0 ? 1:0),
									5 + b*height/16, result.toString() + " " + result.getValue());
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							//niente da fare in teoria non viene lanciata mai
						}
					}
				}
			}
			tileIdForTower.add(ids);
			off.add(toWrite.length());
			offSet.add(off);
			mapBoard.add(list);
		}
	}

	/**
	 * This method draws opponents box and their info
	 * @param width is the terminal width in which we will draw
	 * @param height is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void drawPlayerStats(int width, int height, TextGraphics textGraphics){
		drawSquare(
				(Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 - 1,
				5*height/16,
				width-1,
				7*height/16+2,
				textGraphics
				);
		textGraphics.putString((Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 + 1,
				5*height/16-1, "Turno " + board.getTurnNumber());
		int startCol = (Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 - 1;
		int dist = width-1 - ((Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 - 1);
		for (int i = 0; i < playersList.size(); i++){
			ArrayList<TerminalPosition> list = new ArrayList<>();
			TerminalPosition lastPos = new TerminalPosition(startCol + 1 + i*dist/3,5*height/16);
			Player p = playersList.get(i);
			if (active != null && active.getPlayerID() == p.getPlayerID()){
				textGraphics.setBackgroundColor(TextColor.ANSI.YELLOW);
			}
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, p.getUsername());
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			for (Resource r : p.getResourceList()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, r.toString() + " " + r.getValue());
				lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			}
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte verdi " + p.getGreenCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			list.add(lastPos);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte blu " + p.getBlueCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			list.add(lastPos);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte gialle " + p.getYellowCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			list.add(lastPos);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte viola " + p.getVioletCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			list.add(lastPos);
			mapPlayers.add(list);
		}
	}

	/**
	 * Generic method that draws a square given the starting and ending coordinates (highleft and bottomright)
	 * @param colStart high left x
	 * @param rowStart high left y
	 * @param colEnd bottom right x
	 * @param rowEnd bottom right y
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	public static void drawSquare(int colStart,int rowStart, int colEnd, int rowEnd, TextGraphics textGraphics){
		textGraphics.drawLine(colStart,rowStart,colStart,rowEnd,'|');
		textGraphics.drawLine(colEnd,rowStart,colEnd,rowEnd,'|');
		textGraphics.drawLine(colStart,rowStart,colEnd,rowStart,'-');
		textGraphics.drawLine(colStart,rowEnd,colEnd,rowEnd,'-');
	}

	/**
	 * This method is used to calculate the highest length in a set of string.
	 * @param list is the array containing the length of some string
	 * @return the highest lenght in the array
	 */
	private Integer getMaxOffset(ArrayList<Integer> list){
		Integer max = 0;
		for (Integer i : list){
			if (i > max){
				max = i;
			}
		}
		return max;
	}

	/**
	 * This is the main method that print info about the position selected. He recognizes where you are and automatically call the right method to show you everything you need.
	 * @param width is the terminal width in which we will draw
	 * @param height is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void printInfo(int width, int height, TextGraphics textGraphics){
		int offsetToUse = 0;
		try{
			offsetToUse = getMaxOffset(offSet.get(offSet.size()-1));
		} catch (Exception e){

		}
		int colForInfo = 3*width/8+3+offsetToUse + 3;
		String space = new String(new char[width/8 + 3]).replace('\0', ' ');
		for (int i = 1; i < height/4; i++){
			textGraphics.putString(colForInfo, i, 
					space);
		}

		drawSquare(
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2 + width/8,
				height/4+1,
				textGraphics);

		if (inBoard){
			try{
				if (currentColBoard < board.getTowerList().size() && currentRowBoard < board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()){
					try {
						if (((ActionSpace)board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard))).isOccupied()) {
							infoOccupied(colForInfo,0,textGraphics, ((ActionSpace)board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard))));
						} else {
							infoCard(colForInfo,0,board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)).getCard(), textGraphics);
						}
					} catch (NullPointerException e){
						try{
							infoCard(colForInfo,0,board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)).getCard(), textGraphics);
						} catch (NullPointerException e1){

						}
					}
				} else if(currentColBoard < board.getTowerList().size() && currentRowBoard == board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size() && playersList.size() == 3) {
					infoMarket(colForInfo,0,textGraphics);
				} else if (currentRowBoard == board.getTowerList().size() + 1 && currentColBoard < productionList.size()){
					infoProduction(colForInfo,0,textGraphics);
				} else if (currentRowBoard == board.getTowerList().size() + 1 && currentColBoard >= productionList.size() && currentColBoard < productionList.size() + harvestList.size() ){
					infoHarvest(colForInfo,0,textGraphics);
				} else {
					if (playersList.size() == 2 && currentColBoard == 3 && currentRowBoard == 4){
						infoHarvest(colForInfo,0,textGraphics);
					} else if (playersList.size() == 2 && currentColBoard == 2 && currentRowBoard == 5){
						infoHarvest(colForInfo,0,textGraphics);
					} else if (playersList.size() == 2 && ((currentColBoard == 0 && currentRowBoard == 4) || (currentColBoard == 1 && currentRowBoard == 4))){ 
						infoMarket(colForInfo,0,textGraphics);
					} else {
						infoCouncil(colForInfo,0,textGraphics);
					}
					
				}
			} catch (IndexOutOfBoundsException e){
				try{
					undoMoveDone++;
					undoLastMove(textGraphics);
					
				} catch (IOException e1){

				}
			}
		} else if (inMyStats){
			if (currentColMyStats == 0){
				if (currentRowMyStats < player.getResourceList().size()){
					//risorse
					infoResource(colForInfo,0,textGraphics);
				} else if (currentRowMyStats < player.getResourceList().size() + player.getGreenCardList().size()){
					//carta verde
					infoCard(colForInfo, 0, player.getGreenCardList().get(currentRowMyStats-player.getResourceList().size()), textGraphics);
				} else {
					//carta blu
					infoCard(colForInfo, 0, player.getBlueCardList().get(currentRowMyStats-player.getResourceList().size()-player.getGreenCardList().size()), textGraphics);
				}
			} else {
				if (currentRowMyStats < player.getFamilyList().size()){
					infoFamiliar(colForInfo, 0,textGraphics);
				} else if (currentRowMyStats < player.getFamilyList().size() + 3){
					if (currentRowMyStats == player.getFamilyList().size()){

					} else if (currentRowMyStats == player.getFamilyList().size() + 1){

					} else if (currentRowMyStats == player.getFamilyList().size() + 2){
						try {
							infoBonusTile(colForInfo,0,textGraphics);
						} catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (currentRowMyStats < player.getFamilyList().size() + 3 + player.getYellowCardList().size()){
					infoCard(colForInfo, 0, player.getYellowCardList().get(currentRowMyStats - 3 - player.getFamilyList().size()), textGraphics);
				} else {
					infoCard(colForInfo, 0, player.getYellowCardList().get(currentRowMyStats - 3 - player.getFamilyList().size() - player.getYellowCardList().size()), textGraphics);
				}
			}
		}
	}

	private void undoLastMove(TextGraphics textGraphics) throws IOException{
		if (undoMoveDone >= 3){
			currentColBoard = 0;
			currentRowBoard = 0;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()),textGraphics);
			return;
		}
		if (lastMove != null){
			switch(lastMove.getKeyType()){
			case ArrowUp:
				moveDown(textGraphics);
				break;
			case ArrowDown:
				moveUp(textGraphics);
				break;
			case ArrowLeft:
				moveRight(textGraphics);
				break;
			case ArrowRight:
				moveLeft(textGraphics);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * This method is used to print info about a space occupied.
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @param space we want to know info about its occupant.
	 */
	private void infoOccupied(int column, int row, TextGraphics textGraphics, ActionSpace space){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		ArrayList<Familiar> list = new ArrayList<>();
		list.add(space.getOccupant());
		list.addAll(space.getAdditionalOccupant());
		for (Familiar f : list){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
					f.getRelatedPlayer().getUsername());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + f.getColor().toString() + " " + f.getRelatedDice().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
				"Legal? " + (checkIsLegal() ? "Si" : "No"));
	}

	/**
	 * This method print info about the selected bonus tile.
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw

	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoBonusTile(int column, int row, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Bonus Tile");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		BonusTile tile = player.getBonusTile();
		for (Effect effect : tile.getEffectList()){
			lastPos = activableEffect(lastPos, (ActivableEffect)effect,textGraphics);
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}

	}
	/**
	 * This method print info about the selected familiar (its dice value and if it is used or not) 
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoFamiliar(int column, int row, TextGraphics textGraphics){
		Familiar familiar = (Familiar) player.getFamilyList().toArray()[currentRowMyStats];
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.getColor().toString() + " " + familiar.getRelatedDice().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.isUsed() ? "Posizionato" : "Non usato");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.isUsed() ? familiar.getPosition().toString() : "");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
	}

	/**
	 * This method prints info about the conversion path of faith and military points.
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoResource(int column, int row, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, player.getResourceList().get(currentRowMyStats).toString() + " " +
				player.getResourceList().get(currentRowMyStats).getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (player.getResourceList().get(currentRowMyStats) instanceof FaithResource){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Fede  Vittoria");
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			for (int i = 0; i < board.getFaithPath().size(); i++){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1 , i + "   ->   " +  board.getFaithPath().get(i).getValue());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
		} else if (player.getResourceList().get(currentRowMyStats) instanceof MilitaryResource){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Terreni  Militare");
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			for (int i = 0; i < board.getMilitaryPath().size(); i++){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1 , (i+1) + "   ->   " +  board.getMilitaryPath().get(i).getValue());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
		}
	}

	/**
	 * This method prints info about the selected market space.
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoMarket(int column, int row, TextGraphics textGraphics) throws IndexOutOfBoundsException{
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Mercato " + (currentColBoard + 1));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (marketList.get(currentColBoard).isOccupied()){
			infoOccupied(lastPos.getColumn(), lastPos.getRow(), textGraphics, ((ActionSpace)marketList.get(currentColBoard)));
		} else {
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Dado: " + marketList.get(currentColBoard).getDiceRequirement().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			System.out.println("MARKET REQUIREMENTS: " + marketList.get(currentColBoard).getRequirements().size());
			for (Effect effect : marketList.get(currentColBoard).getEffects()){
				System.out.println("EFFETTO MERCATO ------ " + effect.getEffectType().toString());
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				System.out.println("SIZE-------" + ((SimpleEffect)effect).getResultList().size());
				for (ActionResult result : ((SimpleEffect)effect).getResultList()) {
					System.out.println("BONUS---------" + result.toString());
					try {
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					} catch (NoSuchMethodException e) {
						//non fare niente, lanciato solo con moltiplicatori, qui non ci sono
					}
				}
			}
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
				"Legal? " + (checkIsLegal() ? "Si" : "No"));
	}

	/**
	 * This method prints info about the selected production space
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw

	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoProduction(int column, int row, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Production " + (currentColBoard + 1));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Dado: " + productionList.get(currentColBoard).getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (productionList.get(currentColBoard).isOccupied()){
			infoOccupied(lastPos.getColumn(), lastPos.getRow(), textGraphics, ((ActionSpace)productionList.get(currentColBoard)));
		} 
		if (!productionList.get(currentColBoard).isOccupied() && productionList.get(currentColBoard).getEffects().size() != 0){
			for (Effect effect : productionList.get(currentColBoard).getEffects()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);		
				for (ActionResult result : ((SimpleEffect)effect).getResultList()){
					try {
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow()+1,	
								result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					} catch (NoSuchMethodException e) {
						//non fare niente, lanciata solo con moltiplicatori, qui non ci sono
					}
				}

			}
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
				"Legal? " + (checkIsLegal() ? "Si" : "No"));
	}

	/**
	 * This method print info about a selected card.
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw

	 * @param card is the TowerCard about you want to print the ifno
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoCard(int column, int row, TowerCard card, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				card.getName());
		System.out.println("---------" + card.getName());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Costi:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		try {
			int i = 0;
			for (ArrayList<Resource> choseOr : card.getRequirements()){
				if (i == selectedOpt && inBoard){
					textGraphics.setBackgroundColor(TextColor.ANSI.RED);
				}
				for (Resource res : choseOr){
					if (!res.getID().equals(Dice.ID)){
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, res.toString() + " " + res.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					}
				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
				i++;
			}
		} catch (Exception e){
			//requirementList == null, non fare niente cercare di risolvere
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		try {
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Effetti:");
			System.out.println("--------Effetti carta: " + card.effects.size());
			for (Effect effect : card.getEffects()){
				System.out.println("-------Sono nel ciclo " + effect.getClass());
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				System.out.println(lastPos + "---------Effetto: " + effect.getEffectType().toString());
				if (!(effect instanceof ActivableEffect)) {
					System.out.println("---------risorse per effetto: " + ((SimpleEffect)effect).getResultList().size());
					for (ActionResult result : ((SimpleEffect)effect).getResultList()) {
						System.out.println(lastPos + "--------" + result.toString() + " " + result.getValue());
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					}
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				} else {
					lastPos = activableEffect(lastPos,(ActivableEffect)effect,textGraphics);
				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}

		} catch (NoSuchMethodException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
				"Legal? " + (checkIsLegal() ? "Si" : "No"));
	}

	/**
	 * This method is called by infoCard and is used to print activable effect.
	 * @param lastPos is the last position where the terminal printed a string.
	 * @param effect is the activable effect that contains all the information that we are going to print.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @return the last position where he printed something
	 */
	private TerminalPosition activableEffect(TerminalPosition lastPos, ActivableEffect effect, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getActivableEffectType().toString());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Dado " + effect.getDiceRequired());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);

		if (effect.getResourceRequired().size() != 0){
			lastPos = activableWithResourceRequired(lastPos,effect,textGraphics);
		} else {
			lastPos = activableWithOutResourceRequired(lastPos,effect,textGraphics);
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		return lastPos;
	}

	/**
	 * This method is used to print info about activable effect that doesn't have requirement to activate them (dice excluded)
	 * @param lastPos is the last position where the terminal printed a string.
	 * @param effect is the activable effect that contains all the information that we are going to print.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @return the last position where he printed something
	 */
	private TerminalPosition activableWithOutResourceRequired(TerminalPosition lastPos, ActivableEffect effect, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		for (ArrayList<ActionResult> choseOrRes : effect.getResultList()){
			for (ActionResult result : choseOrRes){
				if ( result instanceof BonusWithMultiplier){
					lastPos = bonusWithMultiplier(lastPos,(BonusWithMultiplier)result,textGraphics);
				} else {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getValue() + " " + result.toString());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				}
			}
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		return lastPos;
	}

	/**
	 * This method is used to print info about activable effect that have requirements to activate them (dice excluded)
	 * @param lastPos is the last position where the terminal printed a string.
	 * @param effect is the activable effect that contains all the information that we are going to print.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @return the last position where he printed something
	 */
	private TerminalPosition activableWithResourceRequired(TerminalPosition lastPos, ActivableEffect effect, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException, NoSuchMethodException{

		for (int i = 0; i < effect.getResourceRequired().size(); i++){
			for (Resource resource : effect.getResourceRequired().get(i)) {
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, resource.getValue() + " " + resource.toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "-->");
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			for (ActionResult result : effect.getResultList().get(i)){
				if ( result instanceof BonusWithMultiplier){
					lastPos = bonusWithMultiplier(lastPos,(BonusWithMultiplier)result,textGraphics);
				} else {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getValue() + " " + result.toString());
				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		return lastPos;
	}

	/**
	 * This method is used to print info about a particular bonus that contains multiplier
	 * @param lastPos is the last position where the terminal printed a string.
	 * @param result is the bonus (with multiplier) about we are going to print info.
	 * @param textGraphics is the graphic object where the CLI is going to write
	 * @return the last position where he printed something
	 */
	private TerminalPosition bonusWithMultiplier(TerminalPosition lastPos, BonusWithMultiplier result, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException{
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() , result.getMultiplier()+"x"+
				result.getCardToCount().newInstance().toString());
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "-->");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getReturnResource().toString());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		return lastPos;
	}

	/**
	 * This method prints info about the selected harvest space
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw

	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoHarvest(int column, int row, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Harvest " + (currentColBoard + 1 - productionList.size()));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Dado: " + harvestList.get(currentColBoard-productionList.size()).getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (harvestList.get(currentColBoard-productionList.size()).isOccupied()){
			infoOccupied(lastPos.getColumn(), lastPos.getRow(), textGraphics, ((ActionSpace)harvestList.get(-productionList.size() + currentColBoard)));
		} 
		if (!harvestList.get(currentColBoard-productionList.size()).isOccupied() && harvestList.get(currentColBoard-productionList.size()).getEffects().size() != 0) {
			for (Effect effect : harvestList.get(currentColBoard-productionList.size()).getEffects()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				for (ActionResult result : ((SimpleEffect)effect).getResultList()){
					try {
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow()+1,	
								result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					} catch (NoSuchMethodException e) {
						//non fare niente, lanciata solo con moltiplicatori, qui non ci sono
					}
				}

			}
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
				"Legal? " + (checkIsLegal() ? "Si" : "No"));
	}

	/**
	 * This method prints info about the council space
	 * @param column is the terminal width in which we will draw
	 * @param row is the terminal height in which we will draw
	 * @param textGraphics is the graphic object where the CLI is going to write
	 */
	private void infoCouncil(int column, int row, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Council");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow()+1, 
				council.getDiceRequirement().toString() + " " + council.getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);

		for (Effect effect : council.getEffects()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow()+1, 
					effect.getEffectType().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			for (ActionResult result : ((SimpleEffect)effect).getResultList()){
				try {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
							result.toString() + " " + result.getValue());
					lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
				} catch (NoSuchMethodException e){
					//qui non fare niente, viene lancio per i moltiplicatori
				}
			}
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Occupanti: ");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		for (Familiar fam : council.getOccupantList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					fam.getRelatedPlayer().getUsername());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"fam " + fam.getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, 
				"Legal? " + (checkIsLegal() ? "Si" : "No"));
	}

	/**
	 * This method create a context where you have to chose between your cards that contains an harvest effect and decide which you want to activate.
	 * @param width is the terminal width in which we will draw
	 */
	private void choseActivableHarvestCard(int width){

		CliTerminalForCardsList chose = new CliTerminalForCardsList(this.player.getGreenCardList(), width, this.player.getGreenCardList().size(), 0);
		ArrayList<?> a = chose.start();
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> option = new ArrayList<>();
		if (a.size() != 0){
			System.out.println("---------A.SIZE = " + a.size());
			ArrayList<ArrayList<Integer>> temp1 = (ArrayList<ArrayList<Integer>>)a;
			System.out.println("CARTE SELEZIONATE " + temp1.size());
			for (Integer c : temp1.get(0)){
				System.out.println("ID CARTA SELEZIONATA PER HARVEST " +c);
				ids.add(c);
			}
			for (Integer c : temp1.get(1)){
				option.add(c);
			}
			CliHarvestSpaceViewObject b = new CliHarvestSpaceViewObject(harvestList.get(-productionList.size() + currentColBoard),
					ghost != null ? ghost.getColor(): ((Familiar)this.player.getFamilyList().toArray()[selectedFam]).getColor(), 
							ids, option);
			b.notifyToActionHandler();
		}

	}

	/**
	 * This method create a context where you have to chose between your cards that contains a production effect and decide which you want to activate.
	 * @param width is the terminal width in which we will draw
	 */
	private void choseActivableProductionCard(int width){

		CliTerminalForCardsList chose = new CliTerminalForCardsList(this.player.getYellowCardList(), width, this.player.getYellowCardList().size(), 0);
		ArrayList<?> a = chose.start();
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> option = new ArrayList<>();
		if (a.size() != 0){
			System.out.println("---------A.SIZE = " + a.size());
			ArrayList<ArrayList<Integer>> temp1 = (ArrayList<ArrayList<Integer>>)a;
			System.out.println("CARTE SELEZIONATE " + temp1.size());
			for (Integer c : temp1.get(0)){
				ids.add(this.player.getYellowCardList().get(c).getReferenceID());
			}
			for (Integer c : temp1.get(1)){
				option.add(c);
			}
			System.out.println("sto per notificare all'observer");
			CliProductionSpaceViewObject b = new CliProductionSpaceViewObject(productionList.get(currentColBoard),
					ghost != null ? ghost.getColor():((Familiar)this.player.getFamilyList().toArray()[selectedFam]).getColor(),
							ids,option);
			b.notifyToActionHandler();
		}
	}

	/**
	 * This method create a context where you have to choose at least one object (it can be a card or a bonus tile).
	 * @param cards is the array that contains the objects among you have to choose
	 * @param width is the terminal width in which we will draw
	 * @return an array of id about the selected items
	 */
	private ArrayList<?> choseDraftCard(ArrayList<?> cards, int width){

		CliTerminalForCardsList chose = new CliTerminalForCardsList(cards, width, 1, 1);
		return chose.start();
	}

	/**
	 * This method create a context where you have to choose the conversion method of your privilege bonus.
	 * @param list contains the possible choice among you can choose to convert the bonus.
	 * @param choiceToDo is the number of the possible choice that you have to do
	 * @return an array containing the number of the index/es selected
	 */
	public ArrayList<Integer> getPrivilegeBonusChoice(ArrayList<ArrayList<Resource>> list, int choiceToDo){
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			CliTerminalForCardsList chose = new CliTerminalForCardsList(list, terminal.getTerminalSize().getColumns(), choiceToDo, choiceToDo);
			for (Object c : chose.start()){
				result.add((Integer)c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * This method create a context where you can see the passed resources. Here you can just see but not select
	 * @param cards contains the objects that will be displayed in the new context
	 * @param width is the terminal width in which we will draw
	 */
	private void visualCard(ArrayList<?> cards, int width){

		CliTerminalForCardsList chose = new CliTerminalForCardsList(cards, width, 0, 0);
		chose.start();
	}

	/**
	 * This method set the player that is going to do an action
	 * @param active is the current player
	 */
	public void setActivePlayer (Player active){
		this.active = active;
		if (active.getPlayerID() == player.getPlayerID()){
			meActive = true;
			createTerminalWithMessage(active.getUsername() + " è il tuo turno!");
		} else {
			meActive = false;
			resetGhostFamiliar();
		}
		updateGame(new GameStatus(playersList, board, this.player, active.getPlayerID()));
	}

	/**
	 * this method prepare the draft of the leader cards
	 * @param list of the leader card among you have to chose
	 * @return the id of the selected card
	 */
	public Integer getCardForLeaderDraft(List<Integer> list) throws IOException{
		try {
			internalSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<LeaderCard> cardsToCheck = new ArrayList<>();
		ArrayList<LeaderCard> allCards = board.getLeaderCardsList();
		System.out.println("Tutte le carte leader sono: " + allCards.size());
		System.out.println("----------le carte leader da mostrare sono " + list.size());
		for (Integer i : list){
			System.out.println("Player.id " + player.getPlayerID() + " Leader.id " + i);
			LeaderCard l = getLeaderWithID(i, allCards);
			System.out.println("carta leader selezionata è null?" + (l == null));
			cardsToCheck.add(l);
		}

		System.out.println("-----------le carte selezionabili sono: " + cardsToCheck.size());
		ArrayList<?> chosenCard = choseDraftCard(cardsToCheck, terminal.getTerminalSize().getColumns());
		internalSemaphore.release();
		Integer c = cardsToCheck.get((Integer)chosenCard.get(0)).getReferenceID();
		return c;
	}

	/**
	 * This method returns a leader card from a given array
	 * @param id is the id of the desired leader card
	 * @param cards is the array containing all the leader card
	 * @return the desired leader card
	 */
	private LeaderCard getLeaderWithID(Integer id, ArrayList<LeaderCard> cards){
		for (LeaderCard l : cards){
			if (l.getReferenceID().equals(id)){
				return l;
			}
		}
		System.out.println("Sto pre ritornare null");
		return null;
	}

	/**
	 * this method create a context where you can see yours leader cards
	 * @param list is the array of leader card
	 * @param width is the terminal width in which we will draw
	 * @return
	 */
	private ArrayList<?> showLeaderCard(ArrayList<?> list, int width){
		CliTerminalForCardsList chose = new CliTerminalForCardsList(list, width, 0, list.size());
		return chose.start();
	}

	/**
	 * this method discard or activate a selected leader card (the card will be selected in a new context)
	 * @throws IOException
	 */
	private void selectLeaderCard() throws IOException{

		ArrayList<?> chosenCard = choseDraftCard(player.getLeaderCardList(), terminal.getTerminalSize().getColumns());
		boolean success = true;

		for (ArrayList<Resource> a : ((LeaderCard)player.getLeaderCardList().get((Integer) chosenCard.get(0))).getRequirements()){
			success = true;
			for (Resource r : a){
				try{
					if (!r.hasEnoughResources(this.player.createGhostFamiliar(0))){
						success = false;
						break;
					}
				} catch(Exception e){
					success = false;
				}
			}
			if (success) break;
		}

		if (success || ((LeaderCard)player.getLeaderCardList().get((Integer) chosenCard.get(0))).isActive()){
			//attivare carta leader
			CliActivateLeaderViewObject obj = new CliActivateLeaderViewObject(((LeaderCard)player.getLeaderCardList().get((Integer) chosenCard.get(0))));
			obj.notifyToObservers();
		} else {
			//player.getLeaderCardList().remove(((LeaderCard)chosenCard.get(0)));
			CliDiscardLeaderViewObject obj = new CliDiscardLeaderViewObject(((LeaderCard)player.getLeaderCardList().get((Integer) chosenCard.get(0))));
			obj.notifyToObservers();
		}
	}

	/**
	 * this method check if an action with a selected familiar and a selected space is legal
	 * @return
	 */
	private boolean checkIsLegal(){

		if (currentColBoard < board.getTowerList().size() && currentRowBoard < board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()){
			if (!((ActionSpace)board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard))).isOccupied()) {
				//NON OCCUPATO
				System.out.println("in board non occupato");
				Action ac = new Action(((Familiar)this.player.getFamilyList().toArray()[selectedFam]),
						board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)));
				System.out.println("Action is legal? " + ac.isLegal());
				return ac.isLegal();
			}
		} else if(currentColBoard < board.getTowerList().size() &&
				currentRowBoard == board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()) {
			//MARKET
			System.out.println("Market");
			Action ac = new Action((Familiar)this.player.getFamilyList().toArray()[selectedFam],marketList.get(currentColBoard));
			System.out.println("Action is legal? " + ac.isLegal());
			return ac.isLegal();

		} else if (currentRowBoard == board.getTowerList().size() + 1 && currentColBoard < productionList.size()){
			//PRODUCTION
			System.out.println("Production");
			Action ac = new Action((Familiar)this.player.getFamilyList().toArray()[selectedFam],productionList.get(currentColBoard));
			System.out.println("Action is legal? " + ac.isLegal());
			return ac.isLegal();
		} else if (currentRowBoard == board.getTowerList().size() + 1 && 
				currentColBoard >= productionList.size() &&
				currentColBoard < productionList.size() + harvestList.size() ){
			//HARVEST
			System.out.println("Harvest");
			Action ac = new Action((Familiar)this.player.getFamilyList().toArray()[selectedFam],harvestList.get(currentColBoard-productionList.size()));
			System.out.println("Action is legal? " + ac.isLegal());
			return ac.isLegal();
		} else {
			//CONSIGLIO
			System.out.println("Consiglio");
			Action ac = new Action((Familiar)this.player.getFamilyList().toArray()[selectedFam],council);
			System.out.println("Action is legal? " + ac.isLegal());
			return ac.isLegal();
		}
		return false;
	}

	/**
	 * This method set a familiar for a bonus action and set active the player
	 * @param f the familiar to be used
	 */
	public void actionWithGhostFamiliar(Familiar f){
		System.out.println("Setted ghost familiar");
		ghost = f;
		meActive = true;
	}

	/**
	 * this method reset the familiar used for the bonus action
	 */
	private void resetGhostFamiliar(){
		ghost = null;
	}

	public boolean askForExcomm() throws IOException{
		//domanda: VOGLIO ESSERE SCOMUNICATO?
		ArrayList<String> list = new ArrayList<>();
		list.add("Non voglio essere scomunicato");
		list.add("Voglio essere scomunicato");
		CliTerminalForCardsList chose = new CliTerminalForCardsList(list, terminal.getTerminalSize().getColumns(), 1, 1);
		ArrayList<Integer> a = (ArrayList<Integer>)chose.start();
		if (a.size() == 0) return false;

		return a.get(0) == 0 ? false : true;

	}

	public Integer getBonusTileDraft(ArrayList<Integer> list){
		System.out.println("nel bonustile draft");
		ArrayList<BonusTile> toShow = new ArrayList<>();
		for (BonusTile t : board.getBonusTileArrayList()){
			for (int i = 0; i < list.size(); i++){
				if (t.getReferenceID().equals(list.get(i))){
					toShow.add(t);
				}
			}
		}
		System.out.println("ho istanziato l'array da mostrare, ha " + toShow.size() + " elementi");

		try {
			System.out.println("Dentro al try");
			internalSemaphore.acquire();
			CliTerminalForCardsList chose = new CliTerminalForCardsList(toShow,
					terminal.getTerminalSize().getColumns(), 1, 1);
			ArrayList<Integer> a = (ArrayList<Integer>)chose.start();
			if (a.size() == 0) return 0;
			System.out.println("A!= 0");
			return toShow.get(a.get(0)).getReferenceID();

		} catch (Exception e) {
			System.out.println("eccezione lanciata, " + e.getClass());
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * this method create a context where you can display some message
	 * @param s is the message that will be displayed
	 */
	public void createTerminalWithMessage(String s){
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		defaultTerminalFactory.setTerminalEmulatorTitle("Attenzione");
		defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(30, 10));
		try {
			Terminal terminalPopUp = defaultTerminalFactory.createTerminal();
			terminalPopUp.enterPrivateMode();
			terminalPopUp.clearScreen();
			terminalPopUp.setCursorVisible(false);

			TextGraphics popupGraphics = terminalPopUp.newTextGraphics();
			String toWrite = s;//board.getExcomCards().get(0).getExcommEffect().toString();

			int size = 30;
			int i = 1;
			do{
				if (toWrite.length() > size - 2 ){
					popupGraphics.putString(1, i, toWrite.substring(0, size-2));
					toWrite = toWrite.substring(size-2,toWrite.length());
				} else {
					popupGraphics.putString(1, i, toWrite);
					toWrite = "";
				}
				i++;
			} while(toWrite.length() != 0);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void setSelectedLeaderCard(ArrayList<Integer> list){
		for (Integer i : list){
			this.player.putLeaderCard(getLeaderWithID(i, board.getLeaderCardsList()));
		}

	}

}
