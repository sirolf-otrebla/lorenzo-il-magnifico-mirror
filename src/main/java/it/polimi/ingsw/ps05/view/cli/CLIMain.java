package it.polimi.ingsw.ps05.view.cli;


import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.CouncilSpace;
import it.polimi.ingsw.ps05.model.HarvestingSpace;
import it.polimi.ingsw.ps05.model.MarketSpace;
import it.polimi.ingsw.ps05.model.ProductionSpace;
import it.polimi.ingsw.ps05.model.ActionSpace;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class CLIMain implements Runnable{
	int currentRow = 0;
	int currentCol = 0;
	String[] color = {"Verde", "Blu", "Gialla", "Viola"}; 
	ArrayList<ArrayList<TerminalPosition>> map;
	ArrayList<ArrayList<Integer>> offSet;
	float ratioWidth = 1;
	float ratioHeight = 1;
	boolean ratioSet = false;
	private static final int PREFERRED_WIDTH = 200;
	private static final int PREFERRED_HEIGHT = 100;
	private static final String OCCUPIED = "Occupato";
	private Board board;
	private ArrayList<MarketSpace> marketList = new ArrayList<MarketSpace>();
	private ArrayList<ProductionSpace> productionList = new ArrayList<ProductionSpace>();
	private ArrayList<HarvestingSpace> harvestList = new ArrayList<HarvestingSpace>();

	public CLIMain(Board board){
		System.out.println("Fatto");
		this.board = board;
	}

	/*
	 * DA FARE:
	 * aggiungere spazi mancanti
	 * aggiungere valori giocatore
	 * aggiungere info posizione con cursore
	 * aggiungere effetto tile in torre
	 * aggiungere controllo occupato in tutti i posti
	 * aggiungere punteggi
	 * usare gli screen per fare cose 
	 * 
	 */

	@Override
	public void run() {
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		Terminal terminal = null;
		try {

			terminal = defaultTerminalFactory.createTerminal();
			//"private mode" is a separate buffer for the text content that does not support any scrolling.
			terminal.enterPrivateMode();
			terminal.clearScreen();
			terminal.setCursorVisible(true);

			final TextGraphics textGraphics = terminal.newTextGraphics();

			textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);


			drawGraphics(textGraphics, terminal.getTerminalSize().getColumns(),terminal.getTerminalSize().getRows());
			printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
			terminal.setCursorPosition(map.get(currentCol).get(currentRow));
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
						drawGraphics(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));	
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
						terminal.flush();
					}
					catch(IOException e) {
						// Not much we can do here
						throw new RuntimeException(e);
					}
				}
			});

			KeyStroke keyStroke = terminal.readInput();
			while(true) {
				switch(keyStroke.getKeyType()){
				case ArrowDown: 
					try{
						currentRow++;
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					} catch(IndexOutOfBoundsException e){
						System.out.println("Impossibile andare giù");
						currentRow--;
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					}
					break;
				case ArrowLeft:
					try{
						currentCol--;
						if (currentRow == map.get(currentCol+1).size()-1){
							currentRow = map.get(currentCol).size()-1;
						}
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Impossibile andare a sinistra");
						currentCol++;
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					}

					break;
				case ArrowRight:
					try {
						currentCol++;
						//se è la riga finale di una colonna e quella dopo ha meno o più colonne, scorrendo a destra
						//mi posiziono sulla sua ultima
						if (currentRow == map.get(currentCol-1).size()-1){ //è l'ultima riga?
							currentRow = map.get(currentCol).size()-1; // si allora metto l'ultima riga della nuova colonna
						} else if (currentRow != map.get(currentCol).size()-1 && currentCol == map.size()-1){ //la riga non c'è nella nuova colonna e la nuova colonna è l'ultima?
							currentRow = map.get(currentCol).size()-1; //si allora imposto l'ultima riga disponibile della colonna
						}
						
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Impossibile andare a destra");
						currentCol--;
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					}
					break;
				case ArrowUp:
					try{
						currentRow--;
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Impossibile andare su");
						currentRow++;
						printInfo(textGraphics, Math.round(ratioWidth*terminal.getTerminalSize().getColumns()),Math.round(ratioHeight*terminal.getTerminalSize().getRows()));
						terminal.setCursorPosition(map.get(currentCol).get(currentRow));
					}
					break;
				case Enter:
					//terminal.setCursorPosition(70, 6);
					break;
				case Escape:
					//terminal.setCursorPosition(currentCol, currentRow);
					break;

				default:
					break;
				}
				terminal.flush();
				keyStroke = terminal.readInput();
			}

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(terminal != null) {
				try {
					/*
	                    The close() call here will exit private mode
					 */
					terminal.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void drawGraphics(TextGraphics textGraphics, int width, int height){
		map = new ArrayList<ArrayList<TerminalPosition>>();
		offSet = new ArrayList<ArrayList<Integer>>();
		marketList = new ArrayList<MarketSpace>();
		productionList = new ArrayList<ProductionSpace>();
		harvestList = new ArrayList<HarvestingSpace>();
		for (int i = 0; i < board.getTowerList().size(); i++){
			String toWrite = "Torre " + color[i];
			textGraphics.putString(i*width/8 + 1, 1, toWrite, SGR.BOLD);
			ArrayList<TerminalPosition> list = new ArrayList<TerminalPosition>();
			ArrayList<Integer> off = new ArrayList<Integer>();
			
			for (int j = 0; j < board.getTowerList().get(i).getTiles().size(); j++){
				off.add(new Integer(((ActionSpace)board.getTowerList().get(i).getTiles().get(j)).isOccupied() ? OCCUPIED.length() :
					board.getTowerList().get(i).getTiles().get(j).getCard().getName().length()));
				textGraphics.putString(i*width/8 + 1 + (i!=0 ? 1:0), 3 + j*height/16, 
						((ActionSpace)board.getTowerList().get(i).getTiles().get(j)).isOccupied() ? OCCUPIED :
							board.getTowerList().get(i).getTiles().get(j).getCard().getName());
				textGraphics.putString(i*width/8 + 1 + (i!=0 ? 1:0), 4 + j*height/16, "Dado: " + 
						board.getTowerList().get(i).getTiles().get(j).getDiceRequired().intValue() );
				list.add(new TerminalPosition(i*width/8 + 1 + off.get(j) + (i!=0 ? 1:0), 3 + j*height/16));
			}
			off.add(toWrite.length());
			offSet.add(off);
			map.add(list);
		}
		drawSquare(textGraphics,
				0,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)),
				4*height/16+1);


		for (int i = 0; i < board.getActionSpace().size(); i++){

			if (board.getActionSpace().get(i) instanceof MarketSpace){
				ArrayList<TerminalPosition> list = map.get(marketList.size());
				list.add(new TerminalPosition((marketList.size()+1)*width/16 - width/32,
						6*height/16 - height/32));
				if (board.getActionSpace().get(i).isOccupied()){
					textGraphics.putString((marketList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(textGraphics,
						marketList.size()*width/16,
						5*height/16,
						(marketList.size()+1)*width/16,
						6*height/16
						);
				marketList.add((MarketSpace) board.getActionSpace().get(i));
			} else if (board.getActionSpace().get(i) instanceof ProductionSpace){
				ArrayList<TerminalPosition> list = map.get(productionList.size());
				list.add(new TerminalPosition((productionList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (board.getActionSpace().get(i).isOccupied()){
					textGraphics.putString((productionList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(textGraphics,
						productionList.size()*width/16,
						6*height/16+2,
						(productionList.size()+1)*width/16,
						7*height/16+2
						);
				productionList.add((ProductionSpace)board.getActionSpace().get(i));
			}
		}
		for (int i = 0; i < board.getActionSpace().size(); i++){
			if (board.getActionSpace().get(i) instanceof HarvestingSpace){
				ArrayList<TerminalPosition> list = map.get(productionList.size()+harvestList.size());
				list.add(new TerminalPosition((productionList.size()+harvestList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (board.getActionSpace().get(i).isOccupied()){
					textGraphics.putString((harvestList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(textGraphics,
						(productionList.size()+harvestList.size())*width/16,
						6*height/16+2,
						(productionList.size()+harvestList.size()+1)*width/16,
						7*height/16+2
						);
				harvestList.add((HarvestingSpace)board.getActionSpace().get(i));
			} else if (board.getActionSpace().get(i) instanceof CouncilSpace){
				ArrayList<TerminalPosition> list;
				drawSquare(textGraphics,
						(Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16,
						5*height/16,
						(Math.max(marketList.size(), productionList.size()+harvestList.size())+2)*width/16,
						7*height/16+2
						);
				try {
					list = map.get(marketList.size());
					list.add(new TerminalPosition((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16 +
							(Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/32,
							6*height/16));
				} catch (IndexOutOfBoundsException e){
					System.out.println("Eccezione consiglio");
					list = new ArrayList<TerminalPosition>();
					list.add(new TerminalPosition((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16 +
							width/32,
							6*height/16));
					map.add(list);
				}
				
			}
		}

		//Aggiungere scritta consiglio
		textGraphics.putString((Math.max(marketList.size(), productionList.size()+harvestList.size())+1)*width/16
				,5*height/16-1, "Consiglio");
		textGraphics.putString((marketList.size()/2)*width/16
				,5*height/16-1, "Mercato");
		textGraphics.putString((productionList.size()/2)*width/16
				,6*height/16+1, "Produzione");
		textGraphics.putString((int) (Math.ceil((productionList.size()+harvestList.size())/2.0))*width/16+1
				,6*height/16+1, "Raccolto");
	}

	private void drawSquare(TextGraphics g, int colStart,int rowStart, int colEnd, int rowEnd){
		g.drawLine(colStart,rowStart,colStart,rowEnd,'|');
		g.drawLine(colEnd,rowStart,colEnd,rowEnd,'|');
		g.drawLine(colStart,rowStart,colEnd,rowStart,'-');
		g.drawLine(colStart,rowEnd,colEnd,rowEnd,'-');
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

	private void printInfo(TextGraphics textGraphics, int width, int height){
		String space = new String(new char[width/8]).replace('\0', ' ');
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, 
				space);
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 2, 
				space);
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 3, 
				space);
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 4, 
				space);
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 5, 
				space);
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 6, 
				space);
		textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 7, 
				space);
		
		drawSquare(textGraphics,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2 + width/8,
				height/4+1);
		
		if (currentCol < board.getTowerList().size() && currentRow < board.getTowerList().get(currentCol).getTiles().size()){
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, 
					board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getName());
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 2, 
					"Costi:");
			for (int i = 0; i < board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getRequirements().size(); i++){
				for (int j = 0; j < board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getRequirements().get(i).size(); j++){
					textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 3+i, 
							board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getRequirements().get(i).get(j).toString() + " " + 
									board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getRequirements().get(i).get(j).getValue());
				}
				
			}
		} else if(currentCol < board.getTowerList().size() && currentRow == board.getTowerList().get(currentCol).getTiles().size()) {
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, 
					"Mercato " + (currentCol + 1));
		} else if (currentRow == board.getTowerList().size() + 1 && currentCol < productionList.size()){
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, 
					"Production " + (currentCol + 1));
		} else if (currentRow == board.getTowerList().size() + 1 && currentCol >= productionList.size() && currentCol < productionList.size() + harvestList.size() ){
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, 
					"Harvest " + (currentCol + 1 - productionList.size()));
		} else {
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, 
					"Council");
		}
		
		
		//textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, 1, "");
		
		/*g.putString(60, 5, "Nome Carta (" + currentCol + "," + currentRow + ")");
		g.putString(60, 6, "Costo 1-1");
		g.putString(60, 7, "Costo 2-1");
		g.putString(60, 9, "Costo 1-2");
		g.putString(60, 10, "Costo 2-2");
		g.putString(60, 11, "Costo 3-2");
		g.putString(60, 13, "Effetti");
		g.putString(60, 14, "Effetto 1");
		g.putString(60, 15, "Effetto 2");
		g.drawLine(59,5,59,16,'|');
		g.drawLine(78,5,78,16,'|');
		g.drawLine(59,4,78,4,'-');
		g.drawLine(59,17,78,17,'-');*/
	}
}
