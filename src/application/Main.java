package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			stage=primaryStage;
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		/*
		 * 
		 * Tanto el SDK para linux como windows se encuentran en la carpeta lib del proyecto.
		 *  Es necesario configurar las VM Arguments en cada sistema. Para ello: 
		 *  Click derecho en el proyecto --> Run As --> Run Configurations --> Seleccionar el run Main que corresponda a este proyecto
		 *  --> VM Arguments -->
		 *  En windows: --module-path "lib/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml
		 *  En linux: --module-path "lib/javafxLin-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml
		 * 
		 * 
		 * */
	}
}
