package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.LoggedMessage;
import it.polimi.ingsw.ps05.net.message.RegisteredMessage;

import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 03/07/2017.
 */
public class AuthResponseVisitor  {

	public AuthResponseVisitor(Semaphore semaphore){

	}

	public void visit(LoggedMessage msg){
		System.out.println("Logged message visited");
		Client.getInstance().getLoginController().getSemaphore().release();
	}

	public void visit(RegisteredMessage msg){
		System.out.println("Registered message visited");
		Client.getInstance().getLoginController().getSemaphore().release();


	}
}
