package it.polimi.ingsw.ps05.model.spaces;

import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

import java.util.ArrayList;

/* represent the all card spaces within towers
 * 
 * further comments will be added.
 */
public class Tile extends TowerTileInterface {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1979532547287923081L;
	private Tower parentTower;
    private TowerCard card;

    private Boolean hasMorePaymentOptions = false;
    public static final int TOWER_OCCUPIED_PAYMENT = 3;
    
    public Tile(){
    	super();
    }

    @Override
    public TowerCard getCard() {
        return card;
    }

    public Tile(TowerCard card, Integer diceRequired, Tower parentTower){
    	super();
        if (card.requirements.size() > 1) this.hasMorePaymentOptions = true;
    	this.parentTower = parentTower;
    	this.card = card; 
    	super.setDiceRequirement( new Dice(ColorEnumeration.Any, diceRequired));
    }
    
    @Override
    public void setOccupied(Familiar f){
    	super.setOccupied(f);
    }
    
    @Override
    public boolean isOccupied(){
    	return super.isOccupied();
    }
    
    @Override
    public Tower getParentTower(){
        return this.parentTower;
    }

    @Override
    public void setDiceRequired(Integer dice) {
        super.setDiceRequirement(new Dice(ColorEnumeration.Any, dice));
    }

    @Override
    public void setParentTower(Tower parentTower){
    	this.parentTower = parentTower;
    }
    
    @Override
    public void setTowerCard(TowerCard card){
        if (card.requirements.size() > 1) this.hasMorePaymentOptions = true;
    	this.card = card;
    }

    @Override
    public ArrayList<Effect> getEffects() {

        return this.card.getEffects();
    }

    @Override
    public  ArrayList<ArrayList<Resource>> getRequirements(){
        // ADDS DICE REQUIREMENT
       ArrayList<ArrayList<Resource>> cardReq = card.getRequirements();
       ArrayList<ArrayList<Resource>> req = new ArrayList<>();
        for (ArrayList<Resource> a : cardReq) {
            ArrayList<Resource> nuovo = new ArrayList<>();
            for (Resource r: a) nuovo.add(r);
            req.add(nuovo);
        }
       if (req.size() == 0){
    	   req.add(new ArrayList<>());
    	   req.get(0).add(this.getDiceRequired());
       } else {
    	   for (ArrayList<Resource> andAlternative: req)
               andAlternative.add(super.getDiceRequirement());
       }
       // ADD TOWER OCCUPIED GOLD REQUIREMENT;
       if (parentTower.isOccupied())
           for (ArrayList<Resource> andAlternative: req)
               andAlternative.add(new GoldResource(Tile.TOWER_OCCUPIED_PAYMENT));
       return req;
    }

    @Override
    public void applyEffect(Familiar pl) {
    	System.out.println("Occupant tower: " + this.getOccupant());
        this.card.moveToPlayer(pl.getRelatedPlayer());
        this.card.applyNonActivableEffects(pl);
        this.getParentTower().setOccupied(true);
    }

	@Override
	public void removeTowerCard() {
		// TODO Auto-generated method stub
		card = null;
	}

    @Override
    public Dice getDiceRequired() {
        return super.getDiceRequirement();
    }

    @Override
    public void acceptVisitor(ViewVisitorInterface vi) {
    	vi.visit(this);
    }
    
    @Override
    public String toString(){
    	return "Tower " + parentTower.getColor().toString() + " T. " + getDiceRequired().getValue();
    }

    @Override
    public boolean hasMorePaymentOptions() {
        return this.hasMorePaymentOptions;
    }
}
