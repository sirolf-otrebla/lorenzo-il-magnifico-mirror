package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.ArrayList;
import java.util.Observable;

import it.polimi.ingsw.ps05.server.controller.PrivilegeBonusListener;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.ClaimPrivilegeStrategy;

public class PrivilegeBonus extends Observable implements ActionResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2496576331602457912L;
	private Integer value; //con value si Integerende il numero di privilegi conferiti dalla carta
						//ricordandosi che i privilegi non possono mai essere uguali tra loro
	transient private Game game;

	//soluzione a mio parere molto brutta pensare ad alternativa

	private ArrayList<ArrayList<ActionResult>> exchangeList;
	private ArrayList<Integer> resChoosen;
	//TODO: IMPLEMENTARE CONTROLLI LUNGHEZZA RISPETTO A QUANTO PASSATO DA FUORI

	public PrivilegeBonus( Integer value){
		this.addObserver(PrivilegeBonusListener.getInstance());
		this.value = value;
	}
	
	public PrivilegeBonus(){
		this.addObserver(PrivilegeBonusListener.getInstance());
	}

	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	public void setConvertedResources(ArrayList<Integer> res){
        this.resChoosen = res;
    }
	@Override
	public void applyResult(PlayerRelated playerR){
		this.game.getEndActionStrategyContainer().setChosenStrategy(new ClaimPrivilegeStrategy(this.value));
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
		return "Privilegio";
	}


}


/*		public PrivilegeBonus() {
		setUpExchange();
	}

	public void setUpExchange(){
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new FaithResource(1))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new GoldResource(2))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new MilitaryResource(2))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new ServantResource(2))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new WoodResource(1), new StoneResource(1))));
	}

		public ArrayList<ArrayList<ActionResult>> showPossibilityForExchange(){
		return exchangeList;
	}

	public void setChoices (int[] choice){
		this.choice = choice.clone();
	}



 */