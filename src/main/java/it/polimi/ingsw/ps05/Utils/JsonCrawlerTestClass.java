package it.polimi.ingsw.ps05.Utils;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ExcommunicationEffects.ExcommunicationEffect;

import java.lang.reflect.Constructor;

import org.json.*;
import org.json.simple.JSONObject;

/* TEST CLASS FOR JSON CRAWLER */ 

public class JsonCrawlerTestClass {

	public JsonCrawlerTestClass(){
		
	}
	
	public ExcommunicationEffect getExcommunicationfromStringNoConstructor(String string){
		try{ 
				Class<?> myClass = Class.forName(string);
				ExcommunicationEffect obj = (ExcommunicationEffect) myClass.newInstance();
				//for (Class<?> a : obj.getClass().getInterfaces()){
				//	if (a.equals(ExcommunicationEffect.class));
				// }
				return obj;
				
		
		} catch (Exception e){
			// no exception managing required
			System.out.println("something has gone wrong!!!");
			System.out.println(e.getStackTrace());
		}
		return null;
		
	}
	
	public ExcommunicationEffect getExcommunicationFromString(String str){
		try{
			Class<?> myClass = Class.forName(str);
			Constructor<?> constructor = myClass.getConstructor(JSONObject.class);
			return (ExcommunicationEffect) constructor.newInstance(new JSONObject());
			
		} catch (Exception e){
			// no exception managing required
			System.out.println("something has gone wrong!!!");
			System.out.println(e.getStackTrace());
			
		}
		
		return null;
		
	}
}
