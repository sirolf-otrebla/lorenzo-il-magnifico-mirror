package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.LoggedMessage;
import it.polimi.ingsw.ps05.net.message.LoginMessage;
import it.polimi.ingsw.ps05.net.message.RegisteredMessage;
import it.polimi.ingsw.ps05.net.message.RegistrationMessage;
import it.polimi.ingsw.ps05.server.database.Database;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

/** this class is designed to managing player Login and Registration on the server's database
 *  it implements a visitor where the Authentication Message is passed as parameter, recognized and
 *  treated differently, depending on the Authentication message Class which can be {@link RegistrationMessage}
 *  or {@link LoginMessage}
 */
public class AuthListener {

    private PlayerClient client;

    /** this is the only constructor of this class. it requires a reference of the {@link PlayerClient}
     *  that is trying to authenticate.
     * @param client    this is the Client who sent the {@link it.polimi.ingsw.ps05.net.message.AuthMessage}
     */
    public AuthListener(PlayerClient client){
        this.client = client;
    }

    /** this method takes care of an incoming {@link LoginMessage},
     * resulting on a log in or a connection refusal.
     *
     * @param msg this is the message sent by the client
     */
    public void visit(LoginMessage msg){

        if( Server.getInstance().getUserDatabase().checkUser(
                msg.getUsername(), msg.getPassword())){
            client.setUsername(msg.getUsername());
            System.out.println("inserisco nuovo client");
            int id = Database.getInstance().getIdForUsername(msg.getUsername());
            if (id >= 0){
            	this.client.setIdAfterLogin(id);
            }
            client.sendMessage(new LoggedMessage(LoggedMessage.STATUS_LOGGED));
            Server.getInstance().putNewClient(client);
            this.client.setLogged(true);
        } else {
            this.client.setLogged(false);
            client.sendMessage(new LoggedMessage(LoggedMessage.STATUS_FAILED_LOGIN));
        }
    }

    /** this method takes care of an incoming {@link RegistrationMessage},
     * resulting in a registation and log in or a connection refusal.
     *
     * @param msg this is the message sent by the client
     */
    public void visit(RegistrationMessage msg){
        if (Server.getInstance().getUserDatabase().registerNewUser(
                msg.getUsername(), msg.getPassword())){
            client.setUsername(msg.getUsername());
            System.out.println("inserisco nuovo client");
            int id = Database.getInstance().getIdForUsername(msg.getUsername());
            if (id >= 0){
            	this.client.setIdAfterLogin(id);
            }
            Server.getInstance().putNewClient(client);
            this.client.setLogged(true);
            client.sendMessage(new RegisteredMessage(RegisteredMessage.STATUS_REGISTERED));
            client.setUsername(msg.getUsername());
        }else {
            this.client.setLogged(false);
            client.sendMessage(new RegisteredMessage(RegisteredMessage.STATUS_FAILED_REGIST));
        }
    }


}
