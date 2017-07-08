package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.message.gamemessages.ConvertPrivilegeTriggerMessage;

import java.util.ArrayList;

/**
 * Created by Alberto on 07/07/2017.
 */
public class ClaimPrivilegeStrategy implements EndActionStrategy {

    private Integer privilegeNumber;

    public ClaimPrivilegeStrategy(Integer privilegeNumber) {
        this.privilegeNumber = privilegeNumber;
    }

    @Override
    public void execute(EndActionStrategyContainer container) {
        ArrayList<ArrayList<Resource>> resAlternatives =
                container.getGame().getPrivilegeConvResAlternatives();
        ConvertPrivilegeTriggerMessage privilegeTriggerMessage =
                new ConvertPrivilegeTriggerMessage(container.getGame().getPrivilegeConvResAlternatives(), privilegeNumber);
        Integer playerID = container.getGame().getActivePlayer().getPlayerID();
        container.getGame().getPlayerClient(playerID).sendMessage(privilegeTriggerMessage);
    }
}
