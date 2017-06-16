package it.polimi.ingsw.ps05.view.cli;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.CouncilSpace;
import it.polimi.ingsw.ps05.model.Effect;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.HarvestingSpace;
import it.polimi.ingsw.ps05.model.MarketSpace;
import it.polimi.ingsw.ps05.model.ProductionSpace;
import it.polimi.ingsw.ps05.model.TileWithEffect;
import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.resourcesandbonuses.BonusWithMultiplier;
import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.model.ActionSpace;
import it.polimi.ingsw.ps05.model.ActivableEffect;

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
	String[] color = {"Viola", "Gialla", "Blu", "Verde"}; 
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
	private CouncilSpace council;
	private TextGraphics textGraphics;

	public CLIMain(Board board){
		System.out.println("Fatto");
		this.board = board;
	}

	/*
	 * DA FARE:
	 * aggiungere valori giocatore
	 * aggiungere effetto tile in torre
	 * aggiungere controllo occupato in tutti i posti
	 * aggiungere punteggi
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

			textGraphics = terminal.newTextGraphics();

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
				if (board.getTowerList().get(i).getTiles().get(j) instanceof TileWithEffect){
					for (ActionResult result : ((TileWithEffect)board.getTowerList().get(i).getTiles().get(j)).getEffectOnPositioning()){
						try {
							textGraphics.putString(i*width/8 + 1 + (i!=0 ? 1:0), 5 + j*height/16, result.toString() + " " + result.getValue());
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}


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



		for (ActionSpace action : board.getActionSpace()){
			if (action instanceof MarketSpace){
				ArrayList<TerminalPosition> list = map.get(marketList.size());
				list.add(new TerminalPosition((marketList.size()+1)*width/16 - width/32,
						6*height/16 - height/32));
				if (action.isOccupied()){
					textGraphics.putString((marketList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(textGraphics,
						marketList.size()*width/16,
						5*height/16,
						(marketList.size()+1)*width/16,
						6*height/16
						);
				marketList.add((MarketSpace) action);
			} else if (action instanceof ProductionSpace){
				ArrayList<TerminalPosition> list = map.get(productionList.size());
				list.add(new TerminalPosition((productionList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (action.isOccupied()){
					textGraphics.putString((productionList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(textGraphics,
						productionList.size()*width/16,
						6*height/16+2,
						(productionList.size()+1)*width/16,
						7*height/16+2
						);
				productionList.add((ProductionSpace)action);
			}
		}
		for (ActionSpace action : board.getActionSpace()){
			if (action instanceof HarvestingSpace){
				ArrayList<TerminalPosition> list = map.get(productionList.size()+harvestList.size());
				list.add(new TerminalPosition((productionList.size()+harvestList.size()+1)*width/16 - width/32,
						7*height/16 - height/32));
				if (action.isOccupied()){
					textGraphics.putString((harvestList.size()+1)*width/16 - width/32,
							6*height/16 - height/32, "X", SGR.BOLD);
				}
				drawSquare(textGraphics,
						(productionList.size()+harvestList.size())*width/16,
						6*height/16+2,
						(productionList.size()+harvestList.size()+1)*width/16,
						7*height/16+2
						);
				harvestList.add((HarvestingSpace)action);
			} else if (action instanceof CouncilSpace){
				council = (CouncilSpace)action;
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
		String space = new String(new char[width/8 + 3]).replace('\0', ' ');
		for (int i = 1; i < height/4; i++){
			textGraphics.putString(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3, i, 
					space);
		}

		drawSquare(textGraphics,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2,
				0,
				3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 2 + width/8,
				height/4+1);

		if (currentCol < board.getTowerList().size() && currentRow < board.getTowerList().get(currentCol).getTiles().size()){
			infoCard(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3,0);
		} else if(currentCol < board.getTowerList().size() && currentRow == board.getTowerList().get(currentCol).getTiles().size()) {
			infoMarket(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3,0);
		} else if (currentRow == board.getTowerList().size() + 1 && currentCol < productionList.size()){
			infoProduction(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3,0);
		} else if (currentRow == board.getTowerList().size() + 1 && currentCol >= productionList.size() && currentCol < productionList.size() + harvestList.size() ){
			infoHarvest(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3,0);
		} else {
			infoCouncil(3*width/8+3+getMaxOffset(offSet.get(offSet.size()-1)) + 3,0);
		}
	}

	private void infoMarket(int column, int row){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Mercato " + (currentCol + 1));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (marketList.get(currentCol).isOccupied()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					marketList.get(currentCol).getOccupant().getRelatedPlayer().getUsername());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + marketList.get(currentCol).getOccupant().getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		} else {
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Dado: " + marketList.get(currentCol).getDiceRequirement().getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);

			for (Effect effect : marketList.get(currentCol).getEffects()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				for (ArrayList<ActionResult> choseOr : effect.getResultList()){
					for (ActionResult result : choseOr) {
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
	}

	private void infoProduction(int column, int row){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Production " + (currentCol + 1));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Dado: " + productionList.get(currentCol).getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (productionList.get(currentCol).isOccupied()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow(), 
					productionList.get(currentCol).getOccupant().getRelatedPlayer().getUsername());
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + productionList.get(currentCol).getOccupant().getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
		if (!productionList.get(currentCol).isOccupied() && productionList.get(currentCol).getEffects().size() != 0){
			System.out.println("In");
			for (Effect effect : productionList.get(currentCol).getEffects()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				for (ArrayList<ActionResult> choseOr : effect.getResultList()){
					for (ActionResult result : choseOr){
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
	}

	private void infoCard(int column, int row){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getName());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Costi:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		try {
			for (ArrayList<Resource> choseOr : board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getRequirements()){
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
			for (Effect effect : board.getTowerList().get(currentCol).getTiles().get(currentRow).getCard().getEffects()){
				textGraphics.putCSIStyledString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				if (!(effect instanceof ActivableEffect)) {
					for (ArrayList<ActionResult> choseOr : effect.getResultList()){
						for (ActionResult result : choseOr) {
							textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.toString() + " " + result.getValue());
							lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
						}
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					}
				} else {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, ((ActivableEffect)effect).getActivableEffectType().toString());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Dado " + ((ActivableEffect)effect).getDiceRequired());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);

					if (((ActivableEffect)effect).getResourceRequired().size() != 0){
						activableWithResourceRequired(lastPos, (ActivableEffect)effect);
					} else {
						for (ArrayList<ActionResult> choseOrRes : ((ActivableEffect)effect).getResultList()){
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
					}
				}
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

	private TerminalPosition activableWithResourceRequired(TerminalPosition lastPos, ActivableEffect effect) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		for (ArrayList<Resource> choseOr : effect.getResourceRequired()){
			for (Resource resource : choseOr) {
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, resource.getValue() + " " + resource.toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			}
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "-->");
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			for (ArrayList<ActionResult> choseOrRes : ((ActivableEffect)effect).getResultList()){
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
				"Harvest " + (currentCol + 1 - productionList.size()));
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Dado: " + harvestList.get(currentCol-productionList.size()).getDiceRequirement().getValue());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		if (harvestList.get(currentCol-productionList.size()).isOccupied()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow(), 
					harvestList.get(currentCol-productionList.size()).getOccupant().getRelatedPlayer().getUsername());
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
					"Fam " + harvestList.get(currentCol-productionList.size()).getOccupant().getColor().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		} 
		if (!harvestList.get(currentCol-productionList.size()).isOccupied() && harvestList.get(currentCol-productionList.size()).getEffects().size() != 0) {
			for (Effect effect : harvestList.get(currentCol-productionList.size()).getEffects()){
				textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
						effect.getEffectType().toString());
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				for (ArrayList<ActionResult> choseOr : effect.getResultList()){
					for (ActionResult result : choseOr){
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
			for (ArrayList<ActionResult> choseOr : effect.getResultList()){
				for (ActionResult result : choseOr){
					try {
						textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
								result.toString() + " " + result.getValue());
						lastPos = new TerminalPosition(lastPos.getColumn(), lastPos.getRow() + 1);
					} catch (NoSuchMethodException e){
						//qui non fare niente, viene lancio per i moltiplicatori
					}
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
