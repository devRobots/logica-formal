package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal implements Initializable {

	@FXML
	private Button btnAceptar;

	@FXML
	private TextArea textArea;
	private ArrayList<String> formula = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// btnAceptar.requestFocus();
		textArea.setFocusTraversable(false);
	}

	@FXML
	void agregarParentesisIzq(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, "(");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);

	}

	@FXML
	void agregarParentesisDer(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, ")");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);
	}

	@FXML
	void agregarNegacion(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, "¬");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);
	}

	@FXML
	void agregarConjuncion(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, "ʌ");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);
	}

	@FXML
	void agregarDisyuncion(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, "v");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);
	}

	@FXML
	void agregarCondicionalIzq(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, "→");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);
	}

	@FXML
	void agregarBicondicional(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		stringaArray();
		formula.add(pos, "↔");
		arrayaString();
		textArea.requestFocus();
		textArea.positionCaret(pos + 1);
	}

	private void stringaArray() {
		formula = new ArrayList<>();
		for (char aux : textArea.getText().toCharArray()) {
			formula.add(String.valueOf(aux));
		}
	}

	private void arrayaString() {
		textArea.setText("");
		String cadena = "";
		for (String aux2 : formula) {
			cadena += aux2;
		}
		textArea.setText(cadena);
	}

	private ArrayList<String> completarParentesis() {
		ArrayList<String> formulaAux = new ArrayList<>(formula);
		ArrayList<Integer> parentesis = new ArrayList<>();
		for (int i = 0; i < formula.size(); i++) {
			if (formulaAux.get(i).equals("ʌ") && formulaAux.get(i).equals("v") && formulaAux.get(i).equals("→’")
					&& formulaAux.get(i).equals("↔")) {
				if (i - 1 > 0 && i + 1 < formulaAux.size()) {
					for (int j = 0; j >= 0; j--) {

					}
				} else {
					return null;
				}
			} else {

			}
			// formula.get(i).equals("¬")
		}
		return formulaAux;
	}

	@FXML
	void aceptar(ActionEvent event) {
		stringaArray();
		ObservableList<Node> list = getFormulaColor(formula);
		if (list != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(
						ControladorVentanaPrincipal.class.getResource("../vista/VentanaConfirmacion.fxml"));
				Scene scene = new Scene(fxmlLoader.load());
				Stage stage = new Stage();
				stage.setTitle("Proyecto");
				stage.setScene(scene);
				ControladorConfirmacion con = fxmlLoader.getController();
				con.setFormula(list, formula, (Stage) textArea.getScene().getWindow());
				stage.show();
				((Stage) textArea.getScene().getWindow()).close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ObservableList<Node> getFormulaColor(ArrayList<String> formula) {
		boolean par = true;
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
				} else {
					par = false;
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
		if (!par) {
			JOptionPane.showMessageDialog(null, "Existe error en los parentesis", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} else if (atomos.size() < 5) {
			JOptionPane.showMessageDialog(null, "Use minimo 5 atomos distintos por favor.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		} else {
			return list;
		}
	}

}
