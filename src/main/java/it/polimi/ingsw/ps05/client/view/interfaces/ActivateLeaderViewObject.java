package it.polimi.ingsw.ps05.client.view.interfaces;

import java.util.Observable;

public abstract class ActivateLeaderViewObject extends Observable {
		public abstract Integer getReferenceID();
		public abstract void notifyToObservers();
}
