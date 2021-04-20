package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Alumn {

	private String name;
	private String firstSurname;
	private String secondSurname;
	private String idDoc;
	private String eMail;
	private Button details;
	
	public Alumn(String name, String firstSurname, String secondSurname, String idDoc, String email) {
		super();
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.idDoc = idDoc;
		this.eMail=email;
		Button b=new Button("DATOS");
		b.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {

				/*
				 * try {
				 * 
				 * FXMLLoader loader=new FXMLLoader(getClass().getResource("VisualAlumnDetails.fxml")); 
				 * Parent root=loader.load();
				 * VisualAlumnDetailsController cont=loader.getController();
				 * cont.setEmail(eMail);
				 * Scene scene = new Scene(root);
				 * scene.getStylesheets().add(getClass().getResource("application.css").
				 * toExternalForm()); Main.stage.setScene(scene);
				 * 
				 * } catch (IOException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); }
				 */
		    	
		    	VisualAlumnDetailsController.currentEmail = email;
				try {
					FXMLLoader loader=new FXMLLoader(getClass().getResource("VisualAlumnDetails.fxml"));
					Parent root = loader.load();
					VisualAlumnDetailsController cont=loader.getController();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());					
					Main.stage.setScene(scene);
				} catch (IOException ee) {
					ee.printStackTrace();
				}
		    	
		    }
		});
		this.details = b;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstSurname() {
		return firstSurname;
	}
	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}
	public String getSecondSurname() {
		return secondSurname;
	}
	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getEmail() {
		return eMail;
	}
	public void setEmail(String email) {
		this.eMail = email;
	}
	public Button getDetails() {
		return details;
	}
	public void setDetails(Button details) {
		this.details = details;
	}
	
	
	
}
