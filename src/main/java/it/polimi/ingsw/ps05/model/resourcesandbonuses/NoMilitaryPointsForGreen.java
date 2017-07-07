package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.controller.Game;

public class NoMilitaryPointsForGreen extends PermanentBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2110987672087368361L;
	transient Game game;
	ArrayList<MilitaryResource> path;
	
	@Override
	public void applyResult(PlayerRelated playerR) {
		for (int i = 0; i < game.getBoard().getMilitaryPath().size(); i++){
			game.getBoard().getMilitaryPath().set(i, new MilitaryResource(0));
		}
		

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
	public void setGame(Game game) {
		this.game = game;
		path = game.getBoard().getMilitaryPath();
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public void linkToActionListeners() {
		//TODO
	}

    @Override
    public void notifyToActionListeners() {

    }

    @Override
	public void resetResult(PlayerRelated playerR) {
		game.getBoard().setMilitaryPath(path);
		
	}

}
