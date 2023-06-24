package curso.jsf.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Log {

	public enum TipoMensagem {
		INFO, ERRO;
	}

	/**
	 * ID da mensagem
	 */
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * Data da mensagem
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date data;
	
	/**
	 * Tipo da mensagem de log 
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoMensagem tipo;
	
	/**
	 * Texto da mensagem
	 */
	private String mensagem;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoMensagem getTipo() {
		return tipo;
	}

	public void setTipo(TipoMensagem tipo) {
		this.tipo = tipo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
