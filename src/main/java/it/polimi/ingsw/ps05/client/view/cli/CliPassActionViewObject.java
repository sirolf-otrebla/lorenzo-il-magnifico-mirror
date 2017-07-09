package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.PassActionViewObject;

public class CliPassActionViewObject extends PassActionViewObject{
	public CliPassActionViewObject(){
		Client.getInstance().linkToObserver(this);
	}
	
	
}
