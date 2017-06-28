package it.polimi.ingsw.ps05.model;

import java.io.Serializable;

public enum EpochEnumeration implements Serializable {
	FIRST("Prima epoca",0),SECOND("Seconda epoca", 1),THIRD("Terza epoca",2), NO_EPOCH("No epoca",3);
	
	private int epochIndex;
	private String name;

	private EpochEnumeration (String name, int epochIndex) { this.epochIndex = epochIndex; this.name = name;}

	public static EpochEnumeration getEpoch(int epochIndex) {
		for (EpochEnumeration o : EpochEnumeration.values()) {
			if (o.epochIndex == epochIndex) return o;
		}
		return NO_EPOCH;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
