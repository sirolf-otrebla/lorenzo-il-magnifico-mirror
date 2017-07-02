package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.LoggedMessage;
import it.polimi.ingsw.ps05.net.message.LoginMessage;
import it.polimi.ingsw.ps05.net.message.RegisteredMessage;
import it.polimi.ingsw.ps05.net.message.RegistrationMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

/**
 * Created by Alberto on 02/07/2017.
 */
public class AuthListener {

    private PlayerClient client;

    public AuthListener(PlayerClient client){


    }
    public void visit(LoginMessage msg){

        if( Server.getInstance().getUserDatabase().checkUser(
                msg.getUsername(), msg.getPassword())){
            Server.getInstance().putNewClient(client);
            this.client.setLogged(true);
            client.sendMessage(new LoggedMessage(LoggedMessage.STATUS_LOGGED));
            Server.getInstance().getServerLobby().addToLobby(client);

        } else {
            this.client.setLogged(false);
            client.sendMessage(new LoggedMessage(LoggedMessage.STATUS_FAILED_LOGIN));
        }
    }

    public void visit(RegistrationMessage msg){
        if (Server.getInstance().getUserDatabase().registerNewUser(
                msg.getUsername(), msg.getPassword())){
            Server.getInstance().putNewClient(client);
            this.client.setLogged(true);
            client.sendMessage(new RegisteredMessage(RegisteredMessage.STATUS_REGISTERED));
            Server.getInstance().getServerLobby().addToLobby(client);
        }else {
            this.client.setLogged(false);
            client.sendMessage(new RegisteredMessage(RegisteredMessage.STATUS_FAILED_REGIST));
        }
    }


}
