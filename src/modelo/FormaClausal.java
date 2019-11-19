package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class FormaClausal {

	ArrayList<String> formas;

	/**
	 * Construye la forma clausal
	 * @param fc
	 */
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
	
	/**
	 * 
	 * Metodo que realiza la resolucion entre dos clausales
	 * @param atomo, literal que se va a eliminar
	 * @param fc1, forma clausal 1
	 * @param fc2, forma clausal 2 
	 * @return forma clausal resultado entre las dos
	 */
	public static FormaClausal resolucion(char atomo, FormaClausal fc1, FormaClausal fc2) {
		FormaClausal fc = null;
		boolean mod = false;
		
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
	
	/**
	 * 
	 * Metodo que elimina el literal de la clausala
	 * @param fc, formula clausal
	 * @param atomo, literal a eliminar
	 * @return la clausal sin el literal
	 */
	private static ArrayList<String> eliminarAtomo(FormaClausal fc, char atomo) {
		fc.formas.remove(atomo + "");
		return fc.formas;
	}

	/**
	 * 
	 * Metodo que elimina el literal negado de la clausula
	 * @param fc, forma clausal
	 * @param atomo, literal a eliminar
	 * @return la clausal sin el literal negado
	 */
	private static ArrayList<String> eliminarNegacion(FormaClausal fc, char atomo) {
		fc.formas.remove(Operadores.NEGACION + atomo);
		return fc.formas;
	}
	
	/**
	 * 
	 * Metodo que verifica si hay una literal con negación
	 * @param fc, forma clausal
	 * @param atomo, literal a comparar
	 * @return true si hay literal negativo
	 */
	private static boolean contieneNegacion(FormaClausal fc, char atomo) {
		return fc.formas.contains(Operadores.NEGACION + String.valueOf(atomo));
	}
	
	/**
	 * 
	 * Metodo que verifica si contiene el literal
	 * @param fc, forma clausal
	 * @param atomo, literal a buscar 
	 * @return true si esta la literal
	 */
	private static boolean contieneAtomo(FormaClausal fc, char atomo) {
		return fc.formas.contains(String.valueOf(atomo));
	}
	
	/**
	 * Metodo toString
	 */
	@Override
	public String toString() {
		return formas.toString().replace(", ", "").replace("[", "").replace("]", "");
	}
	
	/******************************************************************************/
	/******************************************************************************/
	/******************************************************************************/
	public ArrayList<String> getFormas() {
		return formas;
	}

	public void setFormas(ArrayList<String> formas) {
		this.formas = formas;
	}

}
