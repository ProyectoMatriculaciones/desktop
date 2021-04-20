package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import utils.GenericUtils;
import utils.RequestUtils;

public class CreateGradesController implements Initializable{

    
   
    @FXML
    private VBox vboxList;
    
    @FXML
    private ScrollPane scrollPaneList;
    
    @FXML
    private Button btAddReq;
    
    private ArrayList<JSONObject> documents;
    
    private ArrayList<HBox> filesEntries;
    
    private static final String email = "";
    private static final String profileName = "";
    
    
    
   
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	filesEntries = new ArrayList<HBox>();
    	
    	documents = new ArrayList<JSONObject>();
    	
    	vboxList.setSpacing(7);
    	
    	int maxDocLength = 0;
    	JSONArray jaDocs = alumnDocumentsProfile.getJSONArray("arrayDoc");
    	for (int i = 0; i < jaDocs.length(); i++)
    	{
    		documents.add(jaDocs.getJSONObject(i));
    		int length = jaDocs.getJSONObject(i).getString("documentName").length();
    		if (length > maxDocLength)
    			maxDocLength = length;
    	}
    	
    	
    	
    	for (JSONObject doc : documents)
    	{
    		// Document name label
    		String sDocName = doc.getString("documentName") + ":";
    		if (sDocName.length() < maxDocLength)
    		{
    			sDocName += " ".repeat(maxDocLength - sDocName.length());
    		}
    		Label docName = new Label(sDocName + "\t\t");
    		docName.setFont(new Font("System", 18));
    		docName.setStyle("-fx-font-weight: bold");
    		
    		// exists filePath field?
    		boolean isFilePath = false;
    		String filePath = null;
    		try {
    			filePath = doc.getString("filePath");
    			if (filePath != null && !filePath.isBlank())
    			{
    				isFilePath = true;
    			}
    		} catch (Exception e)
    		{
    			isFilePath = false;
    		}    		
    		
    		// File name label
    		Label fileName;
    		if (isFilePath)
    		{
    			fileName = new Label(getfileNameByPath(filePath));
    			fileName.setFont(new Font("System", 16));
    			
    		}
    		else
    		{
    			fileName = new Label("No se ha subido");
    			fileName.setFont(new Font("System", 16));
    		}
    		
    		// exists validate field?
    		boolean isValidate = false;
    		String sValidate = null;
    		try {
    			sValidate = doc.getString("validated");
    			if (sValidate != null && !sValidate.isBlank())
    			{
    				isValidate = true;
    			}
    		} catch (Exception e)
    		{
    			isValidate = false;
    		}   
    		
    		// Download button
    		Button download = new Button("Descargar");
    		EventHandler<ActionEvent> downloadEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    String docName = doc.getString("documentName");
                    String email = "muzqtzsge1@caramail.com"; // email from constant                          
                    String profileName = alumnDocumentsProfile.getString("name");
                    
                    // Get path to download
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(Main.stage);

                    if(selectedDirectory == null){
                    	Alert alert = new Alert(AlertType.ERROR);
            			alert.setTitle("Descarga de archivo");
            			alert.setContentText(
            					"Es necesario seleccionar una ruta a una carpeta para poder descargar el archivo");
            			alert.showAndWait();
                    }
                    else
                    {
                    	// Make request and obtain base64 string and filename
                    	String fileBase64 = "";
                    	String fileName = "";
                    	
                    	JSONObject response = RequestUtils.getDocument(email, profileName, docName);
                    	String filePath = response.getString("filePath");
                    	fileBase64 = response.getString("data");                    	
                    	fileName = getfileNameByPath(filePath);
                    	
                    	
                    	if (fileBase64 != null && !fileBase64.isBlank())
                    	{
                    		// Decode file and save to path
                            byte[] data = Base64.getDecoder().decode(fileBase64);
                            File f = new File(selectedDirectory.getAbsolutePath() + File.separator + fileName);
                            if (f.exists())
                            	f.delete();
                            
                            try (OutputStream stream = new FileOutputStream(f)) {
                            	f.createNewFile();
                            	stream.write(data);
                                Alert alert = new Alert(AlertType.INFORMATION);
                    			alert.setTitle("Descarga de archivo");
                    			alert.setContentText(
                    					"Se ha descargado el archivo en la ruta seleccionada");
                    			alert.showAndWait();
                                
                            } catch (FileNotFoundException e1) {
        						// TODO Auto-generated catch block
        						e1.printStackTrace();
        					} catch (IOException e1) {
        						// TODO Auto-generated catch block
        						e1.printStackTrace();
        					}    
                    	}
                    	else
                    	{
                    		Alert alert = new Alert(AlertType.ERROR);
                			alert.setTitle("Descarga de archivo");
                			alert.setContentText(
                					"Ha habido un error y no se ha podido descargar el archivo");
                			alert.showAndWait();
                    	}
                    	                	
                    }                    
                }
            };
            download.setOnAction(downloadEvent);
    		
    		// Validate button
    		Button validate = new Button();
    		if (isValidate)
    		{
    			validate.setText("Validado");
    			validate.setDisable(true);
    		}
    		else
    		{
    			validate.setText("Validar");
    			// action validar
    			EventHandler<ActionEvent> validateEvent = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                    	String docName = doc.getString("documentName");
                        String email = "muzqtzsge1@caramail.com"; // email from constant                          
                        String profileName = alumnDocumentsProfile.getString("name");
                    	 
                    	 // Make request validate
                    	 boolean validated = RequestUtils.validateDocument(email, profileName, docName);
                    	 
                    	 if (validated)                    		 
                    	 {
                    		 Alert alert = new Alert(AlertType.INFORMATION);
                 			alert.setTitle("Validacion de archivo");
                 			alert.setContentText(
                 					"Se ha validad el archivo");
                 			alert.showAndWait();
                    	 }
                    	 else
                    	 {
                    		 Alert alert = new Alert(AlertType.ERROR);
                 			alert.setTitle("Validcion de archivo");
                 			alert.setContentText(
                 					"Ha habido un error y no se ha podido validar el archivo");
                 			alert.showAndWait();
                    	 }
                    }
                };
                validate.setOnAction(validateEvent);
    		}
    			
    		
    		
    		
    		HBox fileEntry = new HBox();
    		fileEntry.setSpacing(50);
    		fileEntry.getChildren().add(docName);
    		fileEntry.getChildren().add(fileName);
    		fileEntry.getChildren().add(download);
    		fileEntry.getChildren().add(validate);
    		
    		filesEntries.add(fileEntry);
    		vboxList.getChildren().add(fileEntry);
    	}
    
    	
	}
    
    public String getfileNameByPath(String path)
    {
    	String fileName;    	
    	int index = path.lastIndexOf('\\');
    	if (index != -1)
    	{
    		fileName = path.substring(index+1, path.length());
    	}
    	else {
    		index = path.lastIndexOf('/');
    		if (index != -1)
    		{
    			fileName = path.substring(index+1, path.length());
    		}
    		else
    		{
    			return null;
    		}    		
    	}   	
    	return fileName;
    }
    
	
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
	
	
	
	 private JSONObject alumnDocumentsProfile = new JSONObject("{\r\n"
	    		+ "			\"_id\": \"6075c9975f8a130004ae465f\",\r\n"
	    		+ "			\"arrayDoc\": [\r\n"
	    		+ "				{\r\n"
	    		+ "					\"documentName\": \"DNI\",\r\n"
	    		+ "					\"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/a.java\"\r\n"
	    		+ "				},\r\n"
	    		+ "				{\r\n"
	    		+ "					\"documentName\": \"PASS\",\r\n"
	    		+ "					\"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/b.java\"\r\n"
	    		+ "				},\r\n"
	    		+ "				{\r\n"
	    		+ "					\"documentName\": \"asd\",\r\n"
	    		+ "					\"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/c.java\"\r\n"
	    		+ "				},\r\n"
	    		+ "				{\r\n"
	    		+ "					\"documentName\": \"DNI REVERSO\",\r\n"
	    		+ "					\"filePath\": \"/app/uploads/muzqtzsge1caramailcom/Perfil Demo/DNI/d.java\"\r\n"
	    		+ "				}\r\n"
	    		+ "			],\r\n"
	    		+ "			\"name\": \"Perfil Demo\",\r\n"
	    		+ "			\"description\": \"Descripcion Perfil demo\\t\"\r\n"
	    		+ "		}");
}

