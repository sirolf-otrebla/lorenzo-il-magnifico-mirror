package it.polimi.ingsw.ps05.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.json.simple.*;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.*;

public class CommonJsonParser {

	public CommonJsonParser(){
		
	}

	public void parse() {
		
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
