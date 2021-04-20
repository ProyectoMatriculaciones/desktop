package application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import utils.GenericUtils;
import utils.RequestUtils;

public class SampleController {

    @FXML
    private Button bnLogin;

    @FXML
    private TextField usr;

    @FXML
    private PasswordField pass;
    /**
	 * Este metodo envia a la API los datos introducidos y los comprueba con la bd para ver si están registrados
	 * @param event Evento
	 */	
    @FXML
    void Login(ActionEvent event) {

    	// Hash pass to md5
    	String sPass = pass.getText();
    	String md5Pass = "";
    	byte[] bMd5Pass;
    	try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bMd5Pass = md.digest(sPass.getBytes("UTF-8"));
			BigInteger no = new BigInteger(1, bMd5Pass);
			md5Pass = new String(bMd5Pass);			
			md5Pass = no.toString(16);
            while (md5Pass.length() < 32) {
            	md5Pass = "0" + md5Pass;
            }
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
    	// Login request    	
    	String sUrl = GenericUtils.apiUrl + GenericUtils.epLogin;
    	String[][] headers = new String[2][2];
    	headers[0] = RequestUtils.hContentJson;
    	headers[1] = RequestUtils.hAcceptJson;
    	String jsonInputString = "{\"username\": \""+usr.getText()+"\", \"password\": \""+md5Pass+"\"}";    	
    	HttpURLConnection con = RequestUtils.sendRequest(sUrl, "POST", headers, true, jsonInputString);
    	int responseCode = RequestUtils.getResponseCode(con);
    	
    	if (responseCode == 200)
    	{
    		// Save token
    		String sResponse = RequestUtils.getResponse(con);
    		JSONObject jResponse = new JSONObject (sResponse);
    		GenericUtils.currentToken = jResponse.getString("statusData");
    		
    		// Next scene after login
    		changeScene(GenericUtils.viewGradesWindow);
    		
    	}
    	else if (responseCode == 400)
    	{
    		// No deja leer la response en caso de status 400, da error
    		/*String sResponse = RequestUtils.getResponse(con);
    		JSONObject jResponse = new JSONObject (sResponse);*/
    		 Alert alert = new Alert(AlertType.ERROR);
        	 alert.setTitle("Error al iniciar sesion");
        	 alert.setContentText("Los datos de inicio de sesion son incorrectos");
        	 alert.showAndWait();
    	}
    	else if (responseCode == -1)
    	{
    		// No deja leer la response en caso de status 400, da error
    		/*String sResponse = RequestUtils.getResponse(con);
    		JSONObject jResponse = new JSONObject (sResponse);*/
    		 Alert alert = new Alert(AlertType.ERROR);
        	 alert.setTitle("Error de conexion");
        	 alert.setContentText("no se ha podido establecer la conexion");
        	 alert.showAndWait();
    	}
    }
    
    
    /**
	 * Este metodo cambia la escena de la ventana de la aplicación.
	 * @param fxmlName String que contiene el nombre del archivo fxml que usa la escena. 
	 */	
    public void changeScene(String fxmlName)
	{
		Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxmlName));
			root.getChildren().add(GenericUtils.menu);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

