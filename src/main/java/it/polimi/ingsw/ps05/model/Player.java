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
	
	private ArrayList<Resource> resourceList = new ArrayList<Resource>();


	private ArrayList<GreenCard> greenCardList = new ArrayList<GreenCard>();
	private ArrayList<BlueCard> blueCardList = new ArrayList<BlueCard>();
	private ArrayList<YellowCard> yellowCardList = new ArrayList<YellowCard>();
	private ArrayList<VioletCard> violetCardList = new ArrayList<VioletCard>(); 
	private ArrayList<LeaderCard> leaderCardList = new ArrayList<LeaderCard>();

	private ArrayList<ActionResult> permanentEffectResList;
	private ArrayList<OnePerTurnEffect> onePerTurnEffectList;
	
	public Player(int playerID, String username, ColorEnumeration color){
		this.playerID = playerID;
		this.username = username;
		this.color = color;
		
		resourceList.add(new FaithResource(0));
		resourceList.add(new MilitaryResource(0));
		resourceList.add(new VictoryResource(0));
		resourceList.add(new GoldResource(0));
		resourceList.add(new WoodResource(0));
		resourceList.add(new StoneResource(0));
		resourceList.add(new ServantResource(0));
	}

	public Action doAction(Familiar familiar, ActionSpace position, int selectedOption) throws OccupiedPositionException, RequirementsNotFullfilledException {

	    Action thisAction = new Action(familiar, position);
	    try {
	    	if (thisAction.isLegal()){
	    		thisAction.run(selectedOption);
	    	}
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
		return violetCardList;
	}

    @Override
    public Player getRelatedPlayer() {
        return this;
    }

    public ArrayList<LeaderCard> getLeaderCardList() {
        return leaderCardList;
    }
    
    public void addResource(Resource resource){
    	for (Resource r : resourceList){
    		if (r.getId().equals(resource.getId())){
    			r.setValue(r.getValue() + resource.getValue());
    		}
    	}
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
	
    public Resource getResource(String id){
    	for (Resource r : resourceList){
    		if (r.getId().equals(id)){
    			return r;
    		}
    	}
    	return null;
    }
    
    public ColorEnumeration getColor(){
    	return color;
    }

    public ArrayList<PermanentEffect> getPermanentEffectList(){
    	return this.getPermanentEffectList();
	}

	public ArrayList<Resource> getResourceList(){
    	return  this.resourceList;
	}
    
    public void addBlueCard(BlueCard card){
    	blueCardList.add(card);
    }
    
    public void addGreenCard(GreenCard card){
    	greenCardList.add(card);
    }
    
    public void addYellowCard(YellowCard card){
    	yellowCardList.add(card);
    }
    
    public void addVioletCard(VioletCard card){
    	violetCardList.add(card);
    }
}
