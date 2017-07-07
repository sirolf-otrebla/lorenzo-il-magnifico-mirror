package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.message.gamemessages.BonusActionTriggerMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.ConvertPrivilegeTriggerMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameUpdateMessage;

import java.util.ArrayList;

/**
 * Created by Alberto on 07/07/2017.
 */
public class GameResponseMessageVisitor {

	public void visit(GameUpdateMessage msg){
		System.out.println("Primo visitor, GameUpdateMsg");
		Client.getInstance().setGameStatus(msg.getGameStatus());
		System.out.println("Settato game status? " + Client.getInstance().getGameStatus());
		System.out.println("C'è player? " +  Client.getInstance().getGameStatus() == null ? "null" :  Client.getInstance().getGameStatus().getThisPlayer());
		System.out.println("Game: " + Client.getInstance().isInGame());
		
		for (Player p : msg.getGameStatus().getPlayerHashMap().values()){
			System.out.println("ResponeVisitor: " + p.getUsername());
			System.out.println("ResponeVisitorBlu: " + p.getBlueCardList());
			System.out.println("ResponeVisitorVerde: " + p.getGreenCardList());
			System.out.println("ResponeVisitorGiallo: " + p.getYellowCardList());
			System.out.println("ResponeVisitorViola: " + p.getVioletCardList());
			for (Resource r : p.getResourceList()){
				System.out.println("ResponeVisitor" + r.getID() + " " + r.getValue());
			}
		}
		if (Client.getInstance().isInGame()){
			System.out.println("Update");
			ViewAdapter.getInstance().updateView(msg.getGameStatus());
		}else {
			System.out.println("Start");
			ViewAdapter.getInstance().startGameView(msg.getGameStatus());
		}

	}


	public void visit(BonusActionTriggerMessage msg){
		this.visit(msg.getGameUpdateMessage());
		if (Client.getInstance().isInGame()){
			ViewAdapter.getInstance().setGhostFamiliarForAction(msg.getGhostFamiliar());
		}
	}

    public void visit(ConvertPrivilegeTriggerMessage msg){
        ArrayList<Resource> resources = msg.getConversionList();
        Integer privileges = msg.getPrivilegeNum();
    }
}
