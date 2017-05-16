package it.polimi.ingsw.ps05.model;


public class Color {
	private ColorEnumeration color;

	public Color(ColorEnumeration color) {
		this.color = color;
	}
	
	public Color() {
		
	}
	
	public void setColor(ColorEnumeration color){
		this.color = color;
	}
	
	public ColorEnumeration getColor() {
		return this.color;
	}
}
