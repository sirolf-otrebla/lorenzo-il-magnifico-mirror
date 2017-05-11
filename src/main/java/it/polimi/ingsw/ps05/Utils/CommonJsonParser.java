package it.polimi.ingsw.ps05.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonJsonParser {

	public static void main(String[] args) {
		try {     

			File file = new File("C:\\Users\\lucaf\\git\\lorenzo-il-magnifico\\src\\main\\res\\cards.json");
			System.out.println(new File(".").getAbsolutePath());
			System.out.println(file.exists());
			JSONParser parser = new JSONParser();

			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));

			JSONObject jsonObject =  (JSONObject) obj;

			//System.out.println(jsonObject);
			Object key[] = jsonObject.keySet().toArray();

			for (int j = 0; j < key.length; j++){
				JSONArray first = (JSONArray)jsonObject.get(key[j].toString());

				int i = 1;
				for (Object o : first){
					JSONObject card = (JSONObject) o;
					System.out.println(i + " " + card);
					i++;
				}
				i = 1;
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
