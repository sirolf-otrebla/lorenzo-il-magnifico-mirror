package it.polimi.ingsw.ps05.server.net;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.LimConnection;

/**This class is designed only for test purposes
 * Created by Alberto on 06/07/2017.
 */
public class FakeConnection extends LimConnection {
   /** DO NOTHING
   */


   public FakeConnection(){
       //DO NOTHING

   }
    @Override
    public void listen() {
        //DO NOTHING
    }

    @Override
    public void send(NetMessage mess) {
        System.out.println(mess.toString());
    }

    @Override
    public void flushInBuff() {

    }

    @Override
    public NetMessage getInputMessage() {
        return null;
    }

    @Override
    public void flushInBuf() {

    }
}
