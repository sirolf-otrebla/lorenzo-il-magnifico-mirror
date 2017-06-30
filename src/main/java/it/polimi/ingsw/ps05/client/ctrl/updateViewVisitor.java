package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.net.GameStatus;

/**
 * Created by Alberto on 30/06/2017.
 */
public class updateViewVisitor {

    public void visit(GameStatus status){


    }

    public void visit(Board board){
        for (ActionSpace actionSpace:
                board.getActSpacesMap().values()) {
            actionSpace.acceptVisitor(this);
        }
        for (Tower tower : board.getTowerList().values()){
            tower.acceptVisitor(this);
        }


    }
    public void visit(Tile tile){

    }

    public void visit(MarketSpace marketSpace){


    }

    public void visit(CouncilSpace councilSpace){


    }

    public void visit(ProductionSpace productionSpace){


    }

    public void visit(HarvestingSpace harvestingSpace){


    }

    public void visit(Player player){


    }

}

