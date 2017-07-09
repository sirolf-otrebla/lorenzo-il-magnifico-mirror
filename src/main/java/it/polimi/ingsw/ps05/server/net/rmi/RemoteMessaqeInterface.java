package it.polimi.ingsw.ps05.server.net.rmi;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;

import java.rmi.Remote;

/**
 * Created by Alberto on 16/06/2017.
 */
public interface RemoteMessaqeInterface extends Remote {
    
    public NetMessage sendMessage(NetMessage message);

}
