package com.location;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.xml.ws.http.HTTPException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class LocationFinder 
{
	private static final String urlPart1="https://maps.googleapis.com/maps/api/geocode/json?latlng=";
	private static final String urlPart2="&key=";
	private static final String apiKey=""; //Add API key 

	
	private static final String INPUT_FILE = "ToConvert/Tweetpartae.txt";
	private static final String OUTPUT_FILE = "ToConvert/Tweetpartae.result";
	public static void main(String[] args) 
	{
		File inFile=new File(INPUT_FILE);
		FileReader fileReader = null;
		try 
		{
			fileReader = new FileReader(inFile);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("in file not found or could not be opened");
			e.printStackTrace();
		}

		HashMap<String,String> locations=new HashMap<String,String>(); 
		BufferedReader reader =new BufferedReader(fileReader);
		try 
		{
			for(String line; (line=reader.readLine())!=null;)
			{
				if(line.indexOf("UT:")!=-1)
				{
					String[] lineInArray= line.trim().split("\t");
					String coordinates=lineInArray[0].substring(4);
					if(!locations.containsKey(coordinates.trim()))
						locations.put(coordinates.trim(),"");
				}
			}
		}
		catch (IOException e) {
			System.out.println("file could not be read");
			e.printStackTrace();
		}

		Iterator<Entry<String, String>> entries = locations.entrySet().iterator();
		BufferedReader br = null;
		while (entries.hasNext()) 
		{
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			String coordinates=key.toString();
			String apiURL = null;
				
			try	
			{
				// Sample url: https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=API_KEY
				apiURL=urlPart1+coordinates+urlPart2+apiKey;
				URL url = new URL(apiURL);
				System.out.println(apiURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				if (conn.getResponseCode() != 200) 
				{
					System.out.println(conn.getURL() + "     " + conn.getResponseCode());
					throw new HTTPException(conn.getResponseCode());
				}
				br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				JsonReader jsonReader = new JsonReader(br);
				JsonParser parser = new JsonParser();
				JsonArray Jarray = (JsonArray) parser.parse(jsonReader).getAsJsonObject().get("results");

				JsonElement obj = Jarray.get(0);
				JsonObject obj1=obj.getAsJsonObject();
				String address=obj1.get("formatted_address").toString();
				String[] addressArray=address.split(",");
				if (addressArray.length >= 3) {
					String[] state=addressArray[2].split(" ");
					String city=addressArray[1]+", "+state[1];
					locations.put(coordinates, city);
				}else{
					locations.put(coordinates, "Phoenix, AZ");
				}

				conn.disconnect();
			}
			catch (MalformedURLException e) 
			{
				System.out.println("Something went wrong while fetching data API, Please check the URL");
			} catch (IOException e) {

				System.out.println("Something went wrong while fetching data from API, Please check the Input/Output Streams");
			}
		}

		System.out.println("Location size " + locations.size());
		
		File inFileAgain=new File(INPUT_FILE);
		FileReader fileReaderAnagin = null;
		File outFile=new File(OUTPUT_FILE);
		FileWriter fileWriter = null;
		try 
		{
			if (!outFile.exists()) 
			{
				outFile.createNewFile();
			}
			fileReaderAnagin = new FileReader(inFileAgain);
			fileWriter = new FileWriter(outFile);
		} 
		catch (Exception e) 
		{
			System.out.println("in file not found or could not be opened");
			e.printStackTrace();
		}
		BufferedWriter writer =new BufferedWriter(fileWriter);
		BufferedReader readerAgain =new BufferedReader(fileReaderAnagin);
		try 
		{
			for(String line; (line=readerAgain.readLine())!=null;)
			{
				if(line.indexOf("UT:")!=-1)
				{
					String[] lineInArray= line.trim().split("\t");
					String coordinates=lineInArray[0].substring(4);
					String city=locations.get(coordinates.trim());
					writer.write(city);
					writer.write("\t");
					writer.write(lineInArray[1]);
					writer.newLine();
					writer.flush();
				}
			}
		}
		catch (IOException e) {
			System.out.println("file could not be read");
			e.printStackTrace();
		}
	}
}
