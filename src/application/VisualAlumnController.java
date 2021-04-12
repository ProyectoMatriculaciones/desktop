package application;


import java.awt.Checkbox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import utils.GenericUtils;

public class VisualAlumnController  implements Initializable{

	    @FXML
	    private TextField filterId;

	    @FXML
	    private Button btSearch;

	    @FXML
	    private TableView<Alumn> tableView;

	    @FXML
	    private TableColumn<Alumn, String> nameColumn;

	    @FXML
	    private TableColumn<Alumn, String> firstSurnameColumn;

	    @FXML
	    private TableColumn<Alumn, String> secondSurnameColumn;

	    @FXML
	    private TableColumn<Alumn, String> idDocColumn;

	    @FXML
	    private TableColumn<Alumn, Button> detailsColumn;
	    
	    @FXML
	    private Button btAlumnImport;
	    
	    
	    //metodo para importar los alumnmos del csv
	    @FXML
	    void openImportAction(ActionEvent event) {

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
				try (CSVReader reader = new CSVReader(new FileReader (csvFile,StandardCharsets.UTF_8))) {
				   List<String[]> r = reader.readAll();
				   for (int i = 1; i < r.size(); i++) {
					   
					   JSONObject alumn=new JSONObject();
					   alumn.put("convocatory", r.get(i)[0]);
					   alumn.put("convocatoryCode", r.get(i)[1]);
					   alumn.put("termType", r.get(i)[2]);
					   alumn.put("termStatus", r.get(i)[3]);
					   alumn.put("name", r.get(i)[4]);
					   alumn.put("firstSurname", r.get(i)[5]);
					   alumn.put("secondSurname", r.get(i)[6]);
					   alumn.put("RACLIdentification", r.get(i)[7]);
					   alumn.put("alumnType", r.get(i)[8]);
					   alumn.put("instituteCodeP1", r.get(i)[9]);
					   alumn.put("instituteNameP1", r.get(i)[10]);
					   alumn.put("instituteFinanciationP1", r.get(i)[11]);
					   alumn.put("instituteMunicipality", r.get(i)[12]);
					   alumn.put("instituteSSTT", r.get(i)[13]);
					   alumn.put("termCode", r.get(i)[14]);
					   alumn.put("termName", r.get(i)[15]);
					   alumn.put("modalityCode", r.get(i)[16]);
					   alumn.put("modality", r.get(i)[17]);
					   alumn.put("courseP1", r.get(i)[18]);
					   alumn.put("regimeP1", r.get(i)[19]);
					   alumn.put("scheduleP1", r.get(i)[20]);
					   alumn.put("DNI", r.get(i)[21]);
					   alumn.put("NIE", r.get(i)[22]);
					   alumn.put("PASS", r.get(i)[23]);
					   alumn.put("TIS", r.get(i)[24]);
					   alumn.put("birthdate", r.get(i)[25]);
					   alumn.put("sex", r.get(i)[26]);
					   alumn.put("nationality", r.get(i)[27]);
					   alumn.put("birthCountry", r.get(i)[28]);
					   alumn.put("birthMunicipality", r.get(i)[29]);
					   alumn.put("acces", r.get(i)[30]);
					   alumn.put("addressName", r.get(i)[31]);
					   alumn.put("addressNumber", r.get(i)[32]);
					   alumn.put("otherInfo", r.get(i)[33]);
					   alumn.put("resideceProvince", r.get(i)[34]);
					   alumn.put("residenceMunicipality", r.get(i)[35]);
					   alumn.put("residenceLocality", r.get(i)[36]);
					   alumn.put("CP", r.get(i)[37]);
					   alumn.put("residenceCountry", r.get(i)[38]);
					   alumn.put("phoneNumber", r.get(i)[39]);
					   alumn.put("email", r.get(i)[40]);
					   alumn.put("docTypeFirstGuardian", r.get(i)[41]);
					   alumn.put("numDocFirstGuardian", r.get(i)[42]);
					   alumn.put("firstGuardianName", r.get(i)[43]);
					   alumn.put("firstGuardianSurname1", r.get(i)[44]);
					   alumn.put("firstGuardianSurname2", r.get(i)[45]);
					   alumn.put("docTypeSecondGuardian", r.get(i)[46]);
					   alumn.put("numDocSecondGuardian", r.get(i)[47]);
					   alumn.put("secondGuardianName", r.get(i)[48]);
					   alumn.put("secondGuardianSurname1", r.get(i)[49]);
					   alumn.put("secondGuardianSurname2", r.get(i)[50]);
					   alumn.put("originInstituteCode", r.get(i)[51]);
					   alumn.put("originInstituteName", r.get(i)[52]);
					   alumn.put("originTitleCode", r.get(i)[53]);
					   alumn.put("originTitleName", r.get(i)[54]);
					   alumn.put("lastCourse", r.get(i)[55]);
					   alumn.put("language", r.get(i)[56]);
					   alumn.put("religion", r.get(i)[57]);
					   alumn.put("assignedInstituteCode", r.get(i)[58]);
					   alumn.put("password","test");
					   alumn.put("sessionToken","");
					   
					   
				   }
				    
				    
				  } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CsvException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Importacion de usuarios");
				alert.setContentText(
						"Los usuarios se han enviado a la base de datos");
				alert.showAndWait();
			}
	    	
	    	
	    }
	    
	    //mockup
	   
	  
	    
	    		
	    @FXML
	    void openSearchAction(ActionEvent event) {

	    	
	    	
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

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			 String strJSON = "[{\r\n"
			    		+ "        \"name\": \"nombre\",\r\n"
			    		+ "        \"firstSurname\": \"apellido\",\r\n"
			    		+ "        \"secondSurname\": \"segundoapellido\",\r\n"
			    		+ "        \"DNI\": \"00000000T\"\r\n"
			    		+ "    },\r\n"
			    		+ "    {\r\n"
			    		+ "        \"name\": \"nombre\",\r\n"
			    		+ "        \"firstSurname\": \"apellido\",\r\n"
			    		+ "        \"secondSurname\": \"segundoapellido\",\r\n"
			    		+ "        \"DNI\": \"00000000T\"\r\n"
			    		+ "    },\r\n"
			    		+ "    {\r\n"
			    		+ "        \"name\": \"nombre\",\r\n"
			    		+ "        \"firstSurname\": \"apellido\",\r\n"
			    		+ "        \"secondSurname\": \"segundoapellido\",\r\n"
			    		+ "        \"DNI\": \"00000000T\"\r\n"
			    		+ "    }\r\n"
			    		+ "]";
			 
			JSONArray jsonAlumn = (JSONArray) new JSONTokener(strJSON).nextValue();
			
			ObservableList<Alumn> list = FXCollections.observableArrayList();
			
			for (int i = 0; i < jsonAlumn.length(); i++) {
				
				JSONObject jsonObject = jsonAlumn.getJSONObject(i);
				Alumn a =new Alumn(jsonObject.getString("name"), jsonObject.getString("firstSurname"), jsonObject.getString("secondSurname"), jsonObject.getString("DNI"));
				list.add(a);
				
			}
			
			nameColumn.setCellValueFactory(new PropertyValueFactory<Alumn, String>("name"));
			firstSurnameColumn.setCellValueFactory(new PropertyValueFactory<Alumn, String>("firstSurname"));
			secondSurnameColumn.setCellValueFactory(new PropertyValueFactory<Alumn, String>("secondSurname"));
			idDocColumn.setCellValueFactory(new PropertyValueFactory<Alumn, String>("idDoc"));
			detailsColumn.setCellValueFactory(new PropertyValueFactory<Alumn, Button>("details"));
			

			tableView.setItems(list);
			
		}
	
}
