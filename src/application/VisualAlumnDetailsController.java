package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.GenericUtils;
import utils.RequestUtils;

public class VisualAlumnDetailsController implements Initializable {

	public static String currentEmail;
	@FXML
	private Label lbEmail;

	@FXML
	private VBox vBoxId;
	
	@FXML
    private Button btActualizar;
	
	private ArrayList<TextField> txtVboxList=new ArrayList<>();
	
	private ArrayList<String> txtList=new ArrayList<>();
	private ArrayList<String> keyList=new ArrayList<>();	

	JSONObject json;
	
	public static JSONObject lastAlumnDetail;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		json = RequestUtils.getAlumn(currentEmail);
		if (json == null)
		{
			Alert alertError = new Alert(AlertType.ERROR,
    		        "No se han podido obtener los detalles del alumno");
    		alertError.setTitle("Error");
    		alertError.showAndWait();
		}
		else
		{
			lastAlumnDetail = json;
			vBoxId.setSpacing(7);

			Iterator<?> permisos = json.keys();
			while (permisos.hasNext()) {
				String key = (String) permisos.next();

				if (key.equals("matriculatedUfs") ) {	
					
				} else if(key.equals("selectedDocumentsProfile")){					
					
				}else if(key.equals("password")||key.equals("sessionToken") || key.equals("_id")){
					
				}
				else {
					String txt=json.getString(key);
					
					txtList.add(txt);
					keyList.add(key);
					Label l = new Label(key);
					TextField tf = new TextField(txt);
					txtVboxList.add(tf);
					vBoxId.getChildren().add(l);
					vBoxId.getChildren().add(tf);
					
				}
			}
		}
		
		

	}


    @FXML
    void updateJSON(ActionEvent event) {

    	JSONObject updateAlumn = new JSONObject();
    	updateAlumn.put("email", currentEmail);
    	JSONObject updatedFields = new JSONObject();
    	for(int i=0;i<txtVboxList.size();i++) {
    		String sTxt = txtVboxList.get(i).getText();
    		if (!sTxt.equals(txtList.get(i)))
    		{
    			// Campo modificado, informar en el JSON de updatear
    			updatedFields.put(keyList.get(i), sTxt);
    		}
		}
    	updateAlumn.put("updatedFields", updatedFields);
    	
    	boolean response = RequestUtils.updateAlumn(updateAlumn);
    	if (response) {
    		Alert alertOk = new Alert(AlertType.INFORMATION,
    		        "Se ha actualizado el alumno");
    		alertOk.setTitle("Actualizado correctamente");
    		alertOk.showAndWait();
    	}
    	else
    	{
    		Alert alertError = new Alert(AlertType.ERROR,
    		        "No se ha podido actualizar el alumno");
    		alertError.setTitle("Error al actualizar");
    		alertError.showAndWait();
    	}
    }
    
    @FXML
    void backButtonAction(ActionEvent event)    
    {
    	Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource("VisualAlumn.fxml"));
			root.getChildren().add(GenericUtils.menu);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void seeDocumentsButtonAction(ActionEvent e)
    {
    	Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource("DetailAlumnListFiles.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
    }
}
