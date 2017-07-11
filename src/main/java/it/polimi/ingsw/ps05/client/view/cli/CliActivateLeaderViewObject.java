package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.ActivateLeaderViewObject;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;

public class CliActivateLeaderViewObject extends ActivateLeaderViewObject{
	
	private LeaderCard card;
	
	public CliActivateLeaderViewObject(LeaderCard card) {
		this.card = card;
		Client.getInstance().linkToObserver(this);
	}
	
	public LeaderCard getLeaderCard(){
		return this.card;
	}

	@Override
	public void notifyToObservers() {
		setChanged();
		notifyObservers(this);
	}

	public Integer getReferenceID() {
		return card.getReferenceID();
	}

}
