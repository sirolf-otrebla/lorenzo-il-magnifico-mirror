package it.polimi.ingsw.ps05.resourcesandbonuses;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.HarvestingSpace;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.TileWithEffect;
import it.polimi.ingsw.ps05.model.Tower;
import it.polimi.ingsw.ps05.model.TowerCard;
import it.polimi.ingsw.ps05.model.TowerTileInterface;
import it.polimi.ingsw.ps05.net.server.Game;

public class RemoveTileEffect implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

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
		for (Tower t : board.getTowerList()){
			for (TowerTileInterface tile : t.getTiles()){
				if (tile instanceof TileWithEffect){
					((TileWithEffect)tile).setEffectOnPositioning(new ArrayList<ActionResult>());
				}
			}
		}
		for (ActionSpace a : board.getActionSpace()){
			if (!(a instanceof HarvestingSpace)){
				a.addFalseResource();
			}
		}
		//notifica observer
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
}
