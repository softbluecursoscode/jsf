package curso.jsf.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import curso.jsf.model.Endereco;

@Named("form")
@RequestScoped
public class FormBean implements Serializable {

	private Endereco endereco;
	
	public FormBean() {
		endereco = new Endereco();
		endereco.setRua("Rua Y");
		endereco.setNumero("9966");
	}
	
	public String processar() {
		System.out.println("Rua: " + endereco.getRua());
		System.out.println("N�mero: " + endereco.getNumero());
		return "result";
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
