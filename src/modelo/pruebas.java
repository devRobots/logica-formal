package modelo;

import java.util.ArrayList;

public class pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		FormulaBienFormada a=new FormulaBienFormada("(a)v(b)");
		//System.out.println(a.axioma5("((b)"+Operadores.CONJUNCION+"(b))v((a)"+Operadores.CONJUNCION+"(a))"));
		//System.out.println(a.axioma3("((((a)ʌ(b))v(c))ʌ(d))v(e)"));
//		System.out.println(a.axioma3("(a)v((b)ʌ((c)v((d)ʌ(e))))",true));
		//System.out.println(a.axioma3("((a)ʌ(b))v(c)",false));
//		//System.out.println(a.axioma3("(a)"+Operadores.CONJUNCION+"(((a)"+Operadores.CONJUNCION+"(b))v(c))"));
//		System.out.println(a.esFNC(a.axioma3("(((d)"+Operadores.CONJUNCION+"(e))v(f))"+Operadores.CONJUNCION+"(((a)"+Operadores.CONJUNCION+"(b))v(c))")));
//	
//		a(new ArrayList<Integer>(),5);
		
	//	ArbolFormula b=new ArbolFormula("(a)v((a)v(c))");
		ArrayList<Integer> aux=new ArrayList<Integer>();
		aux.add(0);
		aux.add(1);
		aux.add(2);
		aux.add(3);
		aux.add(4);
		
		
		aux.remove(3);
		aux.add(3, 9);
		System.out.println(aux);
//		for(int i=0;i<aux.size();i++) {
//			if(aux.get(i)==1) {
//				aux.remove(i);
//			}
//		}
//		System.out.println(aux);
//		
//		String cadena="hol";
//		System.out.println(cadena.substring(1, 3));
		
	}
	
	public static int a(ArrayList<Integer> b, int valor) {
		if(valor==0) {
			System.out.println("0:"+b);
			return 0;
		}
		b.add(valor);
		if(valor==3) {
			b=new ArrayList<Integer>();
		}
		a(b,valor-1);
		if(valor==5) {
			System.out.println("5"+b);
		}
		return 1;
	}

}
