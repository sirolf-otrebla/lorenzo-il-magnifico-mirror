package it.polimi.ingsw.ps05.model;


/* this interface is a generalization of all card spaces within the towers. 
 * further comments will be added;
 *  */
public interface TowerTileInterface {

	public void setParentTower(Tower tower);
	public void setTowerCard(TowerCard card);
	public void removeTowerCard();
	
}
