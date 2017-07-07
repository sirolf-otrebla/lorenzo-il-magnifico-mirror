package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.spaces.TileWithEffect;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;

/** this is a leader card effect, used by "Santa Rita" leader card. it duplicates
 * the resources obtained through Tower cards
 */
public class DoubleBonus extends PermanentBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6139830404981455814L;
	transient Game game;

	/** this effect duplicates each resource that can be obtained through tower cards
	 *
	 * @param playerR this is the PlayerRelated object that represents the active player.
	 *                it is not used in this implementation, but it's mandatory for
	 *             interface compatibility reasons.
	 */
	@Override
	public void applyResult(PlayerRelated playerR) {
		for (Tower t : game.getBoard().getTowerList().values()) {
			for (TowerTileInterface tile : t.getTiles().values()){
				TowerCard card = tile.getCard();
				for (Effect e : card.getEffects()){
					if (e instanceof ImmediateEffect){
						for (ActionResult r : ((ImmediateEffect) e).getResultList()){
							if (r instanceof Resource) {
								if (((Resource) r).getID().equals(GoldResource.id) || ((Resource) r).getID().equals(StoneResource.id) ||
										((Resource) r).getID().equals(WoodResource.id) || ((Resource) r).getID().equals(ServantResource.id)) {
									try {
										r.setValue(2 * r.getValue());
									} catch (NoSuchMethodException e1) {
										//eccezione che non verrà mai lanciata
									}
								}
							}
						}
					
					}
				}
					
			}
		}
		
	}

	/** this method is not usable at this time. it exists for interface compatibility reasons.
	 *
	 * @param amount
	 * @throws NoSuchMethodException if you launch this method, this exception is thrown.
	 */
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
		
	}

	@Override
	public Game getGame() {
		return game;
	}


	/** resets the board as it was before calling applyResult()
	 *
	 * @param playerR this is the PlayerRelated object that represents the active player.
	 *                it is not used in this implementation, but it's mandatory for
	 *             interface compatibility reasons.
	 */
	@Override
	public void resetResult(PlayerRelated playerR) {
		for (Tower t : game.getBoard().getTowerList().values()) {
			for (TowerTileInterface tile : t.getTiles().values()){
				TowerCard card = tile.getCard();
				for (Effect e : card.getEffects()){
					if (e instanceof ImmediateEffect){
						for (ActionResult r : ((ImmediateEffect) e).getResultList()){
							if (((Resource)r).getID().equals(GoldResource.id) || ((Resource)r).getID().equals(StoneResource.id) ||
									((Resource)r).getID().equals(WoodResource.id) || ((Resource)r).getID().equals(ServantResource.id)){
								try {
									r.setValue(r.getValue()/2);
								} catch (NoSuchMethodException e1) {
									//eccezzione che non verrà mai lanciata
								}
							}
						}
					
					}
				}
					
			}
		}
		
	}

}
