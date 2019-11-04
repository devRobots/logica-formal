package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ControladorOperaciones implements Initializable {

	
	@FXML
	private TextFlow textFlow;

	private ArrayList<String> formula = new ArrayList<String>();
	
	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	void aceptar(ActionEvent event) {
		
	}

	@FXML
	void cancelar(ActionEvent event) {
		primaryStage.show();
		Stage aux=(Stage) textFlow.getScene().getWindow();
		aux.close();
	}


	public void setFormula(ObservableList<Node> formula,ArrayList<String> form,Stage primaryStage) {
		ObservableList<Node> list = textFlow.getChildren();
		list.addAll(formula);
		this.formula=form;
		this.primaryStage=primaryStage;
	}
}
