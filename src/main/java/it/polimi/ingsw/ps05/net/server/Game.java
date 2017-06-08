package it.polimi.ingsw.ps05.net.server;

import java.util.ArrayList;

public class Game {
	
	private int id;
	private ArrayList<ServerPlayer> list = new ArrayList<ServerPlayer>();
	private boolean useCompleteRules = true;
	private boolean useCustomBonusTiles = false;
	
	public Game(boolean useCompleteRules, boolean useCustomBonusTiles, int id){
		this.id = id;
		this.useCompleteRules = useCompleteRules;
		this.useCustomBonusTiles = useCustomBonusTiles;
	}
	
	public void addPlayer(ServerPlayer player){
		list.add(player);
	}
	
	public void removePlayer(ServerPlayer player){
		list.remove(player);
	}
	
	public boolean isUsingCompleteRules(){
		return useCompleteRules;
	}
	
	public boolean isUsingCustomBonusTiles(){
		return useCustomBonusTiles;
	}
	
	public int getGameId(){
		return id;
	}
	
	public ArrayList<ServerPlayer> getPlayerInGame(){
		return list;
	}

}
