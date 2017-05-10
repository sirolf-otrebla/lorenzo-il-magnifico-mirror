package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;


public interface Card {

	public ArrayList<ArrayList<Resource>> getRequirements(); 
	
	public ArrayList<ArrayList<Resource>> getEffects(); 
	//TODO: evaluate if it's better to use a resource object or a value pair object as in preliminary UML -- Sirolfo 
}
