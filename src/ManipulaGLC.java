import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class ManipulaGLC {
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String gramatica;
		String palavra;
		
		JOptionPane.showMessageDialog(null, "*****Programa reconhece somente gramáticas LL(1)*****");
		try {
			gramatica = in.readLine();
			GLC glc = new GLC(gramatica);
			ArrayList<String> palavras = new ArrayList<String>();

			palavra = in.readLine();
			
			while (!palavra.equals("")){
				palavras.add(palavra);
				palavra = in.readLine();
			}
			
			for (String s : palavras){
				System.out.println(glc.aceitaPalavra(s) ? "1" : "0");
			}
			
			/*
			System.out.print("Variáveis: [ ");
			for (Character c : glc.getVariaveis())
				System.out.print(c+" ");
			System.out.println("]");
			
			
			System.out.print("Terminais: [ ");	
			for (Character c : glc.getTerminais())
				System.out.print(c+" ");
			System.out.println("]");
			
			System.out.println("Produções: ");
			for (Producao p : glc.getProducoes())
				System.out.println(p.getVariavel() + " --> "+ p.getProducao());
			
			System.out.println("\nConjuntos Primeiro:");
			for (char t : glc.getVariaveis()){
				System.out.print( t+":{");
				for (char c : glc.primeiro(t+"")){
					System.out.print(c+" ");
				}
				System.out.print("}\n");
			}
			
			System.out.println("\nConjuntos Seguinte:");
			HashMap<Character,HashSet<Character>> s = glc.seguinte();
			for (char key : s.keySet()){
				HashSet<Character> conj = s.get(key);
				System.out.print(key + ":{");
				for (char simbolo : conj){
					System.out.print(simbolo + " ");	
				}
				System.out.println("}");
			}
				
			glc.printTabelaPreditiva();*/
					
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
