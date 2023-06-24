package curso.jsf.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Proprietario {

	@Id
	private Integer id;
	
	@Column(length = 11, nullable = false)
	private String cpf;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "proprietario")
	private List<Veiculo> veiculos = new ArrayList<Veiculo>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}
}
