package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.DiscardLeaderViewObject;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;

public class CliDiscardLeaderViewObject extends DiscardLeaderViewObject{
	
	LeaderCard card;
	
	public CliDiscardLeaderViewObject(LeaderCard card) {
		this.card = card;
		Client.getInstance().linkToObserver(this);
	}

	@Override
	public Integer getReferenceID() {
		return card.getReferenceID();
	}

	@Override
	public void notifyToObservers() {
		setChanged();
		notifyObservers(this);
	}

}
