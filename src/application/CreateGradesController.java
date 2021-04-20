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
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import utils.GenericUtils;
import utils.RequestUtils;

public class CreateGradesController implements Initializable{

    
   @FXML
   private TextField txtCode;
   
   @FXML
   private TextField txtName;
   
   @FXML
   private TextField txtCodeAdapt;
   
   @FXML
   private TextField txtHours;
   
   @FXML
   private TextField txtDateInit;
   
   @FXML
   private TextField txtDateFinish;
   
   @FXML
   private ListView<String> listModuls;
   
   
   public static JSONObject grade;
   
   public static ArrayList<JSONObject> moduls; 
    
    
   
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// recordar que al abrir pantalla desde boton CREAR CICLO setear statics a null
    	if (grade != null)
    	{
    		try {
    			String sCode = grade.getString("careerCode");
    			txtCode.setText(sCode);
    		} catch(Exception e){	
    		}
    		
    		try {
    			String sName = grade.getString("careerName");
    			txtName.setText(sName);
    		} catch(Exception e){	
    		}
    		
    		try {
    			String sCodeAdapt = grade.getString("curricularAdaptationCode");
    			txtCodeAdapt.setText(sCodeAdapt);
    		} catch(Exception e){	
    		}
    		
    		try {
    			String sHours = grade.getString("careerHours");
    			txtHours.setText(sHours);
    		} catch(Exception e){	
    		}
    		
    		try {
    			String sDateInit = grade.getString("careerStartDate");
    			txtDateInit.setText(sDateInit);
    		} catch(Exception e){	
    		}
    		
    		try {
    			String sDateFinish = grade.getString("careerFinishDate");
    			txtDateFinish.setText(sDateFinish);
    		} catch(Exception e){	
    		}
    		
    	}
    	
    	if (moduls != null)
    	{
    		for (JSONObject jModul : moduls)
			{
    			String modulName = jModul.getString("MOName");
    			listModuls.getItems().add(modulName);
			}
    	}
    	
	}
    
    /**
   	 * Este metodo importa los ciclos 
   	 * @param event Evento
   	 */
    @FXML
    void importGrade(ActionEvent event)
    {
    	// Create grade JSON
    	String code = txtCode.getText();
    	String codeAdapt = txtCodeAdapt.getText();
    	String name = txtName.getText();
    	String hours = txtHours.getText();
    	String dateInit = txtDateInit.getText();
    	String dateFinish = txtDateFinish.getText();
    	
    	if ((code != null && !code.isBlank()) &&
    			(codeAdapt != null && !codeAdapt.isBlank()) &&
    			(name != null && !name.isBlank()) &&
    			(hours != null && !hours.isBlank()) &&
    			(dateInit != null && !dateInit.isBlank()) &&
    			(dateFinish != null && !dateFinish.isBlank()))
    	{
    		grade = new JSONObject();
    		grade.put("careerCode", code);
    		grade.put("careerName", name);
    		grade.put("curricularAdaptationCode", codeAdapt);
    		grade.put("careerHours", hours);
    		grade.put("careerStartDate", dateInit);
    		grade.put("careerFinishDate", dateFinish);
    		
    		if (moduls != null && !moduls.isEmpty())
    		{
    			JSONArray arrayMo = new JSONArray();
    			for (JSONObject jModul : moduls)
    			{
    				arrayMo.put(jModul);
    			}
    			grade.put("arrayMO", arrayMo);
    		}
    		
    		// insert grade    		
    		ArrayList<JSONObject> grades = new ArrayList<JSONObject>();
    		grades.add(grade);
    		ArrayList<JSONObject> conflictGrades = RequestUtils.insertGrades(grades, false);
    		if (conflictGrades != null && !conflictGrades.isEmpty())
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error al importar ciclo");
    			alert.setContentText(
    					"Error o codigo existente al importar el ciclo");
    			alert.showAndWait();
    		}
    		else
    		{
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Ciclo importado correctamente");
    			alert.setContentText(
    					"Ciclo importado correctamente");
    			alert.showAndWait();
    			
    			// set statics to nulls
        		grade = null;
        		moduls = null;
        		
        		// Go back
        		Pane root;
    			try {
    				root = FXMLLoader.load(getClass().getResource(GenericUtils.viewGradesWindow));
    				root.getChildren().add(GenericUtils.menu);
    				Scene scene = new Scene(root);
    				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    				Main.stage.setScene(scene);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}  		
    		
    		
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
   	 * Este metodo almacena los datos introducidos en la ventana del ciclo y una vez guardados abre la ventana de crear el modulo
   	 * @param event Evento
   	 */
    @FXML 
    void createModul(ActionEvent event)
    {
    	// Save data for after create Modul
    	String code = txtCode.getText();
    	String codeAdapt = txtCodeAdapt.getText();
    	String name = txtName.getText();
    	String hours = txtHours.getText();
    	String dateInit = txtDateInit.getText();
    	String dateFinish = txtDateFinish.getText();
    	
    	grade = new JSONObject();
    	if (code != null && !code.isBlank())
    	{
    		grade.put("careerCode", code);
    	}
    	
    	if (codeAdapt != null && !codeAdapt.isBlank())
    	{
    		grade.put("curricularAdaptationCode", codeAdapt);
    	}
    	
    	if (name != null && !name.isBlank())
    	{
    		grade.put("careerName", name);
    	}
    	
    	if (hours != null && !hours.isBlank())
    	{
    		grade.put("careerHours", hours);
    	}
    	
    	if (dateInit != null && !dateInit.isBlank())
    	{
    		grade.put("careerStartDate", dateInit);
    	}
    	
    	if (dateFinish != null && !dateFinish.isBlank())
    	{
    		grade.put("careerFinishDate", dateFinish);
    	}
    			
    	changeScene("createModuls.fxml");
    		
    }
    /**
   	 * Este metodo elimina los modulos seleccionados
   	 * @param event Evento
   	 */
    @FXML void deleteModul(ActionEvent event)
    {
    	int index = listModuls.getSelectionModel().getSelectedIndex();
    	System.out.println(index);
    	if (index >= 0)    		
    	{
    		moduls.remove(index);
    		listModuls.getItems().clear();    		
    		for (JSONObject jModul : moduls)
			{
    			String modulName = jModul.getString("MOName");
    			listModuls.getItems().add(modulName);
			}
    	}
    		
    	
    }
    /**
   	 * Este metodo muestra un mensaje de confirmación avisando que se perderan los datos 
   	 * @param event Evento
   	 */
    @FXML void backAction(ActionEvent event)    
    {
    	Alert alertOverwrite = new Alert(AlertType.CONFIRMATION,
		        "Perderas los cambios actuales, ¿Desea continuar?");

		alertOverwrite.setTitle("Datos no guardados");
		ButtonType result = alertOverwrite.showAndWait().orElse(ButtonType.NO);
		if (ButtonType.OK.equals(result)) {	
			grade = null;
			moduls = null;
			Pane root;
			try {
				root = FXMLLoader.load(getClass().getResource(GenericUtils.viewGradesWindow));
				root.getChildren().add(GenericUtils.menu);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Main.stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

