/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Logica Formal
 * Ejercicio: Programa FNC, FND, Reslución
 * @author : Brayan Tabares Hidalgo - Yesid Rosas Toro - Samara Rincón Montaña
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package vista;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class PropertiesLenguaje {
	
	public static Properties prop=new Properties();
	public static boolean idioma=true;

	public static void setEspanol() {
		InputStream is = null;	
		try {
			is = new FileInputStream(new File("lib/ES.properties"));
			prop.load(is);
		} catch(IOException e) {		
			try {
				is = new FileInputStream(new File("src/vista/lib/ES.properties"));
				prop.load(is);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo de español.");
				System.exit(0);
			}
		}
	}
	
	public static void setIngles() {
		InputStream is = null;	
		try {
			is = new FileInputStream(new File("lib/EN.properties"));
			prop.load(is);
		} catch(IOException e) {
			try {
				is = new FileInputStream(new File("src/vista/lib/EN.properties"));
				prop.load(is);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo de inglés.");
				System.exit(0);
			}
		}
	}
}
