package it.polimi.ingsw.ps05.model;

public enum EpochEnumeration {
	FIRST(0),SECOND(1),THIRD(2), NO_EPOCH(3);
	
	private int epochIndex;

	private EpochEnumeration (int epochIndex) { this.epochIndex = epochIndex; }

	public static EpochEnumeration getEpoch(int epochIndex) {
		for (EpochEnumeration o : EpochEnumeration.values()) {
			if (o.epochIndex == epochIndex) return o;
		}
		return NO_EPOCH;
	}

}
