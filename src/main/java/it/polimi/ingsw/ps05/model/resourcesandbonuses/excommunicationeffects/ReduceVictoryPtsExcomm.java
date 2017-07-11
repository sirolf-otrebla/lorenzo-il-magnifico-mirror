package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.MilitaryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.PermanentBonus;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.StoneResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.VictoryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.WoodResource;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.model.effects.EffectType;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

public class ReduceVictoryPtsExcomm extends PermanentBonus implements ExcommunicationEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5529477371507190656L;

	transient Game game;

	@Override
	public EffectType getEffectType() {
		return EffectType.EXCOM;
	}
	
	private ArrayList<ActionResult> malus = null;
	
	private ArrayList<ActionResult> toCheck = null;

	@Override
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException{
		if (this.malus == null){
			this.malus = malus;
		} else {
			throw new RepeatedAssignmentException();
		}
	}
	
	public void setToCheck(ArrayList<ActionResult> toCheck) throws RepeatedAssignmentException{
		if (this.toCheck == null){
			this.toCheck = toCheck;
		} else {
			throw new RepeatedAssignmentException();
		}
	}
	@Override
	public String toString(){
		return null;
	}

	@Override
	public void apply(PlayerRelated familyMember) {
		applyResult(familyMember);
		
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		return malus;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
		
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		//calcolo punteggio
				int pts = game.getGameFlowctrl().evaluateVictoryPts(playerR.getRelatedPlayer());
				int toSub = 0; //sarà negativo
				if (toCheck.size() == 1){
					ActionResult r = toCheck.get(0);
					if (r instanceof VictoryResource){
						try {
							toSub = (int) (malus.get(0).getValue()*Math.floor(pts/r.getValue()));
						} catch (NoSuchMethodException e) {
							//questa eccezione non si verifica mai
							e.printStackTrace();
						}
					} else if (r instanceof MilitaryResource){
						try {
							toSub = (int) (malus.get(0).getValue()*
									Math.floor(playerR.getRelatedPlayer().getResource(MilitaryResource.id).getValue()
											/r.getValue()));
						} catch (NoSuchMethodException e) {
							//questa eccezione non si verifica mai
							e.printStackTrace();
						}
					}
				} else if (toCheck.size() == 2){
					HashMap<String, Resource> temp = new HashMap<>();
					temp.put(StoneResource.id, new StoneResource(0));
					temp.put(WoodResource.id, new WoodResource(0));
					for (YellowCard c : playerR.getRelatedPlayer().getYellowCardList()) {
						for (ArrayList<Resource> or : c.getRequirements()){
							for (Resource r : or){
								for (ActionResult res : toCheck){
									if (r.getID().equals(res)) {
										Resource resource = temp.get(r.getID());
										try {
											resource.setValue((int) (resource.getValue() + Math.floor(r.getValue()/res.getValue())));
										} catch (NoSuchMethodException e) {
											// non si entra mai qui
											e.printStackTrace();
										}
										
										temp.replace(resource.getID(), resource);
									}
								}
							}
						}
					}
					if (temp.get(StoneResource.id).getValue() < temp.get(WoodResource.id).getValue()){
						try {
							toSub = malus.get(0).getValue()*temp.get(StoneResource.id).getValue();
						} catch (NoSuchMethodException e) {
							// non si entra mai qui
							e.printStackTrace();
						}
					} else {
						try {
							toSub = malus.get(0).getValue()*temp.get(WoodResource.id).getValue();
						} catch (NoSuchMethodException e) {
							// non si entra mai qui
							e.printStackTrace();
						}
					}
				} else {
					for (ActionResult r : toCheck){
						try {
							toSub = toSub + malus.get(0).getValue()*r.getValue()*playerR.getRelatedPlayer().getResource(((Resource)r).getID()).getValue();
						} catch (NoSuchMethodException e) {
							// non si entra mai qui 
							e.printStackTrace();
						}
					}
				}
				
				playerR.getRelatedPlayer().addResource(new VictoryResource(toSub));
	}

	@Override
	public void setValue(Integer amount) throws NoSuchMethodException {
		throw new NoSuchMethodException();
		
	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		throw new NoSuchMethodException();
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		
	}
}
