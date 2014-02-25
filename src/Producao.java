
public class Producao {
	private char variavel;
	private String producao;
	
	public Producao(char variavel, String producao){
		this.setVariavel(variavel);
		this.setProducao(producao);
	}

	public char getVariavel() {
		return variavel;
	}

	public void setVariavel(char variavel) {
		this.variavel = variavel;
	}

	public String getProducao() {
		return producao;
	}

	public void setProducao(String producao) {
		this.producao = producao;
	}
}
