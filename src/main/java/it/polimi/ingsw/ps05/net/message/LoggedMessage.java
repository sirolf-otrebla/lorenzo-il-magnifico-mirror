package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import sun.nio.ch.Net;

/**
 * Created by Alberto on 02/07/2017.
 */
public class LoggedMessage implements NetMessage {
    public static final int STATUS_LOGGED = 0x01;
    public static final int STATUS_FAILED_LOGIN = 0;


    private int status;


    public LoggedMessage(int status) {
        this.status = status;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }
}
