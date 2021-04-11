package application;


import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import utils.GenericUtils;

public class VisualAlumnController {

	    @FXML
	    private TextField filterId;

	    @FXML
	    private Button btSearch;

	    @FXML
	    private TableView<?> tableView;

	    @FXML
	    private TableColumn<?, ?> nameColumn;

	    @FXML
	    private TableColumn<?, ?> firstSurnameColumn;

	    @FXML
	    private TableColumn<?, ?> secondSurnameColumn;

	    @FXML
	    private TableColumn<?, ?> idDocColumn;

	    @FXML
	    private TableColumn<?, ?> detailsColumn;
	    
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
				Main.importCsvFile = csvFile;
				changeScene(GenericUtils.importWindow);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Importacion de usuarios");
				alert.setContentText(
						"Los usuarios se han enviado a la base de datos");
				alert.showAndWait();
			}
	    	
	    	
	    }
	    
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
	
}
