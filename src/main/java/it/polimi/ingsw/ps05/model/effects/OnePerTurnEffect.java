package it.polimi.ingsw.ps05.model.effects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;

/**
 * Created by Alberto on 23/05/2017.
 */
public class OnePerTurnEffect extends ImmediateEffect {
    /**
	 * 
	 */
	private static final long serialVersionUID = 533058130936628967L;
	private boolean used = false;
	
	private ArrayList<ActionResult> effectList = new ArrayList<>();
	
	public void setEffectList(ArrayList<ActionResult> actResList){
	    this.effectList = actResList;
    }

    public boolean isUsed() {
        return used;
    }

    private void setUsed() {
        this.used = true;
    }

    public void resetUsed(){
        this.used = false;
    }
    
    public ArrayList<ActionResult> getEffectList(){
    	return effectList;
    }
    
    @Override
    public void apply(PlayerRelated familyMember){
        super.apply(familyMember);
        this.setUsed();
    }
}
