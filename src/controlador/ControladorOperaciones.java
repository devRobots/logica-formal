package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ControladorOperaciones implements Initializable {

	@FXML
	private TextArea textFormulaO;

	@FXML
	private TextArea textFormulaN;

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

	public void setFormula(String formulaAntigua, String formulaNueva, Stage primaryStage) {
		textFormulaO.setText(formulaAntigua);
		textFormulaN.setText(formulaNueva);
		this.primaryStage=primaryStage;
	}
}
