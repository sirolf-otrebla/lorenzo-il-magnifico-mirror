package it.polimi.ingsw.ps05.client.view.cli;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import it.polimi.ingsw.ps05.client.view.SelectionTypeEnum;
import it.polimi.ingsw.ps05.model.BonusTile;
import it.polimi.ingsw.ps05.model.cards.Card;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.effects.ActivableEffect;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.BonusWithMultiplier;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class CliTerminalForCardsList {

	ArrayList<?> cards;
	int width;
	boolean enterDetected = false;
	Terminal cardTerminal;
	ArrayList<Object> selected = new ArrayList<>();
	int indiceCarta = 0;
	int stdCursorPositionY = 15;
	int currentWidth;
	int numMaxCardSelectable = 0;
	int numMinCardSelectable = 0;
	ArrayList<Integer> requirementsAvailable = new ArrayList<>();
	ArrayList<Integer> requirementsSelected = new ArrayList<>();

	public CliTerminalForCardsList(ArrayList<?> cards, int width, int maxCardSelectable, int minCardSelectable){
		this.cards = cards;
		this.width = width;
		System.out.println(this.cards.get(0).getClass());
		if (this.cards.get(0) instanceof Card){
			System.out.println("Ho delle carte");
			for (Object c : cards){
				for (Effect e : ((Card)c).getEffects()){
					if (e instanceof ActivableEffect){
						requirementsAvailable.add(((ActivableEffect)e).getResourceRequired().size());
						requirementsSelected.add(0);
					}
				}
			}
		}
		this.numMinCardSelectable = minCardSelectable;
		this.numMaxCardSelectable = maxCardSelectable;
	}

	public ArrayList<?> start(){
		try {
			System.out.println("Sstarted");
			DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
			int height = 20;
			currentWidth = cards.size()*width/6;
			defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(currentWidth, height));
			cardTerminal = defaultTerminalFactory.createTerminal();

			cardTerminal.enterPrivateMode();
			cardTerminal.clearScreen();
			cardTerminal.setCursorVisible(false);

			TextGraphics cardTextGraphics = cardTerminal.newTextGraphics();
			cardTextGraphics.setForegroundColor(TextColor.ANSI.WHITE);
			cardTextGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
			for (int i = 0; i < cards.size(); i++) {
				CLIMain.drawSquare(
						(i*currentWidth)/cards.size(),
						0,
						((i+1)*currentWidth/cards.size())-1,
						height-1 ,
						cardTextGraphics);
				printInfo(i,cardTextGraphics);

			}
			System.out.println(requirementsAvailable.toString());
			System.out.println(requirementsSelected.toString());
			cardTerminal.flush();
			if (numMaxCardSelectable != 0){
				cardTerminal.setCursorVisible(true);
				cardTerminal.setCursorPosition(((indiceCarta+1)*currentWidth/(cards.size()) - currentWidth/(2*cards.size())), stdCursorPositionY);
				movePointer(cardTextGraphics);
				cardTerminal.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (requirementsAvailable.size() != 0){
			ArrayList<ArrayList<Integer>> toReturn = new ArrayList<>();
			ArrayList<Integer> temp = new ArrayList<>();
			for (Object o : selected){
				temp.add((Integer)o);
			}
			toReturn.add(temp);
			toReturn.add(requirementsSelected);
			return toReturn;
		}

		return selected;
	}

	private void movePointer(TextGraphics textGraphics) throws IOException{
		while(!enterDetected) {
			System.out.println("here");
			KeyStroke keyStroke = cardTerminal.readInput();
			switch(keyStroke.getKeyType()){
			case ArrowLeft:
				moveLeft();
				break;
			case ArrowRight:
				moveRight();
				break;
			case ArrowUp:
				choseUp();
				printInfo(indiceCarta,textGraphics);
				System.out.println(requirementsAvailable.toString());
				System.out.println(requirementsSelected.toString());
				break;
			case ArrowDown:
				choseDown();
				printInfo(indiceCarta,textGraphics);
				System.out.println(requirementsAvailable.toString());
				System.out.println(requirementsSelected.toString());
				break;
			case Enter:
				if (selected.size() >= numMinCardSelectable && selected.size()<= numMaxCardSelectable)
					enterDetected = true;
				break;
			case Character:
				if (keyStroke.getCharacter() == ' ' && numMaxCardSelectable > 0){
					if (selected.contains(cards.get(indiceCarta))){
						System.out.println("Rimuovo selezione");
						selected.remove(indiceCarta);
						textGraphics.putString(cardTerminal.getCursorPosition(), " ");
					} else if (selected.size() < numMaxCardSelectable){
						selected.add(indiceCarta);
						textGraphics.putString(cardTerminal.getCursorPosition(), "X", SGR.BOLD);
						System.out.println("Aggiungo selezione");
					}
				}
				break;
			case Escape:
				enterDetected = true;
				selected = new ArrayList<>();
				break;

			case EOF:
				enterDetected = true;
				selected = new ArrayList<>();
				break;

			default:
				System.out.println("Default " + keyStroke.toString());
				break;
			}
			cardTerminal.setCursorPosition(((indiceCarta+1)*currentWidth/(cards.size()) - currentWidth/(2*cards.size())), stdCursorPositionY);
			cardTerminal.flush();
		}
	}

	private void printInfo(int i,TextGraphics textGraphics){
		if (cards.get(0) instanceof Card){
			infoCard((i*currentWidth)/cards.size() + 1, 0, (Card)cards.get(i), textGraphics);
		} else if (cards.get(0) instanceof BonusTile){
			try {
				infoBonusTile((i*currentWidth)/cards.size() + 1, 0, textGraphics, (BonusTile)cards.get(i));
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (cards.get(0) instanceof String){

		} else {
			infoPrivilege((i*currentWidth)/cards.size() + 1, 0, cards.get(i), textGraphics);
		}
	}

	private void moveLeft() throws IOException{
		if (indiceCarta != 0){
			indiceCarta--;
			cardTerminal.setCursorPosition(((indiceCarta+1)*currentWidth/(cards.size()) - currentWidth/(2*cards.size())), stdCursorPositionY);
		}
	}

	private void moveRight() throws IOException {
		if (indiceCarta != cards.size() - 1){
			indiceCarta++;
			cardTerminal.setCursorPosition(((indiceCarta+1)*currentWidth/(cards.size()) - currentWidth/(2*cards.size())), stdCursorPositionY);
		}
	}

	private void choseUp() {
		if (requirementsSelected.get(indiceCarta) != 0 ){
			requirementsSelected.set(indiceCarta, requirementsSelected.get(indiceCarta) - 1);
		}
	}

	private void choseDown() {
		if (requirementsSelected.get(indiceCarta) < requirementsAvailable.get(indiceCarta) - 1){
			requirementsSelected.set(indiceCarta, requirementsSelected.get(indiceCarta) + 1);
		}
	}

	private void infoPrivilege(int column, int row, Object list, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		for (Resource res : (ArrayList<Resource>)list){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, res.toString() + " " + res.getValue());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
	}

	private void infoCard(int column, int row, Card card, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				card.getName());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				"Costi:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		System.out.println("card requirements: " + card.getRequirements().size());
		try {
			for (ArrayList<Resource> choseOr : card.getRequirements()){
				System.out.println("or req: " + choseOr.size());
				for (Resource res : choseOr){
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, res.toString() + " " + res.getValue());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
			}
		} catch (Exception e){
			System.out.println("no requirements!!");
			//requirementList == null, non fare niente cercare di risolvere
		}
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Effetti:");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		System.out.println("Effetti: " + card.getEffects().size());
		for (Effect effect : card.getEffects()){
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getEffectType().toString());
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			if (!(effect instanceof ActivableEffect)) {
				System.out.println("non effetto attivabile");
				System.out.println("effetti disponibili: " + ((SimpleEffect)effect).getResultList().size());
				for (ActionResult result : ((SimpleEffect)effect).getResultList()) {
					String toWrite;
					try{
						toWrite = result.toString() + " " + result.getValue();
						System.out.println("ho settato la stringa da stampare");
					} catch (Exception e){
						System.err.println("eccezione nel prendere la stringa, ne setto una senza valore");
						toWrite = result.toString();
					}

					int size = currentWidth/cards.size();
					do{
						if (toWrite.length() > size - 2 ){
							textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, toWrite.substring(0, size-2));
							toWrite = toWrite.substring(size-2,toWrite.length());
						} else {
							textGraphics.putString(lastPos.getColumn(), lastPos.getRow() +1, toWrite);
							toWrite = "";
						}
						lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
					} while(toWrite.length() != 0);


				}
				lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			} else {
				try {
					lastPos = activableEffect(lastPos,(ActivableEffect)effect,textGraphics);
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}
	}

	private void infoBonusTile(int column, int row, TextGraphics textGraphics, BonusTile tile) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Bonus Tile");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		for (Effect effect : tile.getEffectList()){
			lastPos = activableEffect(lastPos, (ActivableEffect)effect,textGraphics);
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		}

	}

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

	private TerminalPosition activableWithOutResourceRequired(TerminalPosition lastPos, ActivableEffect effect, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException, NoSuchMethodException{
		for (ArrayList<ActionResult> choseOrRes : effect.getResultList()){
			for (ActionResult result : choseOrRes){
				textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
				if ( result instanceof BonusWithMultiplier){
					lastPos = bonusWithMultiplier(lastPos,(BonusWithMultiplier)result,textGraphics);
				} else {
					textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getValue() + " " + result.toString());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
				}
			}
			lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
		}
		return lastPos;
	}

	private TerminalPosition activableWithResourceRequired(TerminalPosition lastPos, ActivableEffect effect, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException, NoSuchMethodException{

		for (int i = 0; i < effect.getResourceRequired().size(); i++){
			if (requirementsSelected.get(indiceCarta) == i){
				textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
			}
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
			textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
		}
		return lastPos;
	}

	private TerminalPosition bonusWithMultiplier(TerminalPosition lastPos, BonusWithMultiplier result, TextGraphics textGraphics) throws InstantiationException, IllegalAccessException{
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() , result.getMultiplier()+"x"+
				result.getCardToCount().newInstance().toString());
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "-->");
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, result.getReturnResource().toString());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		return lastPos;
	}

}
