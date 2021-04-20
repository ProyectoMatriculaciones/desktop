package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import utils.GenericUtils;

public class CreateModulsController implements Initializable{

    
   @FXML
   private TextField txtCode;
   
   @FXML
   private TextField txtName;
   
   @FXML
   private TextField txtMinDuration;
   
   @FXML
   private TextField txtMaxDuration;
   
   @FXML
   private TextField txtDateInit;
   
   @FXML
   private TextField txtDateFinish;   
   
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	
    	
	}
    
    /**
	 * Este metodo Recoge los parametros introducidos, los convierte en el modulo a insertar y lo a�ade
	 * @param event Evento
	 */
    @FXML
    public void addModul(ActionEvent event)
    {
    	String code = txtCode.getText();
    	String minDuration = txtMinDuration.getText();
    	String name = txtName.getText();
    	String maxDuration = txtMaxDuration.getText();
    	String dateInit = txtDateInit.getText();
    	String dateFinish = txtDateFinish.getText();
    	
    	if ((code != null && !code.isBlank()) &&
    			(minDuration != null && !minDuration.isBlank()) &&
    			(name != null && !name.isBlank()) &&
    			(maxDuration != null && !maxDuration.isBlank()) &&
    			(dateInit != null && !dateInit.isBlank()) &&
    			(dateFinish != null && !dateFinish.isBlank()))
    	{
    		JSONObject modul = new JSONObject();
    		modul.put("MOCode", code);
    		modul.put("MOName", name);
    		modul.put("MOMinDuration", minDuration);
    		modul.put("MOMaxDuration", maxDuration);
    		modul.put("MOSartDate", dateInit);
    		modul.put("MOFinishDate", dateFinish);
    		
    		// add modul to list and change scene
    		
    		if (CreateGradesController.moduls == null)
    		{
    			CreateGradesController.moduls = new ArrayList<JSONObject>();
    		}
    		CreateGradesController.moduls.add(modul);
    		
    		changeScene("CreateGrades.fxml");
    		
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error datos");
			alert.setContentText(
					"Falta por rellenar algun campo obligatorio");
			alert.showAndWait();
    	}
    }  
    /**
   	 * Este metodo muestra un mensaje de confirmaci�n avisando que se perderan los datos 
   	 * @param event Evento
   	 */
    @FXML
    void backAction(ActionEvent event)
    {
    	Alert alertOverwrite = new Alert(AlertType.CONFIRMATION,
		        "Perderas los cambios actuales, �Desea continuar?");

		alertOverwrite.setTitle("Datos no guardados");
		ButtonType result = alertOverwrite.showAndWait().orElse(ButtonType.NO);
		if (ButtonType.OK.equals(result)) {	
			changeScene("CreateGrades.fxml");
		}	
    }
    /**
   	 * Este metodo cambia la escena de la ventana
   	 * @param fxmlName nombre del archivo fxml
   	 * @throws IOException
   	 */
	public void changeScene(String fxmlName)
	{
		Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxmlName));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

