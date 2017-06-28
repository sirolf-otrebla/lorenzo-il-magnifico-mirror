package it.polimi.ingsw.ps05.client.view.cli;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.spaces.CouncilSpace;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.spaces.HarvestingSpace;
import it.polimi.ingsw.ps05.model.spaces.MarketSpace;
import it.polimi.ingsw.ps05.model.spaces.ProductionSpace;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.spaces.TileWithEffect;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.cards.VioletCard;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.BonusWithMultiplier;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.MilitaryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.effects.ActivableEffect;
import it.polimi.ingsw.ps05.model.cards.BlueCard;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class CLIMain implements Runnable{
	int currentRowBoard = 0;
	int currentColBoard = 0;
	int currentRowMyStats = 0;
	int currentColMyStats = 0;
	ArrayList<ArrayList<TerminalPosition>> mapBoard;
	ArrayList<ArrayList<TerminalPosition>> mapMyStats;
	ArrayList<ArrayList<Integer>> offSet;
	float ratioWidth = 1;
	float ratioHeight = 1;
	boolean ratioSet = false;
	boolean inBoard = true;
	boolean inMyStats = false;
	boolean inPlayersStats = false;
	private static int PREFERRED_WIDTH = 200;
	private static int PREFERRED_HEIGHT = 100;
	private static final String OCCUPIED = "Occupato";
	private Board board;
	private ArrayList<MarketSpace> marketList = new ArrayList<MarketSpace>();
	private ArrayList<ProductionSpace> productionList = new ArrayList<ProductionSpace>();
	private ArrayList<HarvestingSpace> harvestList = new ArrayList<HarvestingSpace>();
	private CouncilSpace council;
	private TextGraphics textGraphics;
	private Player player;
	private ArrayList<Player> playersList;
	private Terminal terminal = null;
	private ArrayList<ColorEnumeration> towerOrder = new ArrayList<ColorEnumeration>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add(ColorEnumeration.Green);
		add(ColorEnumeration.Blue);
		add(ColorEnumeration.Yellow);
		add(ColorEnumeration.Violet);
		
	}};
	private ArrayList<ArrayList<Integer>> tileIdForTower = new ArrayList<ArrayList<Integer>>();
	
	/*
	 * fare selezione privilegi
	 * fare draft
	 * 
	 */
	
	//mettere nella wiki che per la CLI la risoluzione minima consigliata è 1280*800
	
	

	public CLIMain(Board board, Player player, ArrayList<Player> playersList){
		this.board = board;
		this.player = player;
		this.playersList = playersList;
		this.playersList.remove(this.player);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double width = (200*screenSize.getWidth()/1280);
		PREFERRED_WIDTH = width.intValue();
		Double height = (100*screenSize.getHeight()/800);
		PREFERRED_HEIGHT = height.intValue();
		System.out.println(screenSize.getWidth());
		System.out.println(screenSize.getHeight());
	}



	@Override
	public void run() {
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		try {
			terminal = defaultTerminalFactory.createTerminal();
			//"private mode" is a separate buffer for the text content that does not support any scrolling.
			terminal.enterPrivateMode();
			terminal.clearScreen();
			terminal.setCursorVisible(true);

			textGraphics = terminal.newTextGraphics();

			textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);


			drawGraphics(terminal.getTerminalSize().getColumns(),terminal.getTerminalSize().getRows());
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			terminal.flush();

			terminal.addResizeListener(new TerminalResizeListener() {
				@Override
				public void onResized(Terminal terminal, TerminalSize newSize) {
					try {
						if (!ratioSet){
							ratioWidth = PREFERRED_WIDTH / newSize.getColumns();
							ratioHeight = PREFERRED_HEIGHT / newSize.getRows();
							ratioSet = true;
						}

						terminal.clearScreen();
						drawGraphics(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));	
						printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
						terminal.flush();
					}
					catch(IOException e) {
						// Not much we can do here
						
						try {
							terminal.clearScreen();
							drawGraphics(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));	
							printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
							terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
							terminal.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
							System.exit(1);
						}
						
						throw new RuntimeException(e);
					}
				}
			});
			
			movePointer();

		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBoard(Board board){
		this.board = board;
		try {
			drawGraphics(terminal.getTerminalSize().getColumns(),terminal.getTerminalSize().getRows());
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
			terminal.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private void movePointer() throws IOException{
		KeyStroke keyStroke = terminal.readInput();
		while(true) {
			switch(keyStroke.getKeyType()){
			case ArrowDown: 
				if (inBoard){
					moveInBoardDown();
				} else if (inMyStats){
					moveInMyStatsDown();
				}

				break;
			case ArrowLeft:
				if (inBoard){
					moveInBoardLeft();
				} else if (inMyStats){
					moveInMyStatsLeft();
				}

				break;
			case ArrowRight:
				if (inBoard){
					moveInBoardRight();
				} else if (inMyStats){
					moveInMyStatsRight();
				}
				break;
			case ArrowUp:
				if (inBoard){
					moveInBoardUp();
				} else if (inMyStats){
					moveInPlayerStatsUp();
				}
				break;
			case Enter:
				//terminal.setCursorPosition(70, 6);
				break;
			case Escape:
				//terminal.setCursorPosition(currentCol, currentRow);
				break;
			case Tab:
				if (inBoard){
					inBoard = false;
					inMyStats = true;
					inPlayersStats = false;
					printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
					terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
				} else if (inMyStats){
					inBoard = true;
					inMyStats = false;
					inPlayersStats = false;
					printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
					terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
				}

				break;
			default:
				break;
			}
			terminal.flush();
			keyStroke = terminal.readInput();
		}
	}

	private void moveInPlayerStatsUp() throws IOException{
		try{
			currentRowMyStats--;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Impossibile andare su");
			currentRowMyStats++;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		}
	}

	private void moveInBoardUp() throws IOException{
		try{
			currentRowBoard--;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Impossibile andare su");
			currentRowBoard++;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		}
	}

	private void moveInBoardRight() throws IOException{
		try {
			currentColBoard++;
			//se è la riga finale di una colonna e quella dopo ha meno o più colonne, scorrendo a destra
			//mi posiziono sulla sua ultima
			if (currentRowBoard == mapBoard.get(currentColBoard-1).size()-1){ //è l'ultima riga?
				currentRowBoard = mapBoard.get(currentColBoard).size()-1; // si allora metto l'ultima riga della nuova colonna
			} else if (currentRowBoard > mapBoard.get(currentColBoard).size()-1 && currentColBoard == mapBoard.size()-1){ //la riga non c'è nella nuova colonna e la nuova colonna è l'ultima?
				currentRowBoard = mapBoard.get(currentColBoard).size()-1; //si allora imposto l'ultima riga disponibile della colonna
			}

			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Impossibile andare a destra");
			currentColBoard--;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		}
	}

	private void moveInMyStatsRight() throws IOException{
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
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Impossibile andare a destra");
			currentColMyStats--;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		}
	}

	private void moveInBoardDown() throws IOException{
		try{
			currentRowBoard++;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		} catch(IndexOutOfBoundsException e){
			System.out.println("Impossibile andare giù");
			currentRowBoard--;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		}
	}

	private void moveInMyStatsDown() throws IOException{
		try{
			currentRowMyStats++;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		} catch(IndexOutOfBoundsException e){
			System.out.println("Impossibile andare giù");
			currentRowMyStats--;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		}
	}

	private void moveInBoardLeft() throws IOException{
		try{
			currentColBoard--;
			if (currentRowBoard == mapBoard.get(currentColBoard+1).size()-1){
				currentRowBoard = mapBoard.get(currentColBoard).size()-1;
			}
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Impossibile andare a sinistra");
			currentColBoard++;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapBoard.get(currentColBoard).get(currentRowBoard));
		}
	}

	private void moveInMyStatsLeft() throws IOException{
		try{
			currentColMyStats--;
			if (currentColMyStats == mapMyStats.get(currentColMyStats+1).size()-1){
				currentColMyStats = mapMyStats.get(currentColMyStats).size()-1;
			}
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Impossibile andare a sinistra");
			currentColMyStats++;
			printInfo(Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(mapMyStats.get(currentColMyStats).get(currentRowMyStats));
		}
	}

	private void drawGraphics(int width, int height){
		mapBoard = new ArrayList<ArrayList<TerminalPosition>>();
		offSet = new ArrayList<ArrayList<Integer>>();
		marketList = new ArrayList<MarketSpace>();
		productionList = new ArrayList<ProductionSpace>();
		harvestList = new ArrayList<HarvestingSpace>();
		mapMyStats = new ArrayList<ArrayList<TerminalPosition>>();



		drawBoard(width,height);

		drawSquare(
				0,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)),
				4*height/16+1);

		Iterator<ActionSpace> iterator = board.getActSpacesMap().values().iterator();
		while(iterator.hasNext()){
			ActionSpace actionSpace = iterator.next();
			if (actionSpace instanceof MarketSpace){
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
						6*height/16
						);
				marketList.add((MarketSpace) actionSpace);
			} else if (actionSpace instanceof ProductionSpace){
				ArrayList<TerminalPosition> list = mapBoard.get(productionList.size());
				list.add(new TerminalPosition((productionList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (actionSpace.isOccupied()){
					textGraphics.putString((productionList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(
						productionList.size()*width/16,
						6*height/16+2,
						(productionList.size()+1)*width/16,
						7*height/16+2
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
					textGraphics.putString((harvestList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(
						(productionList.size()+harvestList.size())*width/16,
						6*height/16+2,
						(productionList.size()+harvestList.size()+1)*width/16,
						7*height/16+2
						);
				harvestList.add((HarvestingSpace)action);
			} else if (action instanceof CouncilSpace){
				council = (CouncilSpace)action;
				ArrayList<TerminalPosition> list;
				drawSquare(
						(Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16,
						5*height/16,
						(Math.max(marketList.size(), productionList.size()+harvestList.size())+2)*width/16,
						7*height/16+2
						);
				try {
					list = mapBoard.get(marketList.size());
					list.add(new TerminalPosition((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16 +
							(Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/32,
							6*height/16));
				} catch (IndexOutOfBoundsException e){
					System.out.println("Eccezione consiglio");
					list = new ArrayList<TerminalPosition>();
					list.add(new TerminalPosition((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16 +
							width/32,
							6*height/16));
					mapBoard.add(list);
				}

			}
		}

		drawPlayerInfo(width,height);

		drawExcomunication(width, height);

		drawPlayerStats(width,height);

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
	}
	
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

	private void drawExcomunication(int width, int height){
		int x = (Math.max(marketList.size(), productionList.size()+harvestList.size())+3)*width/16 - width/32;
		drawSquare(
				(Math.max(marketList.size(), productionList.size()+harvestList.size())+3)*width/16 - width/32,
				5*height/16,
				(Math.max(marketList.size(), productionList.size()+harvestList.size())+4)*width/16 + width/32,
				7*height/16+2
				);
		textGraphics.putString(x + 1, 5*height/16 + 1, board.getExcomCards().get(0).getEpochID().toString());
		textGraphics.putString(x + 1, 5*height/16 + 2, board.getExcomCards().get(0).getFaithRequested().toString() + " " + 
				board.getExcomCards().get(0).getFaithRequested().getValue());
		String toWrite = board.getExcomCards().get(0).getExcommEffect().toString();

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

	private void drawPlayerInfo(int width, int height){
		int chosenColStart = Math.max(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 6 + width/8 , 5*width/8);
		drawSquare(chosenColStart,0,width - 1, 4*height/16 + 1);
		TerminalPosition lastPos = new TerminalPosition(chosenColStart + 1, 0);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, player.getUsername());
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
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 7, "Carte Blu:");
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
		for (Familiar familiar : player.getFamilyList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.isUsed()? "-":"+" +
					familiar.getColor().toString() + " " + 
					familiar.getRelatedDice().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			secondColumn.add(lastPos);
		}
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Scomuniche");
		lastPos = new TerminalPosition(lastPos.getColumn() ,lastPos.getRow() + 1);
		secondColumn.add(lastPos);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte leader");
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
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 7, "Carte Viola:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+7);
		for (VioletCard card : player.getVioletCardList()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, card.getName());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			secondColumn.add(lastPos);
		}
		mapMyStats.add(secondColumn);
	}

	private void drawBoard(int width, int height){
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
				off.add(((ActionSpace)tile).isOccupied() ? OCCUPIED.length() : tile.getCard().getName().length());
				textGraphics.putString(a*width/8 + 1 + (a != 0 ? 1:0), 3 + b*height/16, tile.getCard().getName());
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

	private void drawPlayerStats(int width, int height){
		drawSquare(
				(Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 - 1,
				5*height/16,
				width-1,
				7*height/16+2
				);

		int startCol = (Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 - 1;
		int dist = width-1 - ((Math.max(marketList.size(), productionList.size()+harvestList.size()+2) + 3)*width/16 - 1);
		for (int i = 0; i < playersList.size(); i++){
			TerminalPosition lastPos = new TerminalPosition(startCol + 1 + i*dist/3,5*height/16);
			Player p = playersList.get(i);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, p.getUsername());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			for (Resource r : p.getResourceList()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, r.toString() + " " + r.getValue());
				lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			}
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte verdi " + p.getGreenCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte blu " + p.getBlueCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte gialle " + p.getYellowCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Carte viola " + p.getVioletCardList().size());
			lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);

		}

	}

	private void drawSquare(int colStart,int rowStart, int colEnd, int rowEnd){
		textGraphics.drawLine(colStart,rowStart,colStart,rowEnd,'|');
		textGraphics.drawLine(colEnd,rowStart,colEnd,rowEnd,'|');
		textGraphics.drawLine(colStart,rowStart,colEnd,rowStart,'-');
		textGraphics.drawLine(colStart,rowEnd,colEnd,rowEnd,'-');
	}

	private Integer getMaxOffset(ArrayList<Integer> list){
		Integer max = 0;
		for (Integer i : list){
			if (i > max){
				max = i;
			}
		}
		return max;
	}

	private void printInfo(int width, int height){
		int colForInfo = 3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3;
		String space = new String(new char[width/8 + 3]).replace('\0', ' ');
		for (int i = 1; i < height/4; i++){
			textGraphics.putString(colForInfo, i, 
					space);
		}

		drawSquare(
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2 + width/8,
				height/4+1);


		if (inBoard){
			if (currentColBoard < board.getTowerList().size() && currentRowBoard < board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()){
				infoCard(colForInfo,0,board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().get(tileIdForTower.get(currentColBoard).get(currentRowBoard)).getCard());
			} else if(currentColBoard < board.getTowerList().size() && currentRowBoard == board.getTowerList().get(towerOrder.get(currentColBoard)).getTiles().size()) {
				infoMarket(colForInfo,0);
			} else if (currentRowBoard == board.getTowerList().size() + 1 && currentColBoard < productionList.size()){
				infoProduction(colForInfo,0);
			} else if (currentRowBoard == board.getTowerList().size() + 1 && currentColBoard >= productionList.size() && currentColBoard < productionList.size() + harvestList.size() ){
				infoHarvest(colForInfo,0);
			} else {
				infoCouncil(colForInfo,0);
			}
		} else if (inMyStats){
			if (currentColMyStats == 0){
				if (currentRowMyStats < player.getResourceList().size()){
					//risorse
					infoResource(colForInfo,0);
				} else if (currentRowMyStats < player.getResourceList().size() + player.getGreenCardList().size()){
					//carta verde
					infoCard(colForInfo, 0, player.getGreenCardList().get(currentRowMyStats-player.getResourceList().size()));
				} else {
					//carta blu
					infoCard(colForInfo, 0, player.getBlueCardList().get(currentRowMyStats-player.getResourceList().size()-player.getGreenCardList().size()));
				}
			} else {
				if (currentRowMyStats < player.getFamilyList().size()){
					infoFamiliar(colForInfo, 0);
				} else if (currentRowMyStats < player.getFamilyList().size() + 3){
					if (currentRowMyStats == player.getFamilyList().size()){

					} else if (currentRowMyStats == player.getFamilyList().size() + 1){

					} else if (currentRowMyStats == player.getFamilyList().size() + 2){
						try {
							infoBonusTile(colForInfo,0);
						} catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (currentRowMyStats < player.getFamilyList().size() + 3 + player.getYellowCardList().size()){
					infoCard(colForInfo, 0, player.getYellowCardList().get(currentRowMyStats - 3 - player.getFamilyList().size()));
				} else {
					infoCard(colForInfo, 0, player.getYellowCardList().get(currentRowMyStats - 3 - player.getFamilyList().size() - player.getYellowCardList().size()));
				}
			}
		}
	}

	private void infoBonusTile(int column, int row) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Bonus Tile");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		BonusTile tile = player.getBonusTile();
		for (Effect effect : tile.getEffectList()){
			lastPos = activableEffect(lastPos, (ActivableEffect)effect);
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}

	}

	private void infoFamiliar(int column, int row){
		Familiar familiar = player.getFamilyList().get(currentRowMyStats);
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.getColor().toString() + " " + familiar.getRelatedDice().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.isUsed() ? "Posizionato" : "Non usato");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, familiar.isUsed() ? familiar.getPosition().toString() : "");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
	}

	private void infoResource(int column, int row){
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

	private void infoMarket(int column, int row){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Mercato " + (currentColBoard + 1));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (marketList.get(currentColBoard).isOccupied()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					marketList.get(currentColBoard).getOccupant().getRelatedPlayer().getUsername());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + marketList.get(currentColBoard).getOccupant().getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		} else {
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Dado: " + marketList.get(currentColBoard).getDiceRequirement().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);

			for (Effect effect : marketList.get(currentColBoard).getEffects()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				for (ActionResult result : ((SimpleEffect)effect).getResultList()) {
					try {
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					} catch (NoSuchMethodException e) {
						//non fare niente, lanciato solo con moltiplicatori, qui non ci sono
					}
				}
			}
		}
	}

	private void infoProduction(int column, int row){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Production " + (currentColBoard + 1));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Dado: " + productionList.get(currentColBoard).getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (productionList.get(currentColBoard).isOccupied()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow(), 
					productionList.get(currentColBoard).getOccupant().getRelatedPlayer().getUsername());
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + productionList.get(currentColBoard).getOccupant().getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		if (!productionList.get(currentColBoard).isOccupied() && productionList.get(currentColBoard).getEffects().size() != 0){
			System.out.println("In");
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
	}

	private void infoCard(int column, int row, TowerCard card){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				card.getName());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Costi:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		try {
			for (ArrayList<Resource> choseOr : card.getRequirements()){
				for (Resource res : choseOr){
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, res.toString() + " " + res.getValue());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
		} catch (Exception e){
			//requirementList == null, non fare niente cercare di risolvere
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		try {
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Effetti:");
			for (Effect effect : card.getEffects()){
				textGraphics.putCSIStyledString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				if (!(effect instanceof ActivableEffect)) {
					for (ActionResult result : ((SimpleEffect)effect).getResultList()) {
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					}
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				} else {
					lastPos = activableEffect(lastPos,(ActivableEffect)effect);
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
	}

	private TerminalPosition activableEffect(TerminalPosition lastPos, ActivableEffect effect) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getActivableEffectType().toString());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Dado " + effect.getDiceRequired());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);

		if (effect.getResourceRequired().size() != 0){
			lastPos = activableWithResourceRequired(lastPos,effect);
		} else {
			lastPos = activableWithOutResourceRequired(lastPos,effect);
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		return lastPos;
	}

	private TerminalPosition activableWithOutResourceRequired(TerminalPosition lastPos, ActivableEffect effect) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		for (ArrayList<ActionResult> choseOrRes : effect.getResultList()){
			for (ActionResult result : choseOrRes){
				if ( result instanceof BonusWithMultiplier){
					lastPos = bonusWithMultiplier(lastPos,(BonusWithMultiplier)result);
				} else {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getValue() + " " + result.toString());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				}
			}
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		return lastPos;
	}

	private TerminalPosition activableWithResourceRequired(TerminalPosition lastPos, ActivableEffect effect) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		
		for (int i = 0; i < effect.getResourceRequired().size(); i++){
			for (Resource resource : effect.getResourceRequired().get(i)) {
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, resource.getValue() + " " + resource.toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "-->");
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			for (ActionResult result : effect.getResultList().get(i)){
				if ( result instanceof BonusWithMultiplier){
					lastPos = bonusWithMultiplier(lastPos,(BonusWithMultiplier)result);
				} else {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getValue() + " " + result.toString());
				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		return lastPos;
	}

	private TerminalPosition bonusWithMultiplier(TerminalPosition lastPos, BonusWithMultiplier result) throws InstantiationException, IllegalAccessException{
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() , result.getMultiplier()+"x"+
				result.getCardToCount().newInstance().toString());
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "-->");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getReturnResource().toString());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		return lastPos;
	}

	private void infoHarvest(int column, int row){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Harvest " + (currentColBoard + 1 - productionList.size()));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Dado: " + harvestList.get(currentColBoard-productionList.size()).getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (harvestList.get(currentColBoard-productionList.size()).isOccupied()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow(), 
					harvestList.get(currentColBoard-productionList.size()).getOccupant().getRelatedPlayer().getUsername());
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + harvestList.get(currentColBoard-productionList.size()).getOccupant().getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
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
	}

	private void infoCouncil(int column, int row){
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
	}



}
