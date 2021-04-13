**** PASOS NECESARIOS PARA PODER LANZAR LA APLICACION ****<br>
Es necesario añadir a los VM Arguments la ruta a la libreria de javaFX. Tanto el SDK para linux como windows se encuentran en la carpeta lib del proyecto.<br>
<br>
Para ello: <br>
		 *  Click derecho en el proyecto --> Run As --> Run Configurations --> Seleccionar el run Main que corresponda a este proyecto<br>
		 *  --> VM Arguments --><br>
		 *  En windows: --module-path "lib/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml<br>
		 *  En linux: --module-path "lib/javafxLin-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml<br>
     <br>
La api a la que apunta el programa, es la url definida en una constante que se ubica en: src/utils/GenericUtils.java - linea 6 (apiUrl). Por defecto esta sera la ultima version de nuestra api, ubicada en Heroku
