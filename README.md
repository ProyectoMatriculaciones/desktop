**** PASOS NECESARIOS PARA PODER LANZAR LA APLICACION ****
Es necesario añadir a los VM Arguments la ruta a la libreria de javaFX. Tanto el SDK para linux como windows se encuentran en la carpeta lib del proyecto.

Para ello: 
		 *  Click derecho en el proyecto --> Run As --> Run Configurations --> Seleccionar el run Main que corresponda a este proyecto
		 *  --> VM Arguments -->
		 *  En windows: --module-path "lib/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml
		 *  En linux: --module-path "lib/javafxLin-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml
     
La api a la que apunta el programa, es la url definida en una constante que se ubica en: src/utils/GenericUtils.java - linea 6 (apiUrl). Por defecto esta sera la ultima version de nuestra api, ubicada en Heroku
