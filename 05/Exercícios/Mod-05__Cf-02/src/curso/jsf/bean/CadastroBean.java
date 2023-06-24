package curso.jsf.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import curso.jsf.bean.business.DataProvider;
import curso.jsf.model.Endereco;
import curso.jsf.model.Estado;
import curso.jsf.model.Interesse;
import curso.jsf.model.Pessoa;
import curso.jsf.model.Telefone;

@Named("cadastro")
@SessionScoped
public class CadastroBean implements Serializable {

	private Pessoa pessoa;
	
	@PostConstruct
	protected void init() {
		pessoa = new Pessoa();
		pessoa.setEnderecoResidencial(new Endereco());
		pessoa.getEnderecoResidencial().setEstado(new Estado());
		pessoa.setEnderecoComercial(new Endereco());
		pessoa.getEnderecoComercial().setEstado(new Estado());
		pessoa.setTelefoneResidencial(new Telefone());
		pessoa.setTelefoneCelular(new Telefone());
		pessoa.setTelefoneComercial(new Telefone());
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public List<Estado> getEstados() {
		return DataProvider.ESTADOS;
	}

	public List<Interesse> getInteresses() {
		return DataProvider.INTERESSES;
	}
}