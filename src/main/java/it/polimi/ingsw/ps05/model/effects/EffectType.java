package it.polimi.ingsw.ps05.model.effects;

public enum EffectType {
	PERMANENT("Effetto permanente"),IMMEDIATE("Effetto immediato"),END_GAME("Fine gioco"),ACTIVABLE("Attivabile"),ONCE_PER_TURN("Uno per turno");
	
	private final String type;
	
	private EffectType(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return this.type;
	}
}
