package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.*;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Alberto on 30/06/2017.
 */
public class UpdateViewVisitor implements ViewVisitorInterface{
    private HashMap<Integer, ActionSpaceWidget> actionSpaceWidgetHashMap;
    private GUIMain guiMain;
    private Client client;

    public UpdateViewVisitor(Client client, GUIMain main){
        for (ActionSpaceWidget widget:
             guiMain.getMarketSpaceWidgets()) {
            actionSpaceWidgetHashMap.put(widget.getId(), widget);
        }
        actionSpaceWidgetHashMap.put(guiMain.getHarvestingSpace().getId(), guiMain.getHarvestingSpace());
        actionSpaceWidgetHashMap.put(guiMain.getProductionSpace().getId(), guiMain.getProductionSpace());
        for (TowerTileWidget[] tileWidgetArray: guiMain.getTowerTileWidgetList())
            for (TowerTileWidget tileWidget: tileWidgetArray)
                actionSpaceWidgetHashMap.put(tileWidget.getId(), tileWidget);
    }

    @Override
    public void visit(GameStatus status){


    }

    @Override
    public void visit(Board board){
        for (ActionSpace actionSpace:
                board.getActSpacesMap().values()) {
            actionSpace.acceptVisitor(this);
        }
        for (Tower tower : board.getTowerList().values()){
            tower.acceptVisitor(this);
        }


    }

    @Override
    public void visit(Tile tile){
        TowerTileWidget widget = (TowerTileWidget) actionSpaceWidgetHashMap.get(tile.getId());
        if (tile.isOccupied()){
            widget.setOccupied(true);
            ColorEnumeration playerID = tile.getOccupant().getRelatedPlayerColor();
            widget.setOccupantPlayerColor(playerID);
            //TODO ESISTE UN METODO MIGLIORE PER CONTROLLARE LA LEGALITÀ DI UNA AZIONE, PER ORA TENIAMO QUESTO
            Collection<Familiar> family = Client.getInstance().getGameStatus().getThisPlayer().getFamilyMap().values();
            for (Familiar f: family )
                if ((new Action(f, tile).isLegal())) {
                    widget.setLegal(true);
                    widget.getLegalFamilyMemberList().add(f.getColor());
                }
            if(widget.getAssociatedCard().getReferenceId() != tile.getCard().getReferenceID())
                widget.setAssociatedCard(new CardOnBoardWidget(tile.getCard().getReferenceID()));
            // altro da aggiungere?
        }
    }

    @Override
    public void visit(MarketSpace marketSpace){
        MarketSpaceWidget widget = (MarketSpaceWidget) actionSpaceWidgetHashMap.get(marketSpace.getId());
        if (marketSpace.isOccupied()){
            widget.setOccupied(true);
            widget.setOccupantPlayerColor(marketSpace.getOccupant().getRelatedPlayerColor());

        }

    }

    @Override
    public void visit(CouncilSpace councilSpace){
        /* TODO CouncilSpaceWidget estende MultipleSpaceWidget, non più ActionSpaceWidget
        CouncilSpaceWidget widget = (CouncilSpaceWidget) actionSpaceWidgetHashMap.get(councilSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(councilSpace);
        widget.setOccupingFamiliarList(widgetList);
        */

    }

    @Override
    public void visit(ProductionSpace productionSpace){
        ProductionSpaceWidget widget = (ProductionSpaceWidget) actionSpaceWidgetHashMap.get(productionSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(productionSpace);
        if (widgetList.size() > 1) widget.setMorethanZeroOccupants(true);
        widget.setOccupingFamiliarList(widgetList);

        //TODO ??? ALTRO DA AGGIUNGERE ???

    }

    @Override
    public void visit(HarvestingSpace harvestingSpace){

        ProductionSpaceWidget widget = (ProductionSpaceWidget) actionSpaceWidgetHashMap.get(harvestingSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(harvestingSpace);
        if (widgetList.size() > 1) widget.setMorethanZeroOccupants(true);
        widget.setOccupingFamiliarList(widgetList);

    }

    @Override
    public void visit(Player player){


    }

    @Override
    public void visit(Tower tower) {

    }

    private  ArrayList<Pair<ColorEnumeration, ColorEnumeration>> copyModelOccupantList(MultipleOccupantsActionSpace actionSpace){
        ArrayList<Familiar> occupantList = actionSpace.getOccupantList();
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = new ArrayList<>();
        for (Familiar familiar: occupantList) {
            widgetList.add(new Pair<>(familiar.getRelatedPlayer().getColor(), familiar.getColor()));
        }

        return widgetList;

    }

}

