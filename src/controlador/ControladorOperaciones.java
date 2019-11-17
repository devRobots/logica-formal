package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ControladorOperaciones implements Initializable {

	@FXML
	private TextArea textFormulaO;

	@FXML
	private TextArea textFormulaN;
	
	@FXML
    private Label labelTitle;

	private Stage primaryStage;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

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
			File objetofile = new File("src/vista/guia.txt");
			Desktop.getDesktop().open(objetofile);

		} catch (IOException ex) {

			System.out.println(ex);

		}

	}

	public void setFormula(String formulaAntigua, String formulaNueva, Stage primaryStage, String titulo) {
		textFormulaO.setText(formulaAntigua);
		textFormulaN.setText(formulaNueva);
		this.primaryStage=primaryStage;
		labelTitle.setText(titulo);
	}
}
