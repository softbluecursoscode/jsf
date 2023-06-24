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
		despesas.add(new Despesa(1, "02/10/2014", "Restaurante Comida Boa", "Alimentação", 95.0));
		despesas.add(new Despesa(2, "03/10/2014", "Salão de Beleza", "Beleza e Estética", 87.0));
		despesas.add(new Despesa(3, "04/10/2014", "Energia Elétrica", "Casa", 115.0));
		despesas.add(new Despesa(4, "04/10/2014", "Combustível", "Automóvel", 130.5));
		despesas.add(new Despesa(5, "05/10/2014", "Doce", "Alimentação", 3.0));
		despesas.add(new Despesa(6, "06/10/2014", "Livro", "Livros e Revistas", 39.9));
		despesas.add(new Despesa(7, "06/10/2014", "Uniforme escolar", "Vestuário", 150.4));
		despesas.add(new Despesa(8, "04/10/2014", "Sapato", "Vestuário", 110.9));
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
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alteração na despesa " + d.getId() + " cancelada", null);
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
