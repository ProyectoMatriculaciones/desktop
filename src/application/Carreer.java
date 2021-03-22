package application;

import javafx.scene.control.CheckBox;

public class Carreer {

	private String carreerCode;
	private String carreerName;
	private CheckBox checkBox;
	
	public Carreer(String carreerCode, String carreerName) {
		super();
		this.carreerCode = carreerCode;
		this.carreerName = carreerName;
		this.checkBox=new CheckBox();
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public String getCarreerCode() {
		return carreerCode;
	}

	public void setCarreerCode(String carreerCode) {
		this.carreerCode = carreerCode;
	}

	public String getCarreerName() {
		return carreerName;
	}

	public void setCarreerName(String carreerName) {
		this.carreerName = carreerName;
	}
	
	
	
}
