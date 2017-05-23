package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;

/*
 * this is a decorator for Tile class, designed to add tile-related effects \
 * 
 * further commments will be added.
 */

public class TileWithEffect extends ActionSpaceWithEffect implements TowerTileInterface {

	private Tile toBeDecorated; //?????
	
	private Tower<?> parentTower;
    private TowerCard card;
	
	private Integer diceRequired; //integer o dado??
	private ArrayList<ActionResult> effectOnPositioning;
	
public TileWithEffect(){
    	
    }
    
    public TileWithEffect(TowerCard card, Integer diceRequired, Tower<?> parentTower, ArrayList<ActionResult> effectOnPositioning){
    	
    }
    
    public TileWithEffect(Integer diceRequired){
    	this.diceRequired = diceRequired;
    }
    
    public TileWithEffect(Tower<?> parentTower){
    	this.parentTower = parentTower;
    }
    
    @Override
    public void setTowerCard(TowerCard card){
    	this.card = card;
    }
    
    public void setDiceRequired(Integer diceRequired){
    	this.diceRequired = diceRequired;
    }
    
    public void setTileEffect(ArrayList<ActionResult> effectOnPositioning){
    	this.effectOnPositioning = effectOnPositioning;
    }
	
	public TileWithEffect ( Tile tile){
		this.setToBeDecorated(tile);
	}

	@Override
	public ArrayList<Effect> getEffects() {  //TODO
		return null;
	}

	@Override
	public void setParentTower(Tower<?> tower) {
		this.parentTower = tower;
		
	}

	public Tower<?> getParentTower() {
		return parentTower;
	}

	public TowerCard getCard() {
		return card;
	}

	public Integer getDiceRequired() {
		return diceRequired;
	}

	public ArrayList<ActionResult> getEffectOnPositioning() {
		return effectOnPositioning;
	}

	public void setCard(TowerCard card) {
		this.card = card;
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
	public void applyEffect() {
		// TODO Auto-generated method stub
		
	}
}
