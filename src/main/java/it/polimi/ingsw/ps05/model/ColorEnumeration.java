package it.polimi.ingsw.ps05.model;

import java.io.Serializable;

public enum ColorEnumeration implements Serializable  {
	Violet("Violet"),
	Yellow("Yellow"),
	Blue("Blue"),
	Green("Green"),
	Red("Red"),
	Black("Black"),
	White("White"),
	Orange("Orange"),
	Any("Neutral"),
	Ghost("Ghost"),
	NOT_INITIALIZED("Nessuno");
	
	private final String color;
	
	private ColorEnumeration(String color){
		this.color = color;
	}
	
	@Override
	public String toString(){
		return this.color;
	}
}
