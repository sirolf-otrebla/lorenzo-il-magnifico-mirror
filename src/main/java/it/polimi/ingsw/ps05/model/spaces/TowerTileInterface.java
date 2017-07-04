package it.polimi.ingsw.ps05.model.spaces;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

/* this interface is a generalization of all card spaces within the towers. 
 * further comments will be added;
 *  */
public abstract  class  TowerTileInterface extends SingleOccupantActionSpace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8336428979750702256L;
	public abstract void setParentTower(Tower tower);
	public abstract void setTowerCard(TowerCard card);
	public abstract void removeTowerCard();
	public abstract Dice getDiceRequired();
	public abstract TowerCard getCard();
	public abstract Tower getParentTower();
	public abstract void setDiceRequired(Integer dice);
	public abstract boolean hasMorePaymentOptions();

	
}
