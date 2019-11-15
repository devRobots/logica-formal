package modelo;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;

public class FormulaBienFormada {
	public String fbf;
	public ArrayList<Character> atomos;
	public ArbolFormula arbol;
	public ArrayList<String> procedimiento;

	public FormulaBienFormada(String fbf) {
		atomos = new ArrayList<>();

		for (int i = 0; i < fbf.length(); i++) {
			char c = fbf.charAt(i);

			if (esAtomo(c) && !atomos.contains(c)) {
				atomos.add(c);
			}
		}

		// Collections.sort(atomos);

		this.fbf = fbf;
	}

	private boolean esAtomo(char c) {
		return Character.isAlphabetic(c) && c != 'v' && c != 'ʌ';
	}

	public ArrayList<String> getProcedimiento() {
		return procedimiento;
	}

	public String toFNC() {
		String fnc = "";

		int[][] entradas = tabularEntradas();
		int[] salidas = evaluarFBF(entradas);

		for (int i = 0; i < entradas[0].length; i++) {
			fnc += "(";
			for (int j = 0; j < entradas.length; j++) {
				if (salidas[i] == 1) {
					if (entradas[j][i] == 0) {
						fnc += Operadores.NEGACION;
					}
					fnc += atomos.get(j);
				}
			}
			fnc += ")";

			fnc += i < entradas[0].length - 1 ? Operadores.CONJUNCION : "";
		}

		System.out.println(Arrays.toString(salidas));

		fnc.replace("()", "");

		return fnc;
	}

	public String toFNC2() {
		String fnc = "";

		ArbolFormula arbol = new ArbolFormula(fbf);

		int[][] entradas = tabularEntradas();
		int[][] salidas = new int[entradas.length][entradas[0].length];
		int cont = 0;

		for (int i = 0; i < entradas.length; i++) {
			int valores[] = entradas[i];
			if (resolver(arbol.getRaiz(), valores) == 0) {
				salidas[cont] = valores;
				cont++;
				System.out.print("[");
				for (int v : valores) {
					System.out.print(v + ",");
				}
				System.out.print("]");
				System.out.println();
			}
		}

		return getFormulaSimple(salidas);
	}
	
	private String getFormulaSimple(int [][] salidas) {
		String cadena="";
		for(int i=0;i<salidas.length;i++) {
			cadena+="(";
			for(int j=0;j<salidas[0].length;j++) {
				cadena+="(";
				if(salidas[i][j]==0) {
					cadena+=Operadores.NEGACION+atomos.get(j);
				}else {
					cadena+=atomos.get(j);
				}
				cadena+=")";
				if(j!=salidas[0].length-1) {
					cadena+=Operadores.DISYUNCION;
				}
			}
			cadena+=")";
			if(i!=salidas.length-1) {
				cadena+=Operadores.CONJUNCION;
			}
			
		}	
		return cadena;
	}

	private int resolver(Nodo n, int[] valores) {
		if (n.esAtomo()) {
			for (int i = 0; i < atomos.size(); i++) {
				if (atomos.get(i) == n.getValor()) {
					return valores[i];
				}
			}
		}
		if (n.getValor() == Operadores.CONJUNCION.charAt(0)) {
			return conj(resolver(n.getIzquierdo(), valores), resolver(n.getDerecho(), valores));
		}
		if (n.getValor() == Operadores.DISYUNCION.charAt(0)) {
			return disy(resolver(n.getIzquierdo(), valores), resolver(n.getDerecho(), valores));
		}
		if (n.getValor() == Operadores.CONDICIONAL.charAt(0)) {
			return cond(resolver(n.getIzquierdo(), valores), resolver(n.getDerecho(), valores));
		}
		if (n.getValor() == Operadores.EQUIVALENCIA.charAt(0)) {
			return equi(resolver(n.getIzquierdo(), valores), resolver(n.getDerecho(), valores));
		}
		if (n.getValor() == Operadores.NEGACION.charAt(0)) {
			return negar(resolver(n.getIzquierdo(), valores));
		}
		return 0;
	}

	private int[] evaluarFBF(int[][] entradas) {
		int[] salidas = new int[(int) Math.pow(2, atomos.size())];

		for (int i = 0; i < entradas[0].length; i++) {
			int[] entradaTmp = new int[atomos.size()];

			for (int j = 0; j < entradas.length; j++) {
				entradaTmp[j] = entradas[j][i];
			}

			salidas[i] = valorDeVerdadFBF(entradaTmp, fbf);
		}

		return salidas;
	}

