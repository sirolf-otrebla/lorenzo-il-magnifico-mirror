package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Action implements Period {

	//constants (not useful) for payment choice
	public static final int NO_OPTIONS = 0;
	public static final int FIRST_OPTION = 0;
	public static final int SECOND_OPTION = 1;
	public static final int THIRD_OPTION = 2;


	Player player;
	Familiar familiar;
	ActionSpace position;

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

	public boolean isLegal() {

		// 1
		if (this.familiar.isUsed()) return false;

		// 2- 2b)
		ArrayList<ArrayList<Resource>> list = this.position.getRequirements();
		Iterator<ArrayList<Resource>> iterator = list.iterator();
		Iterator<Resource> innerIterator;
		while (iterator.hasNext()) {
			ArrayList<Resource> thisIteration = iterator.next();
			if(checkResList(thisIteration)) this.suitableReqAlternatives.add(thisIteration);
		}

		if (this.suitableReqAlternatives.size() == 0) return false;
		isLegal = true;
		return  true;

	}



	// returns true if that resource list is suitable
	private boolean checkResList(ArrayList<Resource> resList){

		Iterator<Resource> it = resList.iterator();
		while(it.hasNext()){
			if (!it.next().hasEnoughResources(this.familiar)) return false;
		}
		return  true;
	}



	public void run(int selectedPayment) throws IllegalActionException, NotEnoughResourcesException, DiceTooLowException {

		if (!isLegal) throw new IllegalActionException(IllegalActionException.ACTION_NOT_LEGAL);
		int len = this.getSuitableReqAlternatives().size();
		if ( selectedPayment < 0 || selectedPayment > len )
			throw new IllegalActionException(IllegalActionException.BAD_PAY_CHOICE);
		ArrayList<Resource> paymentList = suitableReqAlternatives.get(selectedPayment);

		//removes requirements from player (pay attention how to handle dices and other familyMember-related resources)
		manageRequirements(paymentList);

		//position.applyNonActivableEffects moves the card (if there is any;
		this.position.applyEffect();
		this.position.setOccupied(this.familiar);
		this.familiar.setPosition(this.position);
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
