package curso.jsf.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import curso.jsf.business.RegioesProvider;
import curso.jsf.model.Estado;
import curso.jsf.model.Regiao;

@Named("form")
@SessionScoped
public class FormBean implements Serializable {
	
	private Collection<Regiao> regioes;
	private Collection<Estado> estados;
	private String siglaRegiao;
	private String siglaEstado;
	
	@PostConstruct
	protected void init() {
		this.regioes = RegioesProvider.getRegioes();
	}
	
	public Collection<Regiao> getRegioes() {
		return regioes;
	}
	
	public Collection<Estado> getEstados() {
		return estados;
	}
	
	public void carregarEstados(ActionEvent event) {
		this.estados = RegioesProvider.getEstadosByRegiao(siglaRegiao);
	}

	public String getSiglaRegiao() {
		return siglaRegiao;
	}

	public void setSiglaRegiao(String siglaRegiao) {
		this.siglaRegiao = siglaRegiao;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}
	
}
