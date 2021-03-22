package application;

import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;


import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import utils.CSVUtils;

public class ListController implements Initializable{

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
	 
	 private ArrayList<String> codelist=new ArrayList<>();
	 
	 private File importFile;

     @FXML
     void importCarreers(ActionEvent event) {

    	 for(int i=0;i<tableView.getItems().size();i++) {
    		 
    		 if(tableView.getItems().get(i).getCheckBox().isSelected()) {
    			 codelist.add(tableView.getItems().get(i).getCarreerCode());
    		 }
    		 
    	 }
    	 
    	 for (String string : codelist) {
			System.out.println(string);
		}
    	 
    	 ArrayList<JSONObject> selectedGrades=CSVUtils.parseGrade(codelist, importFile);
    	 
     }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tableView.setVisible(false);
		carreerCodeColumn.setVisible(false);
		carreerNameColumn.setVisible(false);
		selectionColumn.setVisible(false);
		bnImport.setVisible(false);
		
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Archivo CSV");


        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

        File csvFile = fileChooser.showOpenDialog(Main.stage);
        
        if(csvFile!=null) {
        	importFile=csvFile;
        	tableView.setVisible(true);
    		carreerCodeColumn.setVisible(true);
    		carreerNameColumn.setVisible(true);
    		selectionColumn.setVisible(true);
    		bnImport.setVisible(true);
    		

    		ObservableList<Carreer> list =FXCollections.observableArrayList();
    		BufferedReader br = null;
    	      
    	      try {
    	         
    	         br =new BufferedReader(new FileReader(csvFile,StandardCharsets.UTF_8));
    	         String line = br.readLine();
    	         String initialcode="";
    	         while ((line = br.readLine()) != null) {                
    	             String[] datos = line.split(",");
    	             //Imprime datos.
    	             if(!initialcode.equals(datos[0])) {
    	            	 initialcode=datos[0];
    	            	 Carreer c=new Carreer(datos[0],datos[1]);
    	            	 list.add(c);
    	             }

    	         }
    	         
    	      } catch (Exception e) {
    	        e.printStackTrace();
    	      } finally {
    	         if (null!=br) {
    	            try {
    					br.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    	         }
    	      }
    	      
    	      
    	      carreerCodeColumn.setCellValueFactory(new PropertyValueFactory<Carreer,String>("carreerCode"));
    	      carreerNameColumn.setCellValueFactory(new PropertyValueFactory<Carreer,String>("carreerName"));
    	      selectionColumn.setCellValueFactory(new PropertyValueFactory<Carreer,Checkbox>("checkBox"));
    	      
    	      
    	      tableView.setItems(list);
        }
        
		
		
		
		
	}

	
}
