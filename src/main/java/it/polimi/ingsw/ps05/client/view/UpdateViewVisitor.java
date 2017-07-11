package it.polimi.ingsw.ps05.client.view;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.client.view.gui.*;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.cards.Card;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
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
    private HashMap<Integer, ActionSpaceWidgetInterface> actionSpaceWidgetHashMap = new HashMap<>();
    private GUIMain gui;
    private Client client;
    private GameStatus status;


    public UpdateViewVisitor(GUIMain main, GameStatus status){
        this.status = status;
        this.gui = main;
        this.client = Client.getInstance();
        for (SingleOccupantActionSpaceWidget widget:
             gui.getMarketSpaceWidgets()) {
            actionSpaceWidgetHashMap.put(widget.getReferenceId(), widget);
        }
        actionSpaceWidgetHashMap.put(gui.getHarvestingSpace().getReferenceId(), gui.getHarvestingSpace());
        actionSpaceWidgetHashMap.put(gui.getProductionSpace().getReferenceId(), gui.getProductionSpace());

        for (ColorEnumeration color: towerColorArray)
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
        System.out.println("PLAYER COLOR:");
        System.out.println(status.getThisPlayer().getColor().toString());
        System.out.println("__________________________________________");
        HashMap<ColorEnumeration, Integer> diceHashMap = new HashMap<>();
        for (Familiar f: status.getThisPlayer().getFamilyList()) {
            if (f.getColor() != ColorEnumeration.Any && f.getColor() != ColorEnumeration.Ghost){
                diceHashMap.put(f.getColor(), f.getRelatedDice().getValue());
            }
        }
        this.gui.setDiceValues(diceHashMap);
        this.visit(status.getGameBoard());
        for (Player p : status.getPlayerHashMap().values()) {
            this.visit(p);
        }
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
            TowerTileInterface[] towerTileInterfaces = new TowerTileInterface[tower.getTiles().values().size()];
            int i = 0;
            for ( TowerTileInterface towertile:  tower.getTiles().values()) {
                towerTileInterfaces[i] = towertile;
                i++;
            }
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
        }
        this.setWidgetLegal(widget, tile);
        if (widget.getAssociatedCard().getReferenceId() != tile.getCard().getReferenceID())
            widget.setAssociatedCard(new TowerCardWidget(tile.getCard().getReferenceID()));
        // altro da aggiungere?
        widget.repaint();
    }

    @Override
    public void visit(MarketSpace marketSpace){
        MarketSpaceWidget widget = (MarketSpaceWidget) actionSpaceWidgetHashMap.get(marketSpace.getId());
        if (marketSpace.isOccupied()){
            widget.setOccupied(true);
            widget.setOccupantPlayerColor(marketSpace.getOccupant().getRelatedPlayerColor());

        }
        this.setWidgetLegal(widget, marketSpace);
        widget.repaint();

    }

    @Override
    public void visit(CouncilSpace councilSpace){
        CouncilSpaceWidget widget = (CouncilSpaceWidget) actionSpaceWidgetHashMap.get(councilSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(councilSpace);
        widget.setOccupingFamiliarList(widgetList);
        this.setWidgetLegal(widget, councilSpace);
        widget.repaint();



    }

    @Override
    public void visit(ProductionSpace productionSpace){
        ProductionSpaceWidget widget = (ProductionSpaceWidget) actionSpaceWidgetHashMap.get(productionSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(productionSpace);
        if (widgetList.size() > 1) widget.setMorethanZeroOccupants(true);
        widget.setOccupingFamiliarList(widgetList);
        this.setWidgetLegal(widget, productionSpace);
        widget.repaint();

        //TODO ??? ALTRO DA AGGIUNGERE ???

    }

    @Override
    public void visit(HarvestingSpace harvestingSpace){

        HarvestingSpaceWidget widget = (HarvestingSpaceWidget) actionSpaceWidgetHashMap.get(harvestingSpace.getId());
        ArrayList<Pair<ColorEnumeration, ColorEnumeration>> widgetList = this.copyModelOccupantList(harvestingSpace);
        if (widgetList.size() > 1) widget.setMorethanZeroOccupants(true);
        widget.setOccupingFamiliarList(widgetList);
        this.setWidgetLegal(widget, harvestingSpace);
        widget.repaint();


    }

    @Override
    public void visit(Player player) {

        // setting values on point markers
        Integer[] pointsVector = new Integer[3];
        pointsVector[0] = player.getResource(FaithResource.id).getValue();
        pointsVector[1] = player.getResource(MilitaryResource.id).getValue();
        pointsVector[2] = player.getResource(VictoryResource.ID).getValue();
        this.gui.updatePlayerPoints(player.getColor(), pointsVector);

        // setting resources values
        PersonalBoardWindow boardWindow = null;
        if (player.getColor() == this.gui.getPlayer().getPlayerColor()) {
            for (Resource r : player.getResourceList()) {
                if (r.getID() == GoldResource.id || r.getID() == ServantResource.id ||
                        r.getID() == WoodResource.id || r.getID() == StoneResource.id) {
                    this.gui.getPlayer().getResourceWidget().setResource(r.getID(), r.getValue());
                    this.gui.getPlayer().getResourceWidget().repaint();
                }
            }

            boardWindow = this.gui.getPlayer().getPersonalBoard();

        } else {
            for (OpponentWidget opponentWidget : this.gui.getOpponentsArray()) {
                if (opponentWidget.getOpponentColor() == player.getColor()) {

                    for (Resource r : player.getResourceList()) {
                        if (r.getID() == GoldResource.id || r.getID() == ServantResource.id ||
                                r.getID() == WoodResource.id || r.getID() == StoneResource.id) {
                            opponentWidget.getPersonalBoard().getResourceWidget().setResource(r.getID(), r.getValue());
                            opponentWidget.getPersonalBoard().getResourceWidget().repaint();

                        }
                    }

                    boardWindow = opponentWidget.getPersonalBoard();
                }
            }
        }

        //setting cards on personal boards
        HashMap<ColorEnumeration, ArrayList<TowerCard>> acquiredCardWidgetsHashMap = new HashMap<>();
        acquiredCardWidgetsHashMap.put(ColorEnumeration.Green, new ArrayList<>());
        acquiredCardWidgetsHashMap.put(ColorEnumeration.Blue, new ArrayList<>());
        acquiredCardWidgetsHashMap.put(ColorEnumeration.Yellow, new ArrayList<>());
        acquiredCardWidgetsHashMap.put(ColorEnumeration.Violet, new ArrayList<>());
        for (TowerCard card: player.getGreenCardHashMap().values()) acquiredCardWidgetsHashMap.get(ColorEnumeration.Green).add(card);
        for (TowerCard card: player.getBlueCardHashMap().values()) acquiredCardWidgetsHashMap.get(ColorEnumeration.Blue).add(card);
        for (TowerCard card: player.getYellowCardHashMap().values()) acquiredCardWidgetsHashMap.get(ColorEnumeration.Yellow).add(card);
        for (TowerCard card: player.getVioletCardHashMap().values()) acquiredCardWidgetsHashMap.get(ColorEnumeration.Violet).add(card);

        for (ArrayList<TowerCard> arrayList : acquiredCardWidgetsHashMap.values()){
            ColorEnumeration color = arrayList.get(0).color;
            boardWindow.getCardAcquiredColorMap().remove(color);
            boardWindow.getCardAcquiredColorMap().put(color, new ArrayList<>());
            ArrayList<AcquiredCardWidget> acquiredCardWidgetArrayList = boardWindow.getCardAcquiredColorMap().get(color);
            for (TowerCard towerCard : arrayList){
                Integer id = towerCard.getReferenceID();
                acquiredCardWidgetArrayList.add(new AcquiredCardWidget(id, GraphicResources.getCardPath(id), towerCard.color));
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

    private void setWidgetLegal(ActionSpaceWidgetInterface widget, ActionSpace actionSpace){
        Collection<Familiar> family = Client.getInstance().getGameStatus().getThisPlayer().getFamilyMap().values();
        for (Familiar f : family)
            if ((new Action(f, actionSpace).isLegal())) {
                //widget.setLegal(true);
                widget.getLegalFamilyMemberList().add(f.getColor());
            }
    }


    @Override
    public void run() {
        visit(status);
    }


}

