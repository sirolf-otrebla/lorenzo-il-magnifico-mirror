package it.polimi.ingsw.ps05.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.cards.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.OnePerTurnEffect;
import it.polimi.ingsw.ps05.model.exceptions.MissingCardException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.exceptions.OccupiedPositionException;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.exceptions.RequirementsNotFullfilledException;

public class Player implements Serializable, PlayerRelated, VisitableFromView {

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
	private HashMap<ColorEnumeration, Familiar> familyMap = new HashMap<>();
	private BonusTile bonusTile;

	private ArrayList<Resource> resourceList = new ArrayList<Resource>();


	private HashMap<Integer, GreenCard> greenCardHashMap = new HashMap<>();
	private HashMap<Integer, BlueCard> blueCardHashMap = new HashMap<>();
	private HashMap<Integer, YellowCard> yellowCardHashMap = new HashMap<>();
	private HashMap<Integer, VioletCard> violetCardHashMap = new HashMap<>();
	private HashMap<Integer, LeaderCard> leaderCardHashMap = new HashMap<>();

	private ArrayList<PermanentBonus> permanentEffectResList = new ArrayList<>();
	private ArrayList<OnePerTurnEffect> onePerTurnEffectList = new ArrayList<>();
	private ArrayList<Effect> temporaryEffectList = new ArrayList<>();
	
	//TODO arraylist di scomuniche
	//TODO fare controllo se prima azione del turno nel controller (una scomunica lo richiede)


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
			e.printStackTrace();
		}
	    return thisAction;
    }

	public void addPermanentEffectRes(PermanentBonus eff){
		this.permanentEffectResList.add(eff);
	}

	public ArrayList<GreenCard> getGreenCardList() {
		return new ArrayList<GreenCard>( this.greenCardHashMap.values());
	}

	public ArrayList<YellowCard> getYellowCardList() {
		return  new ArrayList<>(this.yellowCardHashMap.values());
	}

	public ArrayList<BlueCard> getBlueCardList() {
		return  new ArrayList<>(this.blueCardHashMap.values());
	}

	public ArrayList<VioletCard> getVioletCardList() {
		return  new ArrayList<>(this.violetCardHashMap.values());
	}

    @Override
    public Player getRelatedPlayer() {
        return this;
    }

    public ArrayList<LeaderCard> getLeaderCardList() {
        return  new ArrayList<>(this.leaderCardHashMap.values());
    }
    
    public void addResource(Resource resource){
    	for (Resource r : resourceList){
    		if (r.getID().equals(resource.getID())){
    			r.setValue(r.getValue() + resource.getValue());
    		}
    	}
    }

    public void setFamiliars(ArrayList<Familiar> familyList) throws RepeatedAssignmentException {
		if (this.familyMap.size() == 0) {
			this.familyMap = new HashMap<>();
			for (Familiar f: familyList)
				this.familyMap.put(f.getColor(), f);
		} else {
			throw new RepeatedAssignmentException();
		}
    }
    
    public Collection<Familiar> getFamilyList(){
    	return this.familyMap.values();
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

    public Map<ColorEnumeration, Familiar> getFamilyMap(){
    	return familyMap;
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
    	blueCardHashMap.put(card.getReferenceID(), card);
    }

    public void addGreenCard(GreenCard card){
    	greenCardHashMap.put(card.getReferenceID(), card);
    }

    public void addYellowCard(YellowCard card){
    	yellowCardHashMap.put(card.getReferenceID(),card);
    }

    public void addVioletCard(VioletCard card){
    	violetCardHashMap.put(card.getReferenceID(), card);
    }

	public BonusTile getBonusTile() {
		return bonusTile;
	}


	public void setBonusTile(BonusTile bonusTile) {
		this.bonusTile = bonusTile;
	}
	
    public LeaderCard getLeaderCard(String leaderName) throws MissingCardException{

		for (LeaderCard leaderCard:
			 this.leaderCardHashMap.values()) {
			if (leaderCard.getName().equals(leaderName)){
				leaderCardHashMap.remove(leaderCard);
				return leaderCard;
			}
		}
		throw new MissingCardException();
	}

	public void putLeaderCard(LeaderCard leaderCard){
    	this.leaderCardHashMap.put(leaderCard.getReferenceID(), leaderCard);
	}



	public void resetPermanentEffects(Integer activeEffectBound){
		for (int i = 0; i < activeEffectBound ; i++) {
			getPermanentEffectResList().get(i).resetResult(this);
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
	
	public void removeBlueCard() {
		this.blueCardHashMap.clear();
	}
	
	public void removeGreenCard() {
		this.greenCardHashMap.clear(); ;
	}

	public HashMap<Integer, GreenCard> getGreenCardHashMap() {
		return greenCardHashMap;
	}

	public HashMap<Integer, BlueCard> getBlueCardHashMap() {
		return blueCardHashMap;
	}

	public HashMap<Integer, YellowCard> getYellowCardHashMap() {
		return yellowCardHashMap;
	}

	public HashMap<Integer, VioletCard> getVioletCardHashMap() {
		return violetCardHashMap;
	}

	public HashMap<Integer, LeaderCard> getLeaderCardHashMap() {
		return leaderCardHashMap;
	}

	public Familiar getFamilyMember(ColorEnumeration c){
		return this.getFamilyMap().get(c);
	}

	@Override
	public void acceptVisitor(ViewVisitorInterface vi) {

	}
}
