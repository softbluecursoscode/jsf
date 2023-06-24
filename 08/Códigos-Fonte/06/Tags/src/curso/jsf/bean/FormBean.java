package curso.jsf.bean;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import curso.jsf.model.Endereco;

@Named("form")
@RequestScoped
public class FormBean implements Serializable {

	private Endereco endereco;
	private Date dataNasc;
	
	public String processar() {
		System.out.println("Rua: " + endereco.getRua());
		System.out.println("Número: " + endereco.getNumero());
		System.out.println("Data de Nascimento: " + dataNasc);
		return "result";
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
