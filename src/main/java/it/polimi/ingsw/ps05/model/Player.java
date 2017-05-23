package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;

public class Player implements PlayerRelated {
	
	// server information
	int playerID;
	String username;
	
	// game information
	private Color color;
	private ArrayList<Familiar> familyList;
	private BonusTile bonusTile;

	private FaithResource faithPts;
	private MilitaryResource militaryPts;
	private VictoryResource victoryPts;

	GoldResource gold;
	WoodResource wood;
	StoneResource stone;
	ServantResource servants;


	private ArrayList<GreenCard> greenCardList;
	private ArrayList<BlueCard> blueCardList;
	private ArrayList<YellowCard> yellowCardList;
	private ArrayList<VioletCard> purpleCardList;
	private ArrayList<LeaderCard> leaderCardList;

	private ArrayList<Effect> permanentEffectList;

	public Action doAction(Familiar familiar, ActionSpace position) throws OccupiedPositionException, RequirementsNotFullfilledException {

	    Action thisAction = new Action(this, familiar, position);
	    try {
	    	// run actions
		} catch (Exception e /* create more catch branch */ ){

		}
	    return thisAction;
    }

	

	public ArrayList<GreenCard> getGreenCardList() {
		return greenCardList;
	}

	public ArrayList<YellowCard> getYellowCardList() {
		return yellowCardList;
	}

	public ArrayList<BlueCard> getBlueCardList() {
		return blueCardList;
	}

	public ArrayList<VioletCard> getPurpleCardList() {
		return purpleCardList;
	}

    @Override
    public Player getRelatedPlayer() {
        return this;
    }

    public ArrayList<LeaderCard> getLeaderCardList() {
        return leaderCardList;
    }
    
    public void addGold(GoldResource gold){
    	//non sai quale dei due valori devi aggiungere ma sai che solo uno dei due sarà diverso da zero
    	this.gold.setAmount(this.gold.getAmount() + gold.getAmount() + gold.getValue());
    }
    
    public void addFaith(FaithResource faith){
    	//non sai quale dei due valori devi aggiungere ma sai che solo uno dei due sarà diverso da zero
    	this.faithPts.setAmount(this.faithPts.getAmount() + faith.getAmount() + faith.getValue());
    }
    
    public void addMilitary(MilitaryResource military){
    	this.militaryPts.setAmount(this.militaryPts.getAmount() + military.getAmount() + military.getValue());
    }
    
    public void addServant(ServantResource servants){
    	this.servants.setAmount(this.servants.getAmount() + servants.getAmount() + servants.getValue());
    }
    
    public void addStone(StoneResource stone){
    	this.stone.setAmount(this.stone.getAmount() + stone.getAmount() + stone.getValue());
    }
    
    public void addWood(WoodResource wood){
    	this.wood.setAmount(this.wood.getAmount() + wood.getAmount() + wood.getValue());
    }
    
    public void addVictory(VictoryResource victory){
    	this.victoryPts.setAmount(this.victoryPts.getAmount() + victory.getAmount() + victory.getValue());
    }
}
