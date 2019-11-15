package modelo;

public class pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FormulaBienFormada a=new FormulaBienFormada("(a)v(b)");
		//System.out.println(a.axioma5("((b)"+Operadores.CONJUNCION+"(b))v((a)"+Operadores.CONJUNCION+"(a))"));
		System.out.println(a.axioma3("(((d)"+Operadores.CONJUNCION+"(e))v(f))"+Operadores.CONJUNCION+"(((a)"+Operadores.CONJUNCION+"(b))v(c))"));
		//System.out.println(a.axioma3("(a)"+Operadores.CONJUNCION+"(((a)"+Operadores.CONJUNCION+"(b))v(c))"));
		System.out.println(a.esFNC(a.axioma3("(((d)"+Operadores.CONJUNCION+"(e))v(f))"+Operadores.CONJUNCION+"(((a)"+Operadores.CONJUNCION+"(b))v(c))")));
	
	
	}

}
