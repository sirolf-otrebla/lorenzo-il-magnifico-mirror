package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.server.controller.Game;

public class CopyLeader implements ActionResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1350594508245935462L;
	transient Game game;
	Integer value; //usato per tenere l'id della carta leader copiata

	@Override
	public void applyResult(PlayerRelated playerR) {
		for (LeaderCard l : game.getBoard().getLeaderCardsList()) {
			if (l.getReferenceID() == value){
				l.applyNonActivableEffects(playerR);
			}
		}

	}

	@Override
	public void setValue(Integer amount) throws NoSuchMethodException {
		this.value = amount;

	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		return value;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;

	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
    public String toString(){
    	return "Copi una carta leader attiva";
    }

}
