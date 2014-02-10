import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.swing.JOptionPane;

public class GLC {
	private ArrayList<Character> variaveis;
	private ArrayList<Character> terminais;
	private ArrayList<Producao> producoes;
	private char varInicial;
	private TabelaPreditiva tabelaPreditiva;
	public static final char EPSILON = 'E';
	
	public GLC(String glc){
		this.setVariaveis(new ArrayList<Character>());
		this.setTerminais(new ArrayList<Character>());
		this.setProducoes(new ArrayList<Producao>());
		this.setVarInicial(glc.toUpperCase().charAt(0));
		
		Producao producao;
		
		/*Quebra cadeia de entrada para capturar Não terminais e produções*/
		String[] splitA = glc.replace(" ", "").split(";");
		for (int i = 0 ; i < splitA.length; i++){
			//System.out.print("["+splitA[i]+"]");
			
			String[] splitB = splitA[i].split("->");
			for (int j = 0; j < splitB.length; j++){
				
				//System.out.print("["+splitB[j]+"]");
				if (j == 0){
					if (!variaveis.contains(splitB[j])){
						variaveis.add(new Character(splitB[j].charAt(0)));
						
						terminais.remove(new Character(splitB[j].charAt(0)));
					}			
				}
				
				String[] splitC = splitB[j].split("\\|");
				if(j > 0){
					for (int k = 0 ; k < splitC.length; k++){
					
						//System.out.print("["+splitC[k]+"]");
						producao = new Producao(splitA[i].toUpperCase().charAt(0), splitC[k]);
						producoes.add(producao);
						
						char[] splitD = splitC[k].toCharArray();
						for (int z = 0 ; z < splitD.length ; z++){
								
							if (!variaveis.contains(splitD[z])){
								if (!terminais.contains(splitD[z]))
									terminais.add(new Character(splitD[z]));
							} 
							
						}
						
						
					}
				}
			}
			//System.out.println();
		}
		
		this.tabelaPreditiva = new TabelaPreditiva(this.variaveis, this.terminais);
		constroiTabelaPreditiva();
		//System.out.println(varInicial);
	}
	
	public ArrayList<Character> getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(ArrayList<Character> variaveis) {
		this.variaveis = variaveis;
	}

	public ArrayList<Character> getTerminais() {
		return terminais;
	}

	public void setTerminais(ArrayList<Character> terminais) {
		this.terminais = terminais;
	}

	public ArrayList<Producao> getProducoes() {
		return producoes;
	}

	public void setProducoes(ArrayList<Producao> producoes) {
		this.producoes = producoes;
	}

	public char getVarInicial() {
		return varInicial;
	}

	public void setVarInicial(char varInicial) {
		this.varInicial = varInicial;
	}
	
	public TabelaPreditiva getMatrizSintatica(){
		return this.tabelaPreditiva;	
	}
	
	//Retorna o conjunto FIRST de uma determinada regra
	private HashSet<Character> primeiro(String str){
		HashSet<Character> conjPrimeiro = new HashSet<Character>();
		
		if (str.length() == 1){
			
			//se for um terminal
			//A -> a
			if (terminais.contains(str.charAt(0))){
				conjPrimeiro.add(str.charAt(0));
			} else {
				
				for (Producao p : producoes){
					if (p.getVariavel() == str.charAt(0)){
						int k = 0;
						boolean cont = true;
						
						//A -> X_1 (obtendo X_1)
						String s = p.getProducao();
						while(cont && (k < s.length())){
							
							HashSet<Character> conjX = primeiro(s);
							
							//se E pertencer ao conjunto será removido
							if ( conjX != null){
								boolean rem = conjX.remove(new Character(EPSILON));
								
								if (!rem){
									cont = false;
								}
							}
							conjPrimeiro.addAll(conjX);
							
							k++;
						}
						
						if (cont){
							conjPrimeiro.add(new Character(EPSILON));
						}
					}
				}
			}
			
		
		//A -> X_1X_2X_3...X_n
		} else {
			HashSet<Character> conjAuxiliar = primeiro(str.substring(0,1));
			
			boolean vazio = conjAuxiliar.contains(new Character(EPSILON));
			conjAuxiliar.remove(new Character(EPSILON));
			conjPrimeiro.addAll(conjAuxiliar);
			
			for (int i = 1 ; i < str.length() ; i++){
				if (!vazio){
					break;
				}
				for (int k = 0 ; k < i ; k++){
					vazio = primeiro(str.substring(k, k+1)).remove(new Character(EPSILON));
				}
				
				if (vazio){
					conjAuxiliar = primeiro(str.substring(i, i+1));
					conjAuxiliar.remove(new Character(EPSILON));
					conjPrimeiro.addAll(conjAuxiliar);
				}
				
			}
			
			if (vazio){
				conjPrimeiro.add(new Character(EPSILON));
			}

		}
		
		return conjPrimeiro;
	}
	
