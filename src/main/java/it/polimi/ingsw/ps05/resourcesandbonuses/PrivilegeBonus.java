package it.polimi.ingsw.ps05.resourcesandbonuses;

import java.util.ArrayList;
import java.util.Observable;

import it.polimi.ingsw.ps05.controller.PrivilegeBonusListener;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.server.Game;

public class PrivilegeBonus extends Observable implements ActionResult {
	private Integer value; //con value si Integerende il numero di privilegi conferiti dalla carta
						//ricordandosi che i privilegi non possono mai essere uguali tra loro
	private Game game;

	//soluzione a mio parere molto brutta pensare ad alternativa

	private ArrayList<ArrayList<ActionResult>> exchangeList;
	private ArrayList<ActionResult> resChoosen;
	//TODO: IMPLEMENTARE CONTROLLI LUNGHEZZA RISPETTO A QUANTO PASSATO DA FUORI

	public PrivilegeBonus( Integer value){
		this.addObserver(PrivilegeBonusListener.getInstance());
		this.value = value;
		this.setChanged();
		this.notifyObservers();
		//TODO: BASTA COSI?
	}
	
	public PrivilegeBonus(){
		this.addObserver(PrivilegeBonusListener.getInstance());
		this.notifyObservers();
		//TODO: BASTA COSI?
	}


	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	public void setConvertedResources(ArrayList<ActionResult> res){
        this.resChoosen = res;
    }
	@Override
	public void applyResult(PlayerRelated playerR){
		this.setChanged();
		this.notifyObservers();
	    for (int i = 0; i < this.value; i++)
            this.resChoosen.get(i).applyResult(playerR);
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		return game;
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