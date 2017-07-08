package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.spaces.HarvestingSpace;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.TileWithEffect;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;

public class RemoveTileEffect extends PermanentBonus implements ActionResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1299547449525139414L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public RemoveTileEffect(Integer value){
		this.value = value;
	}

	public RemoveTileEffect() {

	}

	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		return value;
	}

	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		//modifica la board aggiungendo risorsa sempre falsa
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			for (TowerTileInterface tile : t.getTiles().values()){
				if (tile instanceof TileWithEffect){
					((TileWithEffect)tile).setEffectOnPositioning(new ArrayList<ActionResult>());
				}
			}
		}
		for (ActionSpace a : board.getActSpacesMap().values()){
			if (!(a instanceof HarvestingSpace)){
				a.addFalseResource();
			}
		}
		//notifica observer
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}
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
		return "Rimuovi eff. Tile";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub
		
	}


}
