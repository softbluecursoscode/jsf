package curso.jsf.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Veiculo {

	@Id
	@Column(length = 7)
	private String placa;
	
	@Column(length = 20, nullable = false)
	private String marca;
	
	@Column(length = 20, nullable = false)
	private String modelo;
	
	@Column (nullable = false)
	private Integer ano;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Proprietario proprietario;
	
	@OneToMany(mappedBy = "veiculo")
	private List<Imposto> impostos = new ArrayList<Imposto>();

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public List<Imposto> getImpostos() {
		return impostos;
	}

	public void setImpostos(List<Imposto> impostos) {
		this.impostos = impostos;
	}
}
