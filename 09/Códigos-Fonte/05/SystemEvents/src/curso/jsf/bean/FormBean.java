package curso.jsf.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import curso.jsf.business.RegioesProvider;
import curso.jsf.model.Estado;
import curso.jsf.model.Regiao;

@Named("form")
@RequestScoped
public class FormBean implements Serializable {
	
	private Collection<Regiao> regioes;
	private Collection<Estado> estados;
	private String siglaRegiao;
	private String siglaEstado;
	
	public void carregarRegioes(ComponentSystemEvent event) {
		this.regioes = RegioesProvider.getRegioes();
	}
	
	public Collection<Regiao> getRegioes() {
		return regioes;
	}
	
	public Collection<Estado> getEstados() {
		return estados;
	}
	
	public void carregarEstados(ValueChangeEvent event) {
		this.estados = RegioesProvider.getEstadosByRegiao(event.getNewValue().toString());
		FacesContext.getCurrentInstance().renderResponse();
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
