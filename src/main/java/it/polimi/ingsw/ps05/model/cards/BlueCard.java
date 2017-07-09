package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

/** this class represents the Character cards located on the blue tower
 *
 */
public class BlueCard extends TowerCard {
	
	/**
	 * 
	 */
	private int referenceId;

	private static final long serialVersionUID = 6589070594892321967L;

	/** this class represents the Character cards located on the blue tower
	 *
	 * @param id		this is the card id, used to represent the card object on view modules and to obtain the card's image from file.
	 * @param epoch 	this is the card's era, used for turn setup
	 * @param color 	this is the card's color, it has to be blue ( not sure why it's here)
	 *                  @see ColorEnumeration
	 * @param cardName 	this is the card's name, as represented in view modules
	 * @param requirements	these are the card's requirements, first List represent an OR plane, the inner list represent
	 *                      an AND plane: then this parameter can be seen as a SOP logic formula: for example, if the requirements
	 *                      are: (Resource1 + Resource2) OR (Resource3 + Resource4), they can be represented in this list as a 2-sized
	 *                      list which is composed by two other list, the firs containing Resource1 and Resource2, and the
	 *                      second one containing Resource3 and Resource4
	 *                      @see Resource
	 * @param effects	this is a list of effects which are gained by the player when the card is moved on the player itself.
	 *                  @see Effect
	 *                  @see SimpleEffect
	 *                  @see it.polimi.ingsw.ps05.model.effects.ActivableEffect
	 */
	public BlueCard(int id, Epoch epoch, ColorEnumeration color, String cardName, ArrayList<ArrayList<Resource>> requirements,
					ArrayList<Effect> effects) {
		super( id,epoch, color, cardName, requirements, effects);
	}

	/** this constructor is designed for those cards who have not Requirements
	 *
	 * @param id		this is the card id, used to represent the card object on view modules and to obtain the card's image from file.
	 * @param epoch		this is the card's era, used for turn setup
	 * @param color		this is the card's color, it has to be blue ( not sure why it's here)
	 *                  @see ColorEnumeration
	 * @param cardName	this is the card's name, as represented in view modules
	 * @param effects	this is a list of effects which are gained by the player when the card is moved on the player itself.
	 *                  @see Effect
	 *                  @see SimpleEffect
	 *                  @see it.polimi.ingsw.ps05.model.effects.ActivableEffect
	 */
	public BlueCard(int id,Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		super(id, epoch, color, cardName, effects);
	}
	
	public BlueCard(){
		super();
	}

	/** this method moves the card from is Tower Tile to the player using {@link PlayerRelated} interface, which is now implemented by
	 *  both {@link it.polimi.ingsw.ps05.model.Player} and FamilyMember Classes.
	 *
	 * @param player	this is the player  where the card will be moved
	 */
	@Override
	public void moveToPlayer(PlayerRelated player) {
		player.getRelatedPlayer().addBlueCard(this);
	}

	/** this method applies the card's effects to a player using {@link PlayerRelated} interface, which is now implemented by
	 *  both {@link it.polimi.ingsw.ps05.model.Player} and FamilyMember Classes.
	 *
	 * @param player	this is the player that will gain the card's effects
	 */
	@Override
	public void applyNonActivableEffects(PlayerRelated player) {
		super.applyNonActivableEffects(player);
	}
	
	@Override
	public String toString(){
		return "Carta blu";
	}


}
