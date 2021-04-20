package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import application.Main;
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
import javafx.scene.layout.Pane;
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
    private Button btUpdate;   

    /**
   	 * Este metodo cambia la escena de la ventana por la visualizacion de los alumnos.
   	 * @param event Evento
   	 */	
    @FXML
    void openVisualizationAction(ActionEvent event) {
    	
    	changeScene(GenericUtils.viewAlumnsWindow);
    	
    }
    /**
   	 * Este metodo abre el explorador de archivos para cargar un archivo CSV
   	 * @param e Evento
   	 */	
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
    /**
   	 * Este metodo cambia la escena de la ventana por la de crear ciclos.
   	 * @param event Evento
   	 */	
    @FXML
    void openCreateGrades(ActionEvent event)
    {
    	CreateGradesController.grade = null;
    	CreateGradesController.moduls = null;
    	Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource("CreateGrades.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private JSONObject lastUpdatedGrade;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// Combobox select listener
    	comboCicles.valueProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue ov, String t, String t1) {
    	        updateAccordion(t1);
    	    }
    	});
    	
    	loadComboGrades();
	}
    /**
   	 * Este metodo carga los ciclos en el combobox
   	 * @param event Evento
   	 */	
    private void loadComboGrades() {
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
    /**
   	 * Este metodo actualiza el contenido del acordeon
   	 * @param sGrade String que contiene los ciclos separados por guiones
   	 */	
	private void updateAccordion(String sGrade)
    {
		
		// Updates Accordion with grade data
		String sGradeCode = sGrade.split(" - ")[0];
    	JSONObject grade = RequestUtils.gradeRequest(sGradeCode);
    	if (grade != null)
    	{
    		accordMps.getPanes().clear();
    		lastUpdatedGrade = grade;
        	
    		JSONArray mps = null;
    		try {
    			mps = grade.getJSONArray("arrayMO");
    		} catch(Exception e) {}
        	
    		if (mps != null)
    		{
    			for (int i = 0; i < mps.length(); i++)
            	{
            		JSONObject mp = mps.getJSONObject(i);
            		JSONArray ufs = null;
            		try {
            			ufs = mp.getJSONArray("arrayUF");
            		} catch(Exception e) {}
            		
            		ArrayList<String> ufNames = new ArrayList<String>(); 
            		if (ufs != null)
            		{
            			for (int j = 0; j < ufs.length(); j++)
                		{
                			JSONObject uf = ufs.getJSONObject(j);
                			ufNames.add(uf.getString("UFName") + " - " + uf.getString("UFDuration") + "h");  
                		}
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

