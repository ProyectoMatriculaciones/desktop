package application;

import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import application.Carreer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import utils.CSVUtils;
import utils.GenericUtils;
import utils.RequestUtils;


/**
 * Esta clase es el controlador de la ventana de visualizacion de los ciclos a importar a la BD de Mongo.
 * @author Dani
 * @author Pablo 
 */

public class ListController implements Initializable {

	@FXML
	private TableView<Carreer> tableView;

	@FXML
	private TableColumn<Carreer, String> carreerCodeColumn;

	@FXML
	private TableColumn<Carreer, String> carreerNameColumn;

	@FXML
	private TableColumn<Carreer, Checkbox> selectionColumn;

	@FXML
	private Button bnImport;

	private ArrayList<String> codelist = new ArrayList<>();

	private File importFile;
	
	
	/**
	 * Este metodo importa los ciclos seleccionados a la BD llamando a la clase de RequestUtils
	 * y muestra una alerta mostrando la cantidad de ciclos que se han introducido en la BD.
	 * @param event Accion que captura la pulsacion del boton de importar ciclos.
	 */

	//
	@FXML
	void importCarreers(ActionEvent event) {

		// Get selected codes
		for (int i = 0; i < tableView.getItems().size(); i++) {

			if (tableView.getItems().get(i).getCheckBox().isSelected()) {
				codelist.add(tableView.getItems().get(i).getCarreerCode());
			}

		}

		// Parse selected codes
		ArrayList<JSONObject> selectedGrades = CSVUtils.parseGrade(codelist, importFile);

		// Make insert request
		ArrayList<JSONObject> conflictGrades = RequestUtils.insertGrades(selectedGrades, false);
		int inserted = selectedGrades.size() - conflictGrades.size();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Importacion de ciclos");
		alert.setContentText("Se han insertado " + inserted + " nuevos ciclos!");
		alert.showAndWait();

		// Manage conflicted grades
		selectedGrades.clear();
		for (JSONObject grade : conflictGrades)
		{			
			Alert alertOverwrite = new Alert(AlertType.CONFIRMATION,
			        "El ciclo con el codigo: " + grade.getString("careerCode") + " - " + grade.getString("careerName") 
			        + " ya existe en la base de datos. ¿Quieres sobreescribirlo?");

			alertOverwrite.setTitle("Ciclo con codigo existente");
			ButtonType result = alertOverwrite.showAndWait().orElse(ButtonType.NO);
			if (ButtonType.OK.equals(result)) {	
				selectedGrades.add(grade);
			}			
		}
		
		// Insert overwriting selected conflict grades
		if (selectedGrades.size() > 0)
		{
			conflictGrades = RequestUtils.insertGrades(selectedGrades, true);
			if (conflictGrades.size() > 0)
			{
				String errorGradesList = "";
				for (JSONObject grade : conflictGrades)
				{
					errorGradesList += "\t-> " + grade.getString("careerCode") + " - " + grade.getString("careerName") + "\n";
				}
				Alert alertOverwrite = new Alert(AlertType.ERROR,
				        "No se han podido importar los siguientes ciclos:\n" + errorGradesList);

				alertOverwrite.setTitle("Error importando ciclos");
				alertOverwrite.showAndWait();
			}
			else
			{
				Alert alertOverwrite = new Alert(AlertType.INFORMATION,
				        "Se han sobreescrito correctamente los ciclos");

				alertOverwrite.setTitle("Sobreescribir ciclos");
				alertOverwrite.showAndWait();
			}
			
		}
		
		changeScene(GenericUtils.viewGradesWindow);

	}

	/**
	 * Este metodo sobreescribe el metodo initialize para que importe los ciclos seleccionados a la BD llamando a la clase de RequestUtils
	 * y mostrar una alerta mostrando la cantidad de ciclos que se han introducido en la BD.
	 * @param arg0 URL
	 * @param arg1 Resource Bundles
	 * @throws IOException Excepcion que surge cuando no es capaz de cargar la siguiente ventana
	 */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tableView.setVisible(false);
		carreerCodeColumn.setVisible(false);
		carreerNameColumn.setVisible(false);
		selectionColumn.setVisible(false);
		bnImport.setVisible(false);

		
		

		if (Main.importCsvFile != null) {
			importFile = Main.importCsvFile;
			tableView.setVisible(true);
			carreerCodeColumn.setVisible(true);
			carreerNameColumn.setVisible(true);
			selectionColumn.setVisible(true);
			bnImport.setVisible(true);

			ObservableList<Carreer> list = FXCollections.observableArrayList();

			try (CSVReader reader = new CSVReader(new FileReader(importFile, StandardCharsets.UTF_8))) {

				List<String[]> r = reader.readAll();

				String carreerCode = "";

				for (int i = 1; i < r.size(); i++) {
					if (!carreerCode.equals(r.get(i)[0])) {
						carreerCode = r.get(i)[0];
						Carreer c = new Carreer(r.get(i)[0], r.get(i)[1]);
						list.add(c);
					}

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

			carreerCodeColumn.setCellValueFactory(new PropertyValueFactory<Carreer, String>("carreerCode"));
			carreerNameColumn.setCellValueFactory(new PropertyValueFactory<Carreer, String>("carreerName"));
			selectionColumn.setCellValueFactory(new PropertyValueFactory<Carreer, Checkbox>("checkBox"));

			tableView.setItems(list);
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
