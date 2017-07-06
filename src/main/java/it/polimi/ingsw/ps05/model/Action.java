package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ServantResource;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.*;

import java.util.ArrayList;
import java.util.Iterator;

/** This class represent an Action during a game round. It is executed on server and modify the game state
 *
 */
public class Action implements Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5391868345452935447L;
	//constants (not useful) for payment choice
	public static final int NO_OPTIONS = 0;
	public static final int FIRST_OPTION = 0;
	public static final int SECOND_OPTION = 1;
	public static final int THIRD_OPTION = 2;


	private Familiar familiar;
	private ActionSpace position;

	private ArrayList<ArrayList<Resource>> suitableReqAlternatives;
	private boolean isLegal = false;

	public Action(Familiar familiar, ActionSpace position) {
		this.position = position;
		this.familiar = familiar;
		this.suitableReqAlternatives = new ArrayList<>();
	}

	// 1) check if the family member is already used
	// 1b) check if the action is a "special" one
	// 2) check dice
	// 2b) check resources
	// c) evaluate special effects?

	public Familiar getFamiliar() {
		return familiar;
	}

	public ActionSpace getPosition() {
		return position;
	}

	public boolean isLegal() {

		// 1
		if (this.isLegal) return true;
		if (this.familiar.isUsed()) return false;
		if (position != null && this.position.isOccupied()) return false; //TODO da verificare se accettà più familiari

		// 2- 2b)
		ArrayList<ArrayList<Resource>> list = this.position.getRequirements();
		Iterator<ArrayList<Resource>> iterator = list.iterator();
		while (iterator.hasNext()) {
			ArrayList<Resource> thisIteration = iterator.next();
			if(checkResList(thisIteration)) this.suitableReqAlternatives.add(thisIteration);
		}
		if (this.suitableReqAlternatives.size() == 0) return false;
		isLegal = true;
		return  isLegal;

	}



	// returns true if that resource list is suitable
	private boolean checkResList(ArrayList<Resource> resList){

		Iterator<Resource> it = resList.iterator();
		while(it.hasNext()){
			if (!it.next().hasEnoughResources(this.familiar)) return false;
		}
		return  true;
	}



	public void run(int selectedPayment) throws IllegalActionException, NotEnoughResourcesException, DiceTooLowException, IllegalMethodCallException {

		if (!isLegal) throw new IllegalActionException(IllegalActionException.ACTION_NOT_LEGAL);
		Integer familiarDieValue = this.familiar.getRelatedDice().getValue();
		Integer requestedDie = this.familiar.getRelatedDice().getValue();
		if ( familiarDieValue < requestedDie) {
			Integer diff = requestedDie - familiarDieValue;
			this.familiar.getRelatedPlayer().getResource(ServantResource.id).remove(diff.intValue());
		}
		int len = this.getSuitableReqAlternatives().size();
		if ( selectedPayment < 0 || selectedPayment > len )
			throw new IllegalActionException(IllegalActionException.BAD_PAY_CHOICE);
		ArrayList<Resource> paymentList = suitableReqAlternatives.get(selectedPayment);

		//removes requirements from player (pay attention how to handle dices and other familyMember-related resources)
		manageRequirements(paymentList);

		//position.applyNonActivableEffects moves the card (if there is any;
		this.position.setOccupied(this.familiar);
		this.familiar.setPosition(this.position);
		this.position.applyEffect(this.familiar);
		
	}


	private void manageRequirements(ArrayList<Resource> resources) throws NotEnoughResourcesException, DiceTooLowException {


		Iterator<Resource> it = resources.iterator();
		while(it.hasNext())
			it.next().removeFromPlayer(this.familiar);
	}


	public ArrayList<ArrayList<Resource>> getSuitableReqAlternatives() {
		return suitableReqAlternatives;
	}


}
