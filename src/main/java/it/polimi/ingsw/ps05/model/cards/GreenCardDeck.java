package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;

import java.util.ArrayList;
import java.util.Random;

public class GreenCardDeck implements Deck {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1646027742455082138L;

	ArrayList<GreenCard> cardList;
	
	ArrayList<GreenCard> firstEpochCard = new ArrayList<GreenCard>();
	ArrayList<GreenCard> secondEpochCard = new ArrayList<GreenCard>();
	ArrayList<GreenCard> thirdEpochCard = new ArrayList<GreenCard>();
	
	/**
	 * The deck is a container of card, divided for color and epoch. Each deck contains the cards of the same
	 * color.
	 * @param cardList is the list of the card, not divided for epoch of the same color
	 */
	public GreenCardDeck(ArrayList<GreenCard> cardList){
		this.cardList = cardList;
		for (GreenCard o : this.cardList) {
			if (o.getEpoch() == EpochEnumeration.FIRST){
				firstEpochCard.add(o);
			} else if (o.getEpoch() == EpochEnumeration.SECOND){
				secondEpochCard.add(o);
			} else {
				thirdEpochCard.add(o);
			}
		}
	}

	/**
	 * @param epoch contains the epoch of the requested card.
	 * @return a card of the selected epoch that will be displayed in the tower.
	 */
	@Override
	public TowerCard getCard(Epoch epoch) {
		if (epoch.getID().equals(EpochEnumeration.FIRST)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(firstEpochCard.size());
			GreenCard card = firstEpochCard.get(theNum);
			removeCardFromList(card, firstEpochCard);
			return card;
		} else if (epoch.getID().equals(EpochEnumeration.SECOND)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(secondEpochCard.size());
			GreenCard card = secondEpochCard.get(theNum);
			removeCardFromList(card, secondEpochCard);
			return card;
		} else {
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(thirdEpochCard.size());
			GreenCard card = thirdEpochCard.get(theNum);
			removeCardFromList(card, thirdEpochCard);
			return card;
		}
	}


	private void removeCardFromList(TowerCard card, ArrayList<?> list) {
		list.remove(card);
	}
}
