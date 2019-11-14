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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
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
	private Button btnAtras;

	@FXML
	private TableView<TablaFormulas> tableFormulas;

	@FXML
	private TableColumn<TablaFormulas, String> columnFormulas;
	
	@FXML
    private TableColumn<TablaFormulas, Button> columnBoton;

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
		columnBoton.setCellValueFactory(new PropertyValueFactory<>("boton"));
		tableFormulas.setItems(formulasTabla);
		textArea.addEventFilter(KeyEvent.ANY, event -> {
			KeyCode code = event.getCode();
			if (code == KeyCode.ENTER || code == KeyCode.TAB || code == KeyCode.CONTROL || code == KeyCode.V) {
				event.consume();
			} else if (code == KeyCode.BACK_SPACE) {
				int pos = textArea.getCaretPosition();
				if (pos > 0) {
					char c = textArea.getText().charAt(pos - 1);
					if (!Character.isAlphabetic(c) || c == 'v' || c == 'ʌ') {
						event.consume();
					}
				}

			} else if (code == KeyCode.DELETE) {
				int pos = textArea.getCaretPosition();
				if (pos < textArea.getText().length()) {
					char c = textArea.getText().charAt(pos);
					if (!Character.isAlphabetic(c) || c == 'v' || c == 'ʌ') {
						event.consume();
					}
				}

			}
//			System.out.println("[" + event.getCharacter() + "] [" + event.getText() + "] [" + event.getCode() + "] ["
//					+ event.getEventType() + "] [" + event.getSource() + "] [" + event.getTarget() + "]");
			actualizarHistorial();

		});

	}

	@FXML
	void agregarAtomo(KeyEvent event) {
		char c = event.getCharacter().charAt(0);
		boolean ward = true;
		if (!Character.isAlphabetic(c) || c == 'v' || c == 'ʌ') {
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
		
		FormulaBienFormada fbf = new FormulaBienFormada(cadena);
		textArea.setText(fbf.toFNC());

		
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
				historial = new ArrayList<String>();
				historial.add("");
				historial.add("");
				Button boton = new Button("Eliminar");
				boton.setOnAction(this::handleButtonAction);
				boton.setId(formulasTabla.size()+"");
				formulasTabla.add(new TablaFormulas(cadena, boton));
			}
		}

	}
	
	private void handleButtonAction(ActionEvent action) {
		String cadena=((Button)action.getSource()).getId();
		for(TablaFormulas aux:formulasTabla) {
			if(aux.getId().equals(cadena)) {
				formulasTabla.remove(aux);
				break;
			}
		}
	}

	@FXML
	void limpiar(ActionEvent event) {
		textArea.setText("");
	}

	@FXML
	void atras(ActionEvent event) {
		System.out.println(historial);
		if (historial.size() > 2) {
			textArea.setText(historial.get(historial.size() - 2));
			historial.remove(historial.size() - 1);
		} else {
			textArea.setText("");
		}
	}

	public void actualizarTabla() {
		tableFormulas.setItems(FXCollections.observableArrayList(formulasTabla));
	}

	private void actualizarHistorial() {
		if (!textArea.getText().equals(historial.get(historial.size() - 1))) {
			historial.add(textArea.getText());
		}
	}

}
