/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quind�o (Armenia - Colombia)
 * Programa de Ingenier�a de Sistemas y Computaci�n
 *
 * Asignatura: Logica Formal
 * Ejercicio: Programa FNC, FND, Resluci�n
 * @author : Brayan Tabares Hidalgo - Yesid Rosas Toro - Samara Rinc�n Monta�a
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package modelo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vista.PropertiesLenguaje;
import vista.TablaFormulas;
/**
 * Clase principal de Ejecuci�n
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		PropertiesLenguaje.setEspanol();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(TablaFormulas.class.getResource("VentanaPrincipal.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle(PropertiesLenguaje.prop.getProperty("proyecto"));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
