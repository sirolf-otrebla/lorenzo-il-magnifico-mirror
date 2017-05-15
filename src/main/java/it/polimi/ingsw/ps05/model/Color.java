package it.polimi.ingsw.ps05.model;


/* ENUM HERE, not class */

public class Color {
	private String color;

	public Color(String color) {
		this.color = color;
	}
	
	public Color() {
		
	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
}
