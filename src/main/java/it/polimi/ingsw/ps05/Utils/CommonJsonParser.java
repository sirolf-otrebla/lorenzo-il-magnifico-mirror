package it.polimi.ingsw.ps05.Utils;

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

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.*;

public class CommonJsonParser {
	
	File file;

	public CommonJsonParser(){
        file = new File("./src/main/res/cards.json");
        
	}

	public ArrayList<Deck> loadDeck() {
		ArrayList<Deck> deck = new ArrayList<Deck>();
		try {
			JSONObject obj = (JSONObject) (new JSONParser()).parse(new FileReader(file));
			deck.add(loadBlueCardDeck(obj));
			deck.add(loadYellowCardDeck(obj));
			deck.add(loadGreenCardDeck(obj));
			deck.add(loadVioletCardDeck(obj));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return deck;
		
	}
	
	private BlueCardDeck loadBlueCardDeck(JSONObject json){
		JSONArray list = (JSONArray) json.get("Blue");
		ArrayList<BlueCard> blueCardList = new ArrayList<BlueCard>();
		for (Object o : list){
			try {
				blueCardList.add(loadBlueCard((JSONObject)o));
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
				yellowCardList.add(loadYellowCard((JSONObject)o));
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
				greenCardList.add(loadGreenCard((JSONObject)o));
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
				violetCardList.add(loadVioletCard((JSONObject)o));
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
		return new GreenCard(getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}
	
	private VioletCard loadVioletCard(JSONObject json) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException{
		return new VioletCard(getCardEpoch(json), getCardColor(json), getCardName(json), getRequirements((JSONObject)json.get("Requirement")), getEffects((JSONObject)json.get("Effect")));
	}
	
	
	private Epoch getCardEpoch(JSONObject json){
		return new Epoch(EpochEnumeration.valueOf(json.get("Epoch").toString()));
	}
	
	private Color getCardColor(JSONObject json){
		return new Color(ColorEnumeration.valueOf(json.get("Color").toString()));
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
					Object object = Class.forName(requirementList.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
					Method method = object.getClass().getDeclaredMethod("setAmount",Integer.class);
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
				if (Class.forName(json.keySet().toArray()[i].toString()).equals(ActivableEffect.class)){
					list.add(createActivable(effectList));
				} else {
					ArrayList<ActionResult> resList = new ArrayList<ActionResult>(); //lista dei singoli componenti
					Object object = Class.forName(json.keySet().toArray()[i].toString()).newInstance(); //creo immediate o activable o permanent o endgame
					Method setList = object.getClass().getDeclaredMethod("setEffectList", resList.getClass()); //non si può mettere ArrayList<ActionResult>.Class quindi uso una variabile uguale e prendo la sua classe
					for (int j = 0; j < effectList.keySet().toArray().length; j++){ //ciclo le risorse/azioni aggiugnendole ad un arraylist
						System.out.println(effectList.keySet().toArray()[j].toString());
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

		Object actionObject = Class.forName(json.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
		Method method = actionObject.getClass().getDeclaredMethod("setValue",Integer.class);
		method.invoke(actionObject, Integer.parseInt(json.get(json.keySet().toArray()[j].toString()).toString()));
		return (ActionResult)actionObject; //cast esplicito a action result necessario per aggiungerlo alla lista. L'oggetto in se non perde la propria classe
	}

	private BonusWithMultiplier createBonusWithMultiplier(JSONObject json) throws NumberFormatException, InstantiationException, IllegalAccessException, ClassNotFoundException{

			return new BonusWithMultiplier(Float.parseFloat(json.get("Multiplier").toString()),
				(Resource)Class.forName(json.get("Return").toString()).newInstance(),
				 Class.forName(json.get("ResourceToCount").toString()));
	}

}
