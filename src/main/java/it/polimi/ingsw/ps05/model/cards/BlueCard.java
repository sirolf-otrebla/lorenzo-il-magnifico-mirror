package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class BlueCard extends TowerCard {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6589070594892321967L;

	public BlueCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<ArrayList<Resource>> requirements,
					ArrayList<Effect> effects, Integer id) {
		super(epoch, color, cardName, requirements, effects, id);
	}

	public BlueCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects, Integer id) {
		super(epoch, color, cardName, effects,id);
	}
	
	public BlueCard(){
		super();
	}

	@Override
	public void moveToPlayer(PlayerRelated player) {
		player.getRelatedPlayer().addBlueCard(this);
	}

	@Override
	public void applyNonActivableEffects(PlayerRelated player) {
		super.applyNonActivableEffects(player);
	}
	
	@Override
	public String toString(){
		return "Carta blu";
	}
	
	
}
