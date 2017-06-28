package it.polimi.ingsw.ps05.model.effects;

import java.io.Serializable;

public enum ActivableEffectType implements Serializable {
	HARVEST("Raccolto"),PRODUCTION("Produzione");
	
	final String type;
	
	private ActivableEffectType(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return this.type;
	}
}
