package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.server.net.Game;

import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.EffectType;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.VioletCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

public class NoVioletVictoryPtsExcomm implements ExcommunicationEffect{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4655761429813840579L;
	
	Game game;

	@Override
    public EffectType getEffectType() {
        return EffectType.EXCOM;
    }
	
	private ArrayList<ActionResult> malus = null;

	@Override
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException{
		if (this.malus == null){
			this.malus = malus;
		} else {
			throw new RepeatedAssignmentException();
		}
	}
	
	@Override
	public String toString(){
		return "Non ricevi punti vittoria dalle carte viola";
	}
	
	@Override
	public void apply(PlayerRelated familyMember) {
		for (VioletCard c : familyMember.getRelatedPlayer().getVioletCardList()){
			c.effects = new ArrayList<Effect>();
		}
		
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		return malus;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
		
	}
}
