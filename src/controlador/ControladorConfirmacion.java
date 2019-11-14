package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ControladorConfirmacion implements Initializable {

	@FXML
	private TextFlow textFlow;

	private ArrayList<String> formula = new ArrayList<>();

	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@FXML
	void aceptar(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(
					ControladorVentanaPrincipal.class.getResource("../vista/VentanaOperaciones.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Proyecto");
			stage.setScene(scene);
			stage.show();
			((Stage) textFlow.getScene().getWindow()).close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		primaryStage.show();
		Stage aux = (Stage) textFlow.getScene().getWindow();
		aux.close();
	}

	@FXML
	void cambiarColor(ActionEvent event) {
		ObservableList<Node> list = textFlow.getChildren();
		list.clear();
		list.addAll(getFormulaColor(formula));
	}

	public void setFormula(ObservableList<Node> formula, ArrayList<String> form, Stage primaryStage) {
		ObservableList<Node> list = textFlow.getChildren();
		list.addAll(formula);
		this.formula = form;
		this.primaryStage = primaryStage;
	}

	public ObservableList<Node> getFormulaColor(ArrayList<String> formula) {
		ObservableList<Node> list = FXCollections.observableArrayList();
		ArrayList<ColorF> atomos = new ArrayList<>();
		ArrayList<ColorF> parentesis = new ArrayList<>();

		for (int i = 0; i < formula.size(); i++) {
			if (formula.get(i).equals("(")) {
				Color color = Color.color(Math.random(), Math.random(), Math.random());
				parentesis.add(new ColorF(color, i + ""));
				Text aux = new Text("(");
				aux.setFont(Font.font("System", FontWeight.BOLD, 24));
				aux.setFill(color);
				list.add(aux);
			} else if (formula.get(i).equals(")")) {
				if (parentesis.size() - 1 >= 0) {
					Text aux = new Text(")");
					aux.setFill(parentesis.get(parentesis.size() - 1).getColor());
					aux.setFont(Font.font("System", FontWeight.BOLD, 24));
					parentesis.remove(parentesis.size() - 1);
					list.add(aux);
				}
			} else if (!formula.get(i).equals("¬") && !formula.get(i).equals("ʌ") && !formula.get(i).equals("v")
					&& !formula.get(i).equals("→’") && !formula.get(i).equals("↔")) {
				boolean ward = true;
				for (int j = 0; j < atomos.size() && ward; j++) {
					if (atomos.get(j).getValue().equals(formula.get(i))) {
						Text aux = new Text(formula.get(i));
						aux.setFill(atomos.get(j).getColor());
						aux.setFont(Font.font("System", FontWeight.BOLD, 24));
						list.add(aux);
						ward = false;
					}
				}
				if (ward) {
					Color color = Color.color(Math.random(), Math.random(), Math.random());
					atomos.add(new ColorF(color, formula.get(i)));
					Text aux = new Text(formula.get(i));
					aux.setFill(color);
					aux.setFont(Font.font("System", FontWeight.BOLD, 24));
					list.add(aux);
				}
			} else {
				Text aux = new Text(formula.get(i));
				aux.setFont(Font.font("System", FontWeight.BOLD, 24));
				list.add(aux);
			}
		}

		return list;
	}
}
