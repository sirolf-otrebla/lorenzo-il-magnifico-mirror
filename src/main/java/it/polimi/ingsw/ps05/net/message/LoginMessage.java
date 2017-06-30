package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class LoginMessage implements LobbyMessage {
	
	private String username;
	private String password;
	
	public LoginMessage(String username, String password){
		this.username = username;
		this.password = password;
	}

	@Override
	public void acceptVisitor(NetMessageVisitor vi) throws Exception {
		vi.visit(this);

	}

}
