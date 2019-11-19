package controlador;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import vista.PropertiesLenguaje;

public class ControladorOperaciones implements Initializable {

	@FXML
	private Menu menuAyuda;

	@FXML
	private Button btnSalir;

	@FXML
	private Menu menuArchivo;

	@FXML
	private MenuItem submenuCerrar;

	@FXML
	private MenuItem submenuGuia;

	@FXML
	private TextArea textFormulaO;

	@FXML
	private TextArea textFormulaN;
	
    @FXML
    private Label labelFormula;

	@FXML
	private Label labelTitle;

	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cambiarIdioma();
	}

	@FXML
	void salir(ActionEvent event) {
		primaryStage.show();
		Stage aux = (Stage) textFormulaO.getScene().getWindow();
		aux.close();
	}

	@FXML
	void cerrar(ActionEvent event) {
		((Stage) textFormulaN.getScene().getWindow()).close();
	}

	@FXML
	void abrirGuia(ActionEvent event) {
		try {
			File objetofile = new File("lib/guia.txt");
			Desktop.getDesktop().open(objetofile);
		} catch (Exception ex) {
			try {
				File objetofile = new File("src/vista/guia.txt");
				Desktop.getDesktop().open(objetofile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Alert alert=new Alert(AlertType.WARNING);
				alert.setTitle("Error");

				alert.setHeaderText(PropertiesLenguaje.prop.getProperty("error"));

				alert.setHeaderText("No se pudo realizar la acción");

				alert.setContentText(PropertiesLenguaje.prop.getProperty("errorGuia"));
				alert.show();
			}

		}

	}

	public void setFormula(String formulaAntigua, String formulaNueva, Stage primaryStage, String titulo) {
		textFormulaO.setText(formulaAntigua);
		textFormulaN.setText(formulaNueva);
		this.primaryStage = primaryStage;
		labelTitle.setText(titulo);
	}
	
	public void cambiarIdioma() {	
		menuAyuda.setText(PropertiesLenguaje.prop.getProperty("ayuda"));
		menuArchivo.setText(PropertiesLenguaje.prop.getProperty("archivo"));
		submenuCerrar.setText(PropertiesLenguaje.prop.getProperty("cerrar"));
		btnSalir.setText(PropertiesLenguaje.prop.getProperty("salir"));
		submenuGuia.setText(PropertiesLenguaje.prop.getProperty("guia"));
		labelFormula.setText(PropertiesLenguaje.prop.getProperty("programa"));
	}
}
