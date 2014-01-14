import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Automato {
	private HashMap<Integer, Estado> estados;
	private Estado estadoInicial;
	private HashMap<Integer, Estado> estadosFinais;
	private HashSet<Transicao> transicoes;
	private static int contadorEstados;


	public Automato(){
		this.estados = new HashMap<Integer, Estado>();
		this.estadosFinais = new HashMap<Integer, Estado>();
		this.transicoes = new HashSet<Transicao>();
	}
	

	public Estado getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(Estado estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public HashMap<Integer, Estado> getEstados() {
		return estados;
	}

	public void setEstados(HashMap<Integer, Estado> estados) {
		this.estados = estados;
	}

	public HashMap<Integer, Estado> getEstadosFinais() {
		return estadosFinais;
	}

	public void setEstadosFinais(HashMap<Integer, Estado> estadosFinais) {
		this.estadosFinais = estadosFinais;
	}

	public HashSet<Transicao> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(HashSet<Transicao> transicoes) {
		this.transicoes = transicoes;
	}

	public void setEstadoFinal(Estado estado){
		getEstadosFinais().put(estado.getId(), estado);
	}
	
	public Estado addEstado() {
		contadorEstados++;
		Estado estado = new Estado(contadorEstados, "q" + contadorEstados);
		estados.put(contadorEstados, estado);	
	
		return estado;
	}
	
	public Transicao addTransicao(Estado e1, Estado e2, String simbolo){
		Transicao t = new Transicao(e1, e2, simbolo);
		getTransicoes().add(t);
		return t;
	}

	public int getContadorEstados() {
		return contadorEstados;
	}
	
	//Obtém o fecho vazio para um estado
	public HashSet<Estado> fechoVazio(Estado estado){
		Stack<Estado> pilha = new Stack<Estado>();
		HashSet<Estado> estadosAcessiveis = new HashSet<Estado>();
		
		pilha.push(estado);
		if (!estadosAcessiveis.contains(estado)){
			estadosAcessiveis.add(estado);
		}
		
		while (!pilha.isEmpty()){
			Estado e = pilha.pop();
			
			for (Transicao t : getTransicoes()){
				if (t.getOrigem().equals(e) && t.getSimbolo().equals("E")){
					if (!estadosAcessiveis.contains(t.getDestino())){
						estadosAcessiveis.add(t.getDestino());
						pilha.push(t.getDestino());
					}
				}
			}
		}
		
		return estadosAcessiveis;
	}
	
	//Verifica se uma palavra é aceita simulando a execução de um AFND
	public boolean aceitaPalavra(String palavra){
		HashSet<Estado> estadosPossiveis = fechoVazio(getEstadoInicial());
		HashSet<Estado> estadosTemp = new HashSet<Estado>();
		char c;
		boolean aceita = false;
		
		/*System.out.print("\nEstado Acessiveis Inicial: [ ");
		for (Estado e : estadosPossiveis){
			System.out.print(e.getNome() + " ");
		}
		System.out.println("] ");*/
		
		for (int i = 0 ; i < palavra.length() ; i++){
			c = palavra.charAt(i);
			
			for (Estado estadoPossivel : estadosPossiveis){
				
				for (Transicao t : getTransicoes()){
					
					if (t.getOrigem().equals(estadoPossivel) && t.getSimbolo().equals(String.valueOf(c))){
						
							HashSet<Estado> fechoEstadoPossivel = fechoVazio(t.getDestino());
							
							for (Estado e : fechoEstadoPossivel){
								
								if (!estadosTemp.contains(e)){
									estadosTemp.add(e);
								}
								
							}
						
					}
					
				}
			}
			
			estadosPossiveis.clear();
			estadosPossiveis.addAll(estadosTemp);
			estadosTemp.clear();
			
			/*System.out.print("Estado Possiveis em "+c+" : [ ");
			for (Estado e : estadosPossiveis){
				System.out.print(e.getNome() + " ");
			}
			System.out.println("] ");*/
			
		}
		
		/*System.out.print("Estado Acessiveis Final: [ ");
		for (Estado e : estadosPossiveis){
			System.out.print(e.getNome() + " ");
		}
		System.out.println("] ");*/
		
		
		//Verifica se algum dos estados possíveis após ler a palavra pertence aos estados finais
		for (Integer key : getEstadosFinais().keySet()){
			if (estadosPossiveis.contains(getEstadosFinais().get(key))){
				aceita = true;
			}
		}
		
		return aceita;
	}
	
	public String showAutomato(){
		StringBuffer str = new StringBuffer();
		
		str.append("\nEstados: [ ");
		for (Integer i : getEstados().keySet()){
			str.append(getEstados().get(i).getNome() +" ");
		}
		str.append("]\n");
		
		str.append("Transicoes: \n");
		for (Transicao t: getTransicoes()){
			str.append(t.getOrigem().getNome() +" --"+ t.getSimbolo() +"--> "+ t.getDestino().getNome() +"\n");
		}
		
		str.append("Estado Inicial ["+getEstadoInicial().getNome()+"]\n");
		
		str.append("Estados Finais: [ ");
		for (Integer i :  getEstadosFinais().keySet()){
			str.append(getEstadosFinais().get(i).getNome() +" ");
		}
		str.append("]\n");

		return str.toString();
	}

}
