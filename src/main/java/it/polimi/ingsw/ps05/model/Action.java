package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.*;

import java.util.ArrayList;

public class Action implements Period {
	
	Player player;
	Familiar familiar;
	ActionSpace position;

	public Action(Player player, Familiar familiar, ActionSpace position){

		this.player = player;
		this.position = position;
		this.familiar = familiar;
	}

	public void execute() throws OccupiedPositionException, RequirementsNotFullfilledException{
		boolean occupied = false; // it has to be inizialized (parser says that it can be uninizialized if we omit init value here, maybe there's some coding error ? )

		try {
			occupied = this.position.isOccupied();
		}
		catch (TowerOccupiedException towerOccupied){
			occupied = false;
			player.gold.remove(towerOccupied.getTowerOccupied().getRentAmount());

			// TODO: lower money (use exceptions to set amount ecc. )
		}
		/* this block executes the action when the TowerOccupiedException is not thrown.
		 * It an IMPORTANT piece of this method, and because of how exceptions works there
		 * is no better way to write it. It MUST work in any case, so please do not
		 * add other exceptions that can modify its behavior. --Sifolfo ( bad English )
		 */
		finally {
			if (occupied) throw new OccupiedPositionException();
			else {
				manageRequirements();
				this.position.setOccupied(this.familiar);
				ArrayList<Effect> effectList = this.position.getEffects();

			}

		}
	}

	private void manageRequirements() throws RequirementsNotFullfilledException{
		try{
			for (Resource a : position.getRequirements()){
				// TODO (remove NotEnoughResourcesException() )

				throw new  NotEnoughResourcesException();
			}

		} catch (NotEnoughResourcesException e){
			RequirementsNotFullfilledException ex = new RequirementsNotFullfilledException();
			ex.decorate(e);
			throw ex;
		}

	}
}
