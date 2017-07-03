package it.polimi.ingsw.ps05;

import it.polimi.ingsw.ps05.client.view.View;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

/**
 * Created by Alberto on 03/07/2017.
 */
public class CliTest {
    PlayerClient a;
    PlayerClient b;
    PlayerClient c;
    PlayerClient d;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    ArrayList<Player> list = new ArrayList<Player>();
    ArrayList<PlayerClient> list1 = new ArrayList<PlayerClient>();
    Game game;
    View view;
    GameSetup setup;


    public void setUp(){
        a = new PlayerClient(null, 0);
         b = new PlayerClient(null, 1);
         c = new PlayerClient(null, 2);
         d = new PlayerClient(null, 3);

        p1 = new Player(0, "luca", ColorEnumeration.Blue);
         p2 = new Player(1, "alberto", ColorEnumeration.Green);
         p3 = new Player(2, "andrea", ColorEnumeration.Violet);
         p4 = new Player(3, "franco", ColorEnumeration.Yellow);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list1.add(a);
        list1.add(b);
        list1.add(c);
        list1.add(d);
        game = new Game(false,false,0, list1);
        try {
            game.start();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         setup = new GameSetup(list, game);
        view = new View();


    }
    public void TestAdicazzo(){
        view.instanceCLI(setup.getBoard(), p1, setup.getTurnSetup().getTurn().getPlayerOrder());
    }

}
