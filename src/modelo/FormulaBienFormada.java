package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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

		this.fbf = fbf;
	}

	private boolean esAtomo(char c) {
		return Character.isAlphabetic(c) && c != 'v' && c != 'ʌ';
	}

	public ArrayList<String> getProcedimiento() {
		return procedimiento;
	}

	public String toFNC() {
		ArbolFormula arbol = new ArbolFormula(fbf);

		int[][] entradas = tabularEntradas();
		int[][] salidas = new int[entradas.length][entradas[0].length];
		int cont = 0;

		for (int i = 0; i < entradas.length; i++) {
			int valores[] = entradas[i];
			if (resolver(arbol.getRaiz(), valores) == 0) {
				salidas[cont] = valores;
				cont++;
			}
		}

		salidas = Arrays.copyOf(salidas, cont);

		String cadena = getFormulaArbol(salidas, true).toString();
		if (!cadena.equals("")) {
			return cadena;
		} else {
			cadena = toFND();
			return axioma5(axioma3(cadena, true));
		}
	}

	public String toFND() {
		ArbolFormula arbol = new ArbolFormula(fbf);

		int[][] entradas = tabularEntradas();
		int[][] salidas = new int[entradas.length][entradas[0].length];
		int cont = 0;

		for (int i = 0; i < entradas.length; i++) {
			int valores[] = entradas[i];
			if (resolver(arbol.getRaiz(), valores) == 1) {
				salidas[cont] = valores;
				cont++;
			}
		}

		salidas = Arrays.copyOf(salidas, cont);

		String cadena = getFormulaArbol(salidas, false).toString();
		if (!cadena.equals("")) {
			return cadena;
		} else {
			cadena = toFNC();
			return axioma5(axioma3(cadena, false));
		}
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
		System.out.println(fcs.toString());
		HashSet<HashSet<String>> set = new HashSet<HashSet<String>>();
		for (int i = 0; i < fcs.size(); i++) {
			set.add(new HashSet<String>());
			String c1 = fcs.get(i);
			HashSet<String> aux = new HashSet<String>();
			for (int j = 0; j < c1.length(); j++) {
				String atomo = String.valueOf(c1.charAt(j));
				if (atomo.equals(Operadores.NEGACION)) {
					atomo += String.valueOf(c1.charAt(++j));
				}
				aux.add(atomo);
			}
			set.add(aux);
		}
		fcs = new ArrayList<String>();
		for (HashSet<String> aux : set) {
			String cadena = "";
			for (String aux2 : aux) {
				cadena += aux2;
			}
			if (!cadena.isEmpty()) {
				fcs.add(cadena);
			}

		}
		return fcs;
	}
	
	public ArrayList<String> resolucion2() {
		ArrayList<String> fc=toFC();
		ArrayList<String> op=new ArrayList<String>();
		for(String aux:fc) {
			op.add("Hipotesis");
		}
		for (int i = 0; i < fc.size(); i++) {
			String c1 = fc.get(i);
			boolean comp=false;
			System.out.println("----calusula"+c1);
			for (int j = 0; j < c1.length()&&!comp; j++) {
				String atomo = String.valueOf(c1.charAt(j));
				if (atomo.equals(Operadores.NEGACION)) {
					atomo += String.valueOf(c1.charAt(++j));
				}
				System.out.println("atomo"+atomo);
				for(int k=i+1;k<fc.size()&&!comp;k++) {
					String c2=c1;
					int index=indexContraparte(atomo, fc.get(k));
					System.out.println("comparacion "+atomo+","+fc.get(k)+"   "+index);
					if(index!=-1) {
						if(atomo.length()==1) {
							c2=c1.replace(Operadores.NEGACION+atomo, "");
							System.out.println("cambio1 "+c2);
						}else {
							if(c1.length()==2) {
								c2="";
								System.out.println("cambio2 "+c2);
							}else if(index==c1.length()-2) {
								c2=c1.substring(0,index);
								System.out.println("cambio3 "+c2);
							}else if(index==0) {
								c2=c1.substring(2,c1.length());
								System.out.println("cambio4 "+c2);
							}else {
								String aux=c1.substring(0,index);
								aux+=c1.substring(index+2);
								c2=aux;
								System.out.println("cambio5 "+c2);
							}
						}
						System.out.println("cambiado por "+c2);
						if(!fc.contains(c2)) {
							op.add("Res("+atomo+"):("+fc.get(i)+","+fc.get(k)+")");
							fc.add(c2);
							comp=true;
							System.out.println("reemplazo");
						}	
					}
				}		
			}
			if(comp) {
				i=0;
				if(fc.contains("")) {
					break;
				}
			}
		}
		ArrayList<String> resolucion=new ArrayList<String>();
		for(int i=0;i<fc.size();i++) {
			resolucion.add("{"+fc.get(i)+"}..."+op.get(i));
			if(i==fc.size()-1) {
				if(fc.get(i).isEmpty()) {
					resolucion.add("Insatisfacible");
				}else {
					resolucion.add("Satisfacible");
				}
			}
		}
		return resolucion;
		
	}

	private int indexContraparte(String atomo, String clausula) {
		if (atomo.length() == 1) {
			return clausula.indexOf(Operadores.NEGACION + atomo);
		} else {
			int first = clausula.indexOf(atomo);
			int last = clausula.lastIndexOf(atomo);
			if (first > 0 && clausula.charAt(first - 1) == Operadores.NEGACION.charAt(0)){
				return first;
			}else if(last > 0 && clausula.charAt(last - 1) == Operadores.NEGACION.charAt(0)) {
				return last;
			}else {
				return -1;
			}	
		}
	}

	private ArbolFormula getFormulaArbol(int[][] salidas, boolean esFNC) {
		ArbolFormula arbol = new ArbolFormula();
		char op1, op2;
		if (esFNC) {
			op1 = Operadores.DISYUNCION.charAt(0);
			op2 = Operadores.CONJUNCION.charAt(0);
		} else {
			op2 = Operadores.DISYUNCION.charAt(0);
			op1 = Operadores.CONJUNCION.charAt(0);
		}
		for (int i = 0; i < salidas.length; i++) {
			for (int j = 0; j < salidas[0].length; j++) {
				if ((salidas[i][j] == 1 && esFNC) || (salidas[i][j] == 0 && !esFNC)) {
					Nodo aux = new Nodo(atomos.get(j));
					Nodo aux2 = new Nodo(Operadores.NEGACION.charAt(0));
					aux2.setIzquierdo(aux);
					arbol.addNodo(aux2, esFNC);
				} else {
					Nodo aux = new Nodo(atomos.get(j));
					arbol.addNodo(aux, esFNC);
				}
				if (j != salidas[0].length - 1) {
					Nodo aux = new Nodo(op1);
					arbol.addNodo(aux, esFNC);
				}
			}
			if (i != salidas.length - 1) {
				Nodo aux = new Nodo(op2);
				arbol.addNodo(aux, esFNC);
			}
		}
		return arbol;
	}

	public void hallarSatisfacibilidad(ArrayList<String> fcs) {
		
		while (!fcs.contains("")) {
			for (int i = 0; i < fcs.size(); i++) {
				for (int j = 0; j < fcs.size() - 1; j++) {
					if (i != j) {
						for (Character atomo : atomos) {
							FormaClausal fc1 = new FormaClausal(fcs.get(i));
							FormaClausal fc2 = new FormaClausal(fcs.get(j));
							
							FormaClausal res = FormaClausal.resolucion(atomo, fc1, fc2);
							
							if (res != null) {
								if (!fcs.contains(res.toString())) {
									fcs.add(res.toString());
								}
							}
						}
					}
				}
			}
		}
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

	public String axioma5(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(salida);

		Nodo actual = arbol.getNodoDuplicado();

		while (actual != null) {
			actual.setValor(actual.getIzquierdo().getValor());

			Nodo phi = actual.getIzquierdo().getIzquierdo();
			Nodo psi = actual.getIzquierdo().getDerecho();

			actual.setIzquierdo(phi);
			actual.setDerecho(psi);

			actual = arbol.getNodoDuplicado();
		}

		salida = arbol.toString();
		return salida;
	}

	public String axioma3(String fbf, boolean esFNC) {
		String salida = fbf;
		ArbolFormula arbol = new ArbolFormula(salida);
		char op1, op2;
		Nodo actual;
		if (esFNC) {
			op1 = Operadores.CONJUNCION.charAt(0);
			op2 = Operadores.DISYUNCION.charAt(0);
			actual = arbol.getNodoDisyuncionConjuncion();
		} else {
			op2 = Operadores.CONJUNCION.charAt(0);
			op1 = Operadores.DISYUNCION.charAt(0);
			actual = arbol.getNodoConjuncionDisyuncion();
		}
		while (actual != null) {
			if (actual.getIzquierdo().getValor() == op1) {
				Nodo izq = new Nodo(op2);
				izq.setIzquierdo(actual.getIzquierdo().getIzquierdo());
				izq.setDerecho(actual.getDerecho());

				Nodo der = new Nodo(op2);
				der.setIzquierdo(actual.getIzquierdo().getDerecho());
				der.setDerecho(actual.getDerecho());

				actual.getIzquierdo().setIzquierdo(izq);
				actual.getIzquierdo().setDerecho(der);
				actual.resetNodo(actual.getIzquierdo());

			} else {
				Nodo izq = new Nodo(op2);
				izq.setIzquierdo(actual.getDerecho().getIzquierdo());
				izq.setDerecho(actual.getIzquierdo());

				Nodo der = new Nodo(op2);
				der.setIzquierdo(actual.getDerecho().getDerecho());
				der.setDerecho(actual.getIzquierdo());

				actual.getDerecho().setIzquierdo(izq);
				actual.getDerecho().setDerecho(der);
				actual.resetNodo(actual.getDerecho());
			}
			if (esFNC) {
				actual = arbol.getNodoDisyuncionConjuncion();
			} else {
				actual = arbol.getNodoConjuncionDisyuncion();
			}
		}

		salida = arbol.toString();
		return salida;
	}

	public boolean esFNC(String fbf) {
		boolean flag = true;
		ArbolFormula arbol = new ArbolFormula(fbf);
		Nodo nodo = arbol.getRaiz();
		flag = esFNC(nodo, true);
		return flag;
	}

	private boolean esFNC(Nodo nodo, boolean isConjuncion) {
		System.out.println(nodo);
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
