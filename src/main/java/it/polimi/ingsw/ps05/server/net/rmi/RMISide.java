package it.polimi.ingsw.ps05.server.net.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMISide extends UnicastRemoteObject implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5518362063820604136L;

	public RMISide() throws RemoteException{

	}

	@Override
	public void run() {
		try 
		{
			RmiConn server = new RmiConn();
			Naming.rebind("//localhost", this);
			server.listen();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
