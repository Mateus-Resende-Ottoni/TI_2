package puc_ti2;

import java.util.*;

class Soma2N {

public static Scanner entrada = new Scanner (System.in);
	
public static void main (String args[]) {

// Variáveis
	int n1, n2, soma;
	
// Ler valores na entrada para o primeiro número
	System.out.println("Digite o primeiro numero: ");
	n1 = entrada.nextInt();
	
// Ler valores na entrada para o segundo número
	System.out.println("Digite o segundo numero: ");
	n2 = entrada.nextInt();
	
// Somar os dois números
	soma = n1 + n2;
	
// Mostrar soma
	System.out.println(n1 + " + " + n2 + " = " + soma);
	
	}
}
