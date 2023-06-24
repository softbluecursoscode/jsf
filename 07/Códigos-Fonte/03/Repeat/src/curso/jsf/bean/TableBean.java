package curso.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import curso.jsf.model.Despesa;

@Named("table")
@SessionScoped
public class TableBean implements Serializable {

	private List<Despesa> despesas = new ArrayList<Despesa>();
	
	public String inserir() {
		Despesa despesa = new Despesa();
		despesa.setEdit(true);
		despesas.add(despesa);
		return null;
	}
	
	public String excluir(Despesa despesa) {
		despesas.remove(despesa);
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

	public List<Despesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}
}
