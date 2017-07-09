package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.AuthListener;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class LoginMessage implements AuthMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7270402833077537316L;
	private String username;
	private String password;
	
	public LoginMessage(String username, String password){
		this.username = username;
		this.password = password;
	}

	@Override
	public void acceptVisitor(NetMessageVisitor vi) {
		vi.visit(this);
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}

	@Override
	public void acceptVisitor(AuthListener vi) {
		vi.visit(this);
	}
}
