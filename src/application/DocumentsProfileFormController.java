package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utils.RequestUtils;

public class DocumentsProfileFormController implements Initializable{

    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextArea txtDesc;
    
    @FXML
    private VBox vboxList;
    
    @FXML
    private ScrollPane scrollPaneList;
    
    @FXML
    private Button btAddReq;
    
    private ArrayList<TextField> txtVboxList;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	vboxList.setSpacing(7);
    	txtVboxList = new ArrayList<TextField>();
    	addThreeFields();
    	
	}
    
    @FXML
    public void saveAction() {
    	// Build json object
    	String sName = txtName.getText();
    	String sDesc = txtDesc.getText();
    	ArrayList<String> sAllReqs = getAllReqsFromVbox();
    	
    	JSONObject reqProfile = new JSONObject();
    	reqProfile.put("name", sName);
    	reqProfile.put("description", sDesc);
    	
    	JSONArray jReqs = new JSONArray();
    	for (String sReq : sAllReqs)
    	{
    		JSONObject jReq = new JSONObject();
    		jReq.put("documentName", sReq);
    		jReqs.put(jReq);
    	}
    	reqProfile.put("arrayDoc", jReqs);

    	// Make request insert profile
    	int responseCode = RequestUtils.insertProfileReq(reqProfile, false);
    	if (responseCode == 200) // ok
    	{
    		Alert alertOk = new Alert(AlertType.INFORMATION,
			        "Se han guardado el perfil correctamente");
    		alertOk.setTitle("Insertado correctamente");
    		alertOk.showAndWait();
    	}
    	else if (responseCode == 400) // conflict name
    	{
    		// Want overwerite ?
    		Alert alertOverwrite = new Alert(AlertType.CONFIRMATION,
			        "El perfil con el nombre: " + sName + " ya existe en la base de datos. ¿Quieres sobreescribirlo?");
			alertOverwrite.setTitle("Perfil con nombre existente");
			ButtonType result = alertOverwrite.showAndWait().orElse(ButtonType.NO);
			if (ButtonType.OK.equals(result)) {	
				int responseCodeOverwrite = RequestUtils.insertProfileReq(reqProfile, true); // overwrite request
				if (responseCodeOverwrite == 200) // overwrite ok
		    	{
		    		Alert alertOk = new Alert(AlertType.INFORMATION,
					        "Se han guardado el perfil correctamente");
		    		alertOk.setTitle("Insertado correctamente");
		    		alertOk.showAndWait();
		    	} 
				else // error overwrite
		    	{
		    		Alert alertError = new Alert(AlertType.ERROR,
					        "Error de conexion al guardar el perfil");
		    		alertError.setTitle("Error");
		    		alertError.showAndWait();
		    	}
			}	
    	}
    	else // error
    	{
    		Alert alertError = new Alert(AlertType.ERROR,
			        "Error de conexion al guardar el perfil");

    		alertError.setTitle("Error");
    		alertError.showAndWait();
    	}
    	
    }
    
    private ArrayList<String> getAllReqsFromVbox() {
    	ArrayList<String> allReqs = new ArrayList<String>();
    	for (TextField txt : txtVboxList)
		{
    		String sTxt = txt.getText();
    		if (sTxt != null && !sTxt.isBlank())
    		{
    			allReqs.add(sTxt);
    		}
		}
		return allReqs;
	}

	@FXML
    public void addThreeFields() {
    	for (int i = 0; i < 3; i++)
    	{
    		TextField txt = new TextField();
    		txtVboxList.add(txt);
    		vboxList.getChildren().add(txt);
    		txt.setMaxWidth(vboxList.getMaxWidth());
    		
    	}
    }
    
	
	public void changeScene(String fxmlName)
	{
		Parent root;
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

