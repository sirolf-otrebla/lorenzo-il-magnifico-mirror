package it.polimi.ingsw.ps05.net.server.rmi;

import it.polimi.ingsw.ps05.model.ActionSpace;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.LeaderCard;
import it.polimi.ingsw.ps05.model.Player;

import java.rmi.Remote;

/**
 * Created by Alberto on 16/06/2017.
 */
public interface RemoteMessaqeInterface extends Remote {


    void discardLeader(LeaderCard leaderCard);

    void activateLeader(LeaderCard leaderCard);

    void executeAction(Familiar familiar, ActionSpace actionSpace);

}
