package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.*;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alberto on 01/07/2017.
 */
public interface ViewVisitorInterface {


    public void visit(GameStatus status);

    public void visit(Board board);

    public void visit(Tile tile);

    public void visit(MarketSpace marketSpace);

    public void visit(CouncilSpace councilSpace);

    public void visit(ProductionSpace productionSpace);

    public void visit(HarvestingSpace harvestingSpace);

    public void visit(Player player);

    public void visit(Tower tower);
}
