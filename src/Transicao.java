
public class Transicao {
	private String simbolo;
	private Estado origem;
	private Estado destino;
	
	public Transicao(Estado origem, Estado destino, String simbolo){
		setOrigem(origem);
		setDestino(destino);
		setSimbolo(simbolo);
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Estado getOrigem() {
		return origem;
	}

	public void setOrigem(Estado origem) {
		this.origem = origem;
	}

	public Estado getDestino() {
		return destino;
	}

	public void setDestino(Estado destino) {
		this.destino = destino;
	}
	

}
