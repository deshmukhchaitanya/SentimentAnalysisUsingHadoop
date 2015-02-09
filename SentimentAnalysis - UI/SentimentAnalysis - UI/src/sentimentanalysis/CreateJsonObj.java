package sentimentanalysis;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;


public class CreateJsonObj 
{

	public void readJobSummary(String inputLocation) 
	{
		File inFile=new File(inputLocation);
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
		ArrayList<ArrayList<?>> foo = new ArrayList<ArrayList<?>>();
		ArrayList<Object> foo1=new ArrayList<>();
		foo1.add("City");
		foo1.add("Sentiments");
		foo1.add("Tweet Count");
		foo.add(foo1);
		BufferedReader reader =new BufferedReader(fileReader);
		try 
		{
			for(String line; (line=reader.readLine())!=null;)
			{
				String[] lineInArray= line.trim().split("\t");
				ArrayList<Object> foo2=new ArrayList<>();
				foo2.add(lineInArray[0]);
				foo2.add(Double.valueOf(lineInArray[1]));
				foo2.add(Double.valueOf(lineInArray[2]));
				foo.add(foo2);
			}
		}
		catch (IOException e) {
			System.out.println("file could not be read");
			e.printStackTrace();
		}
		String json = new Gson().toJson(foo);
		
		File htmlTemplateFile = new File("lib/showResult.html");
		String htmlString;
		try 
		{
			htmlString = FileUtils.readFileToString(htmlTemplateFile);
			htmlString = htmlString.replace("$tag", json);
			File newHtmlFile = new File("result.html");
			FileUtils.writeStringToFile(newHtmlFile, htmlString);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

                System.out.println("Completed Processing...");
	}

}