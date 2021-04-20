package application;
	
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utils.GenericUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage stage;
	public static File importCsvFile;
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("Login.fxml"));
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
		setMenuBar();
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

	private static void setMenuBar() {
		MenuBar menuBar = new MenuBar();
		
		Label lItemVisAlum = new Label("Visualizar alumnos");
		lItemVisAlum.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	Pane root;
        		try {
        			root = FXMLLoader.load(getClass().getResource(GenericUtils.viewAlumnsWindow));
        			root.getChildren().add(GenericUtils.menu);
        			Scene scene = new Scene(root);
        			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        			Main.stage.setScene(scene);
        		} catch (IOException e2) {
        			e2.printStackTrace();
        		}		       
		    }
		});
		Menu itemVisAlum = new Menu();
		itemVisAlum.setGraphic(lItemVisAlum);
		
		
		Label litemVisCicl = new Label("Visualizar ciclos");
		litemVisCicl.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	Pane root;
        		try {
        			root = FXMLLoader.load(getClass().getResource(GenericUtils.viewGradesWindow));
        			root.getChildren().add(GenericUtils.menu);
        			Scene scene = new Scene(root);
        			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        			Main.stage.setScene(scene);
        		} catch (IOException e2) {
        			e2.printStackTrace();
        		}		       
		    }
		});
		Menu itemVisCicl = new Menu();
		itemVisCicl.setGraphic(litemVisCicl);
		
		Label litemFormReq = new Label("Formulario de perfiles de requisitos");
		litemFormReq.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	Pane root;
        		try {
        			root = FXMLLoader.load(getClass().getResource(GenericUtils.documentsProfileFormWindow));
        			root.getChildren().add(GenericUtils.menu);
        			Scene scene = new Scene(root);
        			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        			Main.stage.setScene(scene);
        		} catch (IOException e2) {
        			e2.printStackTrace();
        		}
		    }
		});       
		Menu itemFormReq = new Menu();
		itemFormReq.setGraphic(litemFormReq);
		
        menuBar.getMenus().add(itemVisAlum);
		menuBar.getMenus().add(itemVisCicl);
		menuBar.getMenus().add(itemFormReq);
		GenericUtils.menu = menuBar;
		
	}	
}
