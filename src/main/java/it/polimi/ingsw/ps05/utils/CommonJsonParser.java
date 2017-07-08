package it.polimi.ingsw.ps05.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import it.polimi.ingsw.ps05.model.cards.*;
import it.polimi.ingsw.ps05.model.effects.ActivableEffect;
import it.polimi.ingsw.ps05.model.effects.ActivableEffectType;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.spaces.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.ExcommunicationEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.ReduceVictoryPtsExcomm;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.exceptions.CouncilDiceAlreadySet;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.server.controller.Game;

public class CommonJsonParser {

	private static final String resourcePath = "it.polimi.ingsw.ps05.model.resourcesandbonuses.";
	private static final String excommPath = "it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.";
	private static final String effectPath = "it.polimi.ingsw.ps05.model.effects.";
	private static final String cardPath = "it.polimi.ingsw.ps05.model.cards.";
	private static final String spacePath = "it.polimi.ingsw.ps05.model.spaces.";

	private int playerConnected;
	private Game game;
	// TODO METTERE CONTROLLO PER NON CREARE PIÙ ACTIONSPACE DEL NECESSARIO


	public CommonJsonParser(int playerConnected, Game game){
		this.playerConnected = playerConnected;
		this.game = game;
	}
	
	public ArrayList<ArrayList<Resource>> loadPrivilegeConversion(){

		ArrayList<ArrayList<Resource>> finalList = new ArrayList<>();

		try {
			File file = new File("./src/main/res/privilegeConverter.json");
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			JSONArray array = (JSONArray)obj.get("Privilege");

			for (Object o : array){
				ArrayList<Resource> interList = new ArrayList<>();
				JSONObject a = (JSONObject)o;
				System.out.println("oggetto " + a);
				for (int i = 0; i < a.keySet().size(); i++){
					Object actionObject = Class.forName(resourcePath + a.keySet().toArray()[i].toString()).newInstance();
					Method method = actionObject.getClass().getDeclaredMethod("setValue",Integer.class);
					System.out.println(a.keySet().toArray()[i]);
					System.out.println(a.get(a.keySet().toArray()[i]));
					method.invoke(actionObject, Integer.parseInt(a.get(a.keySet().toArray()[i]).toString()));
					interList.add((Resource)actionObject);
				}
				finalList.add(interList);
				
			}
		} catch (IOException | ParseException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return finalList;
	}

	private ArrayList<LeaderCard> loadLeaderCards() throws FileNotFoundException, IOException, ParseException, NumberFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		File file = new File("./src/main/res/leader.json");
		JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
		JSONArray array = (JSONArray)obj.get("LeaderCard");
		ArrayList<LeaderCard> cards = new ArrayList<>();
		for (int i = 0; i < array.size(); i++){
			cards.add(loadSingleLeaderCard((JSONObject)array.get(i)));
		}

		return cards;
	}

