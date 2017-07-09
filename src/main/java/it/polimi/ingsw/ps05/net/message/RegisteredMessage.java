package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.AuthResponseVisitor;
import it.polimi.ingsw.ps05.client.net.ClientMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 02/07/2017.
 */
public class RegisteredMessage implements AuthResponseMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7653459638509932286L;
	public static final int STATUS_REGISTERED = 0x01;
    public static final int STATUS_FAILED_REGIST= 0;


    private int status;


    public RegisteredMessage(int status) {
        this.setStatus(status);
    }


    @Override
    public void acceptVisitor(ClientMessageVisitor vi){
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(AuthResponseVisitor vi) {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
}
