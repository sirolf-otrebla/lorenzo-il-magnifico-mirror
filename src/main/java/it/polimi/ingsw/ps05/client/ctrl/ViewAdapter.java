package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.LimView;
import it.polimi.ingsw.ps05.client.view.cli.CLIMain;
import it.polimi.ingsw.ps05.client.view.gui.GUIMain;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.LeaderDraftChoiceMessage;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.net.message.SetupDoneMessage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ViewAdapter {
    public static final String GUI_TYPE = "gui";
    public static final String CLI_TYPE = "cli";

    private Thread CliThread;
    private String viewType;

    private LimView view;

    private static ViewAdapter instance = null;

    private ViewAdapter(String type){
        if( type.toLowerCase() == this.GUI_TYPE){
            viewType = type.toLowerCase();
            // TODO ISTANZIARE GUI
        }

        else if(type.toLowerCase() == ViewAdapter.CLI_TYPE) {
            viewType = type.toLowerCase();

            //TODO ISTANZIARE CLI
        }
    }

    public static ViewAdapter getInstance() throws NullPointerException{
        if (instance != null) return instance;
        else throw new NullPointerException();
    }
    public static ViewAdapter createInstance(String type) throws IllegalAccessException{
        if (instance != null) throw new IllegalAccessException();
        ViewAdapter.instance = new ViewAdapter(type);
        return  instance;
    }

    private void setUpGui(GameStatus gameStatus){
        GUIMain gui = new GUIMain();

        UpdateViewVisitor guiVisitor = new  UpdateViewVisitor(Client.getInstance(), gui);

        //TODO
    }

    public void startGameView(GameStatus status){
        if (this.viewType == this.GUI_TYPE) {

        } else {

            CLIMain cli = (CLIMain) this.view;
            
            if(CliThread != null && CliThread.isDaemon()){

            }
            else {
                CliThread = new Thread(cli);
                CliThread.setDaemon(true);
                CliThread.start();
            }
            cli.setActivePlayer(status.getPlayerHashMap().get(status.getActivePlayerId()));
            cli.updateGame(status);
        }
    }

    public void updateView(GameStatus status){
        CLIMain cli = (CLIMain) this.view;
        cli.updateGame(status);
        cli.setActivePlayer(status.getPlayerHashMap().get(status.getActivePlayerId()));


    }

    public void setUpInterface(GameSetupMessage msg){
        if (this.viewType == this.GUI_TYPE) {

        } else {
            ArrayList<Player> playerArrayList = new ArrayList<>(msg.getStatus().getPlayerHashMap().values());
            System.out.println("(setUpInterface) lunghezza playerArrayList: " + playerArrayList.size() );
            this.view = new CLIMain(msg.getStatus().getGameBoard(),
                    msg.getStatus().getThisPlayer(), playerArrayList);
            System.out.println("Sending setup done message");
            SetupDoneMessage setupDoneMessage = new SetupDoneMessage();
            Client.getInstance().sendToServer(setupDoneMessage);
        }
    }

    public void updateDraft(ArrayList<Integer> draftIDs){
        if (this.viewType == this.GUI_TYPE) {
            // TODO

        } else {
            CLIMain cliView = (CLIMain) this.view;
            Integer cardChoosen = null;
            try {
                cardChoosen = cliView.getCardForDraft(draftIDs);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LeaderDraftChoiceMessage responseMessage =
                    new LeaderDraftChoiceMessage(cardChoosen);
            Client.getInstance().sendToServer(responseMessage);
        }
    }
    
    public ArrayList<ActionResult> showPrivilegeConversion(ArrayList<ArrayList<ActionResult>> list, int resToChose){
    	if (this.viewType == this.GUI_TYPE) {
            // TODO

        } else {
            CLIMain cliView = (CLIMain) this.view;
            //TODO
        }
    	return null;
    }

    public void startDraft(ArrayList<Integer> draftIDs){
        if (this.viewType == this.GUI_TYPE) {
            // TODO

        } else {
        	System.out.println("AAAAAAA");
            CLIMain cliView = (CLIMain) this.view;
            try {
                CliThread = new Thread(cliView);
                CliThread.setDaemon(true);
                CliThread.start();
                Integer cardChoosen = cliView.getCardForDraft(draftIDs);
                LeaderDraftChoiceMessage responseMessage =
                        new LeaderDraftChoiceMessage(cardChoosen);
                Client.getInstance().sendToServer(responseMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setGhostFamiliarForAction(Familiar f){
    	if (this.viewType == this.GUI_TYPE) {
            // TODO

        } else {
            CLIMain cliView = (CLIMain) this.view;
            cliView.actionWithGhostFamiliar(f);
        }
    }


}
