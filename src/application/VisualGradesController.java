package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import utils.GenericUtils;
import utils.JsonUtils;
import utils.RequestUtils;

public class VisualGradesController implements Initializable{

    @FXML
    private Accordion accordMps;
    
    @FXML
    private ComboBox<String> comboCicles;
    
    @FXML
    private Label lblCombo;
    
    @FXML
    private Label lblAccord;
    
    @FXML
    private Button btImport;
    
    @FXML 
    void openImportAction(ActionEvent e)
    {
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Buscar Archivo CSV");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
		File csvFile = null;

		// Dialog for get a csv file 
		csvFile = fileChooser.showOpenDialog(Main.stage);
		if (csvFile == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Importacion de ciclos");
			alert.setContentText(
					"Es necesario seleccionar un archivo CSV para acceder a la pantalla de importacion de datos");
			alert.showAndWait();
		}
		else {
			Main.importCsvFile = csvFile;
			changeScene(GenericUtils.importWindow);
		}
    	
    }
	
    
    private JSONObject lastUpdatedGrade;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// Set labels
    	lblCombo.setText("Ciclos: ");
    	lblAccord.setText("Modulos y UF's");
    	
    	// Combobox select listener
    	comboCicles.valueProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue ov, String t, String t1) {
    	        updateAccordion(t1);
    	    }
    	});
    	
    	// Get JSONArray of allGrades from API
    	JSONArray allGrades = RequestUtils.allGradesRequest();    	
    	if (allGrades != null)
    	{
    		// Set Combobox data
        	ArrayList<String> sAllGrades = JsonUtils.parseAllGrades(allGrades);				
    		comboCicles.setItems(FXCollections.observableArrayList(sAllGrades));
    	}
    	else
    	{
    		System.out.println("Error al obtener la lista de ciclos");
    	}
	}
    
    

	private void updateAccordion(String sGrade)
    {
		
		// Updates Accordion with grade data
		String sGradeCode = sGrade.split(" - ")[0];
    	JSONObject grade = RequestUtils.gradeRequest(sGradeCode);
    	if (grade != null)
    	{
    		accordMps.getPanes().clear();
    		lastUpdatedGrade = grade;
        	
        	JSONArray mps = grade.getJSONArray("arrayMO");
        	for (int i = 0; i < mps.length(); i++)
        	{
        		JSONObject mp = mps.getJSONObject(i);
        		JSONArray ufs = mp.getJSONArray("arrayUF");
        		
        		ArrayList<String> ufNames = new ArrayList<String>();    		
        		for (int j = 0; j < ufs.length(); j++)
        		{
        			JSONObject uf = ufs.getJSONObject(j);
        			ufNames.add(uf.getString("UFName") + " - " + uf.getString("UFDuration") + "h");  
        		}	
        		ListView<String> lv = new ListView<String>();
        		lv.setItems(FXCollections.observableArrayList(ufNames));	
        		lv.setPrefHeight(100);
        		AnchorPane content = new AnchorPane();
        		content.getChildren().add(lv);
        		content.setLeftAnchor(lv, 0.0);
        		content.setRightAnchor(lv, 0.0);
               
                
        		TitledPane tp = new TitledPane(mp.getString("MOName"), content);
        		accordMps.getPanes().add(tp);
        		
        	}
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

