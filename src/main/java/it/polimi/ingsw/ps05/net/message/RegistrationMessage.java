package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class RegistrationMessage implements LobbyMessage {
	
	private String username;
	private String password;
	
	public RegistrationMessage(String username, String password){
		this.username = username;
		this.password = password;
	}

	@Override
	public void acceptVisitor(NetMessageVisitor vi) throws Exception {
		vi.visit(this);
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}

}
