package entity;

public class Transferencia {
	private String banco;
	private int valor;
	private int repetir;

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getRepetir() {
		return repetir;
	}

	public void setRepetir(int repetir) {
		this.repetir = repetir;
	}

	@Override
	public String toString() {
		return "Transferencia [banco=" + banco + ", valor=" + valor
				+ ", repetir=" + repetir + "]";
	}
}
