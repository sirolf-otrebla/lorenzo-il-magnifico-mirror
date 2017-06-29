package it.polimi.ingsw.ps05.model.spaces;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

/* this interface is a generalization of all card spaces within the towers. 
 * further comments will be added;
 *  */
public interface TowerTileInterface extends Serializable {

	public void setParentTower(Tower tower);
	public void setTowerCard(TowerCard card);
	public void removeTowerCard();
	public Dice getDiceRequired();
	public TowerCard getCard();
	public Tower getParentTower();
	public void setDiceRequired(Integer dice);
	
}
