package it.polimi.ingsw.ps05.client.view.gui;

/**
 * Created by miotto on 27/06/17.
 */
public class TowerTileWidget extends ActionSpaceWidget {

    private CardWidget associatedCard;

    public TowerTileWidget() {
        super();
    }

    public CardWidget getAssociatedCard() {
        return associatedCard;
    }

    public void setAssociatedCard(CardWidget associatedCard) {
        this.associatedCard = associatedCard;
    }
}
