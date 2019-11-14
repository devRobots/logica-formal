package controlador;

import java.awt.Event;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.FormulaBienFormada;
import modelo.Operadores;

public class ControladorVentanaPrincipal implements Initializable {

	@FXML
	private Button btnAceptar;

	@FXML
	private TableView<TablaFormulas> tableFormulas;

	@FXML
	private TableColumn<TablaFormulas, String> columnFormulas;

	@FXML
	private ComboBox<String> comboMetodo;

	@FXML
	private Button btnEjecutar;

	@FXML
	private TextArea textArea;
	private ArrayList<String> formula = new ArrayList<>();

	@FXML
	private ObservableList<TablaFormulas> formulasTabla = FXCollections.observableArrayList();
	private ArrayList<String> historial = new ArrayList<String>();

	private boolean esPosicionValida(int pos) {
		boolean flag = false;

		String formula = textArea.getText();
		if (!formula.isEmpty()) {
			if (pos > 0 && pos < formula.length()) {
				if (formula.charAt(pos - 1) == '(' && formula.charAt(pos) == ')') {
					flag = true;
				}
			}
		} else {
			flag = true;
		}

		return flag;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textArea.setFocusTraversable(true);
		ObservableList<String> aux = FXCollections.observableArrayList();
		aux.add("FNC");
		aux.add("FND");
		aux.add("FC");
		comboMetodo.setItems(aux);
		actualizarTabla();
		historial.add("");
		historial.add("");
		columnFormulas.setCellValueFactory(new PropertyValueFactory<>("formula"));
		tableFormulas.setItems(formulasTabla);

	}

	@FXML
	void agregarAtomo(KeyEvent event) {
		if (!textArea.getText().isEmpty() && event.getCharacter().equals("")) {
			actualizarHistorial();
			boolean ward = true;
			for (int i = 0; i < historial.get(1).length() && ward; i++) {
				if (historial.get(1).charAt(i) != historial.get(0).charAt(i)) {
					char aux = historial.get(0).charAt(i);
					if (!Character.isAlphabetic(aux) || aux == 'v' || aux=='ʌ') {
						int pos = textArea.getCaretPosition();
						textArea.setText(historial.get(0));
						textArea.positionCaret(pos + 1);
						actualizarHistorial();
					}
					ward = false;
				}
			}
			if(ward) {
				int pos = textArea.getCaretPosition();
				textArea.setText(historial.get(0));
				textArea.positionCaret(pos + 1);
				actualizarHistorial();
			}
		} else {
			char c = event.getCharacter().charAt(0);
			boolean ward = true;
			if (!Character.isAlphabetic(c) || c == 'v') {
				event.consume();
				ward = false;
			}
			if (!esPosicionValida(textArea.getCaretPosition())) {
				event.consume();
				ward = false;
			}
			if (ward) {
				actualizarHistorial();
			}
		}
	}

	@FXML
	void agregarNegacion(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		if (esPosicionValida(pos)) {
			stringaArray();
			formula.add(pos, "¬()");
			arrayaString();
			textArea.requestFocus();
			textArea.positionCaret(pos + 2);
			actualizarHistorial();
		}
	}

	@FXML
	void agregarConjuncion(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		if (esPosicionValida(pos)) {
			stringaArray();
			formula.add(pos, "()ʌ()");
			arrayaString();
			textArea.requestFocus();
			textArea.positionCaret(pos + 1);
			actualizarHistorial();
		}
	}

	@FXML
	void agregarDisyuncion(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		if (esPosicionValida(pos)) {
			stringaArray();
			formula.add(pos, "()v()");
			arrayaString();
			textArea.requestFocus();
			textArea.positionCaret(pos + 1);
			actualizarHistorial();
		}
	}

	@FXML
	void agregarCondicional(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		if (esPosicionValida(pos)) {
			stringaArray();
			formula.add(pos, "()→()");
			arrayaString();
			textArea.requestFocus();
			textArea.positionCaret(pos + 1);
			actualizarHistorial();
		}
	}

	@FXML
	void agregarBicondicional(ActionEvent event) {
		int pos = textArea.getCaretPosition();
		if (esPosicionValida(pos)) {
			stringaArray();
			formula.add(pos, "()↔()");
			arrayaString();
			textArea.requestFocus();
			textArea.positionCaret(pos + 1);
			actualizarHistorial();

		}
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

		FormulaBienFormada fbf = new FormulaBienFormada(textArea.getText());

		System.out.println(fbf.getArbol());
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
			} else if (!formula.get(i).equals(Operadores.NEGACION) && !formula.get(i).equals(Operadores.CONJUNCION)
					&& !formula.get(i).equals(Operadores.DISYUNCION) && !formula.get(i).equals(Operadores.CONDICIONAL)
					&& !formula.get(i).equals(Operadores.EQUIVALENCIA)) {
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

	@FXML
	void seleccionar() {
		if (comboMetodo.getSelectionModel().getSelectedIndex() != -1
				&& tableFormulas.getSelectionModel().getSelectedIndex() != -1) {
			btnEjecutar.setDisable(false);
		} else {
			btnEjecutar.setDisable(true);
		}
	}

	@FXML
	void agregarTabla(ActionEvent event) {
		String cadena = textArea.getText();
		if (cadena.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo está vacio", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (cadena.contains("()")) {
			JOptionPane.showMessageDialog(null, "Complete los atomos en la fórmula por favor", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			ArrayList<Character> list = new ArrayList<Character>();
			for (char aux : cadena.toCharArray()) {
				if (Character.isAlphabetic(aux) && !list.contains(aux)) {
					list.add(aux);
				}
			}
			if (list.size() < 5) {
				JOptionPane.showMessageDialog(null, "Use minimo 5 atomos distintos por favor.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				textArea.setText("");
				formulasTabla.add(new TablaFormulas(cadena));
			}
		}

	}

	@FXML
	void limpiar(ActionEvent event) {
		textArea.setText("");
	}

	public void actualizarTabla() {

		tableFormulas.setItems(FXCollections.observableArrayList(formulasTabla));
	}

	private void actualizarHistorial() {
		historial.add(textArea.getText());
		historial.remove(0);
	}

}
