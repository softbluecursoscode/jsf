package curso.jsf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Turma {

	/**
	 * ID da turma
	 */
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * Nome da turma
	 */
	@Column(length = 10, nullable = false)
	private String nome;
	
	/**
	 * Sala onde a turma está alocada
	 */
	@Column(nullable = false)
	private Integer numSala;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumSala() {
		return numSala;
	}

	public void setNumSala(Integer numSala) {
		this.numSala = numSala;
	}

	@Override
	public String toString() {
		return "Turma [id=" + id + ", nome=" + nome + ", numSala=" + numSala + "]";
	}
}
