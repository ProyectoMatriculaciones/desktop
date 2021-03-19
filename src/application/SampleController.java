package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SampleController {

    @FXML
    private Button bnLogin;

    @FXML
    private TextField usr;

    @FXML
    private PasswordField pass;

    @FXML
    void Login(ActionEvent event) {

    	URL url;
		try {
			
			url = new URL ("https://api-matriculacioones.herokuapp.com/login/alumn");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
	    	con.setRequestMethod("POST");
	    	con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    	con.setRequestProperty("Accept", "application/json");
	    	con.setDoOutput(true);
	    	String jsonInputString = "{\"username\": \""+usr.getText()+"\", \"password\": \""+pass.getText()+"\"}";
	    	System.out.println(jsonInputString);
	    	try(OutputStream os = con.getOutputStream()) {
	    	    byte[] input = jsonInputString.getBytes("utf-8");
	    	    os.write(input, 0, input.length);			
	    	}
	    	int responseCode=con.getResponseCode();
	    	if(responseCode==200) {
	    		try(BufferedReader br = new BufferedReader(
	      			  new InputStreamReader(con.getInputStream(), "utf-8"))) {
	      			    StringBuilder response = new StringBuilder();
	      			    String responseLine = null;
	      			    while ((responseLine = br.readLine()) != null) {
	      			        response.append(responseLine.trim());
	      			    }
	      			    System.out.println(response.toString());
	      		}
	    	}else if (responseCode == 400) {
	    		System.out.println("response code = 400");
	    	}
	    	else
	    	{
	    		
	    	}
	    	
	    	

	    	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    	
    }

}
