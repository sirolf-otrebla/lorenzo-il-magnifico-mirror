 
![LorenzoilMagnifico](http://i.imgur.com/aO13msl.png)
 
 # Lorenzo il Magnifico
 
### Gruppo PS05 (Falaschini, Floris, Miotto)

#### _Final project for IT Engineering Bachelor at "Politecnico di Milano" University_

This is a Java application built around the italian boardgame _**Lorenzo il Magnifico**_ from Cranio Creations. In this game you will take the role of a head of a noble family in Florence during the Renaissance trying to attain more achievements than the other families.
It is the final project of IT Engineering at "Politecnico di Milano" University.

The game is designed for 2-4 players with online multiplayer support.

## Getting started

The application must be run from `./src/main/java/it/polimi/ingsw/ps05/App.java`.

## Usage

At the start of the Application you will be able to choose the instance to run
```
$ LimServer 	 LimClient
$
```
### Server setup

Type `s` to instanciate the server. It is needed in order to establish a connection between the clients (only one server is needed).

```
$ LimServer 	 LimClient
$ s
```

Upon start the server will ask for some game configurations. You can set them with `y/n`.

1. Complete rules (includes excommunications and leader cards)
2. Bonus Tile draft (a special phase at the beginning of the game)

After this little setup, your server is ready to accept client connections.

### Client setup
Every player that wants to join the game needs to run its own client application. This is done by choosing the `c` option after launch
```
$ LimServer 	 LimClient
$ c
```

Insert the server IP address and click **Connect** (the port is automatically set to port 11717)
![bello](http://i.imgur.com/F0FD2cq.png)

Choose your personal username and password and select the user interface. 
_At the moment only CLI is fully functional. GUI is incomplete_

![bellissimo](http://i.imgur.com/S2Uku5u.png)

### Game start

After the connection is established, you will enter the lobby. You will see the list of all the players connected to the server. When the lobby contains at least 2 players a timer will start. If the limit of 4 players is not reached, the game will start at the end of the timer.

![stupendo](http://i.imgur.com/Ut34t9b.png)

## CLI preview

![CLI](http://i.imgur.com/O7YLd34.jpg)

## GUI preview

![GUI](http://i.imgur.com/CSe0qUB.png)











