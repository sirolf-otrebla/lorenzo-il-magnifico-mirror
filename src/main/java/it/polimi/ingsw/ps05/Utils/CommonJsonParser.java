package it.polimi.ingsw.ps05.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;
import it.polimi.ingsw.ps05.model.*;

public class CommonJsonParser {

	public CommonJsonParser(){
		System.out.println("ciao");
	}

	public void parse() {
		try {     

			File file = new File("C:\\Users\\lucaf\\workspace\\ProvaParser\\src\\res\\cards.json");
			System.out.println(file.exists());
			JSONParser parser = new JSONParser();

			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));

			JSONObject jsonObject =  (JSONObject) obj;

			//System.out.println(jsonObject);
			Object key[] = jsonObject.keySet().toArray();

			for (int j = 0; j < key.length; j++){
				JSONArray first = (JSONArray)jsonObject.get(key[j].toString());


				for (Object o : first){
					JSONObject card = (JSONObject) o;
					Object keyCard[] = card.keySet().toArray();
					for (int k = 0; k < keyCard.length; k++){
						System.out.println(keyCard[k] + "-->" + card.get(keyCard[k]));
					}
					//Epoch epoch = new Epoch(Integer.parseInt(card.get("Epoch").toString()));
					//Color color = new Color(card.get("Color").toString());
					JSONObject effect = (JSONObject) card.get("Effect");
					//System.out.println(effect.keySet());
					ArrayList<ArrayList<Resource>> effects = new ArrayList<ArrayList<Resource>>();
					for (int l = 0; l < effect.keySet().toArray().length; l++){
						if (effect.keySet().toArray()[l].toString().equals("ImmediateEffect")){
							ImmediateEffect immediate = new ImmediateEffect();
							JSONObject effectList = (JSONObject) effect.get("ImmediateEffect");
							for (int m = 0; m < effectList.keySet().toArray().length; m++){
								if (effectList.keySet().toArray()[m].toString().equals("Faith")){
									FaithResource faith = new FaithResource(Integer.parseInt(effectList.get(effectList.keySet().toArray()[m]).toString()));
									System.out.println("Faith: " + faith.getAmount());
									ArrayList<Resource> temp  = new ArrayList<Resource>();
									temp.add(faith);
									effects.add(temp);

								}
							}
						}
					}


				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	public BlueCard parseBlue(JSONObject json) {	//json è l'oggetto della singola carta
		//Object keyCard[] = json.keySet().toArray();
		return new BlueCard(new Epoch(EpochEnumeration.valueOf((json.get("Epoch").toString()))),
				new Color(ColorEnumeration.valueOf((json.get("Color").toString()))),
				new String(json.get("CardName").toString()),
				getRequirements(json),
				null);
	}

	private ArrayList<ArrayList<Resource>> getRequirements(JSONObject json){ //json è l'oggetto che contiene first e second

		ArrayList<ArrayList<Resource>> list = new ArrayList<ArrayList<Resource>>(); //lista con le liste tra cui scegliere
		for (int i = 0; i < json.keySet().toArray().length; i++) {
			JSONObject requirementList = (JSONObject) json.get(json.keySet().toArray()[i]); //entro in first o second
			ArrayList<Resource> resList = new ArrayList<Resource>(); //lista dei singoli componenti
			for (int j = 0; j < requirementList.keySet().toArray().length; j++){ //ciclo le risorse aggiugnendole ad un arraylist
				try {
					Object object = Class.forName(requirementList.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
					Method  method = object.getClass().getDeclaredMethod("setAmount",Integer.class);
					method.invoke(object, Integer.parseInt(requirementList.get(requirementList.keySet().toArray()[j].toString()).toString()));
					resList.add((Resource)object); //cast esplicito a risorsa necessario per aggiungerlo alla lista. L'oggetto in se non perde la propria classe
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
			list.add(resList);
		}

		return list;
	}

	private ArrayList<Effect> getEffects(JSONObject json){ //json è l'oggetto che contiene l'elenco degli effetti
		ArrayList<Effect> list = new ArrayList<Effect>(); //lista con le liste tra cui scegliere/attivare e basta
		try{
			/*TODO: questo metodo funziona con tutto tranne:
			 * 1) attivabili, hanno una struttura leggermente diversa dagli altri in quanto hanno bisogno di più info
			 * 2) quelli con moltiplicatori poiché hanno campi diversi
			 * 3) removeTileEffect poiché non so come strutturarlo
			 * 4) i bonus ai costi (e.g. uno in meno di oro, di pietra, di legno)
			 */
			for (int i = 0; i < json.keySet().toArray().length; i++) { 
				JSONObject effectList = (JSONObject) json.get(json.keySet().toArray()[i]);//entro in immediate o ecc...
				ArrayList<ActionResult> resList = new ArrayList<ActionResult>(); //lista dei singoli componenti
				Object object = Class.forName(json.keySet().toArray()[i].toString()).newInstance(); //creo immediate o activable o permanent o endgame
				Method setList = object.getClass().getDeclaredMethod("setEffectList", resList.getClass()); //non si può mettere ArrayList<ActionResult>.Class quindi uso una variabile uguale e prendo la sua classe
				for (int j = 0; j < effectList.keySet().toArray().length; j++){ //ciclo le risorse/azioni aggiugnendole ad un arraylist
					Object actionObject = Class.forName(effectList.keySet().toArray()[j].toString()).newInstance(); //istanza della classe letta da file ed esecuzione del setter per generare la risorsa
					Method method = actionObject.getClass().getDeclaredMethod("setValue",Integer.class);
					method.invoke(actionObject, Integer.parseInt(effectList.get(effectList.keySet().toArray()[j].toString()).toString()));
					resList.add((ActionResult)actionObject); //cast esplicito a action result necessario per aggiungerlo alla lista. L'oggetto in se non perde la propria classe
				}
				setList.invoke(object, resList);//setto nell'oggetto effetto (qualunque sia) la lista di azioni appena ricavata
				list.add((Effect) object); //aggiungo l'effetto alla lista da ritornare
			}
		}  catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return list;
	}


























}
