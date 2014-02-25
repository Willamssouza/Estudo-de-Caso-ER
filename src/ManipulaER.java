import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ManipulaER {
	Operacao op;
	Automato automato;

	public Automato converteERparaAutomato(String expressao) {
		op = new Operacao();
		automato = new Automato();
		char caractere;
		
		
		for (int i = 0; i < expressao.length(); i++) {
			caractere = expressao.charAt(i);

			switch (caractere) {

			case '+':
				automato = op.uniao(automato,
						converteERparaAutomato(expressao.substring(i + 1)));
				i = expressao.length();
				
				break;

			case '.':
				automato = op.concatenacao(automato,
						converteERparaAutomato(expressao.substring(i + 1)));
				i = expressao.length();
				
				break;

			case '*':
				automato = op.fechoK(automato);
				break;

			case '(':
				
				// Extrair a subcadeia entre () e aplicar a fun��o de convers�o
				// sobre ela retornando assim o aut�mato equivalente
				for (int j = i ; j < expressao.length(); j++) {
					
					if (expressao.charAt(j) == ')') {

						automato = converteERparaAutomato(expressao.substring(i + 1, j));

						i=j;
						j = expressao.length();
					}
				}
				
				
				break;

			default:

				automato = op.criarAutomatoSimples(String.valueOf(caractere));
				
			}
		}

		return automato;
	}

	public static void main(String[] args) {

		ManipulaER app = new ManipulaER();
		String expressao = "";
		String palavra = "";
		
		//System.out.println("Express�o Regular (Ex: (1+0).1.0* ): ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
		
		try {
			expressao = in.readLine();
	
			//Converte a express�o para um AFND com transi��es vazias
			Automato automato = app.converteERparaAutomato(expressao);
		
			ArrayList<String> palavras = new ArrayList<String>();
		
			//System.out.println("Digite as palavras: ");
			palavra = in.readLine();
			
			while (!palavra.equals("")){
				palavras.add(palavra);
				palavra = in.readLine();
			}
			
			//System.out.println("Resultado:");
			for (String s : palavras){
				System.out.println(automato.aceitaPalavra(s) ? "1" : "0");
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
