package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;

import java.util.ArrayList;
import java.util.Random;

public class VioletCardDeck implements Deck {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2226102373824889535L;

	ArrayList<VioletCard> cardList;

	ArrayList<VioletCard> firstEpochCard = new ArrayList<VioletCard>();
	ArrayList<VioletCard> secondEpochCard = new ArrayList<VioletCard>();
	ArrayList<VioletCard> thirdEpochCard = new ArrayList<VioletCard>();

	/**
	 * The deck is a container of card, divided for color and epoch. Each deck contains the cards of the same
	 * color.
	 * @param cardList is the list of the card, not divided for epoch of the same color
	 */
	public VioletCardDeck(ArrayList<VioletCard> cardList){
		this.cardList = cardList;
		for (VioletCard o : this.cardList) {
			if (o.getEpoch() == EpochEnumeration.FIRST){
				firstEpochCard.add(o);
			} else if (o.getEpoch() == EpochEnumeration.SECOND){
				secondEpochCard.add(o);
			} else {
				thirdEpochCard.add(o);
			}
		}
		System.out.println("Le carte viola hanno: \nPRIMA EPOCA: " + firstEpochCard.size() + "\nSECONDA EPOCA: " + secondEpochCard.size() + "\nTERZAEPOCA: " + thirdEpochCard.size());
	}

	/**
	 * @param epoch contains the epoch of the requested card.
	 * @return a card of the selected epoch that will be displayed in the tower.
	 */
	@Override
	public TowerCard getCard(Epoch epoch) {
		if (epoch.getID().equals(EpochEnumeration.FIRST)){
			Random randomNum = new Random();
			System.out.println("il deck viola prerimozione " + firstEpochCard.size());
			Integer theNum =  randomNum.nextInt(firstEpochCard.size());
			VioletCard card = firstEpochCard.get(theNum);
			removeCardFromList(card, firstEpochCard);
			System.out.println("il deck viola post rimozione " + firstEpochCard.size());
			return card;
		} else if (epoch.getID().equals(EpochEnumeration.SECOND)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(secondEpochCard.size());
			VioletCard card = secondEpochCard.get(theNum);
			removeCardFromList(card, secondEpochCard);
			return card;
		} else {
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(thirdEpochCard.size());
			VioletCard card = thirdEpochCard.get(theNum);
			removeCardFromList(card, thirdEpochCard);
			return card;
		}
	}

	ColorEnumeration color = ColorEnumeration.Violet;

	public ColorEnumeration getDeckColor(){
		return color;
	}


	private void removeCardFromList(TowerCard card, ArrayList<?> list) {
		list.remove(card);
	}
}