	private int valorDeVerdadFBF(int[] entradas, String fbf) {
		if (esAtomo(fbf)) {
			System.out
					.println(Arrays.toString(entradas) + " : " + fbf + " : " + entradas[atomos.indexOf(fbf.charAt(0))]);
			return entradas[atomos.indexOf(fbf.charAt(0))];
		}

		int indice = indiceDeOperadorPrincipal(fbf);

		if (indice == 0) {
			System.out.println(Arrays.toString(entradas) + " : " + fbf + " : "
					+ negar(valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1))));
			return negar(valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1)));
		}

		char c = fbf.charAt(indice);

		if (Operadores.CONJUNCION.equals(String.valueOf(c))) {
			System.out.println(Arrays.toString(entradas) + " : " + fbf + " : "
					+ conj(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
							valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1))));
			return conj(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
					valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1)));
		} else if (Operadores.DISYUNCION.equals(String.valueOf(c))) {
			System.out.println(Arrays.toString(entradas) + " : " + fbf + " : "
					+ disy(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
							valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1))));
			return disy(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
					valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1)));
		} else if (Operadores.CONDICIONAL.equals(String.valueOf(c))) {
			System.out.println(Arrays.toString(entradas) + " : " + fbf + " : "
					+ cond(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
							valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1))));
			return cond(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
					valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1)));
		} else {
			System.out.println(Arrays.toString(entradas) + " : " + fbf + " : "
					+ equi(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
							valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1))));
			return equi(valorDeVerdadFBF(entradas, fbf.substring(1, indice - 1)),
					valorDeVerdadFBF(entradas, fbf.substring(indice + 2, fbf.length() - 1)));
		}
	}

	private boolean esAtomo(String fbf) {
		return fbf.length() == 1;
	}

	private int conj(int a, int b) {
		if (a == 1 && b == 1) {
			return 1;
		}
		return 0;
	}

	private int disy(int a, int b) {
		if (a == 1 || b == 1) {
			return 1;
		}
		return 0;
	}

	private int cond(int a, int b) {
		if (a == 1 && b == 0) {
			return 0;
		}
		return 1;
	}

	private int equi(int a, int b) {
		if (a == b) {
			return 1;
		}
		return 0;
	}

	private int negar(int val) {
		if (val == 0) {
			return 1;
		}
		return 0;
	}

	private int[][] tabularEntradas() {
		int[][] tabla = new int[(int) Math.pow(2, atomos.size())][atomos.size()];

		for (int i = 0; i < tabla.length; i++) {
			int valor = i;
			for (int j = 0; j < tabla[0].length && valor > 0; j++) {
				int tmp = (int) Math.pow(2, tabla[0].length - j - 1);

				if (tmp <= valor) {
					tabla[i][j] = 1;
					valor -= tmp;
				}
			}
		}

		return tabla;
	}

	public boolean esFNC(String fbf) {
		boolean flag = true;
		ArbolFormula arbol = new ArbolFormula(fbf);
		Nodo nodo = arbol.getRaiz();

		if (nodo.getValor() == Operadores.CONJUNCION.charAt(0)) {

			flag = esFNC(nodo, true);

		} else {

			flag = false;

		}
		return flag;
	}

	private boolean esFNC(Nodo nodo, boolean isConjuncion) {
		if (nodo.esAtomo()) {
			return true;
		}
		if (nodo.getValor() == Operadores.DISYUNCION.charAt(0) && isConjuncion) {
			isConjuncion = false;
		} else if (nodo.getValor() == Operadores.CONJUNCION.charAt(0) && !isConjuncion
				|| nodo.getValor() == Operadores.CONDICIONAL.charAt(0)
				|| nodo.getValor() == Operadores.EQUIVALENCIA.charAt(0)) {
			return false;
		}

		return esFNC(nodo.getIzquierdo(), isConjuncion) && esFNC(nodo.getDerecho(), isConjuncion);

	}

	public ArrayList<String> toFC() {
		ArrayList<String> fcs = new ArrayList<>();
		String[] fds = toFNC().split(Operadores.CONJUNCION);

		for (String fd : fds) {
			String fc = fd.replace(Operadores.DISYUNCION, "");
			fc = fc.replace("(", "");
			fc = fc.replace(")", "");

			fcs.add(fc);
		}

		return fcs;
	}

	public int indiceDeOperadorPrincipal(String fbf) {
		int indice = -1;

		if (fbf.startsWith("ï¿½")) {
			indice = 0;
		} else {
			int contParentesis = 0;

			for (int i = 0; i < fbf.length() && indice == -1; i++) {
				char c = fbf.charAt(i);

				if (c == '(') {
					contParentesis++;
				} else if (c == ')') {
					contParentesis--;
				} else {
					if (contParentesis == 0) {
						indice = i;
					}
				}
			}
		}

		return indice;
	}

	public String getFbf() {
		return fbf;
	}

	public ArrayList<Character> getAtomos() {
		return atomos;
	}

}
