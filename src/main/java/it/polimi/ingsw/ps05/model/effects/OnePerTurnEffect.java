package it.polimi.ingsw.ps05.model.effects;

import it.polimi.ingsw.ps05.model.PlayerRelated;

/**
 * Created by Alberto on 23/05/2017.
 */
public class OnePerTurnEffect extends ImmediateEffect {
    /**
	 * 
	 */
	private static final long serialVersionUID = 533058130936628967L;
	private boolean used = false;

    public boolean isUsed() {
        return used;
    }

    private void setUsed() {
        this.used = true;
    }

    public void resetUsed(){
        this.used = false;
    }
    @Override
    public void apply(PlayerRelated familyMember){
        super.apply(familyMember);
        this.setUsed();
    }
}
