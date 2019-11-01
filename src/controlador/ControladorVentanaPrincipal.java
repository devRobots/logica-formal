package controlador;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControladorVentanaPrincipal {

    @FXML
    private TextArea textArea;
    private ArrayList<String> formula=new ArrayList<String>();

    @FXML
    void agregarParentesisIzq(ActionEvent event) {
    }

    @FXML
    void agregarParentesisDer(ActionEvent event) {

    }

    @FXML
    void agregarNegacion(ActionEvent event) {

    }

    @FXML
    void agregarConjuncion(ActionEvent event) {

    }

    @FXML
    void agregarDisyuncion(ActionEvent event) {

    }

    @FXML
    void agregarCondicionalIzq(ActionEvent event) {

    }

    @FXML
    void agregarCondicionalDer(ActionEvent event) {

    }

    @FXML
    void agregarBicondicional(ActionEvent event) {

    }

    private void transformar() {
    	for(char aux:textArea.getText().toCharArray()) {
    		
    	}
    }
}

