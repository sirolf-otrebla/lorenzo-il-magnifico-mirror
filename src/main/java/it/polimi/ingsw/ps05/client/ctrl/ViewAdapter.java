package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.LimView;
import it.polimi.ingsw.ps05.model.exceptions.IllegalActionException;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ViewAdapter {
    public static final String GUI_TYPE = "gui";
    public static final String CLI_TYPE = "cli";

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
        return  new ViewAdapter(type);
    }

    private void setUpGui(){
        //TODO
    }

    private void setUpCli(){
        //TODO
    }

    private void updateView(){


    }



}
