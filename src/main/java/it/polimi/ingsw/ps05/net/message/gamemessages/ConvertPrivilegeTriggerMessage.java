package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 07/07/2017.
 */
public class ConvertPrivilegeTriggerMessage implements GameResponseMessage {

    private ArrayList<Resource> conversionList;
    private Integer privilegeNum;
    public ConvertPrivilegeTriggerMessage(ArrayList<Resource> conversionList, Integer privilegeNum){
        this.conversionList = conversionList;
        this.privilegeNum = privilegeNum;
    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameResponseMessageVisitor vi) {

    }

    public ArrayList<Resource> getConversionList() {
        return conversionList;
    }

    public Integer getPrivilegeNum() {
        return privilegeNum;
    }
}
