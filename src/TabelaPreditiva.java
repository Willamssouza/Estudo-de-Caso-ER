import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TabelaPreditiva {
	private HashMap<Character,HashMap<Character, ArrayList<Producao>>> tabela;
	
	public TabelaPreditiva(List <Character> variaveis, List<Character> terminais){
		tabela = new HashMap<Character,HashMap<Character, ArrayList<Producao>>>();
		
		for (char c : variaveis){
			HashMap<Character, ArrayList<Producao>> hashColunas = 
					new HashMap<Character,ArrayList<Producao>>();
			for (char t: terminais){
				if (t!=GLC.EPSILON){
					hashColunas.put(t, new ArrayList<Producao>());
				}
			}
			hashColunas.put('$', new ArrayList<Producao>());
			tabela.put(c, hashColunas);
		}
	}
	
	public ArrayList<Producao> getProducoes(char variavel, char terminal){
		return tabela.get(variavel).get(terminal);
	}
	
	public void addProducao(char variavel, char terminal, Producao producao){
		ArrayList<Producao> producoes = getProducoes(variavel, terminal);
		producoes.add(producao);
		tabela.get(variavel).put(terminal, producoes);
	}
	
	public Set<Character> keySetRow(){
		return tabela.keySet();
	}
	
	public Set<Character> keySetCollumn(){
		Set<Character> key = tabela.keySet();
		for (char c : key){
			return tabela.get(c).keySet();
		}
		
		return null;
	}
	
	public boolean isAmbigua(){
		boolean retorno = false;
		for (char keyVariavel : tabela.keySet()){
			for (char keyTerminal : tabela.get(keyVariavel).keySet()){
				retorno |= tabela.get(keyVariavel).get(keyTerminal).size() > 1;
			}
		}
		return retorno;
	}
}
