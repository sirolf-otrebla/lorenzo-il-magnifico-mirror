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
import it.polimi.ingsw.ps05.model.cards.Card;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.effects.ActivableEffect;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.BonusWithMultiplier;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class CliTerminalForCardsList {

	ArrayList<?> cards;
	int width;
	boolean enterDetected = false;
	Terminal cardTerminal;
	ArrayList<Card> selectedCards = new ArrayList<>();
	int indiceCarta = 0;
	int stdCursorPositionY = 15;
	int currentWidth;
	int numMaxCardSelectable = 0;
	SelectionTypeEnum type;
	ArrayList<Integer> requirementsAvailable = new ArrayList<>();
	ArrayList<Integer> requirementsSelected = new ArrayList<>();

	public CliTerminalForCardsList(ArrayList<?> cards, int width, SelectionTypeEnum type){
		this.cards = cards;
		this.width = width;
		this.type = type;
		for (Object c : cards){
			for (Effect e : ((Card)c).getEffects()){
				if (e instanceof ActivableEffect){
					requirementsAvailable.add(((ActivableEffect)e).getResourceRequired().size());
					requirementsSelected.add(0);
				}
			}
		}
		if (type == SelectionTypeEnum.DRAFT){
			numMaxCardSelectable = 1;
		} else if(type != SelectionTypeEnum.VISUAL) {
			numMaxCardSelectable = cards.size();
		}
	}

	public ArrayList<Card> start(){
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
				infoCard((i*currentWidth)/cards.size() + 1, 0, (Card)cards.get(i), cardTextGraphics);
			}
			System.out.println(requirementsAvailable.toString());
			System.out.println(requirementsSelected.toString());
			cardTerminal.flush();
			if (type != SelectionTypeEnum.VISUAL){
				cardTerminal.setCursorVisible(true);
				cardTerminal.setCursorPosition(((indiceCarta+1)*currentWidth/(cards.size()) - currentWidth/(2*cards.size())), stdCursorPositionY);
				movePointer(cardTextGraphics);
				cardTerminal.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return selectedCards;
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
				infoCard((indiceCarta*currentWidth)/cards.size() + 1, 0, (Card)cards.get(indiceCarta), textGraphics);
				System.out.println(requirementsAvailable.toString());
				System.out.println(requirementsSelected.toString());
				break;
			case ArrowDown:
				choseDown();
				infoCard((indiceCarta*currentWidth)/cards.size() + 1, 0, (Card)cards.get(indiceCarta), textGraphics);
				System.out.println(requirementsAvailable.toString());
				System.out.println(requirementsSelected.toString());
				break;
			case Enter:
				enterDetected = true;
				break;
			case Character:
				if (keyStroke.getCharacter() == ' '){
					if (selectedCards.contains(cards.get(indiceCarta))){
						selectedCards.remove(cards.get(indiceCarta));
						textGraphics.putString(cardTerminal.getCursorPosition(), " ");
					} else if (numMaxCardSelectable > selectedCards.size()){
						selectedCards.add((TowerCard)cards.get(indiceCarta));
						textGraphics.putString(cardTerminal.getCursorPosition(), "X", SGR.BOLD);
					}
				}
				
				break;

			default:
				System.out.println("Default");
				System.out.println(cardTerminal.getTerminalSize().toString());
				break;
			}
			cardTerminal.setCursorPosition(((indiceCarta+1)*currentWidth/(cards.size()) - currentWidth/(2*cards.size())), stdCursorPositionY);
			cardTerminal.flush();
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

	private void infoCard(int column, int row, Card card, TextGraphics textGraphics){
		TerminalPosition lastPos = new TerminalPosition(column,row);
		textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, 
				card.getName());
		lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
		try {
			textGraphics.putString(lastPos.getColumn(), lastPos.getRow() + 1, "Effetti:");
			for (Effect effect : card.getEffects()){
				if (effect instanceof ActivableEffect) {
					textGraphics.putCSIStyledString(lastPos.getColumn(), lastPos.getRow() + 1, effect.getEffectType().toString());
					lastPos = new TerminalPosition(lastPos.getColumn(),lastPos.getRow()+1);
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