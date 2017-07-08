package it.polimi.ingsw.ps05.client.view;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.client.view.gui.*;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Alberto on 01/07/2017.
 */
public class SetUpGuiVisitor implements ViewVisitorInterface {

    private

    GUIMain gui;

    public SetUpGuiVisitor(GUIMain gui){
        this.gui = gui;
    }

    @Override
    public void visit(GameStatus status) {
        this.gui.getPlayer().setPlayerColor(status.getThisPlayer().getColor());
        Player activePlayer = status.getPlayerHashMap().get(status.getActivePlayerId());
        status.getPlayerHashMap().remove(status.getActivePlayerId());
        ArrayList<Player> playerArrayList = new ArrayList< >(status.getPlayerHashMap().values());
        this.gui.setInitValues(activePlayer.getColor(), status.getPlayerHashMap().size(), 120); // settare timeout
        status.getGameBoard().acceptVisitor(this);
        for (Player p :
             status.getPlayerHashMap().values()) {
            p.acceptVisitor(this);
        }
        HashMap<ColorEnumeration, int[]> cardIdHashMap = new HashMap<>();

        for (ColorEnumeration c: this.gui.getTowerColorArray()) {
            Tower tower = status.getGameBoard().getTowerList().get(c);
            TowerTileInterface[] towerTileInterfaces = (TowerTileInterface[]) tower.getTiles().values().toArray();
            int[] cards = new int[towerTileInterfaces.length];
            for (TowerTileInterface t: towerTileInterfaces) {
                Integer dice = t.getDiceRequired().getValue();
                cards[(dice -1)/2] = t.getCard().getReferenceID();
            }
            cardIdHashMap.put(c, cards);
        }
        this.gui.insertCards(cardIdHashMap.get(ColorEnumeration.Green), cardIdHashMap.get(ColorEnumeration.Blue),
                cardIdHashMap.get(ColorEnumeration.Yellow), cardIdHashMap.get(ColorEnumeration.Violet));


    }

    @Override
    public void visit(Board board) {
        for (Tower t:  board.getTowerList().values()) t.acceptVisitor(this);
        for (ActionSpace actionSpace : board.getActSpacesMap().values()) actionSpace.acceptVisitor(this);
        for (int i = 0; i < gui.getExcomWidgets().length; i++)
            gui.getExcomWidgets()[i].setReferenceId(board.getExcomCards().get(i).getReferenceID());
        for (int i = 0; i < gui.getFaithPath().length; i++)
            gui.getFaithPath()[i] = (board.getFaithPath().get(i).getValue());
        for (int i = 0; i < gui.getMilitaryPath().length; i++)
            gui.getMilitaryPath()[i] = (board.getMilitaryPath().get(i).getValue());
        for (int i = 0; i < gui.getGreenCardsConversion().length; i++)
            gui.getGreenCardsConversion()[i] = (board.getGreenCardsConversion().get(i).getValue());
        for (int i = 0; i < gui.getBlueCardsConversion().length; i++)
            gui.getBlueCardsConversion()[i] = (board.getBlueCardsConversion().get(i).getValue());

    }

    @Override
    public void visit(Tile tile) {
            // DO  NOTHING
    }

    @Override
    public void visit(MarketSpace marketSpace) {
        
        //TODO CONTROLLARE QUESTA SOLUZIONE
        MarketSpaceWidget[] marketSpaceWidgets = gui.getMarketSpaceWidgets();
        MarketSpaceWidget thisWidget = marketSpaceWidgets[marketSpace.getMarketSpaceTypeID()];
        thisWidget.setReferenceId(marketSpace.getId());
        thisWidget.setResult(marketSpace.getEffects().toString());
    }

    @Override
    public void visit(CouncilSpace councilSpace) {
        Integer id = councilSpace.getId();
        gui.getCouncilSpaceWidget().setReferenceId(id);
    }

    @Override
    public void visit(ProductionSpace productionSpace) {
        Integer id = productionSpace.getId();
        gui.getProductionSpace().setReferenceId(id);

    }

    @Override
    public void visit(HarvestingSpace harvestingSpace) {
        Integer id = harvestingSpace.getId();
        gui.getHarvestingSpace().setReferenceId(id);
    }

    @Override
    public void visit(Player player) {
        if (this.gui.getPlayer().getPlayerColor() == player.getColor()){
            int i = 0;
            for (Familiar f: player.getFamilyList()) {
                this.gui.getPlayer().getFamiliarWidgetList()[i] = new FamiliarWidget(player.getColor(), f.getColor());
                i++;
            }
        }
        else {

            for (int j = 0; j < this.gui.getFamiliarWidgetLists().length; j++){
                if (this.gui.getFamiliarWidgetLists()[j][0] == null){
                    int i = 0;
                    for (Familiar f: player.getFamilyList()) {
                        this.gui.getFamiliarWidgetLists()[j][i] = new FamiliarWidget(player.getColor(), f.getColor());
                        i++;
                    }
                    break;
                }

            }
        }

        //TODO
    }

    @Override
    public void visit(Tower tower) {

        /* array di ColorEnumeration e relativa HashMap sono diventate inutili, si può accedere alla tore del colore desiderato tramite ColorEnumeration

        ColorEnumeration[] colorEnumerations = {
                ColorEnumeration.Green,
                ColorEnumeration.Blue,
                ColorEnumeration.Yellow,
                ColorEnumeration.Violet
        };
        HashMap<ColorEnumeration, Integer> colorIndexHashMap = new HashMap<>();
        for (int i = 0; i < colorEnumerations.length ; i++) colorIndexHashMap.put(colorEnumerations[i], i);

        */

        Collection<TowerTileInterface> tiles = tower.getTiles().values();
        TowerTileWidget[] towerTileWidgets =  gui.getTowerTileWidgetList(tower.getColor()); // aggiornato
        for (TowerTileWidget tileWidget :towerTileWidgets){
            for(TowerTileInterface tile : tiles){
                if(tile.getDiceRequired().getValue() == tileWidget.getMinDie())
                    tileWidget.setReferenceId(tile.getId());
            }
        }
    }
}