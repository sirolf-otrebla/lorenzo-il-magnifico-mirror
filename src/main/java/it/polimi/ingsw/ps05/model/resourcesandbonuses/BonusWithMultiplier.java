package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.BlueCard;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.cards.VioletCard;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.server.controller.Game;

public class BonusWithMultiplier extends PermanentBonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2859778147705557560L;
	private Float multiplier;
	private Resource returnResource;
	private Class<?> cardToCount;
	private transient Game game;

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
		Resource copy = returnResource;
		if (cardToCount.equals(MilitaryResource.class)){
			copy.setValue((int)	Math.floor(playerR.getRelatedPlayer().getResource(MilitaryResource.id).getValue()*multiplier));
			playerR.getRelatedPlayer().addResource(copy);
		} else if (cardToCount.equals(GreenCard.class)){
			copy.setValue((int)	Math.floor(playerR.getRelatedPlayer().getGreenCardList().size()*multiplier));
			playerR.getRelatedPlayer().addResource(copy);
		} else if (cardToCount.equals(BlueCard.class)){
			copy.setValue((int)	Math.floor(playerR.getRelatedPlayer().getBlueCardList().size()*multiplier));
			playerR.getRelatedPlayer().addResource(copy);
		} else if (cardToCount.equals(YellowCard.class)){
			copy.setValue((int)	Math.floor(playerR.getRelatedPlayer().getYellowCardList().size()*multiplier));
			playerR.getRelatedPlayer().addResource(copy);
		} else if (cardToCount.equals(VioletCard.class)){
			copy.setValue((int)	Math.floor(playerR.getRelatedPlayer().getVioletCardList().size()*multiplier));
			playerR.getRelatedPlayer().addResource(copy);
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
