package it.polimi.ingsw.ps05.server.net.rmi;

import it.polimi.ingsw.ps05.net.message.NetMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alberto on 16/06/2017.
 */
public class MessageBuilder extends UnicastRemoteObject implements RemoteMessaqeInterface {
	
	private static MessageBuilder instance = null;
	
    private MessageBuilder() throws RemoteException {
		super();
	}
    
    public static MessageBuilder getInstance() throws RemoteException{
    	if (instance == null){
    		instance = new MessageBuilder();
    	}
    	return instance;
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = -6482868064446750712L;

	@Override
	public NetMessage sendMessage(NetMessage message) {
		// TODO Auto-generated method stub
		return null;
	}
}
