package vista;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLenguaje {
	
	private static Properties prop=new Properties();
	
	public PropertiesLenguaje() {
		setEspanol();
	}
	
	public Properties getProp() {
		return prop;
	}

	public void setEspanol() {
		InputStream is = null;	
		try {
			is = new FileInputStream(new File(PropertiesLenguaje.class.getResource("ES.properties")+""));
			prop.load(is);
		} catch(IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public void setIngles() {
		InputStream is = null;	
		try {
			is = new FileInputStream(new File(PropertiesLenguaje.class.getResource("EN.properties")+""));
			prop.load(is);
		} catch(IOException e) {
			System.out.println(e.toString());
		}
	}
}
