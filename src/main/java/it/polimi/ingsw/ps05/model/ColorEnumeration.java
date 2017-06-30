package it.polimi.ingsw.ps05.model;

import java.io.Serializable;

public enum ColorEnumeration implements Serializable  {
	Violet("Viola"),
	Yellow("Giallo"),
	Blue("Blu"),
	Green("Verde"),
	Red("Rosso"),
	Black("Nero"),
	White("Bianco"),
	Orange("Arancione"),
	Any("Neutro"),
	Ghost("Fantasma"),
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
