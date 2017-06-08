package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;

public class Player implements PlayerRelated {



	// server information
	int playerID;

	String username;
	ColorEnumeration color;
	
	// game information
	private ArrayList<Familiar> familyList;
	private BonusTile bonusTile;

	private FaithResource faithPts;
	private MilitaryResource militaryPts;
	private VictoryResource victoryPts;

	private GoldResource gold;
	private WoodResource wood;
	private StoneResource stone;
	private ServantResource servants;


	private ArrayList<GreenCard> greenCardList;
	private ArrayList<BlueCard> blueCardList;
	private ArrayList<YellowCard> yellowCardList;
	private ArrayList<VioletCard> purpleCardList;
	private ArrayList<LeaderCard> leaderCardList;

	private ArrayList<ActionResult> permanentEffectResList;
	private ArrayList<OnePerTurnEffect> onePerTurnEffectList;
	
	public Player(int playerID, String username, ColorEnumeration color){
		this.playerID = playerID;
		this.username = username;
		this.color = color;
		this.faithPts = new FaithResource(0);
		this.militaryPts = new MilitaryResource(0);
		this.victoryPts = new VictoryResource(0);
		this.gold = new GoldResource(0);
		this.wood = new WoodResource(0);
		this.stone = new StoneResource(0);
		this.servants = new ServantResource(0);
	}

	public Action doAction(Familiar familiar, ActionSpace position) throws OccupiedPositionException, RequirementsNotFullfilledException {

	    Action thisAction = new Action(familiar, position);
	    try {
	    	// run actions
		} catch (Exception e /* create more catch branch */ ){

		}
	    return thisAction;
    }

	public void addPermanentEffectRes(ActionResult eff){
		this.permanentEffectResList.add(eff);
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
    	this.gold.setValue(this.gold.getValue() + gold.getValue());
    }
    
    public void addFaith(FaithResource faith){
    	//non sai quale dei due valori devi aggiungere ma sai che solo uno dei due sarà diverso da zero
    	this.faithPts.setValue(this.faithPts.getValue() + faith.getValue());
    }
    
    public void addMilitary(MilitaryResource military){
    	this.militaryPts.setValue(this.militaryPts.getValue() + military.getValue());
    }
    
    public void addServant(ServantResource servants){
    	int val = this.servants.getValue();
    	this.servants.setValue(val + servants.getValue());
    }
    
    public void addStone(StoneResource stone){
    	this.stone.setValue(this.stone.getValue() + stone.getValue());
    }
    
    public void addWood(WoodResource wood){
    	this.wood.setValue(this.wood.getValue() + wood.getValue());
    }
    
    public void addVictory(VictoryResource victory){
    	this.victoryPts.setValue(this.victoryPts.getValue() + victory.getValue());
    }
    
    public void setFamiliars(ArrayList<Familiar> familyList){
    	this.familyList = familyList;
    }
    
    public ArrayList<Familiar> getFamilyList(){
    	return this.familyList;
    }

	public String getUsername() {
		return username;
	}

	public int getPlayerID() {
		return playerID;
	}

    public GoldResource getGold() {
        return gold;
    }

    public FaithResource getFaithPts() {
        return faithPts;
    }

    public MilitaryResource getMilitaryPts() {
        return militaryPts;
    }

    public VictoryResource getVictoryPts() {
        return victoryPts;
    }

    public WoodResource getWood() {
        return wood;
    }

    public StoneResource getStone() {
        return stone;
    }

    public ServantResource getServants() {
        return servants;
    }
    
    public ColorEnumeration getColor(){
    	return color;
    }
}
