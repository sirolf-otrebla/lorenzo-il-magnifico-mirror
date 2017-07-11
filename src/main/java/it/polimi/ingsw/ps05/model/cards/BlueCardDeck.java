package it.polimi.ingsw.ps05.model.cards;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;

import java.util.ArrayList;
import java.util.Random;

public class BlueCardDeck implements Deck {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3048095334437683967L;

	ArrayList<BlueCard> cardList;
	
	ArrayList<BlueCard> firstEpochCard = new ArrayList<BlueCard>();
	ArrayList<BlueCard> secondEpochCard = new ArrayList<BlueCard>();
	ArrayList<BlueCard> thirdEpochCard = new ArrayList<BlueCard>();
	
	/**
	 * The deck is a container of card, divided for color and epoch. Each deck contains the cards of the same
	 * color.
	 * @param cardList is the list of the card, not divided for epoch of the same color
	 */
	public BlueCardDeck(ArrayList<BlueCard> cardList){
		this.cardList = cardList;
		for (BlueCard o : this.cardList) {
			if (o.getEpoch() == EpochEnumeration.FIRST){
				firstEpochCard.add(o);
			} else if (o.getEpoch() == EpochEnumeration.SECOND){
				secondEpochCard.add(o);
			} else {
				thirdEpochCard.add(o);
			}
		}
		System.out.println("Le carte blu hanno: \nPRIMA EPOCA: " + firstEpochCard.size() + "\nSECONDA EPOCA: " + secondEpochCard.size() + "\nTERZAEPOCA: " + thirdEpochCard.size());
	}

	/**
	 * @param epoch contains the epoch of the requested card.
	 * @return a card of the selected epoch that will be displayed in the tower.
	 */
	@Override
	public TowerCard getCard(Epoch epoch) {
		if (epoch.getID().equals(EpochEnumeration.FIRST)){
			Random randomNum = new Random();
			System.out.println("il deck blu prerimozione " + firstEpochCard.size());
			Integer theNum =  randomNum.nextInt(firstEpochCard.size());
			BlueCard card = firstEpochCard.get(theNum);
			removeCardFromList(card, firstEpochCard);
			System.out.println("il deck blue postrimozione " + firstEpochCard.size());
			return card;
		} else if (epoch.getID().equals(EpochEnumeration.SECOND)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(secondEpochCard.size());
			BlueCard card = secondEpochCard.get(theNum);
			removeCardFromList(card, secondEpochCard);
			return card;
		} else {
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(thirdEpochCard.size());
			BlueCard card = thirdEpochCard.get(theNum);
			removeCardFromList(card, thirdEpochCard);
			return card;
		}
	}
	
	ColorEnumeration color = ColorEnumeration.Blue;
	
	public ColorEnumeration getDeckColor(){
		return color;
	}


	private void removeCardFromList(TowerCard card, ArrayList<?> list) {
		list.remove(card);
	}
	
}
