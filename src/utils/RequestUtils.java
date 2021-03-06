package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestUtils {
	
	public final static String[] hContentJson = {"Content-Type", "application/json; charset=UTF-8"};
	public final static String[] hAcceptJson = {"Accept", "application/json"};
	
	public final static String[] allowedMethods = {"POST", "GET"};
	
	public final static String hToken = "Access-Token";

	public static HttpURLConnection sendRequest(String sUrl, String requestMethod, String[][] headers, boolean doOuput, String inputString)
	{
		boolean correctMethod = false;
		for (int i = 0; i < allowedMethods.length; i++)
		{
			if (allowedMethods[i].equals(requestMethod))
				correctMethod = true;
		}
		if (!correctMethod)
			return null;
		
		URL url;
		try {			
			// Set url and open connection
			url = new URL (sUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			// Set request method
	    	con.setRequestMethod(requestMethod);
	    	// Set headers with array
	    	for (int i = 0; i < headers.length; i++)
	    	{
	    		if (headers[i].length > 1)		    		
	    			con.setRequestProperty(headers[i][0], headers[i][1]);
	    	}
	    	// Set doOutput
	    	con.setDoOutput(doOuput);
	    	
	    	// Send data
	    	if (requestMethod.equals("POST") && inputString != null)
	    	{	    		
		    	try(OutputStream os = con.getOutputStream()) {
		    	    byte[] input = inputString.getBytes("utf-8");
		    	    os.write(input, 0, input.length);			
		    	}
		    	catch(Exception e) {
		    		
		    	}
	    	}	    	
	    	
	    	return con;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static String[] setAccessToken(String token)
	{
		String accTok[] = new String[2];
		accTok[0] = hToken;
		accTok[1] = token;
		return accTok;
	}
	
	public static int getResponseCode(HttpURLConnection con)
	{
		try {
			return con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String getResponse(HttpURLConnection con)
	{
		String response = "";
		try {
			Scanner in = new Scanner(con.getInputStream(), StandardCharsets.UTF_8);
			while (in.hasNextLine())
			{
				response += in.nextLine();
			}	
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	
	
	
	
	// ****************
	// Request sends
	// ****************
	
	public static JSONArray allGradesRequest() {
    	// Makes request to /get/AllGrades and return response as JSONArray
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epAllGrades;
    	String headers[][] = new String[2][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "GET", headers, true, null);
		int responseCode = RequestUtils.getResponseCode(con);
		String response = "";
		if (responseCode == 200)
		{
			response = RequestUtils.getResponse(con);
			if (response != null)
				return new JSONArray(response);
		}		
		return null;			
	}
	
	public static JSONObject gradeRequest(String careerCode) {
		
		// Makes request to /get/grade?careedCode= and return response as JSONObject
		String query = "?careerCode=" + careerCode.replaceAll(" ", "+");
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epGrade + query;
    	String headers[][] = new String[2][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "GET", headers, true, null);
		int responseCode = RequestUtils.getResponseCode(con);
		String response = "";
		if (responseCode == 200)
		{
			response = RequestUtils.getResponse(con);
			if (response != null)
				return new JSONObject(response);
		}
		return null;		
	}
	
	public static ArrayList<JSONObject> insertGrades(ArrayList<JSONObject> grades, boolean overwrite)
	{
		ArrayList<JSONObject> conflictGrade = new ArrayList<JSONObject>();
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epInsertGrade;
    	String headers[][] = new String[3][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
    	headers[2] = RequestUtils.hContentJson;
    	int inserted = 0;
    	for (int i = 0; i < grades.size(); i++)
    	{
    		JSONObject grade = new JSONObject();
    		grade.put("grade", grades.get(i));
    		String sOverwrite = "false";
    		if (overwrite){
    			sOverwrite = "true";
    		}
    		grade.put("overwrite", sOverwrite);
    		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, grade.toString());
			int responseCode = RequestUtils.getResponseCode(con);
			if (responseCode == 400)
			{
				conflictGrade.add(grades.get(i));
			}
    	}
    	return conflictGrade;				
	}
	
	public static int insertProfileReq(JSONObject documentsProfile, boolean overwrite)
	{
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epInsertDocumentsProfile;
    	String headers[][] = new String[3][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
    	headers[2] = RequestUtils.hContentJson;
    	
		JSONObject documentsProfilePost = new JSONObject();
		documentsProfilePost.put("documentsProfile", documentsProfile);
		String sOverwrite = "false";
		if (overwrite){
			sOverwrite = "true";
		}
		documentsProfilePost.put("overwrite", sOverwrite);
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, documentsProfilePost.toString());
		int responseCode = RequestUtils.getResponseCode(con);
		return responseCode;		  					
	}
	
	public static JSONArray allAlumnsRequest(String careerCode) {
    	// Makes request to /get/allAlumns and return response as JSONArray
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epGetAllAlumns + "?careerCode=" + careerCode.replaceAll(" ", "+");
    	String headers[][] = new String[2][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "GET", headers, true, null);
		int responseCode = RequestUtils.getResponseCode(con);
		String response = "";
		if (responseCode == 200)
		{
			response = RequestUtils.getResponse(con);
			if (response != null)
				return new JSONArray(response);
		}		
		return null;				
	}
	
	public static ArrayList<JSONObject> insertAlumns(ArrayList<JSONObject> alumns)
	{
		ArrayList<JSONObject> conflictAlumn = new ArrayList<JSONObject>();
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epInsertAlumn;
    	String headers[][] = new String[3][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
    	headers[2] = RequestUtils.hContentJson;
    	int inserted = 0;
    	for (int i = 0; i < alumns.size(); i++)
    	{
    		JSONObject alumnPost = new JSONObject();
    		alumnPost.put("alumn", alumns.get(i));
    		
    		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, alumnPost.toString());
			int responseCode = RequestUtils.getResponseCode(con);
			if (responseCode == 400)
			{
				conflictAlumn.add(alumns.get(i));
			}
    	}		
    	return conflictAlumn;
	}	

	public static boolean updateAlumn(JSONObject updateAlumnPost)
	{
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epUpdateAlumn;
    	String headers[][] = new String[3][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
    	headers[2] = RequestUtils.hContentJson;		
		
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, updateAlumnPost.toString());
		int responseCode = RequestUtils.getResponseCode(con);
		if (responseCode == 200)
		{
			return true;
		}
		else
		{
			return false;
		}  
	}

	public static boolean validateDocument(String email, String profileName, String documentName)
	{
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epValidateFile;
    	String headers[][] = new String[3][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
    	headers[2] = RequestUtils.hContentJson;
    	
		JSONObject jsPost = new JSONObject();
		jsPost.put("email", email);
		jsPost.put("profileName", profileName);
		jsPost.put("documentName", documentName);		
		
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, jsPost.toString());

		int responseCode = RequestUtils.getResponseCode(con);
		if (responseCode == 200)
		{
			return true;
		}
		else
		{
			return false;
		}    	
	}
	public static JSONObject getAlumn(String email) {
    	// Makes request to /get/alumn and return response as JSONArray
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epGetAlumn + "?username=" + email.replaceAll("@", "%40");
    	String headers[][] = new String[2][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "GET", headers, true, null);
		int responseCode = RequestUtils.getResponseCode(con);
		String response = "";
		if (responseCode == 200)
		{
			response = RequestUtils.getResponse(con);
			if (response != null)
				return new JSONObject(response);
		}		
		return null;
}
	public static JSONObject getDocument(String email, String profileName, String documentName)
	{
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epGetDocument;
    	String headers[][] = new String[3][2];
    	headers[0] = RequestUtils.hAcceptJson;
    	headers[1] = RequestUtils.setAccessToken(GenericUtils.currentToken);
    	headers[2] = RequestUtils.hContentJson;
    	
		JSONObject jsPost = new JSONObject();
		jsPost.put("email", email);
		jsPost.put("profileName", profileName);
		jsPost.put("documentName", documentName);		
		
		HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, jsPost.toString());
		int responseCode = RequestUtils.getResponseCode(con);
		if (responseCode == 200)
		{
			return new JSONObject(RequestUtils.getResponse(con));
		}
		else
		{
			return null;
		} 
	}
}
