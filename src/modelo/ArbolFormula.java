package modelo;

import java.util.ArrayList;

public class ArbolFormula {
	private Nodo raiz;

	public ArbolFormula(String fbf) {
		raiz = new Nodo(fbf);
	}

	public ArrayList<Nodo> findAll(char caracter) {
		ArrayList<Nodo> coincidencias = new ArrayList<>();
		findAll(raiz, caracter, coincidencias);
		return coincidencias;
	}

	private void findAll(Nodo nodo, char caracter, ArrayList<Nodo> coincidencias) {
		if (raiz == null) {
			return;
		}

		if (nodo.getValor() == caracter) {
			coincidencias.add(nodo);
		}

		findAll(nodo.getIzquierdo(), caracter, coincidencias);
		findAll(nodo.getDerecho(), caracter, coincidencias);
	}

	@Override
	public String toString() {
		return raiz.toString();
	}

	/**
	 * @return the raiz
	 */
	public Nodo getRaiz() {
		return raiz;
	}

	/**
	 * @param raiz the raiz to set
	 */
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

}
