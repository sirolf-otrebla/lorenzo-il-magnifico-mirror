package it.polimi.ingsw.ps05.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.ingsw.ps05.resourcesandbonuses.*;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.net.server.Game;

public class CommonJsonParser {

	private static final String resourcePath = "it.polimi.ingsw.ps05.resourcesandbonuses.";
	private static final String modelPath = "it.polimi.ingsw.ps05.model.";
	
	private int playerConnected;
	private Game game;

	public CommonJsonParser(int playerConnected, Game game){
		this.playerConnected = playerConnected;
		this.game = game;
	}

	//XXX Metodi per caricamento board
	public Board loadBoard(String path) {
		File file = new File(path);
		ArrayList<ActionSpace> notTowerSpace = new ArrayList<ActionSpace>();
		ArrayList<VictoryResource> faithList = new ArrayList<VictoryResource>();
		ArrayList<Tower> towerList = new ArrayList<Tower>();
		ArrayList<MilitaryResource> militaryList = new ArrayList<MilitaryResource>();
		try {
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			notTowerSpace.addAll(detectNumMarketSpace(obj.get("MarketSpace")));
			notTowerSpace.add(loadCouncilSpace((JSONObject)obj.get("CouncilSpace")));
			notTowerSpace.addAll(detectNumHarvestingSpace(obj.get("HarvestSpace")));
			notTowerSpace.addAll(detectNumProductionSpace(obj.get("ProductionSpace")));
			faithList = loadFaithPath(obj.get("FaithPath"));
			towerList = loadTower((JSONObject)obj.get("Tower"));
			militaryList = loadMilitaryPath(obj.get("MilitaryPath"));
		} catch (IOException | ParseException | RepeatedAssignmentException e) {
			e.printStackTrace();
		}
		
		return Board.initBoard(towerList, notTowerSpace, faithList, militaryList,null);
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
		ArrayList<TowerTileInterface> list = new ArrayList<TowerTileInterface>();
		for (int i = 0; i < json.keySet().toArray().length; i++){
			JSONArray array = (JSONArray)json.get(json.keySet().toArray()[i].toString());
			for (int j = 0; j < array.toArray().length; j++){
				try {
					list.add(loadSingleTile((JSONObject)array.toArray()[j],json.keySet().toArray()[i].toString()));
				} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		
		Object tower = Class.forName(modelPath + key).newInstance();
		Method method = tower.getClass().getDeclaredMethod("setTiles", list.getClass());
		method.invoke(tower, list);
		for (TowerTileInterface t : list){
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

		Object tile = Class.forName(modelPath + keyClass).newInstance(); // istanza della classe letta da file ed esecuzione del setter per generare la risorsa
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
				if (!json.keySet().toArray()[i].equals("numPlayer")){
					list.add(createAllExceptActivable(json, i));
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException
					| SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		//TODO modificare json per rendere automatica questa creazione
		Dice diceRequired;
		ImmediateEffect effect = new ImmediateEffect();
		effect.setEffectList(list);
		ArrayList<Effect> effectList = new ArrayList<Effect>();
		effectList.add(effect);
		try{
			diceRequired = new Dice(ColorEnumeration.Any,Integer.parseInt(json.get("diceRequired").toString()));
		} catch (NullPointerException e){
			return new MarketSpace(effectList);
		}
		
		
		return new MarketSpace(diceRequired, effectList);
	}
	
	private CouncilSpace loadCouncilSpace(JSONObject json) throws RepeatedAssignmentException{
		ArrayList<ActionResult> list = new ArrayList<ActionResult>();
		for (int i = 0; i < ((JSONObject)json.get("Effect")).keySet().toArray().length; i++){
			try {
				list.add(createAllExceptActivable((JSONObject)json.get("Effect"), i));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException
					| SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		//TODO modificare json per rendere automatica questa creazione
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
			// TODO Auto-generated catch block
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
	
	//XXX Metodi per caricamento carte
	/*public ArrayList<Deck> loadDeck(String path) {
		ArrayList<Deck> deck = new ArrayList<Deck>();
		try {
			File file = new File(path);
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			deck.add(loadBlueCardDeck(obj));
			deck.add(loadYellowCardDeck(obj));
			deck.add(loadGreenCardDeck(obj));
			deck.add(loadVioletCardDeck(obj));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return deck;

	}*/

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
		return new BlueCard(getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}

	private YellowCard loadYellowCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		return new YellowCard(getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}

	private GreenCard loadGreenCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		ArrayList<ArrayList<Resource>> req = getRequirements((JSONObject)json.get("Requirement"));
		
		return new GreenCard(getCardEpoch(json), getCardColor(json), getCardName(json), req == null ? new ArrayList<ArrayList<Resource>>():req, getEffects((JSONObject)json.get("Effect")));
	}

	private VioletCard loadVioletCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		return new VioletCard(getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
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
		System.out.println("list null: " + list == null);
		return list;
	}

	private ArrayList<Effect> getEffects(JSONObject json) throws NumberFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{ //json è l'oggetto che contiene l'elenco degli effetti
		ArrayList<Effect> list = new ArrayList<Effect>(); //lista con le liste tra cui scegliere/attivare e basta
		for (int i = 0; i < json.keySet().toArray().length; i++) { 
			JSONObject effectList = (JSONObject) json.get(json.keySet().toArray()[i]);//entro in immediate o ecc...
			if (Class.forName(modelPath + json.keySet().toArray()[i].toString()).equals(ActivableEffect.class)){
				list.add(createActivable(effectList));
			} else {
				ArrayList<ActionResult> resList = new ArrayList<ActionResult>(); //lista dei singoli componenti
				Object object = Class.forName(modelPath + json.keySet().toArray()[i].toString()).newInstance(); //creo immediate o activable o permanent o endgame
				Method setList = object.getClass().getDeclaredMethod("setEffectList", resList.getClass()); //non si può mettere ArrayList<ActionResult>.Class quindi uso una variabile uguale e prendo la sua classe
				for (int j = 0; j < effectList.keySet().toArray().length; j++){ //ciclo le risorse/azioni aggiugnendole ad un arraylist
					if (!effectList.keySet().toArray()[j].toString().equals("Return") & !effectList.keySet().toArray()[j].toString().equals("Multiplier") & !effectList.keySet().toArray()[j].toString().equals("ResourceToCount")){
						resList.add(createAllExceptActivable(effectList, j)); 
					} else {
						resList.add(createBonusWithMultiplier(effectList));
						break;
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

	private ActionResult createAllExceptActivable(JSONObject json, int j) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, NumberFormatException, IllegalArgumentException, InvocationTargetException{

		Object actionObject = Class.forName(resourcePath + json.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
		Method method = actionObject.getClass().getDeclaredMethod("setValue",Integer.class);
		method.invoke(actionObject, Integer.parseInt(json.get(json.keySet().toArray()[j].toString()).toString()));
		Method method2 = actionObject.getClass().getDeclaredMethod("setGame",Game.class);
		method2.invoke(actionObject, game);
		return (ActionResult)actionObject; //cast esplicito a action result necessario per aggiungerlo alla lista. L'oggetto in se non perde la propria classe
	}

	private BonusWithMultiplier createBonusWithMultiplier(JSONObject json) throws NumberFormatException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		BonusWithMultiplier bonus;	
		try {
			bonus = new BonusWithMultiplier(Float.parseFloat(json.get("Multiplier").toString()),
					(Resource)Class.forName(resourcePath + json.get("Return").toString()).newInstance(),
					Class.forName(modelPath + json.get("ResourceToCount").toString()));
		} catch (ClassNotFoundException e){
			bonus = new BonusWithMultiplier(Float.parseFloat(json.get("Multiplier").toString()),
					(Resource)Class.forName(resourcePath + json.get("Return").toString()).newInstance(),
					Class.forName(resourcePath + json.get("ResourceToCount").toString()));
		}
		return bonus;
	}

}