	private LeaderCard loadSingleLeaderCard (JSONObject obj) throws NumberFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		LeaderCard card = new LeaderCard(Integer.parseInt(obj.get("id").toString()));
		card.setCardName(getCardName(obj));
		card.setEffects(getEffects((JSONObject)obj.get("Effect")));
		return card;
	}

	//XXX Metodi per caricamento board
	public Board loadBoard(String path) {
		File file = new File(path);
		ArrayList<ActionSpace> notTowerSpace = new ArrayList<ActionSpace>();
		ArrayList<VictoryResource> faithList = new ArrayList<VictoryResource>();
		ArrayList<Tower> towerList = new ArrayList<Tower>();
		ArrayList<MilitaryResource> militaryList = new ArrayList<MilitaryResource>();
		ArrayList<VictoryResource> blueConv = new ArrayList<VictoryResource>();
		ArrayList<VictoryResource> greenConv = new ArrayList<VictoryResource>();
		ArrayList<LeaderCard> leaderCardsList = new ArrayList<>();
		try {
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			notTowerSpace.addAll(detectNumMarketSpace(obj.get("MarketSpace")));
			notTowerSpace.add(loadCouncilSpace((JSONObject)obj.get("CouncilSpace")));
			notTowerSpace.addAll(detectNumHarvestingSpace(obj.get("HarvestSpace")));
			notTowerSpace.addAll(detectNumProductionSpace(obj.get("ProductionSpace")));
			faithList = loadFaithPath(obj.get("FaithPath"));
			towerList = loadTower((JSONObject)obj.get("Tower"));
			militaryList = loadMilitaryPath(obj.get("MilitaryPath"));
			blueConv = loadBlueConversionPath(obj.get("BlueConversion"));
			greenConv = loadGreenConversionPath(obj.get("GreenConversion"));
			leaderCardsList = loadLeaderCards();
		} catch (IOException | ParseException | RepeatedAssignmentException | CouncilDiceAlreadySet | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return new Board(towerList, notTowerSpace, faithList, militaryList, blueConv, greenConv, leaderCardsList);
	}

	private ArrayList<VictoryResource> loadBlueConversionPath(Object json){
		JSONArray obj = (JSONArray)json;
		ArrayList<VictoryResource> list = new ArrayList<VictoryResource>();
		for (int i = 0; i < obj.toArray().length; i++){
			list.add(new VictoryResource(Integer.parseInt(obj.toArray()[i].toString())));
		}
		return list;
	}

	private ArrayList<VictoryResource> loadGreenConversionPath(Object json){
		JSONArray obj = (JSONArray)json;
		ArrayList<VictoryResource> list = new ArrayList<VictoryResource>();
		for (int i = 0; i < obj.toArray().length; i++){
			list.add(new VictoryResource(Integer.parseInt(obj.toArray()[i].toString())));
		}
		return list;
	}

	private ArrayList<MilitaryResource> loadMilitaryPath(Object json){
		JSONArray obj = (JSONArray)json;
		ArrayList<MilitaryResource> list = new ArrayList<MilitaryResource>();
		for (int i = 0; i < obj.toArray().length; i++){
			list.add(new MilitaryResource(Integer.parseInt(obj.toArray()[i].toString())));
		}

		return list;
	}

	private ArrayList<VictoryResource> loadFaithPath(Object json){
		JSONArray obj = (JSONArray)json;
		ArrayList<VictoryResource> list = new ArrayList<VictoryResource>();
		for (int i = 0; i < obj.toArray().length; i++){
			list.add(new VictoryResource(Integer.parseInt(obj.toArray()[i].toString())));
		}

		return list;
	}

	private ArrayList<Tower> loadTower(JSONObject json) throws FileNotFoundException, IOException, ParseException{
		ArrayList<Tower> list = new ArrayList<Tower>();
		for (int i = 0; i < json.keySet().toArray().length; i++){
			try {
				list.add(loadSingleTower((JSONObject)json.get(json.keySet().toArray()[i]),json.keySet().toArray()[i].toString()));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private Tower loadSingleTower(JSONObject json, String key) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, IOException, ParseException{
		//ArrayList<TowerTileInterface> list = new ArrayList<TowerTileInterface>();
		HashMap<Integer, TowerTileInterface> list = new HashMap<Integer, TowerTileInterface>();
		for (int i = 0; i < json.keySet().toArray().length; i++){
			JSONArray array = (JSONArray)json.get(json.keySet().toArray()[i].toString());
			for (int j = 0; j < array.toArray().length; j++){
				try {
					TowerTileInterface tile = loadSingleTile((JSONObject)array.toArray()[j],json.keySet().toArray()[i].toString());
					list.put(((ActionSpace)tile).getId(), tile);
				} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
			}
		}

		Object tower = Class.forName(spacePath + key).newInstance();
		Method method = tower.getClass().getDeclaredMethod("setTiles", list.getClass());
		method.invoke(tower, list);
		for (TowerTileInterface t : list.values()){
			t.setParentTower((Tower)tower);
		}

		File file = new File("./src/main/res/cards.json");
		JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
		if (tower instanceof BlueTower){
			((BlueTower) tower).setDeck(loadBlueCardDeck(obj));
		} else if (tower instanceof GreenTower){
			((GreenTower) tower).setDeck(loadGreenCardDeck(obj));
		} else if (tower instanceof VioletTower){
			((VioletTower) tower).setDeck(loadVioletCardDeck(obj));
		} else if (tower instanceof YellowTower){
			((YellowTower) tower).setDeck(loadYellowCardDeck(obj));
		}

		return (Tower)tower;
	}

	private TowerTileInterface loadSingleTile(JSONObject json, String keyClass)
			throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InstantiationException, ClassNotFoundException, NoSuchMethodException, SecurityException { // keyClass indica se è un tile o tilewitheffect

		Object tile = Class.forName(spacePath + keyClass).newInstance(); // istanza della classe letta da file ed esecuzione del setter per generare la risorsa
		Method method = tile.getClass().getDeclaredMethod("setDiceRequired", Integer.class);
		method.invoke(tile, Integer.parseInt(json.get("diceRequired").toString()));
		ArrayList<ActionResult> list = new ArrayList<ActionResult>();
		try {
			Method method2 = tile.getClass().getDeclaredMethod("setTileEffect", list.getClass());
			for (int i = 0; i < ((JSONObject) json.get("Effect")).keySet().toArray().length; i++) {
				list.add(createAllExceptActivable((JSONObject) json.get("Effect"), i));
			}
			method2.invoke(tile, list);
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Tryed to execute method to set effects on tile without effect, is an automated method, and this is an exception. Don't worry :)");
		}
		return (TowerTileInterface) tile;
	}

	private ArrayList<MarketSpace> detectNumMarketSpace(Object json){
		JSONArray obj = (JSONArray)json;
		ArrayList<MarketSpace> list = new ArrayList<MarketSpace>();
		for (int i = 0; i < obj.toArray().length; i++){
			if (Integer.parseInt(((JSONObject)obj.toArray()[i]).get("numPlayer").toString()) <= playerConnected){
				list.add(loadMarketSpace((JSONObject)obj.toArray()[i]));
			}

		}
		return list;
	}

	private MarketSpace loadMarketSpace(JSONObject json) {

		ArrayList<ActionResult> list = new ArrayList<ActionResult>();
		for (int i = 0; i < json.keySet().toArray().length; i++){
			try {
				if (!json.keySet().toArray()[i].equals("numPlayer") && !json.keySet().toArray()[i].equals("id")){
					list.add(createAllExceptActivable(json, i));
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
					| SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		Dice diceRequired;
		ImmediateEffect effect = new ImmediateEffect();
		effect.setEffectList(list);
		ArrayList<Effect> effectList = new ArrayList<Effect>();
		effectList.add(effect);
		try{
			diceRequired = new Dice(ColorEnumeration.Any,Integer.parseInt(json.get("diceRequired").toString()));
		} catch (NullPointerException e){
			MarketSpace space = new MarketSpace(effectList);
			space.setMarketSpaceTypeID(Integer.parseInt(json.get("id").toString()));
			return space;
		}

		MarketSpace space = new MarketSpace(diceRequired, effectList);
		space.setMarketSpaceTypeID(Integer.parseInt(json.get("id").toString()));
		return new MarketSpace(diceRequired, effectList);
	}

	private CouncilSpace loadCouncilSpace(JSONObject json) throws RepeatedAssignmentException, CouncilDiceAlreadySet{
		ArrayList<ActionResult> list = new ArrayList<ActionResult>();
		for (int i = 0; i < ((JSONObject)json.get("Effect")).keySet().toArray().length; i++){
			try {
				list.add(createAllExceptActivable((JSONObject)json.get("Effect"), i));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
					| SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		Dice diceRequired;
		ImmediateEffect effect = new ImmediateEffect();
		effect.setEffectList(list);
		ArrayList<Effect> effectList = new ArrayList<Effect>();
		effectList.add(effect);
		try{
			diceRequired = new Dice(ColorEnumeration.Any,Integer.parseInt(json.get("diceRequired").toString()));
		} catch (NullPointerException e){
			return new CouncilSpace(effectList);
		}

		return new CouncilSpace(diceRequired, effectList);
	}

	private ArrayList<HarvestingSpace> detectNumHarvestingSpace(Object json) throws RepeatedAssignmentException{
		JSONArray obj = (JSONArray)json;
		ArrayList<HarvestingSpace> list = new ArrayList<HarvestingSpace>();
		for (int i = 0; i < obj.toArray().length; i++){
			if (Integer.parseInt(((JSONObject)obj.toArray()[i]).get("numPlayer").toString()) <= playerConnected){
				list.add(loadHarvestSpace((JSONObject)obj.toArray()[i]));
			}
		}
		return list;
	}

	private HarvestingSpace loadHarvestSpace(JSONObject json) throws RepeatedAssignmentException{
		ArrayList<Effect> list = new ArrayList<Effect>();
		for (int i = 0; i < ((JSONObject)json.get("Effect")).keySet().toArray().length; i++){
			try {
				list = (getEffects((JSONObject)json.get("Effect")));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException
					| SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		Dice diceRequired;
		try{
			diceRequired = new Dice(ColorEnumeration.Any,Integer.parseInt(json.get("diceRequired").toString()));
		} catch (NullPointerException e){
			return new HarvestingSpace(list);
		}

		return new HarvestingSpace(diceRequired, list);
	}

	private ArrayList<ProductionSpace> detectNumProductionSpace(Object json) throws RepeatedAssignmentException{
		JSONArray obj = (JSONArray)json;
		ArrayList<ProductionSpace> list = new ArrayList<ProductionSpace>();
		for (int i = 0; i < obj.toArray().length; i++){
			if (Integer.parseInt(((JSONObject)obj.toArray()[i]).get("numPlayer").toString()) <= playerConnected){
				list.add(loadProductionSpace((JSONObject)obj.toArray()[i]));
			}
		}
		return list;
	}

	private ProductionSpace loadProductionSpace(JSONObject json) throws RepeatedAssignmentException{
		ArrayList<Effect> list = new ArrayList<Effect>();
		for (int i = 0; i < ((JSONObject)json.get("Effect")).keySet().toArray().length; i++){
			try {
				list = (getEffects((JSONObject)json.get("Effect")));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException
					| SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		Dice diceRequired;
		try{
			diceRequired = new Dice(ColorEnumeration.Any,Integer.parseInt(json.get("diceRequired").toString()));
		} catch (NullPointerException e){
			return new ProductionSpace(list);
		}

		return new ProductionSpace(diceRequired, list);
	}

	//XXX BonusTiles
	public ArrayList<BonusTile> loadBonusTiles(String path, BonusTileType type){
		File file = new File(path);
		ArrayList<BonusTile> list = new ArrayList<BonusTile>();
		try {
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));

			if (type == BonusTileType.Default){
				JSONArray array =  (JSONArray)obj.get("Default");
				for (int i = 0; i < playerConnected; i++){
					try {
						list.add(loadSingleBonusTile(array, type));
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| NoSuchMethodException | SecurityException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} else {
				JSONArray array =  (JSONArray)obj.get("Custom");
				for (int i = 0; i < array.size(); i++){
					try {
						list.add(loadSingleBonusTile((JSONArray)array.get(i), type));
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| NoSuchMethodException | SecurityException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}

		return list;
	}

	private BonusTile loadSingleBonusTile(JSONArray json, BonusTileType type) throws NumberFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		ArrayList<Effect> list = new ArrayList<Effect>();
		for (int i = 0; i < json.size(); i++){
			list.addAll(getEffects((JSONObject)json.get(i)));
		}

		return new BonusTile(list, type);
	}

	//XXX Metodi per caricamento scomuniche

	public ArrayList<ExcommunicationCard> loadExcommunicationCard(String path) {
		File file = new File(path);
		System.out.println(path);
		ArrayList<ExcommunicationCard> toReturn = new ArrayList<ExcommunicationCard>();
		try {
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			JSONObject firstJson =  (JSONObject)obj.get("FIRST");
			ArrayList<ExcommunicationEffect> first = new ArrayList<ExcommunicationEffect>(); 
			for (Object exc : firstJson.keySet()){
				first.add(selectFirstSecondEpochExcommEffect((JSONObject)(firstJson.get(exc.toString())),exc));
			}
			JSONObject secondJson =  (JSONObject)obj.get("SECOND");
			ArrayList<ExcommunicationEffect> second = new ArrayList<ExcommunicationEffect>(); 
			for (Object exc : secondJson.keySet()){
				second.add(selectFirstSecondEpochExcommEffect((JSONObject)(secondJson.get(exc.toString())),exc));
			}

			JSONObject thirdJson =  (JSONObject)obj.get("THIRD");
			ArrayList<ExcommunicationEffect> third = new ArrayList<ExcommunicationEffect>();
			for (Object exc : thirdJson.keySet()){
				if (exc.toString().equals("ReduceVictoryPtsExcomm")){
					JSONArray array = (JSONArray)thirdJson.get(exc.toString());
					ArrayList<ActionResult> toCheck = new ArrayList<ActionResult>();
					for (Object o : array){
						for (int i = 0; i < ((JSONObject)o).keySet().size(); i++){
							ReduceVictoryPtsExcomm excomm = new ReduceVictoryPtsExcomm();
							excomm.setGame(game);
							if (((JSONObject)o).keySet().toArray()[i].toString().equals("toCheck")){
								JSONObject checkList = (JSONObject)((JSONObject)o).get("toCheck");
								for (int j = 0; j < checkList.keySet().size(); j++){
									toCheck.add(createAllExceptActivable(checkList, j));
								}
								excomm.setToCheck(toCheck);
							} else {
								ArrayList<ActionResult> result = new ArrayList<ActionResult>();
								result.add(createAllExceptActivable(((JSONObject)o), i));
								excomm.setMalus(result);
							}
							third.add(excomm);
						}

					}
				} else {
					third.add(selectThirdEpochExcommEffect(thirdJson.get(exc.toString()),exc));
				}

			}
			Random randomNum = new Random();
			toReturn.add(new ExcommunicationCard(new FaithResource(3), EpochEnumeration.FIRST, first.get(randomNum.nextInt(first.size()))));
			toReturn.add(new ExcommunicationCard(new FaithResource(4), EpochEnumeration.SECOND, second.get(randomNum.nextInt(second.size()))));
			toReturn.add(new ExcommunicationCard(new FaithResource(5), EpochEnumeration.THIRD, third.get(randomNum.nextInt(third.size()))));


		} catch(IOException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | ParseException | RepeatedAssignmentException e) {
			e.printStackTrace();
		}







		return toReturn;
	}

	private ExcommunicationEffect selectFirstSecondEpochExcommEffect(JSONObject json, Object key) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, NumberFormatException, IllegalArgumentException, InvocationTargetException{
		Object actionObject = Class.forName(excommPath + key.toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
		ArrayList<ActionResult> malus = new ArrayList<ActionResult>();
		for (int i = 0; i < json.keySet().size(); i++){
			malus.add(createAllExceptActivable(json, i));
		}
		Method method = actionObject.getClass().getDeclaredMethod("setMalus",malus.getClass());
		method.invoke(actionObject, malus);

		Method method1 = actionObject.getClass().getDeclaredMethod("setGame",game.getClass());
		method1.invoke(actionObject, game);

		return (ExcommunicationEffect)actionObject;
	}

	private ExcommunicationEffect selectThirdEpochExcommEffect(Object json, Object key) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, NumberFormatException, IllegalArgumentException, InvocationTargetException{
		Object actionObject = Class.forName(excommPath + key.toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
		ArrayList<ActionResult> malus = new ArrayList<ActionResult>();
		for (int i = 0; i < ((JSONObject)json).keySet().size(); i++){
			malus.add(createAllExceptActivable((JSONObject)json, i));
		}
		Method method = actionObject.getClass().getDeclaredMethod("setMalus",malus.getClass());
		method.invoke(actionObject, malus);

		Method method1 = actionObject.getClass().getDeclaredMethod("setGame",game.getClass());
		method1.invoke(actionObject, game);

		return (ExcommunicationEffect)actionObject;
	}

	//XXX Metodi per caricamento carte

	private BlueCardDeck loadBlueCardDeck(JSONObject json){
		JSONArray list = (JSONArray) json.get("Blue");
		ArrayList<BlueCard> blueCardList = new ArrayList<BlueCard>();
		for (Object o : list){
			try {
				BlueCard card = loadBlueCard((JSONObject)o);
				blueCardList.add(card);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new BlueCardDeck(blueCardList);

	}

	private YellowCardDeck loadYellowCardDeck(JSONObject json){
		JSONArray list = (JSONArray) json.get("Yellow");
		ArrayList<YellowCard> yellowCardList = new ArrayList<YellowCard>();
		for (Object o : list){
			try {
				YellowCard card = loadYellowCard((JSONObject)o);
				yellowCardList.add(card);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new YellowCardDeck(yellowCardList);
	}

	private GreenCardDeck loadGreenCardDeck(JSONObject json){
		JSONArray list = (JSONArray) json.get("Green");
		ArrayList<GreenCard> greenCardList = new ArrayList<GreenCard>();
		for (Object o : list){
			try {
				GreenCard card = loadGreenCard((JSONObject)o);
				greenCardList.add(card);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new GreenCardDeck(greenCardList);
	}

	private VioletCardDeck loadVioletCardDeck(JSONObject json){
		JSONArray list = (JSONArray) json.get("Violet");
		ArrayList<VioletCard> violetCardList = new ArrayList<VioletCard>();
		for (Object o : list){
			try {
				VioletCard card = loadVioletCard((JSONObject)o);
				violetCardList.add(card);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new VioletCardDeck(violetCardList);
	}

	private BlueCard loadBlueCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		return new BlueCard(getCardId(json),getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}

	private YellowCard loadYellowCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		return new YellowCard(getCardId(json),getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}

	private GreenCard loadGreenCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		ArrayList<ArrayList<Resource>> req = getRequirements((JSONObject)json.get("Requirement"));

		return new GreenCard(getCardId(json),getCardEpoch(json), getCardColor(json), getCardName(json), req == null ? new ArrayList<ArrayList<Resource>>():req, getEffects((JSONObject)json.get("Effect")));
	}

	private VioletCard loadVioletCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		return new VioletCard(getCardId(json),getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}

	private Integer getCardId(JSONObject json) {
		return new Integer(Integer.parseInt(json.get("id").toString()));
	}

	private Epoch getCardEpoch(JSONObject json){
		return new Epoch(EpochEnumeration.valueOf(json.get("Epoch").toString()));
	}

	private ColorEnumeration getCardColor(JSONObject json){
		return ColorEnumeration.valueOf(json.get("Color").toString());
	}

	private String getCardName(JSONObject json){
		return json.get("CardName").toString();
	}

	private ArrayList<ArrayList<Resource>> getRequirements(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{ //json è l'oggetto che contiene first e second
		ArrayList<ArrayList<Resource>> list = new ArrayList<ArrayList<Resource>>(); //lista con le liste tra cui scegliere
		for (int i = 0; i < json.keySet().toArray().length; i++) {
			JSONObject requirementList = (JSONObject) json.get(json.keySet().toArray()[i]); //entro in first o second
			ArrayList<Resource> resList = new ArrayList<Resource>(); //lista dei singoli componenti
			for (int j = 0; j < requirementList.keySet().toArray().length; j++){ //ciclo le risorse aggiugnendole ad un arraylist
				Object object = Class.forName(resourcePath + requirementList.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
				Method method = object.getClass().getDeclaredMethod("setValue",Integer.class);
				method.invoke(object, Integer.parseInt(requirementList.get(requirementList.keySet().toArray()[j].toString()).toString()));
				resList.add((Resource)object); //cast esplicito a risorsa necessario per aggiungerlo alla lista. L'oggetto in se non perde la propria classe
			}
			list.add(resList);
		}
		return list;
	}

	private ArrayList<Effect> getEffects(JSONObject json) throws NumberFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{ //json è l'oggetto che contiene l'elenco degli effetti
		ArrayList<Effect> list = new ArrayList<Effect>(); //lista con le liste tra cui scegliere/attivare e basta
		for (int i = 0; i < json.keySet().toArray().length; i++) { 
			JSONObject effectList = (JSONObject) json.get(json.keySet().toArray()[i]);//entro in immediate o ecc...
			if (Class.forName(effectPath + json.keySet().toArray()[i].toString()).equals(ActivableEffect.class)){
				list.add(createActivable(effectList));
			} else {
				ArrayList<ActionResult> resList = new ArrayList<ActionResult>(); //lista dei singoli componenti
				Object object = Class.forName(effectPath + json.keySet().toArray()[i].toString()).newInstance(); //creo immediate o activable o permanent o endgame
				Method setList = object.getClass().getDeclaredMethod("setEffectList", resList.getClass()); //non si può mettere ArrayList<ActionResult>.Class quindi uso una variabile uguale e prendo la sua classe
				for (int j = 0; j < effectList.keySet().toArray().length; j++){ //ciclo le risorse/azioni aggiugnendole ad un arraylist
					if (!effectList.keySet().toArray()[j].toString().equals("Return") &
							!effectList.keySet().toArray()[j].toString().equals("Multiplier") &
							!effectList.keySet().toArray()[j].toString().equals("ResourceToCount") &
							!effectList.keySet().toArray()[j].toString().equals("BonusDice")){
						resList.add(createAllExceptActivable(effectList, j)); 
					} else if (!effectList.keySet().toArray()[j].toString().equals("BonusDice")){
						resList.add(createBonusWithMultiplier(effectList));
						break;
					} else {
						
						JSONObject array = (JSONObject)effectList.get(effectList.keySet().toArray()[j]);
						for (int k = 0; k < array.keySet().toArray().length; k++){
							JSONObject obj = (JSONObject)array.get(array.keySet().toArray()[k]);
							BonusDice dice = new BonusDice(Integer.parseInt(obj.get("Value").toString()),
									ColorEnumeration.valueOf(obj.get("Color").toString()), (boolean)obj.get("ToAdd"));
							resList.add((ActionResult)dice);
						}
						
					}
				}
				setList.invoke(object, resList);//setto nell'oggetto effetto (qualunque sia) la lista di azioni appena ricavata
				list.add((Effect) object); //aggiungo l'effetto alla lista da ritornare
			}
		} 

		return list;
	}

	private ArrayList<ArrayList<ActionResult>> getActivableActionResultFromJSON(JSONObject json) throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, NumberFormatException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{

		ArrayList<ArrayList<ActionResult>> list = new ArrayList<ArrayList<ActionResult>>();
		for (int i = 0; i < json.keySet().toArray().length; i++) {

			ArrayList<ActionResult> resList = new ArrayList<ActionResult>();
			JSONObject effectList = (JSONObject) json.get(json.keySet().toArray()[i]); //entro in first o second
			for (int j = 0; j < effectList.keySet().toArray().length; j++){ //ciclo le risorse/azioni aggiugnendole ad un arraylist

				if (!effectList.keySet().toArray()[j].toString().equals("Return") &
						!effectList.keySet().toArray()[j].toString().equals("Multiplier") &
						!effectList.keySet().toArray()[j].toString().equals("ResourceToCount")){

					resList.add(createAllExceptActivable(effectList, j));
				}
				else {

					resList.add(createBonusWithMultiplier(effectList));
					break;
				}
			}
			list.add(resList);
		}
		return list;
	}

	private ActivableEffect createActivable(JSONObject json) throws NumberFormatException, InstantiationException,
	IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{

		return new ActivableEffect(ActivableEffectType.valueOf(json.get("Type").toString()),
				Integer.parseInt(json.get("DiceRequired").toString()),
				getRequirements((JSONObject)json.get("Requirement")),
				getActivableActionResultFromJSON((JSONObject)json.get("Effect")));
	}

	private ActionResult createAllExceptActivable(JSONObject json, int j) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NumberFormatException, IllegalArgumentException{
		Object actionObject = Class.forName(resourcePath + json.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
		

		try {
			Method method = actionObject.getClass().getDeclaredMethod("setValue",Integer.class);
			method.invoke(actionObject, Integer.parseInt(json.get(json.keySet().toArray()[j].toString()).toString()));
			
		} catch (NoSuchMethodException | InvocationTargetException e){
			//TODO in realtà niente da fare deve solo rimanere qui dentro
		}
		
		
		try {
			Method method2 = actionObject.getClass().getDeclaredMethod("setGame",Game.class);
			method2.invoke(actionObject, game);
		} catch (NoSuchMethodException | InvocationTargetException e) {
			//TODO in realtà niente da fare, deve solo rimanere qui dentro
		}
		
		
		return (ActionResult)actionObject; //cast esplicito a action result necessario per aggiungerlo alla lista. L'oggetto in se non perde la propria classe
	}
	
	

	private BonusWithMultiplier createBonusWithMultiplier(JSONObject json) throws NumberFormatException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		BonusWithMultiplier bonus;	
		try {
			bonus = new BonusWithMultiplier(Float.parseFloat(json.get("Multiplier").toString()),
					(Resource)Class.forName(resourcePath + json.get("Return").toString()).newInstance(),
					Class.forName(effectPath + json.get("ResourceToCount").toString()));
		} catch (ClassNotFoundException e){
			try {
				bonus = new BonusWithMultiplier(Float.parseFloat(json.get("Multiplier").toString()),
						(Resource)Class.forName(resourcePath + json.get("Return").toString()).newInstance(),
						Class.forName(cardPath + json.get("ResourceToCount").toString()));
			} catch (Exception e1) {
				bonus = new BonusWithMultiplier(Float.parseFloat(json.get("Multiplier").toString()),
						(Resource)Class.forName(resourcePath + json.get("Return").toString()).newInstance(),
						Class.forName(resourcePath + json.get("ResourceToCount").toString()));
			}
		}
		return bonus;
	}
	
	public ArrayList<Resource> loadStartingResources(){
		ArrayList<Resource> list = new ArrayList<>();
		try {
			File file = new File("./src/main/res/startResource.json");
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			for (int i = 0; i < obj.keySet().size(); i++){
				Object actionObject = Class.forName(resourcePath + obj.keySet().toArray()[i].toString()).newInstance();
				Method method = actionObject.getClass().getDeclaredMethod("setValue",Integer.class);
				method.invoke(actionObject, Integer.parseInt(obj.get(obj.keySet().toArray()[i]).toString()));
				list.add((Resource)actionObject);
			}
		} catch (IOException | ParseException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("Resource loaded: " + list.size());
		return list;
	}

}
