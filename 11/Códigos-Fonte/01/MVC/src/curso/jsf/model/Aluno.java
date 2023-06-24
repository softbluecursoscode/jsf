package curso.jsf.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Aluno {

	/**
	 * Número de matrícula
	 */
	@Id
	@Column(length = 10)
	private String numMatricula;
	
	/**
	 * Nome do aluno
	 */
	@Column(length = 50, nullable = false)
	private String nome;
	
	/**
	 * Data de nascimento
	 */
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	/**
	 * ID da turma onde o aluno estuda
	 */
	@ManyToOne
	private Turma turma;

	public String getNumMatricula() {
		return numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataNascStr = dataNascimento != null ? sdf.format(dataNascimento) : null;
		
		return "Aluno [numMatricula=" + numMatricula + ", nome=" + nome + ", dataNascimento=" + dataNascStr + "]";
	}
}
