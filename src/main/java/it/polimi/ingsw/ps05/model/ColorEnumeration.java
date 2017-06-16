package it.polimi.ingsw.ps05.model;

public enum ColorEnumeration {
	Green("Verde"),
	Violet("Viola")
	,Yellow("Giallo")
	,Blue("Blu"),
	Black("Nero"),
	White("Bianco"),
	Orange("Arancione"),
	Any("Neutro"),
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
