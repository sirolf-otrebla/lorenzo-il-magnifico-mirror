package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.ActionSpaceWidget;
import it.polimi.ingsw.ps05.client.view.gui.GUIMain;
import it.polimi.ingsw.ps05.client.view.gui.MarketSpaceWidget;
import it.polimi.ingsw.ps05.client.view.gui.TowerTileWidget;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Alberto on 01/07/2017.
 */
public class SetUpGuiVisitor implements  ViewVisitorInterface {

    GUIMain gui;
    public SetUpGuiVisitor(GUIMain gui){
        this.gui = gui;
    }

    @Override
    public void visit(GameStatus status) {
        status.getGameBoard().acceptVisitor(this);
        for (Player p :
             status.getPlayerHashMap().values()) {
            p.acceptVisitor(this);
        }

    }

    @Override
    public void visit(Board board) {
        for (Tower t:  board.getTowerList().values()) t.acceptVisitor(this);
        for (ActionSpace actionSpace : board.getActSpacesMap().values()) actionSpace.acceptVisitor(this);
        for (int i = 0; i < gui.getExcomWidgets().length; i++)
            gui.getExcomWidgets()[i].setReferenceID(board.getExcomCards().get(i).getReferenceID());
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
        thisWidget.setId(marketSpace.getId());
        thisWidget.setResult(marketSpace.getEffects().toString());
    }

    @Override
    public void visit(CouncilSpace councilSpace) {
        Integer id = councilSpace.getId();
        gui.getCouncilSpaceWidget().setId(id);
    }

    @Override
    public void visit(ProductionSpace productionSpace) {
        Integer id = productionSpace.getId();
        gui.getProductionSpace().setId(id);

    }

    @Override
    public void visit(HarvestingSpace harvestingSpace) {
        Integer id = harvestingSpace.getId();
        gui.getHarvestingSpace().setId(id);
    }

    @Override
    public void visit(Player player) {

        //TODO
    }

    @Override
    public void visit(Tower tower) {
        ColorEnumeration[] colorEnumerations = {
                ColorEnumeration.Green,
                ColorEnumeration.Blue,
                ColorEnumeration.Yellow,
                ColorEnumeration.Violet
        };
        HashMap<ColorEnumeration, Integer> colorIndexHashMap = new HashMap<>();
        for (int i = 0; i < colorEnumerations.length ; i++) colorIndexHashMap.put(colorEnumerations[i], i);

        Collection<TowerTileInterface> tiles = tower.getTiles().values();
        TowerTileWidget[] towerTileWidgets =  gui.getTowerTileWidgetList()[colorIndexHashMap.get(tower.getColor())];
        for (TowerTileWidget tileWidget :towerTileWidgets){
            for(TowerTileInterface tile : tiles){
                if(tile.getDiceRequired().getValue() == tileWidget.getMinDie())
                    tileWidget.setId(tile.getId());
            }
        }
    }
}
