package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.Despesa;

import org.primefaces.event.RowEditEvent;

@Named
@RequestScoped
public class TableBean implements Serializable {

	private static List<Despesa> despesas = new ArrayList<Despesa>();
	
	private Despesa[] selectedDespesa;
	
	static {
		despesas = new ArrayList<Despesa>();
		despesas.add(new Despesa(1, "02/10/2014", "Restaurante Comida Boa", "Alimenta��o", 95.0));
		despesas.add(new Despesa(2, "03/10/2014", "Sal�o de Beleza", "Beleza e Est�tica", 87.0));
		despesas.add(new Despesa(3, "04/10/2014", "Energia El�trica", "Casa", 115.0));
		despesas.add(new Despesa(4, "04/10/2014", "Combust�vel", "Autom�vel", 130.5));
		despesas.add(new Despesa(5, "05/10/2014", "Doce", "Alimenta��o", 3.0));
		despesas.add(new Despesa(6, "06/10/2014", "Livro", "Livros e Revistas", 39.9));
		despesas.add(new Despesa(7, "06/10/2014", "Uniforme escolar", "Vestu�rio", 150.4));
		despesas.add(new Despesa(8, "04/10/2014", "Sapato", "Vestu�rio", 110.9));
	}
	
	public void processar() {
		StringBuilder sb = new StringBuilder("Despesas selecionadas: ");
		for (Despesa d : selectedDespesa) {
			sb.append(d.getDescricao()).append(", ");
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa '" + sb.toString(), null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onEdit(RowEditEvent event) {
		Despesa d = (Despesa) event.getObject();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa " + d.getId() + " alterada", null);
		FacesContext.getCurrentInstance().addMessage(null,  msg);
	}
	
	public void onCancel(RowEditEvent event) {
		Despesa d = (Despesa) event.getObject();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Altera��o na despesa " + d.getId() + " cancelada", null);
		FacesContext.getCurrentInstance().addMessage(null,  msg);
	}
	
	public List<Despesa> getDespesas() {
		return despesas;
	}

	public Despesa[] getSelectedDespesa() {
		return selectedDespesa;
	}

	public void setSelectedDespesa(Despesa[] selectedDespesa) {
		this.selectedDespesa = selectedDespesa;
	}
}
