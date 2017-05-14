package it.polimi.ingsw.ps05.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
                	Epoch epoch = new Epoch(Integer.parseInt(card.get("Epoch").toString()));
                	Color color = new Color(card.get("Color").toString());
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

}
