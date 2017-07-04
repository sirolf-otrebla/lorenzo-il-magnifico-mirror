package it.polimi.ingsw.ps05.client.view.gui;

/**
 * Created by miotto on 27/06/17.
 */
public class MarketSpaceWidget extends SingleOccupantActionSpaceWidget {

    private String result;


    public MarketSpaceWidget(int minimumDie) {
        super(minimumDie);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