	//Retorna os conjuntos FOLLOW da gramática
	private HashMap<Character,HashSet<Character>> seguinte(){
		HashMap<Character,HashSet<Character>> seguintes = 
				new HashMap<Character, HashSet<Character>>();
		
		HashSet<Character> conjAuxiliar = new HashSet<Character>();
			
		for (char var : variaveis){
			seguintes.put(var, new HashSet<Character>());
		}
		
		conjAuxiliar.add('$');
		seguintes.put(varInicial, conjAuxiliar);
		
		for (Producao p : producoes){
				
				String regra = p.getProducao();
				//System.out.println("\n"+p.getVariavel() +"->"+ p.getProducao());
				for (int i = 0 ; i < regra.length() ; i++){
					
					if (variaveis.contains(regra.charAt(i))){
						HashSet<Character> c = new HashSet<Character>();
						
						if (i == regra.length()-1){
							c.add(EPSILON);
						}else{
							c.addAll(primeiro(regra.substring(i+1)));
						}
						
						//System.out.print("["+regra.charAt(i)+"]" + regra.substring(i+1) +" ");
						boolean rem = c.remove(EPSILON);
						
						seguintes.get(regra.charAt(i)).addAll(c);
						
						if (rem){
							
							seguintes.get(regra.charAt(i)).addAll(seguintes.get(p.getVariavel()));
							
						}
					
					}
				}
			
		}
		
		return seguintes;
	}
	
	//Constrói a tabela de análise preditiva
	private void constroiTabelaPreditiva(){
		HashMap<Character, HashSet<Character>> conjSeguinte = seguinte();
		
		for (Producao producao : this.producoes){
			HashSet<Character> conjPrimeiro = primeiro(producao.getProducao());
			
			for (char terminal : conjPrimeiro){
				
				if (terminal != EPSILON){
					tabelaPreditiva.addProducao(producao.getVariavel(), terminal, producao);
				} 
				
				if (conjPrimeiro.contains(GLC.EPSILON)){
					for (char c : conjSeguinte.get(producao.getVariavel())){
						tabelaPreditiva.addProducao(producao.getVariavel(), c, producao);
					}
				}
				
				
			}
		}
	}
	
	public void printTabelaPreditiva(){
		System.out.println();	
		for (char variavel : tabelaPreditiva.keySetRow()){
			System.out.print(variavel + "| ");
			for (char terminal : tabelaPreditiva.keySetCollumn()){
				ArrayList<Producao> producoes = tabelaPreditiva.getProducoes(variavel, terminal);
				
				if (producoes.size() == 0){
					System.out.print(terminal=='$' && variavel == varInicial?"[OK]  ":"[ERRO]  ");
				} else{
					System.out.print(terminal);
					for (Producao p : producoes){
						System.out.print("["+variavel +"->"+ p.getProducao()+"]");
					}
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
	public boolean aceitaPalavra(String palavra){	
		Stack<Character> pilha = new Stack<Character>();
		pilha.push('$');
		pilha.push(varInicial);
		palavra = palavra.concat("$").replaceFirst(
				String.valueOf(GLC.EPSILON), "");
		
		char simbolo;
		char topoPilha;
		int contadorSimbolo = 0;
		
		do{
			topoPilha = (char) pilha.peek();
			simbolo = palavra.charAt(contadorSimbolo);
			
			//Se topo da pilha for um terminal
			if (terminais.contains(topoPilha)){
				
				//Se o terminal for igual ao símbolo 
				//da cadeia avança para o próximo símbolo
				if (topoPilha == simbolo){
					pilha.pop();
					topoPilha = (char) pilha.peek();
					contadorSimbolo++;
				} else {
					return false;
				}
			
				//Se topo da pilha for um não terminal
			} else if (variaveis.contains(topoPilha)){
				ArrayList<Producao> producoes = tabelaPreditiva.getProducoes(topoPilha, simbolo);
				
				//Verifica a tabela de análise preditiva. 
				//Caso não encontre uma produção para M[topoPilha,simbolo]
				//será retornado null, ocasionando erro 
				//(palavra na pertencente a gramática).
				if (producoes.size() > 0){

					char[] regra = producoes.get(0).getProducao().toCharArray();
					pilha.pop();
					
					//Empilha Y_k Y_k-1 ... Y_1   da produção X -> Y_1 Y_2 ... Y_k
					for (int i = regra.length - 1 ; i >= 0 ; i--){
						if (regra[i] != GLC.EPSILON){
							pilha.push(regra[i]);
						}
					}
					
					topoPilha = (char) pilha.peek();
					//System.out.println(p.getVariavel()+"->"+p.getProducao());
					
				} else {
					return false;
				}
				
				//Caso não seja um terminal ou um não terminal
			} else {
				return false;
			}
			
			
		}while(topoPilha != '$');

		simbolo = palavra.charAt(contadorSimbolo);
		
		return topoPilha == simbolo;
	}
	
	
}
