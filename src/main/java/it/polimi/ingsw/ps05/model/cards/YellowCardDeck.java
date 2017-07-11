package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;

import java.util.ArrayList;
import java.util.Random;

public class YellowCardDeck implements Deck {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3076127639892944906L;

	ArrayList<YellowCard> cardList;

	ArrayList<YellowCard> firstEpochCard = new ArrayList<YellowCard>();
	ArrayList<YellowCard> secondEpochCard = new ArrayList<YellowCard>();
	ArrayList<YellowCard> thirdEpochCard = new ArrayList<YellowCard>();

	/**
	 * The deck is a container of card, divided for color and epoch. Each deck contains the cards of the same
	 * color.
	 * @param cardList is the list of the card, not divided for epoch of the same color
	 */
	public YellowCardDeck(ArrayList<YellowCard> cardList){
		this.cardList = cardList;
		for (YellowCard o : this.cardList) {
			if (o.getEpoch() == EpochEnumeration.FIRST){
				firstEpochCard.add(o);
			} else if (o.getEpoch() == EpochEnumeration.SECOND){
				secondEpochCard.add(o);
			} else {
				thirdEpochCard.add(o);
			}
		}
		System.out.println("Le carte giallo hanno: \nPRIMA EPOCA: " + firstEpochCard.size() + "\nSECONDA EPOCA: " + secondEpochCard.size() + "\nTERZAEPOCA: " + thirdEpochCard.size());
	}

	/**
	 * @param epoch contains the epoch of the requested card.
	 * @return a card of the selected epoch that will be displayed in the tower.
	 */
	@Override
	public TowerCard getCard(Epoch epoch) {
		if (epoch.getID().equals(EpochEnumeration.FIRST)){
			Random randomNum = new Random();
			System.out.println("deck giallo pre rimozione " + firstEpochCard.size());
			Integer theNum =  randomNum.nextInt(firstEpochCard.size());
			YellowCard card = firstEpochCard.get(theNum);
			removeCardFromList(card, firstEpochCard);
			System.out.println("deck giallo post rimozione " + firstEpochCard.size());
			return card;
		} else if (epoch.getID().equals(EpochEnumeration.SECOND)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(secondEpochCard.size());
			YellowCard card = secondEpochCard.get(theNum);
			removeCardFromList(card, secondEpochCard);
			return card;
		} else {
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(thirdEpochCard.size());
			YellowCard card = thirdEpochCard.get(theNum);
			removeCardFromList(card, thirdEpochCard);
			return card;
		}
	}

	ColorEnumeration color = ColorEnumeration.Yellow;

	public ColorEnumeration getDeckColor(){
		return color;
	}


	private void removeCardFromList(TowerCard card, ArrayList<?> list) {
		list.remove(card);
	}
}
