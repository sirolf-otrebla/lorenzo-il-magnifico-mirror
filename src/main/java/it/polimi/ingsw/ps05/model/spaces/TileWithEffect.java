package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

/*
 * this is a decorator for Tile class, designed to add tile-related effects \
 * 
 * further commments will be added.
 */

public class TileWithEffect extends TowerTileInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 675964833623423175L;

	private Tile toBeDecorated;

	private ArrayList<ActionResult> effectOnPositioning = new ArrayList<ActionResult>();
	private Boolean hasMorePaymentOptions = false;

    public TileWithEffect(){
        this.toBeDecorated = new Tile();
    }
    
    public TileWithEffect(TowerCard card, Integer diceRequired, Tower parentTower, ArrayList<ActionResult> effectOnPositioning){
    	this.toBeDecorated = new Tile(card, diceRequired, parentTower);
    }

    
    @Override
    public void setTowerCard(TowerCard card) {
    	if (card.requirements.size() > 1) this.hasMorePaymentOptions = true;
    	this.toBeDecorated.setTowerCard(card);
    }
    @Override
    public void setDiceRequired(Integer diceRequired){

        this.toBeDecorated.setDiceRequired(diceRequired);
    }
    
    @Override
    public boolean isOccupied(){
    	return super.isOccupied();
    }
    
    public void setTileEffect(ArrayList<ActionResult> effectOnPositioning){
    	this.effectOnPositioning = effectOnPositioning;
    }
	
	public TileWithEffect(Tile tile){
		this.setToBeDecorated(tile);
	}

	@Override
	public ArrayList<Effect> getEffects() throws IllegalMethodCallException { 
		throw new IllegalMethodCallException();
	}

	@Override
	public void setParentTower(Tower tower) {
		this.toBeDecorated.setParentTower(tower);
		
	}
	@Override
	public Tower getParentTower() {
		return this.toBeDecorated.getParentTower();
	}

	@Override
	public TowerCard getCard() {
		return this.toBeDecorated.getCard();
	}
	@Override
	public Dice getDiceRequired() {
		return this.toBeDecorated.getDiceRequired();
	}

	public ArrayList<ActionResult> getEffectOnPositioning() {
		return effectOnPositioning;
	}

	public void setEffectOnPositioning(ArrayList<ActionResult> effectOnPositioning) {
		this.effectOnPositioning = effectOnPositioning;
	}

	public Tile getToBeDecorated() {
		return toBeDecorated;
	}

	public void setToBeDecorated(Tile toBeDecorated) {
		this.toBeDecorated = toBeDecorated;
	}

	@Override
	public void applyEffect(Familiar pl) {
		for (ActionResult r : this.getEffectOnPositioning()){
			r.applyResult(pl);
		}
		toBeDecorated.applyEffect(pl);
	}

	@Override
	public void removeTowerCard() {
		toBeDecorated.removeTowerCard();
	}

	@Override
	public void acceptVisitor(ViewVisitorInterface vi) {
		vi.visit(this);
	}
	
	@Override
    public String toString(){
    	return "Tower " + toBeDecorated.getParentTower().getColor().toString() + " T. " + toBeDecorated.getDiceRequirement().getValue();
    }

	@Override
	public boolean hasMorePaymentOptions() {
		return this.hasMorePaymentOptions;
	}
	
	@Override
    public  ArrayList<ArrayList<Resource>> getRequirements(){
        return this.toBeDecorated.getRequirements();
    }
}
