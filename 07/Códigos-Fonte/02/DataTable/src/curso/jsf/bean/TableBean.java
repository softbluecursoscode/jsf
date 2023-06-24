package curso.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import curso.jsf.model.Despesa;

@Named("table")
@SessionScoped
public class TableBean implements Serializable {

	private List<Despesa> despesasList = new ArrayList<Despesa>();
	
	private ListDataModel<Despesa> despesas = new ListDataModel<Despesa>(despesasList);
	
	public String inserir() {
		Despesa despesa = new Despesa();
		despesa.setEdit(true);
		despesasList.add(despesa);
		return null;
	}
	
	public String excluir(Despesa despesa) {
		despesasList.remove(despesa);
		return null;
	}
	
	public String editar(Despesa despesa) {
		despesa.setEdit(true);
		return null;
	}
	
	public String atualizar(Despesa despesa) {
		despesa.setEdit(false);
		return null;
	}

	public ListDataModel<Despesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(ListDataModel<Despesa> despesas) {
		this.despesas = despesas;
	}
}
