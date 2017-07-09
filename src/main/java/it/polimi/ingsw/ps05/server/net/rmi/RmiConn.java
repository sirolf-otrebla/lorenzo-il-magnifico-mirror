package it.polimi.ingsw.ps05.server.net.rmi;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.LimConnection;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Created by Alberto on 08/06/2017.
 */
public class RmiConn extends LimConnection {

	public RmiConn() {
		try {
			//this.rmiInterface = new MessageBuilder();
			//RemoteMessaqeInterface rmiInter = (RemoteMessaqeInterface) UnicastRemoteObject.exportObject(this.rmiInterface, this.RMI_PORT);
			//this.reg = LocateRegistry.getRegistry();
			//this.reg.bind("ServerInterface", rmiInterface);
			MessageBuilder builder = MessageBuilder.getInstance();
			Naming.rebind("//localhost/server", builder);

		} catch (RemoteException ex){
			//TODO
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void listen() {
		
	}

	@Override
	public void send(NetMessage mess) {
		try {
			MessageBuilder.getInstance().sendMessage(mess);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void flushInBuff() {
		// TODO Auto-generated method stub

	}

	@Override
	public NetMessage getInputMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flushInBuf() {

	}
}
