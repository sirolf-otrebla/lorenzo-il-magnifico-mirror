package it.polimi.ingsw.ps05.model.exceptions;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

import java.util.ArrayList;

/**
 * Created by Alberto on 16/05/2017.
 */
public class MoreRequirementOptionsException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Resource>> options;
    public boolean cancelAction;
    public int choice;

    public MoreRequirementOptionsException(ArrayList<ArrayList<Resource>> options){
        this.options = options;
    }

    public void setChoice(int choice){
        this.choice = choice;
    }

    public ArrayList<ArrayList<Resource>> getOptions(){
        return options;
    }
}
