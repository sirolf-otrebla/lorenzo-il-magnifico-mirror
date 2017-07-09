package it.polimi.ingsw.ps05.client.view;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.client.view.gui.*;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;
import javafx.util.Pair;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Alberto on 30/06/2017.
 */
public class UpdateViewVisitor implements ViewVisitorInterface, Runnable {
    private HashMap<Integer, ActionSpaceWidgetInterface> actionSpaceWidgetHashMap;
    private GUIMain gui;
    private Client client;

    public UpdateViewVisitor(Client client, GUIMain main){
        for (SingleOccupantActionSpaceWidget widget:
             gui.getMarketSpaceWidgets()) {
            actionSpaceWidgetHashMap.put(widget.getReferenceId(), widget);
        }
        actionSpaceWidgetHashMap.put(gui.getHarvestingSpace().getReferenceId(), gui.getHarvestingSpace());
        actionSpaceWidgetHashMap.put(gui.getProductionSpace().getReferenceId(), gui.getProductionSpace());

        for (ColorEnumeration color: playerColorArray)
            for (TowerTileWidget tileWidget: gui.getTowerTileWidgetList(color))
                actionSpaceWidgetHashMap.put(tileWidget.getReferenceId(), tileWidget);

        /* CICLO AGGIORNATO dopo cambiamenti di memorizzazione delle TowerTileWidgetList (utilizzo di hashmap)
        for (TowerTileWidget[] tileWidgetArray: gui.getTowerTileWidgetList())
            for (TowerTileWidget tileWidget: tileWidgetArray)
                actionSpaceWidgetHashMap.put(tileWidget.getReferenceId(), tileWidget);
        */
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

        HashMap<ColorEnumeration, int[]> cardIdHashMap = new HashMap<>();

        for (ColorEnumeration c: this.gui.getTowerColorArray()) {
            Tower tower = board.getTowerList().get(c);
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
    public void visit(TowerTileInterface tile) {
        TowerTileWidget widget = (TowerTileWidget) actionSpaceWidgetHashMap.get(tile.getId());
        if (tile.isOccupied()) {
            widget.setOccupied(true);
            ColorEnumeration playerID = tile.getOccupant().getRelatedPlayerColor();
            widget.setOccupantPlayerColor(playerID);
            //TODO ESISTE UN METODO MIGLIORE PER CONTROLLARE LA LEGALITÀ DI UNA AZIONE, PER ORA TENIAMO QUESTO
            Collection<Familiar> family = Client.getInstance().getGameStatus().getThisPlayer().getFamilyMap().values();
            for (Familiar f : family)
                if ((new Action(f, tile).isLegal())) {
                    widget.setLegal(true);
                    widget.getLegalFamilyMemberList().add(f.getColor());
                }
            if (widget.getAssociatedCard().getReferenceId() != tile.getCard().getReferenceID())
                widget.setAssociatedCard(new TowerCardWidget(tile.getCard().getReferenceID()));
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
        CouncilSpaceWidget widget = (CouncilSpaceWidget) actionSpaceWidgetHashMap.get(councilSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(councilSpace);
        widget.setOccupingFamiliarList(widgetList);
        widget.repaint();


    }

    @Override
    public void visit(ProductionSpace productionSpace){
        ProductionSpaceWidget widget = (ProductionSpaceWidget) actionSpaceWidgetHashMap.get(productionSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(productionSpace);
        if (widgetList.size() > 1) widget.setMorethanZeroOccupants(true);
        widget.setOccupingFamiliarList(widgetList);
        widget.repaint();

        //TODO ??? ALTRO DA AGGIUNGERE ???

    }

    @Override
    public void visit(HarvestingSpace harvestingSpace){

        HarvestingSpaceWidget widget = (HarvestingSpaceWidget) actionSpaceWidgetHashMap.get(harvestingSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(harvestingSpace);
        if (widgetList.size() > 1) widget.setMorethanZeroOccupants(true);
        widget.setOccupingFamiliarList(widgetList);
        widget.repaint();


    }

    @Override
    public void visit(Player player) {
        Integer[] pointsVector = new Integer[3];
        pointsVector[0] = player.getResource(FaithResource.id).getValue();
        pointsVector[1] = player.getResource(MilitaryResource.id).getValue();
        pointsVector[2] = player.getResource(VictoryResource.ID).getValue();
        this.gui.updatePlayerPoints(player.getColor(), pointsVector);
        if (player.getColor() == this.gui.getPlayer().getPlayerColor()) {
            for (Resource r : player.getResourceList()) {
                if (r.getID() == GoldResource.id || r.getID() == ServantResource.id ||
                        r.getID() == WoodResource.id || r.getID() == StoneResource.id) {
                    this.gui.getPlayer().getResourceWidget().setResource(r.getID(), r.getValue());
                    this.gui.getPlayer().getResourceWidget().repaint();
                }
            }
        } else {
            for (OpponentWidget opponentWidget : this.gui.getOpponentsArray()) {
                if (opponentWidget.getOpponentColor() == player.getColor()) {
                    for (Resource r : player.getResourceList()) {
                        if (r.getID() == GoldResource.id || r.getID() == ServantResource.id ||
                                r.getID() == WoodResource.id || r.getID() == StoneResource.id) {
                            opponentWidget.getResourceWidget().setResource(r.getID(), r.getValue());
                            opponentWidget.getResourceWidget().repaint();

                        }
                    }
                }


            }
        /*if (player.getColor() == this.gui.getPlayer().getPlayerColor()){

        }
        else {
            for (OpponentWidget widget
                : this.gui.getOpponentsArray()) {
                if(widget.getOpponentColor().equals(player.getColor())){
                    // widget.setOpponentMarkers();
                    /*
                    widget.getResourceWidget().setGoldValue(
                            player.getResource(GoldResource.id).getValue());
                    widget.getResourceWidget().setServantValue(
                            player.getResource(ServantResource.id).getValue());
                    widget.getResourceWidget().setStoneValue(
                            player.getResource(StoneResource.id).getValue());
                    widget.getResourceWidget().setWoodValue(
                            player.getResource(WoodResource.id).getValue());


                    Resource[] points = new Resource[3];
                    points[0] = player.getResource(VictoryResource.ID);
                    points[1] = player.getResource(MilitaryResource.id);
                    points[2] = player.getResource(FaithResource.id);

                    HashMap<String, MarkerWidget> markerWidgetHashMap = new HashMap<>();
                    for (MarkerWidget markerWidget:
                            widget.getOpponentMarkers()) {
                        markerWidgetHashMap.put(markerWidget.getID(), markerWidget);
                    }

                    for (Resource r: points)
                        markerWidgetHashMap.get(r.getID()).setIntPoints(r.getValue());



                }
            }
        }
        */

        }
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

    @Override
    public void run() {

    }
}

