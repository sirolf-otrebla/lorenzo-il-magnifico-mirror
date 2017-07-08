package it.polimi.ingsw.ps05.client.view;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.client.view.gui.*;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
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
        Board board = status.getGameBoard();
        board.acceptVisitor(this);

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
        status.getPlayerHashMap().put(status.getActivePlayerId(), activePlayer);

        Integer[] excommCardsIdArray = new Integer[3];
        for (ExcommunicationCard card : board.getExcomCards()) {
            excommCardsIdArray[card.getEpochID().ordinal()] = card.getReferenceID();
        }
        this.gui.insertExcomCards(excommCardsIdArray);













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

        // update dei punti fede, militare, vittoria
        for (Player p : status.getPlayerHashMap().values()) {
            Integer[] pointsVector = new Integer[3];
            pointsVector[0] = p.getResource(FaithResource.id).getValue();
            pointsVector[1] = p.getResource(MilitaryResource.id).getValue();
            pointsVector[2] = p.getResource(VictoryResource.ID).getValue();
            this.gui.updatePlayerPoints(p.getColor(), pointsVector);
        }
        // fine update dei punti militari.

        // inserisco risorse nei giocatori
        HashMap<String, Integer> playerResourcesHashMap = new HashMap<>();
        for(Resource r : status.getThisPlayer().getResourceList()){
            if (r.getID() == GoldResource.id || r.getID() == ServantResource.id ||
                    r.getID() == WoodResource.id ||r.getID() == StoneResource.id){
                playerResourcesHashMap.put(r.getID(), r.getValue());
            }
        }

        HashMap<ColorEnumeration, HashMap<String, Integer>> opponentsResourcesHashMap = new HashMap<>();
        status.getPlayerHashMap().remove(status.getThisPlayer().getPlayerID());
        for (Integer i : status.getPlayerHashMap().keySet()){
            HashMap<String, Integer> opponentResourcesHashMap = new HashMap<>();
            for(Resource r : status.getPlayerHashMap().get(i).getResourceList()){
                if (r.getID() == GoldResource.id || r.getID() == ServantResource.id ||
                        r.getID() == WoodResource.id ||r.getID() == StoneResource.id){
                    opponentResourcesHashMap.put(r.getID(), r.getValue());
                }
            }
            opponentsResourcesHashMap.put(status.getPlayerHashMap().get(i).getColor(), opponentResourcesHashMap);
        }

        this.gui.updatePlayerResources(playerResourcesHashMap, opponentsResourcesHashMap);

        // fine setup risorse










    }

    @Override
    public void visit(Board board) {
        for (Tower t:  board.getTowerList().values()) t.acceptVisitor(this);
        for (ActionSpace actionSpace : board.getActSpacesMap().values()) actionSpace.acceptVisitor(this);
    }

    @Override
    public void visit(TowerTileInterface towerTileInterface) {
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
