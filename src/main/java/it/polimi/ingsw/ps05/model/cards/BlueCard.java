package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class BlueCard extends TowerCard {
	
	public BlueCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<ArrayList<Resource>> requirements,
					ArrayList<Effect> effects) {
		super(epoch, color, cardName, requirements, effects);
	}

	public BlueCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		super(epoch, color, cardName, effects);
		// TODO Auto-generated constructor stub
	}
	
	public BlueCard(){
		super();
	}

	@Override
	public void moveToPlayer(PlayerRelated player) {
		// TODO Auto-generated method stub
		player.getRelatedPlayer().addBlueCard(this);
	}

	@Override
	public void applyNonActivableEffects(PlayerRelated player) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString(){
		return "Carta blu";
	}
	
	
}
