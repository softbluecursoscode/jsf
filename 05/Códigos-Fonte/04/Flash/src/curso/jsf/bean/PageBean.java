package curso.jsf.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

@Named
@RequestScoped
public class PageBean implements Serializable {

	private String nome;
	
	public String processar() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("nomeDaPessoa", nome);
		
		return "result?faces-redirect=true";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
