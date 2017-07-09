package it.polimi.ingsw.ps05.client.view.interfaces;

import java.util.Observable;

public abstract class PassActionViewObject extends Observable {
	
	public void notifyToObservers(){
		setChanged();
		notifyObservers(this);
	}
	
}
