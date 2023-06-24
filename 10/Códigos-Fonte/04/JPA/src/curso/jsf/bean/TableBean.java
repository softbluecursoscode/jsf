package curso.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import curso.jsf.entity.Despesa;

@Named("table")
@SessionScoped
public class TableBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;
	
	private List<Despesa> despesasList = new ArrayList<Despesa>();
	
	private ListDataModel<Despesa> despesas = new ListDataModel<Despesa>(despesasList);
	
	private Despesa editDespesa;
	
	public String inserir() {
		Despesa despesa = new Despesa();
		editDespesa = despesa;
		despesasList.add(despesa);
		return null;
	}
	
	public String editar(Despesa despesa) {
		editDespesa = despesa;
		return null;
	}
	
	public String cancelar(Despesa despesa) {
		if (despesa.getId() == null) {
			despesasList.remove(despesa);
		}
		editDespesa = null;
		return null;
	}
	
	public boolean isEdit(Despesa despesa) {
		return despesa == editDespesa;
	}
	
	public String excluir(Despesa despesa) throws Exception {
		try {
			ut.begin();
			
			Despesa despesaDB = em.find(Despesa.class, despesa.getId());
			em.remove(despesaDB);
			
			ut.commit();
		} catch (Exception e) {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
			throw e;
		}
		
		lerDespesas();
		return null;
	}

	public String atualizar(Despesa despesa) throws Exception {
		editDespesa = null;
		
		try {
			ut.begin();
			
			if (despesa.getId() == null) {
				// Inserção
				em.persist(despesa);
			
			} else {
				em.merge(despesa);
			}
			
			ut.commit();
		
		} catch (Exception e) {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
			throw e;
		}

		lerDespesas();
		return null;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	protected void lerDespesas() throws Exception {
		Query q = em.createQuery("SELECT d FROM Despesa d ORDER BY d.data");
		List<Despesa> result = q.getResultList();
		this.despesasList.clear();
		this.despesasList.addAll(result);
	}
	
	public ListDataModel<Despesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(ListDataModel<Despesa> despesas) {
		this.despesas = despesas;
	}
}
