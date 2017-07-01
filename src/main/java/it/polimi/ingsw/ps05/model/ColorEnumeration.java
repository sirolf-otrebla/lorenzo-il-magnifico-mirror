package it.polimi.ingsw.ps05.model;

import java.io.Serializable;

public enum ColorEnumeration implements Serializable  {
	Violet("violet"),
	Yellow("yellow"),
	Blue("blue"),
	Green("green"),
	Red("red"),
	Black("black"),
	White("white"),
	Orange("orange"),
	Any("neutral"),
	Ghost("ghost"),
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
