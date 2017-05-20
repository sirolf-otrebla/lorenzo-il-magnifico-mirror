package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class FaithResource implements Resource, ActionResult {

	//TODO : scegliere se usare due variabili separate o usarne una sola
	private Integer amount; 
	private Integer value;

	public FaithResource(Integer amount) {
		this.amount = amount;
	}

	public FaithResource() {

	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return this.amount;
	}
	
	public void setValue(Integer value){ //metodo utilizzato come setter quando la risorsa è il risultato di un effetto
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {

	}

	@Override
	public void remove(Resource res) {

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) {
	}
}
