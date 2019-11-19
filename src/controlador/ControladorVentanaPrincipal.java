package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modelo.FormulaBienFormada;
import modelo.Operadores;
import vista.PropertiesLenguaje;
import vista.TablaFormulas;

public class ControladorVentanaPrincipal implements Initializable {

	@FXML
	private Button btnAtras;

	@FXML
	private TableView<TablaFormulas> tableFormulas;

	@FXML
	private TableColumn<TablaFormulas, String> columnFormulas;

	@FXML
	private TableColumn<TablaFormulas, Button> columnBoton;

	@FXML
	private TableColumn<TablaFormulas, CheckBox> columnSeleccionar;

	@FXML
	private ComboBox<String> comboMetodo;

	@FXML
	private Button btnEjecutar;
	
	@FXML
    private Menu menuAyuda;

    @FXML
    private Menu menuArchivo;

    @FXML
    private MenuItem submenuReset;

    @FXML
    private Label labelEnunciado;

    @FXML
    private MenuItem submenuCerrar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Menu menuEditar;
    
    @FXML
    private MenuItem submenuCambiar;

    @FXML
    private MenuItem submenuGuia;
    
    @FXML
    private Label labelPrograma;

    @FXML
    private Label labelHallar;
    

	@FXML
	private TextArea textArea;
	private ArrayList<String> formula = new ArrayList<>();
	private ArrayList<String> formulasSeleccionadas = new ArrayList<>();

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
		aux.add(PropertiesLenguaje.prop.getProperty("satis"));
		comboMetodo.setItems(aux);
		tableFormulas.setItems(formulasTabla);
		historial.add("");
		historial.add("");
		columnFormulas.setCellValueFactory(new PropertyValueFactory<>("formula"));
		columnBoton.setCellValueFactory(new PropertyValueFactory<>("boton"));
		columnSeleccionar.setCellValueFactory(new PropertyValueFactory<>("check"));
		tableFormulas.setItems(formulasTabla);
		textArea.addEventFilter(KeyEvent.ANY, event -> {
			KeyCode code = event.getCode();
			textArea.deselect();
			if (code == KeyCode.ENTER || code == KeyCode.TAB || code == KeyCode.X || code == KeyCode.V) {
				event.consume();
			} else if (code == KeyCode.BACK_SPACE) {
				int pos = textArea.getCaretPosition();
				if (pos > 0) {
					char c = textArea.getText().charAt(pos - 1);
					if (!Character.isAlphabetic(c) || c == 'v' || c == 'ʌ' || c == 'V') {
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
			actualizarHistorial();

		});
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(createDefaultMenuItems(textArea));
		textArea.setContextMenu(contextMenu);
		textArea.setTextFormatter(new TextFormatter<>((change) -> {
			change.setText(change.getText().toLowerCase());
			return change;
		}));
		cambiarIdioma();
		
	}

	private List<MenuItem> createDefaultMenuItems(TextInputControl t) {
		MenuItem cut = new MenuItem("Cut");
		cut.setOnAction(e -> t.cut());
		MenuItem copy = new MenuItem("Copy");
		copy.setOnAction(e -> t.copy());
		MenuItem paste = new MenuItem("Paste");
		paste.setOnAction(e -> t.paste());
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e -> t.deleteText(t.getSelection()));
		MenuItem selectAll = new MenuItem("Select All");
		selectAll.setOnAction(e -> t.selectAll());

		BooleanBinding emptySelection = Bindings.createBooleanBinding(() -> t.getSelection().getLength() == 0,
				t.selectionProperty());

		BooleanBinding neverSelection = Bindings.createBooleanBinding(() -> true, t.selectionProperty());

		cut.disableProperty().bind(neverSelection);
		copy.disableProperty().bind(emptySelection);
		delete.disableProperty().bind(neverSelection);
		paste.disableProperty().bind(neverSelection);

		return Arrays.asList(cut, copy, paste, delete, new SeparatorMenuItem(), selectAll);
	}

	@FXML
	void agregarAtomo(KeyEvent event) {
		char c = event.getCharacter().charAt(0);
		boolean ward = true;

		if (!Character.isLetter(c) || c == 'v' || c == 'ʌ' || c == 'V') {
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
	void seleccionar() {
		formulasSeleccionadas = new ArrayList<String>();
		tableFormulas.getSelectionModel().clearSelection();
		for (TablaFormulas aux : formulasTabla) {
			if (aux.getCheck().isSelected()) {
				formulasSeleccionadas.add(aux.getFormula());
				tableFormulas.getSelectionModel().select(aux);
			}
		}
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
			JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorVacio"), "Error", JOptionPane.ERROR_MESSAGE);
		} else if (cadena.contains("()")) {
			JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorAtomo"), "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			textArea.setText("");
			historial = new ArrayList<String>();
			historial.add("");
			historial.add("");
			Button boton = new Button(PropertiesLenguaje.prop.getProperty("eliminar"));
			boton.setOnAction(this::handleButtonAction);
			boton.setId(formulasTabla.size() + "");
			CheckBox check = new CheckBox();
			check.setId(formulasTabla.size() + "");
			check.setOnAction(this::handleCheckAction);
			formulasTabla.add(new TablaFormulas(cadena, boton, check));

		}

	}

	private void handleCheckAction(ActionEvent action) {
		String cadena = ((CheckBox) action.getSource()).getId();
		for (TablaFormulas aux : formulasTabla) {
			if (aux.getId().equals(cadena)) {
				tableFormulas.getSelectionModel().select(aux);
				seleccionar();
				break;
			}
		}

	}

	private void handleButtonAction(ActionEvent action) {
		String cadena = ((Button) action.getSource()).getId();
		for (TablaFormulas aux : formulasTabla) {
			if (aux.getId().equals(cadena)) {
				formulasTabla.remove(aux);
				seleccionar();
				break;
			}
		}
	}

	@FXML
	void limpiar(ActionEvent event) {
		textArea.setText("");
	}

	@FXML
	void limpiarTodo(ActionEvent event) {
		textArea.setText("");
		formulasTabla.clear();
		historial = new ArrayList<String>();
		historial.add("");
		historial.add("");

	}

	@FXML
	void cerrar(ActionEvent event) {
		((Stage) textArea.getScene().getWindow()).close();
	}

	@FXML
	void abrirGuia(ActionEvent event) {
		try {
			File objetofile = new File("src/vista/guia.txt");
			Desktop.getDesktop().open(objetofile);

		} catch (IOException ex) {
			try {
				File objetofile = new File("lib/guia.txt");
				Desktop.getDesktop().open(objetofile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorGuia"));
			}

		}

	}

	@FXML
	void atras(ActionEvent event) {
		if (historial.size() > 2) {
			textArea.setText(historial.get(historial.size() - 2));
			historial.remove(historial.size() - 1);
		} else {
			textArea.setText("");
		}
	}

	@FXML
	void ejecutar(ActionEvent event) {
		FormulaBienFormada fbf = new FormulaBienFormada(
				tableFormulas.getSelectionModel().getSelectedItem().getFormula());
		String cadena = "";
		String titulo = "";
		String formulaL = tableFormulas.getSelectionModel().getSelectedItem().getFormula();
		if (comboMetodo.getSelectionModel().getSelectedItem().equals("FNC")) {
			if(formulasSeleccionadas.size()!=1) {
				JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorSeleccionar"), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				cadena = visualizarFNC(fbf.toFNC());
				titulo = "FNC";
			}
		} else if (comboMetodo.getSelectionModel().getSelectedItem().equals("FC")) {
			if(formulasSeleccionadas.size()!=1) {
				JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorSeleccionar"), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				cadena = visualizarFC(fbf.toFC().toString());
				titulo = "FC";
			}		
		} else if (comboMetodo.getSelectionModel().getSelectedItem().equals("FND")) {
			if(formulasSeleccionadas.size()!=1) {
				JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorSeleccionar"), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				cadena = visualizarFND(fbf.toFND());
				titulo = "FND";
			}
		} else {
			if (formulasSeleccionadas.size() >= 3) {
				ArrayList<String> fcs = new ArrayList<String>();
				for(String aux:formulasSeleccionadas) {
					FormulaBienFormada fbfAux = new FormulaBienFormada(aux);
					for (String fc : fbfAux.toFC()) {
						if (!fcs.contains(fc)) {
						}
							fcs.add(fc);
					}
				}
				cadena = visualizarResolucion(fbf.hallarSatisfacibilidad(fcs).toString());
				if(fcs.contains("")) {
					cadena+="\n\t"+PropertiesLenguaje.prop.getProperty("satisF");
				}else {
					cadena+="\n\t"+PropertiesLenguaje.prop.getProperty("satisV");
				}
				titulo = PropertiesLenguaje.prop.getProperty("satis");
				formulaL=formulasSeleccionadas.toString();
			} else {
				JOptionPane.showMessageDialog(null, PropertiesLenguaje.prop.getProperty("errorResolucion"), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(TablaFormulas.class.getResource("VentanaOperaciones.fxml"));
				Scene scene = new Scene(fxmlLoader.load());
				Stage stage = new Stage();
				stage.setTitle(PropertiesLenguaje.prop.getProperty("proyecto"));
				stage.setScene(scene);
				ControladorOperaciones con = fxmlLoader.getController();
				con.setFormula(formulaL, cadena, (Stage) textArea.getScene().getWindow(), titulo);
				stage.show();
				((Stage) textArea.getScene().getWindow()).close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

	private String visualizarFNC(String cadena) {
		cadena = "\t" + cadena;
		cadena = cadena.replaceAll(Operadores.CONJUNCION, " " + Operadores.CONJUNCION + "\n\t");

		return cadena;

	}

	private String visualizarFND(String cadena) {
		cadena = "\t" + cadena;
		cadena = cadena.replaceAll(Operadores.DISYUNCION, " " + Operadores.DISYUNCION + "\n\t");

		return cadena;

	}

	private String visualizarFC(String cadena) {
		cadena = "\t" + cadena;
		cadena = cadena.replaceAll(",", ",\n\t");

		return cadena;

	}

	private String visualizarResolucion(String cadena) {
		cadena = cadena.substring(1, cadena.length() - 1);
		cadena = "\t" + cadena;
		cadena = cadena.replace("|,", ",\n\t");
		cadena = cadena.replace("|", "");

		return cadena;

	}

	private void actualizarHistorial() {
		if (!textArea.getText().equals(historial.get(historial.size() - 1))) {
			historial.add(textArea.getText());
		}
	}
	
	@FXML
    public void cambiarIdioma(ActionEvent event) {
		((Stage) textArea.getScene().getWindow()).close();
		if(PropertiesLenguaje.idioma) {
			PropertiesLenguaje.setIngles();
			PropertiesLenguaje.idioma=false;
		}else {
			PropertiesLenguaje.setEspanol();
			PropertiesLenguaje.idioma=true;
		}
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
	
	public void cambiarIdioma() {	
		btnAtras.setText(PropertiesLenguaje.prop.getProperty("atras"));
		columnFormulas.setText(PropertiesLenguaje.prop.getProperty("tablaFormulas"));
		columnBoton.setText(PropertiesLenguaje.prop.getProperty("tablaBoton"));
		comboMetodo.setPromptText(PropertiesLenguaje.prop.getProperty("combo"));
		btnEjecutar.setText(PropertiesLenguaje.prop.getProperty("ejecutar"));
		menuAyuda.setText(PropertiesLenguaje.prop.getProperty("ayuda"));
		menuArchivo.setText(PropertiesLenguaje.prop.getProperty("archivo"));
		submenuReset.setText(PropertiesLenguaje.prop.getProperty("resetear"));
		labelEnunciado.setText(PropertiesLenguaje.prop.getProperty("enunciado"));
		submenuCerrar.setText(PropertiesLenguaje.prop.getProperty("cerrar"));
		btnLimpiar.setText(PropertiesLenguaje.prop.getProperty("limpiar"));
		btnAgregar.setText(PropertiesLenguaje.prop.getProperty("agregar"));
		menuEditar.setText(PropertiesLenguaje.prop.getProperty("editar"));
		submenuCambiar.setText(PropertiesLenguaje.prop.getProperty("idioma"));
		submenuGuia.setText(PropertiesLenguaje.prop.getProperty("guia"));
		labelPrograma.setText(PropertiesLenguaje.prop.getProperty("programa"));
		labelHallar.setText(PropertiesLenguaje.prop.getProperty("hallar"));
		tableFormulas.setPlaceholder(new Label(PropertiesLenguaje.prop.getProperty("tabla")));
		
	}

}
