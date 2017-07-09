package it.polimi.ingsw.ps05.client.net.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.rmi.RemoteMessaqeInterface;

public class RmiConnection {
	
	RemoteMessaqeInterface rmi;
	
	public RmiConnection(){
		try {
			rmi = (RemoteMessaqeInterface)Naming.lookup("//localhost/server");
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(NetMessage msg){
		rmi.sendMessage(msg);
	}
}
