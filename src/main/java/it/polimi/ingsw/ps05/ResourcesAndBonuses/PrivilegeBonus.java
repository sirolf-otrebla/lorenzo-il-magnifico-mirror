package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import java.util.ArrayList;
import java.util.Arrays;

import it.polimi.ingsw.ps05.model.PlayerRelated;

public class PrivilegeBonus implements ActionResult {
	private Integer value; //con value si Integerende il numero di privilegi conferiti dalla carta
						//ricordandosi che i privilegi non possono mai essere uguali tra loro
	
	//soluzione a mio parere molto brutta pensare ad alternativa
	
	private ArrayList<ArrayList<ActionResult>> exchangeList;
	
	
	private int[] choice;
	
	public PrivilegeBonus(Integer value){
		this.value = value;
		setUpExchange();
	}
	
	public PrivilegeBonus() {
		setUpExchange();
	}
	
	public void setUpExchange(){
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new FaithResource(1))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new GoldResource(2))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new MilitaryResource(2))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new ServantResource(2))));
		exchangeList.add(new ArrayList<ActionResult>(Arrays.asList(new WoodResource(1), new StoneResource(1))));
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR){
		//se il numero delle scelte è diverso dal valore di scelte possibili allora non si fa niente
		if (this.choice.length == value) {
			for (int i = 0; i < choice.length; i++){
				ArrayList<ActionResult> thisIteration = exchangeList.get(choice[i]);
				for (ActionResult a : thisIteration){
					a.applyResult(playerR);
				}
			}
		}
	}
	
	public ArrayList<ArrayList<ActionResult>> showPossibilityForExchange(){
		return exchangeList;
	}
	
	public void setChoices (int[] choice){
		this.choice = choice.clone();
	}
	
}
