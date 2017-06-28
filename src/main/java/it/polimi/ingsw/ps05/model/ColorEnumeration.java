package it.polimi.ingsw.ps05.model;

public enum ColorEnumeration {
	Violet("Viola"),
	Yellow("Giallo"),
	Blue("Blu"),
	Green("Verde"),
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
