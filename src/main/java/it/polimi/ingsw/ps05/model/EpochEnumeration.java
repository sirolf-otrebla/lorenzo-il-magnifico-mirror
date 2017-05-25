package it.polimi.ingsw.ps05.model;

public enum EpochEnumeration {
	FIRST(1),SECOND(2),THIRD(3), NO_EPOCH(0);
	
	 private int epochIndex;

	   private EpochEnumeration (int epochIndex) { this.epochIndex = epochIndex; }

	   public static EpochEnumeration getEpoch(int epochIndex) {
	      for (EpochEnumeration o : EpochEnumeration.values()) {
	          if (o.epochIndex == epochIndex) return o;
	      }
	      return NO_EPOCH;
	   }

}
