package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.server.Game;

public class BonusWithMultiplier extends PermanentBonus {
	private Float multiplier;
	private Resource returnResource;
	private Class<?> cardToCount;
	private Game game;

	public BonusWithMultiplier() {

	}

	public BonusWithMultiplier(Float multiplier, Resource returnResource, Class<?> cardToCount) {
		super();
		this.multiplier = multiplier;
		this.returnResource = returnResource;
		this.cardToCount = cardToCount;
	}

	public Float getMultiplier(){
		return multiplier;
	}

	public Resource getReturnResource() {
		return returnResource;
	}

	public Class<?> getCardToCount() {
		return cardToCount;
	}

	public void setMultiplier(Float multiplier) {
		this.multiplier = multiplier;
	}

	public void setReturnResource(Resource returnResource) {
		this.returnResource = returnResource;
	}

	public void setCardToCount(Class<?> cardToCount) {
		this.cardToCount = cardToCount;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub

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
	}

	@Override
	public Game getGame() {
		return game;
	}


	@Override
	public String toString(){
		return "Bonus moltipl.";
	}



	@Override
	public void resetResult(PlayerRelated playerR) {
		//todo
	}
}
