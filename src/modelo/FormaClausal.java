package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class FormaClausal {

	ArrayList<String> formas;
	
	public FormaClausal(String fc) {
		formas = new ArrayList<>();
		
		for (int i = 0; i < fc.length(); i++) {
			String forma = fc.charAt(i) + "";
			
			if (forma.equals(Operadores.NEGACION)) {
				String neg = forma + fc.charAt(i+1);
				i++;
				if (!formas.contains(neg)) {
					formas.add(neg);
				}
			}
			else {
				if (!formas.contains(forma)) {
					formas.add(forma);
				}
			}
		}
		
		Collections.sort(formas);
	}
	
	public static FormaClausal resolucion(char atomo, FormaClausal fc1, FormaClausal fc2) {
		FormaClausal fc = null;
		boolean mod = false;
		boolean prueba=false;
		
		if (contieneNegacion(fc1, atomo)) {
			if (contieneAtomo(fc2, atomo)) {
				
				fc1.formas = eliminarNegacion(fc1, atomo);
				fc2.formas = eliminarAtomo(fc2, atomo);
				mod = true;
			}
		}
		if (contieneNegacion(fc2, atomo)) {
			if (contieneAtomo(fc1, atomo)) {
				fc1.formas = eliminarAtomo(fc1, atomo);
				fc2.formas = eliminarNegacion(fc2, atomo);
				mod = true;
			}
		}
		
		if (mod) {			
			fc = new FormaClausal(fc1.toString() + fc2.toString());
		}
		
		return fc;
	}

	private static ArrayList<String> eliminarAtomo(FormaClausal fc, char atomo) {
		fc.formas.remove(atomo + "");
		return fc.formas;
	}

	private static ArrayList<String> eliminarNegacion(FormaClausal fc, char atomo) {
		fc.formas.remove(Operadores.NEGACION + atomo);
		return fc.formas;
	}
	
	private static boolean contieneNegacion(FormaClausal fc, char atomo) {
		return fc.formas.contains(Operadores.NEGACION + String.valueOf(atomo));
	}
	
	private static boolean contieneAtomo(FormaClausal fc, char atomo) {
		return fc.formas.contains(String.valueOf(atomo));
	}

	@Override
	public String toString() {
		return formas.toString().replace(", ", "").replace("[", "").replace("]", "");
	}

	public ArrayList<String> getFormas() {
		return formas;
	}

	public void setFormas(ArrayList<String> formas) {
		this.formas = formas;
	}

}
