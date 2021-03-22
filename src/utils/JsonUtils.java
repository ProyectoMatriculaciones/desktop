package utils;

import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {

	public static ArrayList<String> parseAllGrades (JSONArray allGrades){
		ArrayList<String> sAllGrades = new ArrayList<String>();
		
		for (int i = 0; i < allGrades.length(); i++)
		{
			JSONObject grade = allGrades.getJSONObject(i);
			String sGrade = grade.getString("careerCode") + " - " + grade.getString("careerName");
			sAllGrades.add(sGrade);
		}
		
		return sAllGrades;
	}
	
	
}
