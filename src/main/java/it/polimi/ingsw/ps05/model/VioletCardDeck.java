package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import java.util.Random;

public class VioletCardDeck implements Deck {
	
ArrayList<VioletCard> cardList;
	
	ArrayList<VioletCard> firstEpochCard;
	ArrayList<VioletCard> secondEpochCard;
	ArrayList<VioletCard> thirdEpochCard;
	
	
	public VioletCardDeck(ArrayList<VioletCard> cardList){
		this.cardList = cardList;
		for (VioletCard o : this.cardList) {
			if (o.getEpoch().equals(EpochEnumeration.FIRST)){
				firstEpochCard.add(o);
			} else if (o.getEpoch().equals(EpochEnumeration.SECOND)){
				secondEpochCard.add(o);
			} else {
				thirdEpochCard.add(o);
			}
		}
	}


	@Override
	public TowerCard getCard(Epoch epoch) {
		if (epoch.getEpoch().equals(EpochEnumeration.FIRST)){
			Random randomNum = new Random();
			Integer theNum =  randomNum.nextInt(firstEpochCard.size());
			VioletCard card = firstEpochCard.get(theNum);
			removeCardFromList(card, firstEpochCard);
			return card;
		} else if (epoch.getEpoch().equals(EpochEnumeration.SECOND)){
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


	private void removeCardFromList(TowerCard card, ArrayList<?> list) {
		list.remove(card);
	}
}
