package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.cards.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.OnePerTurnEffect;
import it.polimi.ingsw.ps05.model.exceptions.MissingCardException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;

public class Player implements PlayerRelated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3214968707144065789L;
	// server information
	int playerID;
	public static final int FAMILY_DIM = 4;
	String username;
	ColorEnumeration color;
	
	// game information
	private ArrayList<Familiar> familyList = null;
	private BonusTile bonusTile;

	private ArrayList<Resource> resourceList = new ArrayList<Resource>();


	private ArrayList<GreenCard> greenCardList = new ArrayList<GreenCard>();
	private ArrayList<BlueCard> blueCardList = new ArrayList<BlueCard>();
	private ArrayList<YellowCard> yellowCardList = new ArrayList<YellowCard>();
	private ArrayList<VioletCard> violetCardList = new ArrayList<VioletCard>();
	private ArrayList<LeaderCard> leaderCardList = new ArrayList<LeaderCard>();

	private ArrayList<PermanentBonus> permanentEffectResList;
	private ArrayList<OnePerTurnEffect> onePerTurnEffectList;
	private ArrayList<Effect> temporaryEffectList;


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

	public void addPermanentEffectRes(PermanentBonus eff){
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

	public ArrayList<VioletCard> getVioletCardList() {
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
    		if (r.getID().equals(resource.getID())){
    			r.setValue(r.getValue() + resource.getValue());
    		}
    	}
    }

    public void setFamiliars(ArrayList<Familiar> familyList) throws RepeatedAssignmentException {
		if (this.familyList == null) {
			this.familyList = familyList;
		} else {
			throw new RepeatedAssignmentException();
		}
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
    		if (r.getID().equals(id)){
    			return r;
    		}
    	}
    	return null;
    }
    
    public ColorEnumeration getColor(){
    	return color;
    }

    public ArrayList<PermanentBonus> getPermanentBonusList(){
    	return permanentEffectResList;
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

	public BonusTile getBonusTile() {
		return bonusTile;
	}


	public void setBonusTile(BonusTile bonusTile) {
		this.bonusTile = bonusTile;
	}
	
    public LeaderCard getLeaderCard(String leaderName) throws MissingCardException{

		for (LeaderCard leaderCard:
			 this.leaderCardList) {
			if (leaderCard.getName().equals(leaderName)){
				leaderCardList.remove(leaderCard);
				return leaderCard;
			}
		}
		throw new MissingCardException();
	}

	public void resetPermanentEffects(){
		for (PermanentBonus r:
			 getPermanentBonusList()) {

			r.resetResult(this);
		}
	}

	public void evaluatePermanentEffects(){
		for (PermanentBonus e :
				this.getPermanentBonusList()) {
			e.applyResult(this);
		}
	}
	
	public Familiar createGhostFamiliar(Integer diceRequired) {
		return new Familiar(new Dice(ColorEnumeration.Ghost,diceRequired),ColorEnumeration.Ghost,this);
	}

	public ArrayList<PermanentBonus> getPermanentEffectResList() {
		return permanentEffectResList;
	}

	public ArrayList<OnePerTurnEffect> getOnePerTurnEffectList() {
		return onePerTurnEffectList;
	}

	public ArrayList<Effect> getTemporaryEffectList() {
		return temporaryEffectList;
	}

}
