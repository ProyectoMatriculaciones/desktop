package utils;

import javafx.scene.control.MenuBar;

public class GenericUtils {


	public static final String apiUrl = "https://api-matriculacioones.herokuapp.com";
	public static final String epLogin = "/login/admin";
	public static final String epAllGrades = "/get/allGrades";
	public static final String epGrade = "/get/grade";
	public static final String epInsertGrade = "/insert/grade";
	public static final String epInsertDocumentsProfile = "/insert/documentsProfile";
	public static final String epGetAllAlumns = "/get/allAlumns";
	public static final String epInsertAlumn = "/insert/alumn";
	public static final String epUpdateAlumn = "/update/alumn";
	public static final String epGetAlumn = "/get/alumn";
	public static final String epValidateFile = "/update/validateFile";
	public static final String epGetDocument = "/get/document";
	public static String currentToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwNGY4ZTE4NTZiNDhhMWJmNGRlMDRiMCIsImFkbWluIjp0cnVlLCJpYXQiOjE2MTg4NDY1MjAsImV4cCI6MTYxODg0Nzk2MH0.67QmlnSRCfkRRuWoalm48Apzi8A8_AgTao0WV7AMjGk";

	public static String importWindow = "CarreerSelector.fxml";
	public static String loginWindow = "Login.fxml";
	public static String viewGradesWindow = "VisualGrades.fxml";	
	public static String documentsProfileFormWindow = "DocumentsProfileForm.fxml";
	public static String viewAlumnsWindow = "VisualAlumn.fxml";
	
	
	public static MenuBar menu;
}

