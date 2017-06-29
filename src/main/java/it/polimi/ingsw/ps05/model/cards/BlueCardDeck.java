package it.polimi.ingsw.ps05.model.cards;

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
	}


	@Override
	public TowerCard getCard(Epoch epoch) {
		if (epoch.getID().equals(EpochEnumeration.FIRST)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(firstEpochCard.size());
			BlueCard card = firstEpochCard.get(theNum);
			removeCardFromList(card, firstEpochCard);
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


	private void removeCardFromList(TowerCard card, ArrayList<?> list) {
		list.remove(card);
	}
	
}