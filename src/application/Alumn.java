package application;

import javafx.scene.control.Button;

public class Alumn {

	private String name;
	private String firstSurname;
	private String secondSurname;
	private String idDoc;
	private Button details;
	
	public Alumn(String name, String firstSurname, String secondSurname, String idDoc, Button details) {
		super();
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.idDoc = idDoc;
		this.details = new Button("DATOS");
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
	public Button getDetails() {
		return details;
	}
	public void setDetails(Button details) {
		this.details = details;
	}
	
	
	
}
