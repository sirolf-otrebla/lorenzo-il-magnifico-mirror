package it.polimi.ingsw.ps05.server.net.rmi;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.LimConnection;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Created by Alberto on 08/06/2017.
 */
public class RmiConn extends LimConnection {

	public static final int RMI_PORT = 0;
	private MessageBuilder rmiInterface;
	private Registry reg;

	public RmiConn() {
		try {
			this.rmiInterface = new MessageBuilder();
			RemoteMessaqeInterface rmiInter = (RemoteMessaqeInterface) UnicastRemoteObject.exportObject(this.rmiInterface, this.RMI_PORT);
			this.reg = LocateRegistry.getRegistry();
			this.reg.bind("ServerInterface", rmiInterface);

		} catch (RemoteException ex){
			//TODO
		} catch (AlreadyBoundException ex){
			//TODO
		}
	}

    @Override
    public void listen() {

    }

	@Override
	public void send(NetMessage mess) {
		// TODO Auto-generated method stub
		
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
