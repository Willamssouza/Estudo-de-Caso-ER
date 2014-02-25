


public class Operacao {
	
	public Automato criarAutomatoSimples(String simbolo){
		Automato a = new Automato();
		
		Estado e1 = a.addEstado();
		a.setEstadoInicial(e1);
		
		Estado e2 = a.addEstado();
		a.setEstadoFinal(e2);
		
		a.addTransicao(e1, e2, simbolo);
		
		return a;
	}
	
	public Automato concatenacao(Automato a1, Automato a2){
		Automato a =  new Automato();
		
		a.setEstados(a1.getEstados());
		a.getEstados().putAll(a2.getEstados());
		
		a.setEstadoInicial(a1.getEstadoInicial());
		a.setEstadosFinais(a2.getEstadosFinais());
		
		a.setTransicoes(a1.getTransicoes());
		a.getTransicoes().addAll(a2.getTransicoes());
		
		for(Integer i : a1.getEstadosFinais().keySet()){
			a.addTransicao(a1.getEstadosFinais().get(i), a2.getEstadoInicial(), "E");
		}
		
		return a;
	}
	
	public Automato uniao(Automato a1, Automato a2){
		Automato a =  new Automato();
		
		a.setEstados(a1.getEstados());
		a.getEstados().putAll(a2.getEstados());
		
		a.setEstadoInicial(a.addEstado());
		
		Estado estadoFinal = a.addEstado();
		a.setEstadoFinal(estadoFinal);
		
		a.setTransicoes(a1.getTransicoes());
		a.getTransicoes().addAll(a2.getTransicoes());
		
		
		a.addTransicao(a.getEstadoInicial(), a1.getEstadoInicial(), "E");
		a.addTransicao(a.getEstadoInicial(), a2.getEstadoInicial(), "E");
		
		for(Integer i : a1.getEstadosFinais().keySet()){
			a.addTransicao(a1.getEstadosFinais().get(i), estadoFinal, "E");
		}
		
		for(Integer i : a2.getEstadosFinais().keySet()){
			a.addTransicao(a2.getEstadosFinais().get(i), estadoFinal, "E");
		}
		
		return a;
	}
	
	public Automato fechoK(Automato a1){
		Automato a =  new Automato();
		
		a.setEstados(a1.getEstados());
		
		a.setEstadoInicial(a1.getEstadoInicial());
		
		Estado estadoFinal = a.addEstado();
		a.setEstadoFinal(estadoFinal);
		
		a.setTransicoes(a1.getTransicoes());
		
		for(Integer i : a1.getEstadosFinais().keySet()){
			a.addTransicao(a1.getEstadosFinais().get(i), estadoFinal, "E");
			a.addTransicao(a1.getEstadosFinais().get(i), a.getEstadoInicial(), "E");
		}
		
		a.addTransicao(a.getEstadoInicial(), estadoFinal, "E");
		
		return a;
	}
	
}
