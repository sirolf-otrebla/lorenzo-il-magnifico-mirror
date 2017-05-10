package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;


public interface Card {

	public ArrayList<ArrayList<Resource>> getRequirements(); 
	
	public ArrayList<ArrayList<Resource>> getEffects(); 
	
	public boolean isActive(); // for excommunication 
	
	public void applyEffect(Object /* aka player, substitute */ player);
	
	public Epoch getEpoch();
	
	public String getName();
	
	
	
	//TODO: evaluate if it's better to use a resource object or a value pair object as in preliminary UML -- Sirolfo 
}
