package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.*;

public class BonusWithMultiplier implements ActionResult {
	private Float multiplier;
	private Resource returnResource;
	private Class cardToCount;
	
	public BonusWithMultiplier() {
		
	}

	public BonusWithMultiplier(Float multiplier, Resource returnResource, Class cardToCount) {
		super();
		this.multiplier = multiplier;
		this.returnResource = returnResource;
		this.cardToCount = cardToCount;
	}
}
