package it.polimi.ingsw.ps05.Utils;

import java.util.Iterator;
import java.util.List;

public class AlternativesList<T> implements Iterable<Alternative<T>> {

	private List<List<T>> toBeDecorated;
	
	public AlternativesList(){
		this.toBeDecorated = null;
	}
	public AlternativesList(List<List<T>> toBeDecorated){
		this.toBeDecorated = toBeDecorated;
	}
	
	@Override
	public Iterator<Alternative<T>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
